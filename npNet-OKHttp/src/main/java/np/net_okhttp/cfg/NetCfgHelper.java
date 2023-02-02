package np.net_okhttp.cfg;

import java.util.HashSet;

/**
 * 配置文件
 */
public class NetCfgHelper {

    private NetCfgHelper() {
    }

    private static NetCfgHelper instance = new NetCfgHelper();

    public static NetCfgHelper getInstance() {
        return instance;
    }

    public HashSet<String> getHosts() {
        return hosts;
    }

    /**
     * 允许放行的host
     */
    private HashSet<String> hosts = new HashSet<>();


    public void addHost(String... host) {
        if (host != null) {
            for (String s : host) {
                hosts.add(s);
            }
        }
    }

    public void removeHost(String... host) {
        if (host != null) {
            for (String s : host) {
                hosts.remove(s);
            }
        }
    }

    public void clearHosts() {
        if (hosts != null) {
            hosts.clear();
        }
    }
}
