package com.hechuangwu.rxjavaretrofit.inter;


import com.hechuangwu.rxjavaretrofit.Module.ParamModule;
import com.hechuangwu.rxjavaretrofit.Module.RequestMoudle;

import java.util.ArrayList;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by cwh on 2018/12/15.
 * 功能:
 */
public interface RequestUrl {
//    http://p.3.cn/prices/mgets?skuIds=J_954081&type=1
    @GET()
    Observable<ResponseBody> getFileCall(@Url String url);

    @GET("prices/mgets")
    Call<ArrayList<ParamModule>> getCall(@Query("skuIds") String skuId, @Query("type") int type);

    @GET("prices/mgets")
    Call<ArrayList<ParamModule>> getParamCall(@QueryMap Map<String, String> map);

    @POST("prices/mgets")
    Call<ArrayList<ParamModule>> getPostCall(@Body RequestMoudle requestMoudle);
}
