package com.superfactory.library.Communication

/**
 * Created by vicky on 2018.01.23.
 *
 * @Author vicky
 * @Date 2018年01月23日  17:29:38
 * @ClassName 这里输入你的类名(或用途)
 */
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by amitacharya on 12/12/17.
 */
interface IRetrofit {

    @FormUrlEncoded
    @POST("upcoming")
    fun testPostForm(@Field("api_key") api_key: String): Call<Any>

    @POST("upcoming")
    fun testPostJson(@Field("api_key") api_key: String): Call<Any>

    @GET("{path}")
    fun testGet(@Path("path") path: String, @QueryMap filters: Map<String, String>): Call<Any>

}