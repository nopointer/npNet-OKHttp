package np.net_okhttp.core.ycimpl.data;

import java.io.Serializable;

/**
 * Created by nopointer on 2018/8/7.
 */

public class YCResp implements Serializable {


    /**
     * errorCode : 2009
     * message : 验证码错误
     */

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
