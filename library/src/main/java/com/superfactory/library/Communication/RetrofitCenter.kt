package com.superfactory.library.Communication

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.superfactory.library.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



/**
 * Created by vicky on 2018.01.23.
 *
 * @Author vicky
 * @Date 2018年01月23日  17:46:46
 * @ClassName 这里输入你的类名(或用途)
 */
open class RetrofitCenter<T : IRetrofit>(val baseUrl:String) {
    companion object {
        private var retrofitCenter: RetrofitCenter<*>? = null
        fun <T : IRetrofit> getRetrofiter(baseUrl:String): RetrofitCenter<T> {
            if (retrofitCenter == null) {
                retrofitCenter = RetrofitCenter<T>(baseUrl)
            }
            return retrofitCenter as RetrofitCenter<T>
        }
    }

    open fun buildingApiServer(): IRetrofit? {
        if (IRETROFIT==null){
            getRetrofitAPI()
        }
        return IRETROFIT

//        if (retrofitCenter != null) {
//            val client = (retrofitCenter as RetrofitCenter<T>)
//            if (client.IRETROFIT == null)
//                (retrofitCenter as RetrofitCenter<T>).getRetrofitAPI()
//            return client.IRETROFIT
//        }
//        return null
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

        val typeArg= (javaClass.genericSuperclass as? ParameterizedType)?.actualTypeArguments
        if (typeArg!=null&&typeArg.size>0){
            val vararg = typeArg[0] as? Class<T> ?: throw ExceptionInInitializerError("类名无法加载")
            IRETROFIT = Retrofit.Builder().baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .callFactory(httpClientBuilder.build())
                    .build()
                    .create(vararg)
        }else{
            throw ExceptionInInitializerError("类名无法加载")
        }

        return IRETROFIT
    }
}