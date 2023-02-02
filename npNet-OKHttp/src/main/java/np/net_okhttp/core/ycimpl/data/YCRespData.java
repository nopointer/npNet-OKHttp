package np.net_okhttp.core.ycimpl.data;

/**
 * Created by nopointer on 2018/8/7.
 */

public class YCRespData<T> extends YCResp {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "YCRespData{" +
                "data=" + data +
                "} " + super.toString();
    }
}
