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

    /**
     * posy请求
     * 尽量不要使用这个函数，此函数是问了兼容某些情况下（如请求对象因为某些原因不能继承BasePara的时候）
     *
     * @param url  url
     * @param para
     * @param <T>
     */
    public <T> void postRequestByJson(String url, T para) {
        postRequestByJson(url, para, null);
    }

    /**
     * posy请求
     * 尽量不要使用这个函数，此函数是问了兼容某些情况下（如请求对象因为某些原因不能继承BasePara的时候）
     *
     * @param url  url
     * @param para
     * @param <T>
     */
    public <T> void postRequestByJson(String url, T para, NetReqAddedData addedData) {
        net.postRequest(url, getJsonBody(para), addedData);
    }

    public void postRequestByJson(String url, BasePara basePara) {
        postRequestByJson(url, basePara, null);
    }

    public void postRequestByJson(String url, BasePara basePara, NetReqAddedData addedData) {
        net.postRequest(url, getJsonBody(basePara), addedData);
    }


    public void postRequestByJsonStr(String url, String jsonString) {
        postRequestByJsonStr(url, jsonString, null);
    }

    public void postRequestByJsonStr(String url, String jsonString, NetReqAddedData addedData) {
        net.postRequest(url, getJsonBody(jsonString), addedData);
    }


    public void postRequestByForm(String url, BasePara basePara) {
        postRequestByForm(url, basePara, null);
    }

    public void postRequestByForm(String url, BasePara basePara, NetReqAddedData addedData) {
        net.postRequest(url, getFormBody(basePara), addedData);
    }

    public void getRequest(String url) {
        getRequest(url, null);
    }

    public void getRequest(String url, NetReqAddedData addedData) {
        net.getRequest(url, addedData);
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
