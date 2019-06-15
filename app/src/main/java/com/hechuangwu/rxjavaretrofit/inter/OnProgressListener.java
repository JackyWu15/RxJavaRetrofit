package com.hechuangwu.rxjavaretrofit.inter;

/**
 * Created by cwh on 2018/12/14.
 * 功能:
 */
public interface OnProgressListener {
    void setOnProgressListener(long currentBytes, long contentLength, boolean done);
}
