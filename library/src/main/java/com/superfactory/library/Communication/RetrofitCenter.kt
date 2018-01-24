package com.superfactory.library.Communication

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.superfactory.library.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


/**
 * Created by vicky on 2018.01.23.
 *
 * @Author vicky
 * @Date 2018年01月23日  17:46:46
 * @ClassName 这里输入你的类名(或用途)
 */
open class RetrofitCenter<T : IRetrofit>(val baseUrl: String) {
    companion object {
        private var retrofitCenter: RetrofitCenter<*>? = null
        fun <T : IRetrofit> getRetrofiter(baseUrl: String): RetrofitCenter<T> {
            if (retrofitCenter == null) {
                retrofitCenter = RetrofitCenter<T>(baseUrl)
            }
            return retrofitCenter as RetrofitCenter<T>
        }
    }

    open fun buildingApiServer(): IRetrofit? {
        if (IRETROFIT == null) {
            getRetrofitAPI()
        }
        return IRETROFIT
    }

    @Volatile
    private var IRETROFIT: IRetrofit? = null

    @Synchronized
    private fun getRetrofitAPI(): IRetrofit? {
        val gsonBuilder = GsonBuilder()
        val gson = gsonBuilder.create()

        val httpClientBuilder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addInterceptor(loggingInterceptor)
        }


        //1得到该泛型类的子类对象的Class对象
        val type:ParameterizedType? = this.javaClass.genericSuperclass as? ParameterizedType
        val types:Array<Type?>? = type?.actualTypeArguments
        if (types==null|| types.isEmpty()){
            throw ExceptionInInitializerError("类名无法加载")
        }
        val cls = types[0] as Class<T>
        IRETROFIT = Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .callFactory(httpClientBuilder.build())
                .build()
                .create(cls)

        return IRETROFIT
    }
}