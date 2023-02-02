package demo.nopointer.npNet;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import demo.nopointer.R;
import demo.nopointer.npNet.net.reqPara.UserLogin;


public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserLogin userLogin =new UserLogin();
        userLogin.getParaMap();


//        NetManager.getInstance().login();

        findViewById(R.id.test_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




//                NetManager.getInstance().receiveTrsnsTask(new YCNetCallback<YCResp>() {
//                    @Override
//                    public void onSuccess(NpCall<YCResp> call, YCResp response) {
//                        NpNetLog.log("onSuccess" + response.toString());
//                    }
//
//                    @Override
//                    public void onSuccessWithJson(NpCall<YCResp> call, String jsonString) {
//                        NpNetLog.log("拿到原始json数据："+jsonString);
//                    }
//
//
//                });
//                NetManager.getInstance().login();
//                NetManager.getInstance().login();
//                NetManager.getInstance().login();
//                NetManager.getInstance().login();
//                NetManager.getInstance().login();
//                NetManager.getInstance().login();
//                NetManager.getInstance().login();
//                NetManager.getInstance().login();
//                NetManager.getInstance().login();
//                NetManager.getInstance().login();
//                NetManager.getInstance().login();
            }
        });

    }


}
