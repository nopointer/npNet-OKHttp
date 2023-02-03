package demo.nopointer.npNet.net.appImpl;

import java.io.IOException;

import np.net_okhttp.core.abs.BaseNetParser;
import np.net_okhttp.log.CYNetLog;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 网络解析实现
 */
class NetParserImpl extends BaseNetParser {

    @Override
    public void parser(Call call, Response response) throws IOException {
        String json = response.body().string();
        CYNetLog.log("call = " + call.request().toString());
        CYNetLog.log("response = " + response.toString());
        CYNetLog.log("result = " + json);
    }

    @Override
    public void onFailure(Call call, IOException e) {
        CYNetLog.log("result = " + e.getLocalizedMessage());
    }
}
