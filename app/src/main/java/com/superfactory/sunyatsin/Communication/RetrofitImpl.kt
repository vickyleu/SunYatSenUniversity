package com.superfactory.sunyatsin.Communication

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by vicky on 2018.01.23.
 *
 * @Author vicky
 * @Date 2018年01月23日  18:28:13
 * @ClassName 这里输入你的类名(或用途)
 */
interface RetrofitImpl {

    @FormUrlEncoded
    @POST("upcoming")
    fun getList(@Field("value") value: String): Call<ResponseBody>


    @GET("{path}")
    fun eraseBadge(@Path("path")path: String): Call<ResponseBody>

    @GET("user/login")
    fun login(@Query("username") username: String, @Query("password") password: String): Call<ResponseBody>

    @POST
    fun getMovieDetails(path: String, filters: Map<String, String>): Call<Any>

}