package np.net_okhttp.core.abs;

public interface IResponseListener<T> {

    public void onCancel();

    public void onError(int code, String msg);

    public void onSuccess(T data);


    public void onComplete();


}
