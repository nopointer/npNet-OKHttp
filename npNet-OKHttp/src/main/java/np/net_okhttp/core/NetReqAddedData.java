package np.net_okhttp.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 网络请求的附加数据
 * 比如里面配置了自定义超时时间，header等数据
 */
public class NetReqAddedData implements Serializable {

    public Map<String, Object> headers =new HashMap<>();

    public int timeOutBySeconds =60;

    public NetReqAddedData() {
    }

    public NetReqAddedData(int timeOutBySeconds) {
        this.timeOutBySeconds = timeOutBySeconds;
    }
}
