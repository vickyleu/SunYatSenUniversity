package com.superfactory.library.Communication

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.superfactory.library.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.reflect.KClass


/**
 * Created by vicky on 2018.01.23.
 *
 * @Author vicky
 * @Date 2018年01月23日  17:46:46
 * @ClassName 这里输入你的类名(或用途)
 */
open class RetrofitCenter<T : Any>(val baseUrl: String, val clazz: KClass<T>) {
    companion object {
        private var retrofitCenter: RetrofitCenter<*>? = null
        fun <T : Any> getRetrofiter(baseUrl: String, type: KClass<T>): RetrofitCenter<T> {
            if (retrofitCenter != null) {
                if (retrofitCenter?.clazz != type) {
                    retrofitCenter = null
                }
            }
            if (retrofitCenter == null) {
                retrofitCenter = RetrofitCenter<T>(baseUrl, type)
            }
            return retrofitCenter as RetrofitCenter<T>
        }
    }


    open fun buildingApiServer(): Any? {
        if (IRETROFIT == null) {
            getRetrofitAPI()
        }
        return IRETROFIT
    }

    @Volatile
    private var IRETROFIT: Any? = null

    @Synchronized
    private fun getRetrofitAPI(): Any? {
        val gsonBuilder = GsonBuilder()
        val gson = gsonBuilder.setLenient().create()

        val httpClientBuilder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addInterceptor(loggingInterceptor)
        }


        IRETROFIT = Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .callFactory(httpClientBuilder.build())
                .build()
                .create(clazz.java)

        return IRETROFIT
    }
}