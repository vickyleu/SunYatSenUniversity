package com.superfactory.library.Communication

import com.google.gson.GsonBuilder
import com.superfactory.library.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
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
        fun <T : Any> getRetrofiter(baseUrl: String, type: KClass<T>, flags: Boolean): RetrofitCenter<T> {
            if (flags) {
                return RetrofitCenter<T>(baseUrl, type)
            } else {
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
        val gson = gsonBuilder.serializeNulls().setLenient().setPrettyPrinting().create()

        val httpClientBuilder = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                            .addHeader("Content-Type", "application/json")
                            .addHeader("Accept", "application/json")
                            .build()
                    chain.proceed(request)
                }
        if (BuildConfig.DEBUG) {
            val logger = HttpLoggingInterceptor.Logger { message -> Platform.get().log(Platform.WARN, message, null) }
            val loggingInterceptor = HttpLoggingInterceptor(logger)
//            loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addInterceptor(loggingInterceptor)
        }
//每一个Call实例可以同步(call.excute())或者异步(call.enquene(CallBack<?> callBack))的被执行，
        //每一个实例仅仅能够被使用一次，但是可以通过clone()函数创建一个新的可用的实例。
        //默认情况下，Retrofit只能够反序列化Http体为OkHttp的ResponseBody类型
        //并且只能够接受ResponseBody类型的参数作为@body

        IRETROFIT = Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))// 使用Gson作为数据转换器
//                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//使用RxJava作为回调适配器
//                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .callFactory(httpClientBuilder.build())
                .build()
                .create(clazz.java)

        return IRETROFIT
    }
}