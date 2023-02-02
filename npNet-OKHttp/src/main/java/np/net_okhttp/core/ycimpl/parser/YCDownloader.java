package np.net_okhttp.core.ycimpl.parser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import np.net_okhttp.core.OkHttpCore;
import np.net_okhttp.log.CYNetLog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 原驰下载器
 */
public class YCDownloader {
    private YCDownloader() {
    }

    private static YCDownloader ycDownloader = new YCDownloader();

    public static YCDownloader getYcDownloader() {
        return ycDownloader;
    }


    public void download(String url, final File file, final OnDownloadListener onDownloadListener) {
        OkHttpCore.getRequestSync(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (onDownloadListener != null) {
                    onDownloadListener.onFailure(e);
                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                try {
                    FileOutputStream outputStream = new FileOutputStream(file);
                    byte[] buffer = new byte[1024 * 100];
                    int len = -1;
                    ResponseBody responseBody = response.body();
                    InputStream inputStream = responseBody.byteStream();
                    long totalLen = responseBody.contentLength();

                    if (totalLen <= 0) {
                        if (onDownloadListener != null) {
                            onDownloadListener.onFailure(null);
                        }
                        return;
                    }

                    CYNetLog.logAndSave("contentLength = " + totalLen);
                    long count = 0;
                    while ((len = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, len);
                        count += len;
                        if (onDownloadListener != null) {
                            float tmp = (count * 1.0f) / totalLen;
                            onDownloadListener.onProgress(tmp);
                        }
                    }
                    if (onDownloadListener != null) {
                        onDownloadListener.onSuccess(file);
                    }
                    outputStream.flush();
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    CYNetLog.logAndSave("下载出错异常" + e.getMessage());

                    if (onDownloadListener != null) {
                        onDownloadListener.onFailure(e);
                    }
                }
            }
        });
    }


    public interface OnDownloadListener {

        void onProgress(float intProgress);

        void onFailure(Exception e);

        void onSuccess(File file);

    }


}
