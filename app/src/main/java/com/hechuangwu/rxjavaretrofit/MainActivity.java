package com.hechuangwu.rxjavaretrofit;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.UserHandle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.hechuangwu.rxjavaretrofit.inter.OnProgressListener;
import com.hechuangwu.rxjavaretrofit.inter.RequestUrl;
import com.hechuangwu.rxjavaretrofit.utils.ProgressResponse;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity  {
    private static String baseUrl = "https://imtt.dd.qq.com/";
    private static String downLoadUrl = baseUrl+"16891/075159DD08CC34C39906B058F2BE733C.apk?fsname=com.mojiebusiness.mobuz_2.6.6_45.apk&csr=1bbd";
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse( "application/octet-stream; charset=utf-8" );
    private ProgressBar mPb_bar;
    private TextView mTv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        mTv_content = findViewById( R.id.tv_content );
        mPb_bar = findViewById( R.id.pb_bar );

        findViewById( R.id.bt_click ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PermissionsUtil.hasPermission( MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE )) {
                    click();
                } else {
                    PermissionsUtil.requestPermission( MainActivity.this, new PermissionListener() {
                        @Override
                        public void permissionGranted( String[] permissions) {
                            click();
                        }

                        @Override
                        public void permissionDenied(String[] permissions) {
                        }
                    }, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE} );
                }
            }
        } );
    }

    private void click(){
        downLoadApk( MainActivity.this, new OnProgressListener() {
            @Override
            public void setOnProgressListener(long currentBytes, long contentLength, boolean done) {
                int currentProgress = (int) (currentBytes * 100 / contentLength);
                mPb_bar.setProgress( currentProgress );
                mTv_content.setText( currentProgress + "%" );
            }
        }, new Observer<InputStream>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(InputStream inputStream) {
                writeFile( inputStream );
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        } );
    }

    private void downLoadApk(final Activity activity, final OnProgressListener onProgressListener, Observer<InputStream> observer) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout( 10000, TimeUnit.MILLISECONDS )
                .readTimeout( 10000, TimeUnit.MILLISECONDS )
                .writeTimeout( 10000, TimeUnit.MILLISECONDS )
                .retryOnConnectionFailure( true )
                .addInterceptor( new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response response = chain.proceed( chain.request() );
                        return response.newBuilder().body( new ProgressResponse( activity, response.body(), onProgressListener ) ).build();
                    }
                } ).build();


        new Retrofit.Builder()
                .baseUrl( baseUrl )
                .client( okHttpClient )
                .addCallAdapterFactory( RxJava2CallAdapterFactory.create() )
                .build()
                .create( RequestUrl.class ).getFileCall( downLoadUrl )
                .subscribeOn( Schedulers.io() )
                .unsubscribeOn( Schedulers.io() )
                .map( new Function<ResponseBody, InputStream>() {
                    @Override
                    public InputStream apply(ResponseBody responseBody) throws Exception {
                        return responseBody.byteStream();
                    }
                } )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribe( observer );

    }

    private void writeFile(InputStream inputStream) {
        final String filePath = Environment.getExternalStorageDirectory() + File.separator + "yqs.apk";
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            bufferedOutputStream = new BufferedOutputStream( new FileOutputStream( filePath ) );
            bufferedInputStream = new BufferedInputStream( inputStream );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] bytes = new byte[1024];
        int len;
        try {
            while ((len = bufferedInputStream.read( bytes )) != -1) {
                bufferedOutputStream.write( bytes, 0, len );
            }
            bufferedInputStream.close();
            bufferedOutputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public synchronized ComponentName startForegroundServiceAsUser(Intent service, UserHandle user) {
        return null;
    }
}
