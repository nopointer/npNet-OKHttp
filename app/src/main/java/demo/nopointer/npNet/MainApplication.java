package demo.nopointer.npNet;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.support.multidex.MultiDex;


public class MainApplication extends Application {



    public static MainApplication mainApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mainApplication = this;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static MainApplication getMainApplication() {
        return mainApplication;
    }

}
