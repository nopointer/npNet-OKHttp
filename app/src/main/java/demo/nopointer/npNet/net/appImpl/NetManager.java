package demo.nopointer.npNet.net.appImpl;

import demo.nopointer.npNet.net.NetCfg;
import demo.nopointer.npNet.net.reqPara.UserLogin;
import np.net_okhttp.core.ycimpl.parser.YCErrCodeParser;

public class NetManager {
    private static NetManager netManager = new NetManager();

    public static NetManager getNetManager() {
        return netManager;
    }

    private YCErrCodeParser ycErrCodeParser = null;

    private NetManager() {
    }

    NetImpl net =new NetImpl();

    public void userLogin(UserLogin userLogin){
        net.postReq(NetCfg.User.login);
    }






}
