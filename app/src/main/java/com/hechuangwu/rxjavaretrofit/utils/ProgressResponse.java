package com.hechuangwu.rxjavaretrofit.utils;

import android.app.Activity;

import com.hechuangwu.rxjavaretrofit.inter.OnProgressListener;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by cwh on 2018/12/14.
 * 功能:
 */
public class ProgressResponse extends ResponseBody {
    private ResponseBody mResponseBody;
    private BufferedSource mBufferedSource;
    private OnProgressListener mOnProgressListener;
    private Activity mActivity;
    public ProgressResponse(Activity activity, ResponseBody responseBody, OnProgressListener onProgressListener){
        this.mResponseBody = responseBody;
        this.mOnProgressListener = onProgressListener;
        this.mActivity = activity;
    }
    @Override
    public MediaType contentType() {
        return mResponseBody.contentType();
    }

    @Override
    public long contentLength() {
        return mResponseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if(mBufferedSource==null){
            mBufferedSource = Okio.buffer( dealWithSource(mResponseBody.source()) );
        }
        return mBufferedSource;
    }

    private Source dealWithSource(BufferedSource source) {
        return new ForwardingSource( source ){
            long currentBytes;
            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long read = super.read( sink, byteCount );
                currentBytes += ((read==-1)?0:read);
                mActivity.runOnUiThread( new Runnable() {
                    @Override
                    public void run() {
                        mOnProgressListener.setOnProgressListener( currentBytes,contentLength(),currentBytes==contentLength() );
                    }
                } );
                return read;
            }
        };
    }



}
