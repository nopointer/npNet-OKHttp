package demo.nopointer.npNet.net.appImpl;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import demo.nopointer.npNet.net.NetCfg;
import demo.nopointer.npNet.net.para.BasePara;
import np.net_okhttp.core.NetReqAddedData;
import np.net_okhttp.log.CYNetLog;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class NetWorker {

    NetImpl net = new NetImpl();

    public static final int JSON_PARSER_TYPE_CYNet = 0;
    public static final int JSON_PARSER_TYPE_BSX = 1;
    public static final int JSON_PARSER_TYPE_YC = 2;

    /**
     * posy请求
     * 尽量不要使用这个函数，此函数是问了兼容某些情况下（如请求对象因为某些原因不能继承BasePara的时候）
     *
     * @param url  url
     * @param para
     * @param <T>
     */
    public <T> void postRequestByJson(String url, T para, NetListener netListener, Class<?>... clazz) {
        postRequestByJson(url, para, null, netListener, JSON_PARSER_TYPE_CYNet, clazz);
    }

    /**
     * posy请求
     * 尽量不要使用这个函数，此函数是问了兼容某些情况下（如请求对象因为某些原因不能继承BasePara的时候）
     *
     * @param url  url
     * @param para
     * @param <T>
     */
    public <T> void postRequestByJson(String url, T para, NetListener netListener, int parserType, Class<?>... clazz) {
        postRequestByJson(url, para, null, netListener, parserType, clazz);
    }

    /**
     * posy请求
     * 尽量不要使用这个函数，此函数是问了兼容某些情况下（如请求对象因为某些原因不能继承BasePara的时候）
     *
     * @param url  url
     * @param para
     * @param <T>
     */
    public <T> void postRequestByJson(String url, T para, NetReqAddedData addedData, NetListener netListener, int parserType, Class<?>... clazz) {
        net.postRequest(url, getJsonBody(para), addedData, netListener, parserType, clazz);
    }


    /**
     * @param url
     * @param basePara
     * @param netListener
     * @param clazz
     */
    public void postRequestByJson(String url, BasePara basePara, NetListener netListener, Class<?>... clazz) {
        postRequestByJson(url, basePara, null, netListener, clazz);
    }

    /**
     * @param url
     * @param basePara
     * @param netListener
     * @param clazz
     */
    public void postRequestByJson(String url, BasePara basePara, NetListener netListener, int parserType, Class<?>... clazz) {
        postRequestByJson(url, basePara, null, netListener, parserType, clazz);
    }


    /**
     * @param url
     * @param basePara
     * @param addedData
     * @param netListener
     * @param clazz
     */
    public void postRequestByJson(String url, BasePara basePara, NetReqAddedData addedData, NetListener netListener, Class<?>... clazz) {
        postRequestByJson(url, basePara, addedData, netListener, 0, clazz);
    }

    /**
     * @param url
     * @param basePara
     * @param addedData
     * @param netListener
     * @param parserType
     * @param clazz
     */
    public void postRequestByJson(String url, BasePara basePara, NetReqAddedData addedData, NetListener netListener, int parserType, Class<?>... clazz) {
        net.postRequest(url, getJsonBody(basePara), addedData, netListener, parserType, clazz);
    }


    public void postRequestByJsonStr(String url, String jsonString, NetListener netListener, Class<?>... clazz) {
        postRequestByJsonStr(url, jsonString, null, netListener, clazz);
    }

    public void postRequestByJsonStr(String url, String jsonString, NetReqAddedData addedData, NetListener netListener, Class<?>... clazz) {
        net.postRequest(url, getJsonBody(jsonString), addedData, netListener, clazz);
    }


    public void postRequestByForm(String url, BasePara basePara, NetListener netListener, Class<?>... clazz) {
        postRequestByForm(url, basePara, null, netListener, clazz);
    }

    public void postRequestByForm(String url, BasePara basePara, NetReqAddedData addedData, NetListener netListener, Class<?>... clazz) {
        net.postRequest(url, getFormBody(basePara), addedData, netListener, clazz);
    }

    public void getRequest(String url, NetListener netListener, Class<?>... clazz) {
        getRequest(url, null, netListener, clazz);
    }

    public void getRequest(String url, NetReqAddedData addedData, NetListener netListener, Class<?>... clazz) {
        getRequest(url, addedData, netListener, JSON_PARSER_TYPE_CYNet, clazz);
    }

    public void getRequest(String url, NetReqAddedData addedData, NetListener netListener, int parserType, Class<?>... clazz) {
        net.getRequest(url, addedData, netListener, parserType, clazz);
    }

    ////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////

    //封装请求参数

    ////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////

    String getParaByJson(Object object) {
        String json = new Gson().toJson(object);
        return json;
    }

    HashMap<String, Object> getParaMap(Object object) {
        String json = getParaByJson(object);
        HashMap<String, Object> hashMap = JSON.parseObject(json, HashMap.class);
        return hashMap;
    }


    RequestBody getFormBody(Object object) {
        MultipartBody.Builder requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
//
//        Log.e("net", "filePath = " + filePath);
////        Log.e("net", "fileName = " + fileName);
//
////        if (!TextUtils.isEmpty(filePath)) {
////            requestBody.addFormDataPart("file", fileName,
////                    RequestBody.create(MediaType.parse("multipart/form-data"), new File(filePath)));
////        }
////
        Map<String, Object> kvs = getParaMap(object);
        CYNetLog.log("打印from请求参数 : " + kvs);
        if (kvs != null) {
            for (Map.Entry<String, Object> entry : kvs.entrySet()) {
                if (entry.getKey() != null && !TextUtils.isEmpty(entry.getKey()) && entry.getValue() != null) {
                    requestBody.addFormDataPart(entry.getKey(), entry.getValue().toString());
                }
            }
        }
////
////        requestBody.addFormDataPart("flg", BuildConfig.BUILD_TYPE);
        return requestBody.build();
    }

    RequestBody getJsonBody(Object object) {
        String json = getParaByJson(object);
        CYNetLog.log("打印json请求参数 : " + json);
        RequestBody requestBody = RequestBody.create(NetCfg.JSON, json);
        return requestBody;
    }


    RequestBody getJsonBody(String jsonString) {
        CYNetLog.log("打印json请求参数 : " + jsonString);
        RequestBody requestBody = RequestBody.create(NetCfg.JSON, jsonString);
        return requestBody;
    }
}
