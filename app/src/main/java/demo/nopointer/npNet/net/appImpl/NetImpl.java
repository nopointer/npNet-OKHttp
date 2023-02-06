package demo.nopointer.npNet.net.appImpl;

import np.net_okhttp.core.NetReqAddedData;
import np.net_okhttp.core.base.BaseNetRequest;
import okhttp3.RequestBody;

final class NetImpl extends BaseNetRequest {


    /**
     * 发送post请求
     *
     * @param url         url完整路径
     * @param requestBody 请求体
     */
    public final void postRequest(String url, RequestBody requestBody, NetListener netListener, Class<?>... clazz) {
        postRequest(url, requestBody, netListener, clazz);
    }

    /**
     * 发送posy请求
     *
     * @param url         url完整路径
     * @param requestBody 请求体
     * @param addedData   额外数据，比如header 或者请求超时时间等自定义配置
     */
    public final void postRequest(String url, RequestBody requestBody, NetReqAddedData addedData, NetListener netListener, Class<?>... clazz) {
        sendPostRequest(url, requestBody, addedData, new NetParserImpl(netListener, 0, clazz).getCallBack());
    }

    /**
     * 发送posy请求
     *
     * @param url         url完整路径
     * @param requestBody 请求体
     * @param addedData   额外数据，比如header 或者请求超时时间等自定义配置
     */
    public final void postRequest(String url, RequestBody requestBody, NetReqAddedData addedData, NetListener netListener, int parserType, Class<?>... clazz) {
        sendPostRequest(url, requestBody, addedData, new NetParserImpl(netListener, parserType, clazz).getCallBack());
    }


    public void getRequest(String url, NetReqAddedData addedData, NetListener netListener, Class<?>... clazz) {
        getRequest(url, addedData, netListener, NetWorker.JSON_PARSER_TYPE_CYNet, clazz);
    }

    public void getRequest(String url, NetReqAddedData addedData, NetListener netListener, int parserType, Class<?>... clazz) {
        sendGetRequest(url, addedData, new NetParserImpl(netListener, parserType, clazz).getCallBack());
    }
}
