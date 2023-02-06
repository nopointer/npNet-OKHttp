package demo.nopointer.npNet.net;

import demo.nopointer.npNet.net.appImpl.NetListener;
import demo.nopointer.npNet.net.appImpl.NetWorker;
import demo.nopointer.npNet.net.jsonData.BaseData;
import demo.nopointer.npNet.net.jsonData.CyResp;
import demo.nopointer.npNet.net.jsonData.CyRespData;
import demo.nopointer.npNet.net.reqPara.FeedbackPara;
import demo.nopointer.npNet.net.reqPara.UserLogin;
import demo.nopointer.npNet.net.resp.DialPageData;
import np.net_okhttp.core.NetReqAddedData;

public class NetManager {
    private static NetManager netManager = new NetManager();

    public static NetManager getNetManager() {
        return netManager;
    }


    private NetManager() {
        netCore = new NetWorker();
    }

    private static NetWorker netCore;

    public void userLogin(UserLogin userLogin, NetListener<BaseData> netListener) {
        netCore.postRequestByJson(NetCfg.User.login, userLogin, netListener, NetWorker.JSON_PARSER_TYPE_BSX, BaseData.class);
    }

    public void feedback(FeedbackPara para, NetListener netListener) {
        netCore.postRequestByJson(NetCfg.User.feedback, para, netListener, NetWorker.JSON_PARSER_TYPE_CYNet, CyResp.class);
    }

    public void listDial(NetListener<CyRespData<DialPageData>> netListener) {
        NetReqAddedData netReqAddedData = new NetReqAddedData();
        netReqAddedData.headers.put("equipment-model", "FB117");
        netReqAddedData.headers.put("random", "ba303a733bbc221a3f37b1950786cfd4");
        netReqAddedData.headers.put("sign", "59f20a633abbf1663c5e4631a2d5fb51");
        netReqAddedData.headers.put("language", "zh_cn");
        netReqAddedData.headers.put("firmware-small-version", "1.01.08");
        netReqAddedData.headers.put("timestamp", 1675396627);
        netCore.getRequest(NetCfg.User.dialList, netReqAddedData, netListener, NetWorker.JSON_PARSER_TYPE_CYNet, CyRespData.class, DialPageData.class);
    }


}
