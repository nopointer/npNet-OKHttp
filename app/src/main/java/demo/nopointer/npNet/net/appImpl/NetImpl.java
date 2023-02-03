package demo.nopointer.npNet.net.appImpl;

import demo.nopointer.npNet.net.NetCfg;
import np.net_okhttp.core.NetReqAddedData;
import np.net_okhttp.core.abs.BaseNetRequest;
import okhttp3.RequestBody;

final class NetImpl extends BaseNetRequest {


    /**
     * 发送post请求
     *
     * @param url         url完整路径
     * @param requestBody 请求体
     */
    public final void postRequest(String url, RequestBody requestBody) {
        postRequest(url, requestBody, null);
    }

    /**
     * 发送posy请求
     *
     * @param url         url完整路径
     * @param requestBody 请求体
     * @param addedData   额外数据，比如header 或者请求超时时间等自定义配置
     */
    public final void postRequest(String url, RequestBody requestBody, NetReqAddedData addedData) {
        sendPostRequest(url, requestBody, addedData, new NetParserImpl().getCallBack());
    }


    public void getRequest(String url, NetReqAddedData addedData) {
        sendGetRequest(url, addedData, new NetParserImpl().getCallBack());
    }
}
