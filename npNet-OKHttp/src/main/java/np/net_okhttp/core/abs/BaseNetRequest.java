package np.net_okhttp.core.abs;


import java.util.Map;

import np.net_okhttp.core.NetReqAddedData;
import np.net_okhttp.core.OkHttpCore;
import okhttp3.Callback;
import okhttp3.RequestBody;

/**
 * 封装饿请求基类
 */
public class BaseNetRequest {

//    protected void post(String url, IDataParser dataParser, FormBody formBody) {
//        OkHttpCore.postRequest(url, dataParser.getCallBack(), formBody);
//    }

//    protected void post(String url, IDataParser dataParser, RequestBody formBody) {
//        OkHttpCore.postRequest(url, dataParser.getCallBack(), formBody,null);
//    }

    protected void sendPostRequest(String url, RequestBody formBody, NetReqAddedData addedData, Callback callback) {
        OkHttpCore.asyncPostRequest(url, formBody, addedData, callback);
    }

    protected void sendGetRequest(String url, NetReqAddedData addedData, Callback callback) {
        OkHttpCore.asyncGetRequest(url, addedData, callback);
    }

//    protected void sendGetRequest(String url, BaseNetParser dataParser, Map<String, String> headers) {
////        OkHttpCore.getRequestSync(url, dataParser.getCallBack(), headers);
//    }

}
