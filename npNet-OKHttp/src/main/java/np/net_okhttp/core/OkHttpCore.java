package np.net_okhttp.core;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import np.net_okhttp.cfg.NetCfgHelper;
import np.net_okhttp.core.safe.SslContextFactory;
import np.net_okhttp.log.CYNetLog;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 网络请求的细节
 */
public class OkHttpCore {

    /**
     * 发送异步post请求
     *
     * @param url         url
     * @param requestBody 请求数据
     * @param addedData   附加数据
     * @param callback    回调
     * @throws IOException
     */
    public static void asyncPostRequest(String url, RequestBody requestBody, NetReqAddedData addedData, Callback callback) {
        createClient(addedData).newCall(packRequestData(url, requestBody, addedData)).enqueue(callback);
    }

    /**
     * 发送同步post请求
     *
     * @param url      url
     * @param formBody 请求数据
     * @throws IOException
     */
    public static Response syncPostRequest(String url, FormBody formBody) throws IOException {
        return createClient().newCall(packRequestData(url, formBody)).execute();
    }

    /**
     * 发送同步get请求
     *
     * @param url url
     */
    public static Response syncGetRequest(String url) throws IOException {
        return createClient().newCall(packRequestData(url, null)).execute();
    }

    /**
     * 发送异步get请求
     *
     * @param url url
     */
    public static void asyncGetRequest(String url, NetReqAddedData addedData, Callback callback) {
        createClient(addedData).newCall(packRequestData(url, null, addedData)).enqueue(callback);
    }
//
//    public static void getRequestSync(String url, Callback callback, Map<String, String> headers) {
//        createClient().newCall(packRequestData(url, null, headers)).enqueue(callback);
//    }

    /**
     * 文件同步上传
     *
     * @param url         url
     * @param requestBody 参数
     * @return
     * @throws IOException
     */
    public static Response uploadFile(String url, RequestBody requestBody) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        return createClient().newCall(request).execute();
    }

    /**
     * 文件异步上传
     *
     * @param url         url
     * @param requestBody 参数
     * @param callback
     * @throws IOException
     */
    public static void uploadFile(String url, RequestBody requestBody, Callback callback) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        createClient().newCall(request).enqueue(callback);
    }


    //封装请求参数
    private static Request packRequestData(String url, RequestBody formBody) {
        return packRequestData(url, formBody, null);
    }

    //封装请求参数
    private static Request packRequestData(String url, RequestBody formBody, NetReqAddedData addedData) {
        Request.Builder builder = new Request.Builder().url(url);
        if (formBody != null) {
            builder.post(formBody);
        }
        CYNetLog.log("addedData : " + addedData);
        if (addedData != null) {
            Map<String, Object> headers = addedData.headers;
            if (headers != null && headers.size() > 0) {
                Iterator it = headers.keySet().iterator();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    Object value = headers.get(key);
                    builder.addHeader(key, value.toString());
                }
            }
        }
        return builder.build();
    }


    private static OkHttpClient createClient(NetReqAddedData reqAddedData) {
        int timeout = 0;
        if (reqAddedData != null) {
            timeout = reqAddedData.timeOutBySeconds;
        }
        OkHttpClient client = new OkHttpClient.Builder()
                .sslSocketFactory(SslContextFactory.getDefaultSSLSocketFactory())
                .callTimeout(timeout * 1000, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        HashSet<String> hosts = NetCfgHelper.getInstance().getHosts();
                        if (hosts == null || hosts.contains(hostname)) {
                            return true;
                        }
                        return false;
                    }
                }).build();
        return client;
    }

    private static OkHttpClient createClient() {
        return createClientWithTimeHout(60);
    }

    /**
     * 创建超时时间
     *
     * @param timeOutBySeconds
     * @return
     */
    private static OkHttpClient createClientWithTimeHout(int timeOutBySeconds) {
        return createClient(new NetReqAddedData(timeOutBySeconds));
    }


}
