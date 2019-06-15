package com.hechuangwu.rxjavaretrofit.Module;

/**
 * Created by cwh on 2018/12/14.
 * 功能:
 */
public class ProgressModule {
    private long currentBytes;
    private long contentLength;
    private boolean allOver;

    public long getCurrentBytes() {
        return currentBytes;
    }

    public void setCurrentBytes(long currentBytes) {
        this.currentBytes = currentBytes;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public boolean isAllOver() {
        return allOver;
    }

    public void setAllOver(boolean allOver) {
        this.allOver = allOver;
    }
}
