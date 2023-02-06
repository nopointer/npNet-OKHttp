package np.net_okhttp.core.base;

import java.io.File;

/**
 * 原驰下载器
 */
public class BaseDownloader {
    private BaseDownloader() {
    }

    private static BaseDownloader ycDownloader = new BaseDownloader();

    public static BaseDownloader getYcDownloader() {
        return ycDownloader;
    }


    public void download(String url, final File file, final OnDownloadListener onDownloadListener) {
//        OkHttpCore.getRequestSync(url, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                if (onDownloadListener != null) {
//                    onDownloadListener.onFailure(e);
//                }
//            }
//
//            @Override
//            public void onResponse(Call call, final Response response) throws IOException {
//                try {
//                    FileOutputStream outputStream = new FileOutputStream(file);
//                    byte[] buffer = new byte[1024 * 100];
//                    int len = -1;
//                    ResponseBody responseBody = response.body();
//                    InputStream inputStream = responseBody.byteStream();
//                    long totalLen = responseBody.contentLength();
//
//                    if (totalLen <= 0) {
//                        if (onDownloadListener != null) {
//                            onDownloadListener.onFailure(null);
//                        }
//                        return;
//                    }
//
//                    CYNetLog.logAndSave("contentLength = " + totalLen);
//                    long count = 0;
//                    while ((len = inputStream.read(buffer)) != -1) {
//                        outputStream.write(buffer, 0, len);
//                        count += len;
//                        if (onDownloadListener != null) {
//                            float tmp = (count * 1.0f) / totalLen;
//                            onDownloadListener.onProgress(tmp);
//                        }
//                    }
//                    if (onDownloadListener != null) {
//                        onDownloadListener.onSuccess(file);
//                    }
//                    outputStream.flush();
//                    outputStream.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    CYNetLog.logAndSave("下载出错异常" + e.getMessage());
//
//                    if (onDownloadListener != null) {
//                        onDownloadListener.onFailure(e);
//                    }
//                }
//            }
//        });
    }


    public interface OnDownloadListener {

        void onProgress(float intProgress);

        void onFailure(Exception e);

        void onSuccess(File file);

    }


}
