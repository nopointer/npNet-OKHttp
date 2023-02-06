package demo.nopointer.npNet.net;

import okhttp3.MediaType;

public class NetCfg {


    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    static String getDomain() {

//        return "http://szbsx.com:8081";

//        return "http://mlb2.app168.com/index.php/Home";
        return "http://dail.cynet2open.com:10090";
//        return "http://192.168.5.63:8090";
    }

    public static class User {
        //        public static String login = "/public/user/sendCode";
        public static String login = getDomain() + "/account/login";
        public static String feedback = getDomain() + "/api/opinion/submit.html";
        public static String dialList = getDomain() + "/api/clockDial/page.html?page=1&limit=999&typeId=1494191239649935362";
    }
}
