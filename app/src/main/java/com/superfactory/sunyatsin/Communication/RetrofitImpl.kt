package com.superfactory.sunyatsin.Communication

import com.superfactory.library.Communication.IRetrofit
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by vicky on 2018.01.23.
 *
 * @Author vicky
 * @Date 2018年01月23日  18:28:13
 * @ClassName 这里输入你的类名(或用途)
 */
class RetrofitImpl : IRetrofit() {
    @GET
    fun getMovieList(api_key: String): Call<Any> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    @POST
    fun getMovieDetails(path: String, filters: Map<String, String>): Call<Any> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}