package demo.nopointer.npNet.net.resp;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * 更多表盘
 */
public class DialEntity implements Serializable {

    private String id;
    private String name;
    private String code;
    private String cover;
    private String total;
    private String downNum;
    private boolean isCustomDial =false;

    //扩展用的，该字段不在接口返回数据里面
    private float progress;
    //表盘状态0默认 1下载中，2安装中
    private int dialState =0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDownNum() {
        return TextUtils.isEmpty(downNum) ? "0" : downNum;
    }

    public void setDownNum(String downNum) {
        this.downNum = downNum;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public int getDialState() {
        return dialState;
    }

    public void setDialState(int dialState) {
        this.dialState = dialState;
    }

    public boolean isCustomDial() {
        return isCustomDial;
    }

    public void setCustomDial(boolean customDial) {
        isCustomDial = customDial;
    }
}
