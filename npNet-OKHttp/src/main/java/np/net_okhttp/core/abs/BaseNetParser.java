package np.net_okhttp.core.abs;


import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 抽象的数据解析
 */
public abstract class BaseNetParser {

    public abstract void parser(Call call, Response response) throws IOException;

    public abstract void onFailure(Call call, IOException e);

    public Callback getCallBack() {
        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                BaseNetParser.this.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                parser(call, response);
            }
        };
        return callback;
    }

}
