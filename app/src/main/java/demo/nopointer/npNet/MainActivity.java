package demo.nopointer.npNet;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import demo.nopointer.R;
import demo.nopointer.npNet.net.NetManager;
import demo.nopointer.npNet.net.appImpl.NetListener;
import demo.nopointer.npNet.net.jsonData.BaseData;
import demo.nopointer.npNet.net.jsonData.CyRespData;
import demo.nopointer.npNet.net.reqPara.FeedbackPara;
import demo.nopointer.npNet.net.reqPara.UserLogin;
import demo.nopointer.npNet.net.resp.DialPageData;
import np.net_okhttp.log.CYNetLog;


public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserLogin userLogin = new UserLogin();
        userLogin.email = "123";
        userLogin.password = "123";
        userLogin.userName = "abac";

        findViewById(R.id.test_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NetManager.getNetManager().userLogin(userLogin, new NetListener<BaseData>() {
                    @Override
                    public void onSuccess(BaseData data) {
                        CYNetLog.log("data = " + data.data);
                    }

                    @Override
                    public void onError(int httpCode, String apiCode, String msg) {
                        super.onError(httpCode, apiCode, msg);
                        CYNetLog.log("httpCode = " + httpCode + " , apiCode = " + apiCode + " , msg = " + msg);
                    }
                });
            }
        });

        findViewById(R.id.btn_feedback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedbackPara feedbackPara = new FeedbackPara();
//                feedbackPara.
                NetManager.getNetManager().feedback(feedbackPara, new NetListener() {
                    @Override
                    public void onSuccess(Object data) {
                        CYNetLog.log("意见反馈成功");
                    }
                });
            }
        });


        findViewById(R.id.btn_getDial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetManager.getNetManager().listDial(new NetListener<CyRespData<DialPageData>>() {
                    @Override
                    public void onSuccess(CyRespData<DialPageData> data) {

                    }
                });
            }
        });

    }


}
