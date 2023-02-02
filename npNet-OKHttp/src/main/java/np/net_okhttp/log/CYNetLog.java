package np.net_okhttp.log;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class CYNetLog {


    private static final String tag = "_cwwx_net_";

    private static Context mContext = null;

    //log日志目录
    private static String mLogDir = "netLog";

    //log日志文件名
    private static String mLogFileName = "cyNetLog";

    //日志文件的最大大小（以M为单位），最大不能超过5M
    private static float logFileMaxSizeByM = 5;

    //是否强制使用超过最大的记录，就是不设置上限，也就是1T的大小
    private static boolean isForceOutOfMaxSize = false;

    //是否显示当前日志的大小
    private static boolean enableShowCurrentLogFileSize = false;


    //是否显示调用路径和行号
    public static boolean allowShowCallPathAndLineNumber = true;

    //是否保存显示路径和行号
    public static boolean saveCallPathAndLineNumber = false;

    //是否允许打印日志，默认允许
    public static boolean allowLog = true;

    //是否允许保存,默认允许
    public static boolean allowSave = true;


    private static ExecutorService executorService = Executors.newSingleThreadExecutor();


    /**
     * log日志文件名
     */
    public static void setLogFileName(String mLogFileName) {
        CYNetLog.mLogFileName = mLogFileName;
    }

    public static void setEnableShowCurrentLogFileSize(boolean enableShowCurrentLogFileSize) {
        CYNetLog.enableShowCurrentLogFileSize = enableShowCurrentLogFileSize;
    }

    /**
     * 日志文件的最大大小（以M为单位），最大不能超过5M
     */
    public static void setLogFileMaxSizeByM(float logFileMaxSizeByM) {
        CYNetLog.logFileMaxSizeByM = logFileMaxSizeByM;
    }

    public static void setIsForceOutOfMaxSize(boolean isForceOutOfMaxSize) {
        CYNetLog.isForceOutOfMaxSize = isForceOutOfMaxSize;
    }


    public static void setSaveCallPathAndLineNumber(boolean saveCallPathAndLineNumber) {
        CYNetLog.saveCallPathAndLineNumber = saveCallPathAndLineNumber;
    }

    /**
     * 初始化日志管理
     *
     * @param logDir      日志的文件夹
     * @param logFileName 日志文件,不需要带后缀名称
     */
    public static void initLog(String logDir, String logFileName, Context context) {
        mContext = context;
        mLogDir = logDir;
        mLogFileName = logFileName;
        initDirAndFileName();
        Log.e("npLogTag", "初始化log管理器" + mLogDir + "/" + mLogFileName);

    }





    public static File getBleLogFile() {
        initDirAndFileName();
        File appDir = new File(getLogParentDir(), getFilePath());
        return appDir;
    }


    private CYNetLog() {
    }

    private static SimpleDateFormat smp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);


    /**
     * 打印日志
     *
     * @param content
     */
    public static void log(String content) {
        if (allowLog) {
            if (allowShowCallPathAndLineNumber) {
                StackTraceElement caller = getCallerStackTraceElement();
                content = "[" + getCallPathAndLineNumber(caller) + "]：" + content;
            }
            Log.d(tag, content);
        }
    }

    public static void logAndSave(String content) {
        String oldContent = content;
        if (allowShowCallPathAndLineNumber) {
            StackTraceElement caller = getCallerStackTraceElement();
            content = "[" + getCallPathAndLineNumber(caller) + "]：" + content;
        }
        if (allowLog) {
            Log.e(tag, content);
        }
        if (!allowSave) {
            return;
        }
        String dateTime = smp.format(new Date());
        try {
            if (saveCallPathAndLineNumber) {
                writeFile(dateTime + "  " + content);
            } else {
                writeFile(dateTime + "  " + oldContent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存日志
     *
     * @param content
     */
    public static void save(String content) {
        if (!allowSave) {
            return;
        }
        String dateTime = smp.format(new Date());
        if (saveCallPathAndLineNumber) {
            StackTraceElement caller = getCallerStackTraceElement();
            content = "[" + getCallPathAndLineNumber(caller) + "]：" + content;
        }
        try {
            writeFile(dateTime + "  " + content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //记录日志
    public synchronized static void writeFile(final String strLine) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // 首先创建文件夹
                File appDir = new File(getLogParentDir(), mLogDir);
                if (!appDir.exists()) {
                    appDir.mkdirs();
                }
                String fileName = mLogFileName + ".txt";
                File file = new File(appDir, fileName);
                if (file.exists()) {
                    if (enableShowCurrentLogFileSize) {
                        log("size:" + file.length());
                    }
                    if (file.length() > logFileMaxSizeByM * 1024 * 1024) {
                        clearLogFile();
                        writeFile(strLine);
                        return;
                    }
                }
                //追加文件写的内容
                try {
                    BufferedWriter fileOutputStream = new BufferedWriter(new FileWriter(file, true));
                    fileOutputStream.write(strLine);
                    fileOutputStream.newLine();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * 删除日志文件
     */
    public synchronized static void clearLogFile() {
        File file = new File(getLogParentDir(), getFilePath());
        if (file.exists()) {
            log("成功删除文件" + file.getAbsolutePath());
            file.delete();
        }
    }

    /**
     * 向src文件添加header
     *
     * @param content
     * @param srcPath
     * @throws Exception
     */
    private static void appendFileHeader(String content, String srcPath) throws Exception {
        RandomAccessFile src = new RandomAccessFile(srcPath, "rw");
        int srcLength = (int) src.length();
        byte[] buff = new byte[srcLength];
        src.read(buff, 0, srcLength);
        src.seek(0);
        byte[] header = content.getBytes("utf-8");
        src.write(header);
        src.seek(header.length);
        src.write(buff);
        src.close();
    }


    /**
     * 获取日志文件的路径
     *
     * @return
     */
    private static String getFilePath() {
        initDirAndFileName();
        return mLogDir + "/" + mLogFileName + ".txt";
    }

    /**
     * 初始化日志文件夹和名称
     */
    private static void initDirAndFileName() {
        if (TextUtils.isEmpty(mLogDir)) {
            mLogDir = "netLog";
        }
        if (TextUtils.isEmpty(mLogFileName)) {
            mLogFileName = "cyNetLog";
        }
        if (logFileMaxSizeByM <= 0) {
            logFileMaxSizeByM = 2;
        }

        if (!isForceOutOfMaxSize) {
            if (logFileMaxSizeByM >= 5) {
                logFileMaxSizeByM = 5;
            }
        } else {
            logFileMaxSizeByM = 1024 * 1024 * 1024;
        }
    }

    /**
     * 获取调用路径和行号
     *
     * @return
     */
    private static String getCallPathAndLineNumber(StackTraceElement caller) {
        String result = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        result = String.format(result, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        return result;
    }


    public static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }

    public static File getLogParentDir() {
        return mContext.getExternalFilesDir(null).getParentFile();
    }

}
