package np.net_okhttp.core.abs;


import java.util.Map;

import np.net_okhttp.core.NetReqAddedData;
import np.net_okhttp.core.OkHttpCore;
import okhttp3.RequestBody;

/**
 * 封装饿请求基类
 */
public class BaseRequest {

//    protected void post(String url, IDataParser dataParser, FormBody formBody) {
//        OkHttpCore.postRequest(url, dataParser.getCallBack(), formBody);
//    }

//    protected void post(String url, IDataParser dataParser, RequestBody formBody) {
//        OkHttpCore.postRequest(url, dataParser.getCallBack(), formBody,null);
//    }

    protected void sendPostRequest(String url, IDataParser dataParser, RequestBody formBody, NetReqAddedData addedData) {
        OkHttpCore.asyncPostRequest(url, formBody, addedData, dataParser.getCallBack());
    }

    protected void sendGetRequest(String url, IDataParser dataParser) {
        sendGetRequest(url, dataParser, null);
    }

    protected void sendGetRequest(String url, IDataParser dataParser, Map<String, String> headers) {
//        OkHttpCore.getRequestSync(url, dataParser.getCallBack(), headers);
    }

}
