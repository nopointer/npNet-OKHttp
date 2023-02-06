package demo.nopointer.npNet.net.appImpl;

import np.net_okhttp.core.base.BaseResponseListener;

public abstract class NetListenerImpl<T> implements BaseResponseListener<T> {

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(int httpCode, String apiCode, String msg) {

    }

    public void onResponseWithJson(String jsonString) {

    }


    @Override
    public void onComplete() {

    }
}
