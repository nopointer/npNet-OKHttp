package np.net_okhttp.core.ycimpl.response;


import np.net_okhttp.core.abs.IResponseListener;
import np.net_okhttp.core.ycimpl.parser.YCErrCodeParser;
import np.net_okhttp.log.CYNetLog;

/**
 * Created by nopointer on 2018/8/4.
 */

public abstract class YCResponseListener<T> implements IResponseListener<T> {


    private YCErrCodeParser ycErrCodeParser = null;

    public void cfgYCErrCodeParser(YCErrCodeParser ycErrCodeParser) {
        this.ycErrCodeParser = ycErrCodeParser;
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(int code, String msg) {
        if (ycErrCodeParser != null) {
            ycErrCodeParser.parser(code, msg);
        } else {
            CYNetLog.log("没有状态码解析器");
        }
    }


    @Override
    public void onComplete() {
        if (ycErrCodeParser != null) {
            ycErrCodeParser.handComplete();
        } else {
            CYNetLog.log("没有状态码解析器");
        }
    }

    /**
     * 返回原始json数据，最原始的数据 不处理错误码
     *
     * @param jsonStr
     */
    public void onResultWithJson(String jsonStr) {

    }

}
