package com.superfactory.sunyatsin.Communication

import okhttp3.ResponseBody
import retrofit2.Call
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

    @FormUrlEncoded
    @POST("upcoming")
    fun getList(@Field("value") value: String): Observable<ResponseBody>


    @GET("{path}")
    fun eraseBadge(@Path("path")path: String): Observable<ResponseBody>

    @GET("user/login")
    fun login(@Query("username") username: String, @Query("password") password: String): Call<ResponseBody>

    @POST
    fun getMovieDetails(path: String, filters: Map<String, String>): Call<Any>



//    takeApi(RetrofitImpl::class)?.eraseBadge(
//    "eraseBadge/index.html")?.senderAsync(
//    String::class, this@ProfileFragmentItemComponent)
//
//    takeApi(RetrofitImpl::class)?.eraseBadge(
//    "eraseBadge/index.html")?.senderListAsync(
//    String::class, this@ProfileFragmentItemComponent)
//
////                        var any2: String? = takeApi(RetrofitImpl::class)?.eraseBadge(
////                                "eraseBadge/index.html")?.senderAwait(
////                                this@ProfileFragmentItemComponent)
////
////                        var any3: List<String>? = takeApi(RetrofitImpl::class)?.eraseBadge(
////                                "eraseBadge/index.html")?.senderListAwait(
////                                this@ProfileFragmentItemComponent)

}