package com.superfactory.library.Communication

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.superfactory.library.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by vicky on 2018.01.23.
 *
 * @Author vicky
 * @Date 2018年01月23日  17:46:46
 * @ClassName 这里输入你的类名(或用途)
 */
class RetrofitCenter<T:IRetrofit>(val vararg : T) {
    companion object {
        private var retrofitCenter: RetrofitCenter<*>? = null
        fun <T:IRetrofit> getRetrofiter(t:T): RetrofitCenter<T>? {
            if (retrofitCenter == null) {
                retrofitCenter = RetrofitCenter<T>(t)
               if (retrofitCenter!=null){
                   (retrofitCenter as RetrofitCenter<T>).getRetrofitAPI()
               }
            }
            return retrofitCenter as RetrofitCenter<T>
        }
    }



    @Volatile
    private var IRETROFIT: IRetrofit? = null

    @Synchronized
    fun getRetrofitAPI(): IRetrofit? {
        val gsonBuilder = GsonBuilder()
        val gson = gsonBuilder.create()

        val httpClientBuilder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addInterceptor(loggingInterceptor)
        }

        IRETROFIT = Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/movie/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .callFactory(httpClientBuilder.build())
                .build()
                .create(vararg.javaClass)
        return IRETROFIT
    }
}