package demo.nopointer.npNet.net;

import demo.nopointer.npNet.net.appImpl.NetWorker;
import demo.nopointer.npNet.net.reqPara.FeedbackPara;
import demo.nopointer.npNet.net.reqPara.UserLogin;
import np.net_okhttp.core.NetReqAddedData;
import np.net_okhttp.core.ycimpl.parser.YCErrCodeParser;

public class NetManager {
    private static NetManager netManager = new NetManager();

    public static NetManager getNetManager() {
        return netManager;
    }

    private YCErrCodeParser ycErrCodeParser = null;

    private NetManager() {
        netCore = new NetWorker();
    }

    private static NetWorker netCore;

    public void userLogin(UserLogin userLogin) {
        netCore.postRequestByJson(NetCfg.User.login, userLogin);
    }


    public void feedback(FeedbackPara para) {
        netCore.postRequestByJson(NetCfg.User.feedback, para);
    }

    public void listDial() {
        NetReqAddedData netReqAddedData = new NetReqAddedData();
        netReqAddedData.headers.put("equipment-model", "FB117");
        netReqAddedData.headers.put("random", "ba303a733bbc221a3f37b1950786cfd4");
        netReqAddedData.headers.put("sign", "59f20a633abbf1663c5e4631a2d5fb51");
        netReqAddedData.headers.put("language", "zh_cn");
        netReqAddedData.headers.put("firmware-small-version", "1.01.08");
        netReqAddedData.headers.put("timestamp", 1675396627);

        netCore.getRequest(NetCfg.User.dialList, netReqAddedData);
    }


}
