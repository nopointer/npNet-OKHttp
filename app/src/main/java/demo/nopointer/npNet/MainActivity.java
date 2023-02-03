package demo.nopointer.npNet;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import demo.nopointer.R;
import demo.nopointer.npNet.net.NetManager;
import demo.nopointer.npNet.net.reqPara.FeedbackPara;
import demo.nopointer.npNet.net.reqPara.UserLogin;


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

                NetManager.getNetManager().userLogin(userLogin);

            }
        });

        findViewById(R.id.btn_feedback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedbackPara feedbackPara =new FeedbackPara();
//                feedbackPara.
                NetManager.getNetManager().feedback(feedbackPara);
            }
        });


        findViewById(R.id.btn_getDial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetManager.getNetManager().listDial();
            }
        });

    }


}
