package com.superfactory.sunyatsin.Communication

import okhttp3.ResponseBody
import retrofit2.http.*
import rx.Observable

/**
 * Created by vicky on 2018.01.23.
 *
 * @Author vicky
 * @Date 2018年01月23日  18:28:13
 * @ClassName 这里输入你的类名(或用途)
 */
interface RetrofitImpl {

    @GET("{path}")
    fun eraseBadge(@Path("path") path: String): Observable<ResponseBody>

    @Headers("Content-Type: application/json",
            "Accept: application/json")//需要添加头
    @POST("login?__ajax")
    fun login(@Query("username") username: String, @Query("password") password: String,
              @Query("mobileLogin") mobileLogin: Boolean): Observable<ResponseBody>

    @Headers("Content-Type: application/json",
            "Accept: application/json")//需要添加头
    @GET("sys/user/infoData;JSESSIONID={JSESSIONID}?")
    fun loginAfter(@Path("JSESSIONID") JSESSIONID: String, @Query("__ajax=true&mobileLogin") mobileLogin: Boolean): Observable<ResponseBody>

}