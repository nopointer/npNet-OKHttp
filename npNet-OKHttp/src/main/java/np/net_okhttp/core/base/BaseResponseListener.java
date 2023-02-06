package np.net_okhttp.core.base;

public interface BaseResponseListener<T> {

    public void onCancel();

    public void onError(int HttpCode, String apiCode, String msg);

    public void onSuccess(T data);

    public void onComplete();


}
