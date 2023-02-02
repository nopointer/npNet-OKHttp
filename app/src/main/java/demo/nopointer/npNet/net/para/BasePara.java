package demo.nopointer.npNet.net.para;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.io.File;
import java.util.Map;
import java.util.Set;

import np.net_okhttp.log.CYNetLog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class BasePara {

    public void getJson() {

    }

    public String filePath = null;


    public Map<String, Object> getParaMap() {
        String json = JSON.toJSONString(this);
        CYNetLog.log("json = " + json);
        return null;
    }


    public RequestBody getFormBody() {


//        Set<Map.Entry<String, String>> kvs = paraMap.entrySet();

        MultipartBody.Builder requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        Log.e("net", "filePath = " + filePath);
//        Log.e("net", "fileName = " + fileName);

//        if (!TextUtils.isEmpty(filePath)) {
//            requestBody.addFormDataPart("file", fileName,
//                    RequestBody.create(MediaType.parse("multipart/form-data"), new File(filePath)));
//        }
//
//        for (Map.Entry<String, String> entry : kvs) {
//            if (entry.getKey() != null && !TextUtils.isEmpty(entry.getKey()) && !TextUtils.isEmpty(entry.getValue())) {
//                requestBody.addFormDataPart(entry.getKey(), entry.getValue());
//            }
//        }
//
//        requestBody.addFormDataPart("flg", BuildConfig.BUILD_TYPE);

        return requestBody.build();
    }


}
