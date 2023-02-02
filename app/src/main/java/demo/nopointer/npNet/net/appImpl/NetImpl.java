package demo.nopointer.npNet.net.appImpl;

import demo.nopointer.npNet.net.NetCfg;
import np.net_okhttp.core.abs.BaseRequest;

public class NetImpl extends BaseRequest {


    public void postReq(String urlPart) {
        postReqByAllUrl(NetCfg.getDomain() + urlPart);
    }


    public void postReqByAllUrl(String urlAllPath) {
//        sendPostRequest(urlAllPath);
    }


}
