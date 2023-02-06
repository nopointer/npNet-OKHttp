package demo.nopointer.npNet.net.appImpl;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.ParameterizedTypeImpl;

import java.io.IOException;
import java.lang.reflect.Type;

import np.net_okhttp.core.base.BaseNetParser;
import np.net_okhttp.log.CYNetLog;
import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.Response;

/**
 * 网络解析实现
 */
class NetParserImpl extends BaseNetParser {


    private Class<?>[] clazz;
    private NetListenerImpl netListener = null;
    private int parserType = 0;//0默认数据类型

    public NetParserImpl(NetListenerImpl netListener, Class<?>... clazz) {
        this(netListener, 0, clazz);
    }

    public NetParserImpl(NetListenerImpl netListener, int parserType, Class<?>... clazz) {
        this.clazz = clazz;
        this.netListener = netListener;
        this.parserType = parserType;
    }

    @Override
    public void parser(Call call, Response response) throws IOException {
        handResponse(call, response, netListener, parserType, clazz);
    }

    @Override
    public void onFailure(Call call, IOException e) {
        handFailure(call, e);
    }


    private static synchronized void handResponse(Call call, Response response, NetListenerImpl netListener, int parserType, Class<?>... clazz) throws IOException {
        CYNetLog.log("==============handResponse=====================");

        int netCode = response.code();
        String string = response.body().string();
        CYNetLog.log("call = " + call.request().toString());
        CYNetLog.log("response = " + response.toString());
        CYNetLog.log("string = " + string);

        Headers headers = call.request().headers();
        if (headers != null && headers.size() > 0 && headers.names().size() > 0) {
            CYNetLog.log("" + headers.toString());
        }
        if (netCode == 200) {
            if (!TextUtils.isEmpty(string)) {
                //先把json的返回回去
                if (netListener != null) {
                    netListener.onResponseWithJson(string);
                }
                if (clazz != null && clazz.length > 0) {
                    jsonParser(call, string, netListener, parserType, clazz);
                } else {
                    netListener.onSuccess(string);
                }
            }
        } else {
            if (netListener != null) {
                netListener.onError(netCode, "-1", "app request error");
            }
        }
        if (netListener != null) {
            netListener.onComplete();
        }
    }


    /**
     * Json解析 最外层的包包装 需要根据具体的api接口去解析，这里要根据实际情况来
     *
     * @param call
     * @param jsonStr
     */
    private static void jsonParserWithBsx(Call call, String jsonStr, NetListenerImpl netListener, Class<?>... clazz) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            //如果包含这2个字段 就是yc的标准json格式
            if (jsonObject.containsKey("success")) {
                boolean success = jsonObject.getBoolean("success");
                String message = jsonObject.getString("msg");
                //请求数据成功
                if (success) {
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
                    if (netListener != null) {
                        Object object = JSON.parseObject(jsonStr, beforeType);
                        CYNetLog.log(JSON.toJSONString(object));
                        netListener.onSuccess(object);
                    }
                } else {
                    int status = jsonObject.getInteger("status");
                    if (netListener != null) {
                        netListener.onError(200, status + "", message);
                    }
                }
            } else {
                CYNetLog.log(call.request().toString() + "非标准数据json格式，请从 onResponseWithJson() 方法里面获取回调数据！！！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (netListener != null && e != null && !TextUtils.isEmpty(e.getMessage())) {
                netListener.onError(200, "NULL", "\n" + e.getMessage());
            }
        }

    }


    /**
     * Json解析 最外层的包包装 需要根据具体的api接口去解析，这里要根据实际情况来
     *
     * @param call
     * @param jsonStr
     */
    private static void jsonParserWithCyNet(Call call, String jsonStr, NetListenerImpl netListener, Class<?>... clazz) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            //如果包含这2个字段 就是yc的标准json格式
            if (jsonObject.containsKey("code") && (jsonObject.containsKey("msg"))) {
                int code = jsonObject.getInteger("code");
                String message = jsonObject.getString("msg");
                //请求数据成功
                if (code == 200) {
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
                    if (netListener != null) {
                        Object object = JSON.parseObject(jsonStr, beforeType);
                        CYNetLog.log(JSON.toJSONString(object));
                        netListener.onSuccess(object);
                    }
                } else {
                    if (netListener != null) {
                        netListener.onError(200, code + "", message);
                    }
                }
            } else {
                CYNetLog.log(call.request().toString() + "非标准数据json格式，请从 onResponseWithJson() 方法里面获取回调数据！！！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (netListener != null && e != null && !TextUtils.isEmpty(e.getMessage())) {
                netListener.onError(200, "NULL", "\n" + e.getMessage());
            }
        }

    }


    /**
     * Json解析 最外层的包包装 需要根据具体的api接口去解析，这里要根据实际情况来
     *
     * @param call
     * @param jsonStr
     */
    private static void jsonParserWithYC(Call call, String jsonStr, NetListenerImpl netListener, Class<?>... clazz) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            //如果包含这2个字段 就是yc的标准json格式
            if (jsonObject.containsKey("success") && (jsonObject.containsKey("msg"))) {
                int errorCode = jsonObject.getInteger("success");
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
                    if (netListener != null) {
                        Object object = JSON.parseObject(jsonStr, beforeType);
                        CYNetLog.log(JSON.toJSONString(object));
                        netListener.onSuccess(object);
                    }
                } else {
                    if (netListener != null) {
                        netListener.onError(200, errorCode + "", message);
                    }
                }
            } else {
                CYNetLog.log(call.request().toString() + "非标准数据json格式，请从 onResponseWithJson() 方法里面获取回调数据！！！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (netListener != null && e != null && !TextUtils.isEmpty(e.getMessage())) {
                netListener.onError(200, "NULL", "\n" + e.getMessage());
            }
        }

    }


    /**
     * Json解析 最外层的包包装 需要根据具体的api接口去解析，这里要根据实际情况来
     *
     * @param call
     * @param jsonStr
     */
    private static void jsonParser(Call call, String jsonStr, NetListenerImpl netListener, int parserType, Class<?>... clazz) {
        CYNetLog.log("解析类型 = " + parserType);
        switch (parserType) {
            case 0:
                jsonParserWithCyNet(call, jsonStr, netListener, clazz);
                break;
            case 1:
                jsonParserWithBsx(call, jsonStr, netListener, clazz);
                break;
            case 2:
                jsonParserWithYC(call, jsonStr, netListener, clazz);
                break;
        }
    }

    private void handFailure(Call call, IOException e) {
        CYNetLog.log("==============handFailure=====================");
        CYNetLog.log("result = " + e.getLocalizedMessage());
        if (netListener != null) {
            netListener.onComplete();
        }
    }
}
