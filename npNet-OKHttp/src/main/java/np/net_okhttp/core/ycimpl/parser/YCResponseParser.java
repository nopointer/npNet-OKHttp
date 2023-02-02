package np.net_okhttp.core.ycimpl.parser;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.ParameterizedTypeImpl;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;

import np.net_okhttp.core.abs.IDataParser;
import np.net_okhttp.core.ycimpl.response.YCResponseListener;
import np.net_okhttp.log.CYNetLog;
import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.Response;


/**
 * Created by nopointer on 2018/8/4.
 */

public class YCResponseParser extends IDataParser {

    private Class<?>[] clazz;
    private YCResponseListener ycResponseListener = null;

    public YCResponseParser(YCResponseListener ycResponseListener, Class<?>... clazz) {
        this.clazz = clazz;
        this.ycResponseListener = ycResponseListener;
    }

    @Override
    public void parser(Call call, Response response) throws IOException {
        handResponse(call, response);
    }


    @Override
    public void onFailure(Call call, IOException e) {
        CYNetLog.log("网络错误 = " + e.getMessage());
        if (e instanceof SocketTimeoutException) {
            CYNetLog.log("网络超时了吧");
            if (ycResponseListener != null) {
//                ycResponseListener.onError(CODE_TIMEOUT, "网络请求超时");
            }
        } else {
            CYNetLog.log("网络不可用的状态吧");
            if (ycResponseListener != null) {
//                ycResponseListener.onError(NET_EXCEPTION, "网络异常");
            }
        }
    }


    private synchronized void handResponse(Call call, Response response) throws IOException {
        int code = response.code();
        String string = response.body().string();

        CYNetLog.log("\n\n                                               ");
        CYNetLog.save("\n\n                                               ");

//        CYNetLog.logAndSave("请求     " + call.request().headers());
        CYNetLog.logAndSave("" + response.toString());
        Headers headers = call.request().headers();
        if (headers != null && headers.size() > 0 && headers.names().size() > 0) {
            CYNetLog.logAndSave("" + headers.toString());
        }
        CYNetLog.log("数据     " + string);

        if (code == 200) {
            if (!TextUtils.isEmpty(string)) {
                //先把json的返回回去
                if (ycResponseListener != null) {
                    ycResponseListener.onResultWithJson(string);
                }
                if (clazz != null && clazz.length > 0) {
                    withResponse(call, string);
                }
            }
        } else if (code < 500 && code >= 400) {
            if (ycResponseListener != null) {
                ycResponseListener.onError(code, "app request error");
            }
        } else if (code < 600 && code >= 500) {
            if (ycResponseListener != null) {
                ycResponseListener.onError(code, "remote service inner error");
            }
        }
        if (ycResponseListener != null) {
            ycResponseListener.onComplete();
        }
    }


    private void withResponse(Call call, String jsonStr) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            //如果包含这2个字段 就是yc的标准json格式
            if (jsonObject.containsKey("code") && (jsonObject.containsKey("msg"))) {
                int errorCode = jsonObject.getInteger("code");
                String message = jsonObject.getString("msg");
                //请求数据成功
                if (errorCode == 200) {
                    //解析带有泛型的数据
                    ParameterizedTypeImpl beforeType = null;
                    if (clazz.length > 1) {
                        //支持多级泛型的解析
                        for (int i = clazz.length - 1; i > 0; i--) {
                            beforeType = new ParameterizedTypeImpl(new Type[]{beforeType == null ? clazz[i] : beforeType}, null, clazz[i - 1]);
                        }
                    } else {
                        beforeType = new ParameterizedTypeImpl(new Type[1], null, clazz[0]);
                    }
                    if (ycResponseListener != null) {
                        Object object = JSON.parseObject(jsonStr, beforeType);
                        CYNetLog.logAndSave(JSON.toJSONString(object));
                        ycResponseListener.onSuccess(object);
                    }
                } else {
                    if (ycResponseListener != null) {
                        ycResponseListener.onError(errorCode, message);
                    }
                }
            } else {
                CYNetLog.logAndSave(call.request().toString() + "非 yc的标准数据json格式，请从onSuccessWithJson()方法里面获取回调数据！！！");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            if (ycResponseListener != null) {
                CYNetLog.logAndSave("json解析异常");
//                ycResponseListener.onError(CODE_JSON_RESOVE_EXCEPTION, "json解析异常");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
//            ycResponseListener.onError(CODE_NET_UNKNOWN_EXCEPTION, "Unknown exception");
            if (ycResponseListener != null && e != null && !TextUtils.isEmpty(e.getMessage())) {
//                ycResponseListener.onError(CODE_NET_UNKNOWN_EXCEPTION, "\n" + e.getMessage());
            }
        }

    }


}
