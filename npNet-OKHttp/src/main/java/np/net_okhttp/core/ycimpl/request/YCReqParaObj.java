package np.net_okhttp.core.ycimpl.request;

import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import npNet.nopointer.BuildConfig;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by nopointer on 2018/8/4.
 */

public class YCReqParaObj {

    private HashMap<String, String> paraMap = null;

    private YCReqParaObj() {
        if (paraMap == null) {
            paraMap = new HashMap<>();
        }
    }

    private YCReqParaObj(String key, String val) {
        this();
        paraMap.put(key, val);
    }

    private String filePath;
    private String fileName;

    public YCReqParaObj uploadFile(String filePath, String fileName) {
        this.fileName = fileName;
        this.filePath = filePath;
        return this;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public static YCReqParaObj create() {
        return new YCReqParaObj();
    }

    public static YCReqParaObj create(String key, String val) {
        return new YCReqParaObj(key, val);
    }


    public YCReqParaObj addPara(String key, String val) {
        paraMap.put(key, val);
        return this;
    }

    public YCReqParaObj addPara(YCReqParaObj ycReqParaObj) {
        if (ycReqParaObj != null && ycReqParaObj.paraMap != null) {
            Set<Map.Entry<String, String>> kvs = ycReqParaObj.paraMap.entrySet();
            for (Map.Entry<String, String> entry : kvs) {
                if (entry.getKey() != null && !TextUtils.isEmpty(entry.getKey())) {
                    paraMap.put(entry.getKey(), entry.getValue());
                }
            }
        }
        return this;
    }


    public void clear() {
        if (paraMap != null && paraMap.size() > 0) {
            paraMap.clear();
        }
    }

    public HashMap<String, String> getParaMap() {
        return paraMap;
    }

    public RequestBody getFormBody() {
        Set<Map.Entry<String, String>> kvs = paraMap.entrySet();

        MultipartBody.Builder requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        Log.e("net", "filePath = " + filePath);
        Log.e("net", "fileName = " + fileName);

        if (!TextUtils.isEmpty(filePath)) {
            requestBody.addFormDataPart("file", fileName,
                    RequestBody.create(MediaType.parse("multipart/form-data"), new File(filePath)));
        }

        for (Map.Entry<String, String> entry : kvs) {
            if (entry.getKey() != null && !TextUtils.isEmpty(entry.getKey()) && !TextUtils.isEmpty(entry.getValue())) {
                requestBody.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }

        requestBody.addFormDataPart("flg", BuildConfig.BUILD_TYPE);

        return requestBody.build();
    }

}
