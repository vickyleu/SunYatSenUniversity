package com.superfactory.library.Communication.Sender

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.superfactory.library.Bridge.Anko.BaseObservable
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Communication.Responder.fromJson
import com.superfactory.library.Communication.Responder.fromJsonList
import com.superfactory.library.Debuger
import com.xiasuhuei321.loadingdialog.view.LoadingDialog
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass


/**
 * Created by vicky on 2018.01.24.
 *
 * @Author vicky
 * @Date 2018年01月24日  13:56:49
 * @ClassName 这里输入你的类名(或用途)
 */

/**
 * 同步请求,会阻塞线程
 */
inline fun <reified D : Any, T : ResponseBody> Call<T>.senderAwait(component: BindingComponent<*, *>, ctx: Context): D? {
    val exec = Executors.newSingleThreadExecutor()
    var any: D? = null
    exec.submit {
        try {
            var body: ResponseBody? = null
            Debuger.printMsg(this, "开始同步")
            val exe = this.execute()
            if (exe.isSuccessful) {
                body = exe.body()
            } else {
                body = exe.errorBody()
            }
            any = GsonBuilder()
                    .serializeNulls()
                    .registerTypeAdapterFactory(NullStringToEmptyAdapterFactory())//然后用上面一行写的gson来序列化和反序列化实体类type
                    .setLenient()
                    .create().fromJson(body?.toString()?.trim()
                    ?: "")
            Debuger.printMsg(this, any?.toString()?.trim() ?: "null")
        } catch (e: IOException) {
            e.printStackTrace()
            Debuger.printMsg(this, e.message ?: "null")
        }
    }
    return any
}

/**
 * 异步请求
 */
inline fun <reified D : Any, T : ResponseBody> Call<T>.senderAsync(clazz: KClass<D>, component: BindingComponent<*, *>, ctx: Context) {
    senderAsync(clazz, component, ctx, true)
}

inline fun <reified D : Any, T : ResponseBody> Call<T>.senderAsync(clazz: KClass<D>, component: BindingComponent<*, *>, ctx: Context, flags: Boolean) {
    val viewModel = component.viewModel
    val ld = LoadingDialog(ctx)
    (viewModel as? BaseObservable)?.startRequest(ld)
    this.enqueue(object : Callback<T> {
        /**
         * Invoked for a received HTTP response.
         *
         *
         * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
         * Call [Response.isSuccessful] to determine if the response indicates success.
         */
        override fun onResponse(call: Call<T>?, response: Response<T>?) {
            try {
                val model: D? = GsonBuilder()
                        .serializeNulls()
                        .registerTypeAdapterFactory(NullStringToEmptyAdapterFactory())//然后用上面一行写的gson来序列化和反序列化实体类type
                        .setLenient().create().fromJson(json = response?.body()?.string()?.trim()
                        ?: "")
                Debuger.printMsg("tags", model ?: "null")
                (viewModel as? BaseObservable)?.requestSuccess(ld, model)
            } catch (e: Exception) {
                e.printStackTrace()
                (viewModel as? BaseObservable)?.requestFailed(ld, e)
            }
        }


        override fun onFailure(call: Call<T>, t: Throwable) {
            Debuger.printMsg(this, t.message ?: "null")
            (viewModel as? BaseObservable)?.requestFailed(ld, t)
        }
    })
    Debuger.printMsg(this, "开始异步")
}


/**
 * 同步请求,会阻塞线程
 */

inline fun <reified D : Any, T : ResponseBody> Observable<T>.senderAwait(component: BindingComponent<*, *>, ctx: Context): D? {
    try {
        return subscribeOn(Schedulers.newThread())//请求在新的线程中执行
                .observeOn(Schedulers.io())         //请求完成后在io线程中执行
                .map(object : Function<T, D?> {
                    override fun apply(t: T): D? {
                        try {
                            val model: D? = GsonBuilder()
                                    .serializeNulls()
                                    .registerTypeAdapterFactory(NullStringToEmptyAdapterFactory())//然后用上面一行写的gson来序列化和反序列化实体类type
                                    .setLenient().create().fromJson(json = t.string()?.trim()
                                    ?: "")
                            Debuger.printMsg(this, model ?: "null")
                            return model
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                        return null
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .timeout(1000, TimeUnit.MILLISECONDS)
                .blockingSingle()
    } catch (ex: RuntimeException) {
        if (ex.cause is IOException) {
            // handle IOException
        } else {
            throw ex // something other happened
        }
    }
    return null
}

inline fun <reified D : Any, T : ResponseBody> Observable<T>.senderListAwait(component: BindingComponent<*, *>, ctx: Context): List<D>? {
    try {
        return subscribeOn(Schedulers.newThread())//请求在新的线程中执行
                .observeOn(Schedulers.io())         //请求完成后在io线程中执行
                .map(object : Function<T, List<D>?> {
                    override fun apply(t: T): List<D>? {
                        try {
                            val model: List<D>? = GsonBuilder()
                                    .serializeNulls()
                                    .registerTypeAdapterFactory(NullStringToEmptyAdapterFactory())//然后用上面一行写的gson来序列化和反序列化实体类type
                                    .setLenient().create().fromJsonList(json = t.string()?.trim()
                                    ?: "")
                            Debuger.printMsg(this, model ?: "null")
                            return model
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                        return null
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .timeout(1000, TimeUnit.MILLISECONDS)
                .blockingSingle()
    } catch (ex: RuntimeException) {
        if (ex.cause is IOException) {
            // handle IOException
        } else {
            throw ex // something other happened
        }
    }
    return null
}

inline fun <reified D : Any, T : ResponseBody> Observable<T>.senderListAsync(clazz: KClass<D>, component: BindingComponent<*, *>, ctx: Context) {
    this.subscribeOn(Schedulers.newThread())//请求在新的线程中执行
            .observeOn(Schedulers.io())         //请求完成后在io线程中执行
            .map(object : Function<T, List<D>?> {
                override fun apply(t: T): List<D>? {
                    try {
                        val model: List<D>? = GsonBuilder()
                                .serializeNulls()
                                .registerTypeAdapterFactory(NullStringToEmptyAdapterFactory())//然后用上面一行写的gson来序列化和反序列化实体类type
                                .setLenient().create().fromJsonList(json = t.string()?.trim()
                                ?: "")
                        Debuger.printMsg(this, model ?: "null")
                        return model
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    return null
                }
            })
            .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
            .subscribe(object : Observer<List<D>?> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: List<D>) {
                    //请求成功
                }

                override fun onError(e: Throwable) {
                    //请求失败
                    Debuger.printMsg(this, e.message ?: "null")
                }

            })

    Debuger.printMsg(this, "开始异步")
}


/**
 * 异步请求
 */
inline fun <reified D1 : Any, reified D2 : Any, T1 : ResponseBody, T2 : ResponseBody> Observable<T1>.senderAsyncMultiple(clazz: KClass<D1>, component: BindingComponent<*, *>, ctx: Context,
                                                                                                                         clazzB: KClass<D2>, crossinline fun1: ((D1) -> Observable<T2>?)) {
    val ld = LoadingDialog(ctx)
    (component.viewModel as? BaseObservable)?.startRequest(ld)
    this.subscribeOn(Schedulers.newThread())//请求在新的线程中执行
            .observeOn(Schedulers.io())//在io线程中进行Gson解析
            .map(object : Function<T1, D1?> {
                override fun apply(t: T1): D1? {
                    try {
                        return GsonBuilder()
                                .serializeNulls()
                                .registerTypeAdapterFactory(NullStringToEmptyAdapterFactory())//然后用上面一行写的gson来序列化和反序列化实体类type
                                .setLenient().create().fromJson<D1?>(json = t.string()?.trim()
                                ?: "")
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    return null
                }
            })
            .observeOn(AndroidSchedulers.mainThread())//在主线程拦截嵌套请求
            .map(Function<D1?, D1?>
            { t ->
                if (t == null) return@Function null
                val flag: Boolean = (component.viewModel as? BaseObservable)?.appendingRequest(ld, t)
                        ?: true
                if (flag) t else null
            })
            .observeOn(Schedulers.newThread())//然后再到新的线程中执行请求
            .flatMap(Function<D1?, Observable<T2>?>
            { t ->
                if (t == null) return@Function null
                fun1(t)
            })
            .observeOn(Schedulers.io())
            .map(Function<T2?, D2?>
            { t ->
                try {
                    return@Function GsonBuilder()
                            .serializeNulls()
                            .registerTypeAdapterFactory(NullStringToEmptyAdapterFactory())//然后用上面一行写的gson来序列化和反序列化实体类type
                            .setLenient().create().fromJson<D2?>(json = t?.string()?.trim()
                            ?: "")
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                null
            })
            .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
            .subscribe(object : Observer<D2?> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: D2) {
                    //请求成功
//                        //在你代码中合适的位置调用反馈
                    (component.viewModel as? BaseObservable)?.requestSuccess(ld, t)
                }

                override fun onError(e: Throwable) {
                    (component.viewModel as? BaseObservable)?.requestFailed(ld, e)
//                        //请求失败
                    Debuger.printMsg(this, e.message ?: "null")
                }

            })
}

open class NullStringToEmptyAdapterFactory : TypeAdapterFactory {
    /**
     * Returns a type adapter for `type`, or null if this factory doesn't
     * support `type`.
     */
    override fun <T : Any?> create(gson: Gson?, type: TypeToken<T>?): TypeAdapter<T>? {
        val rawType = type?.rawType as? Class<T>
        val strClazz = String::class.java
        if (rawType != strClazz) {
            return null
        }
        return StringNullAdapter() as TypeAdapter<T>
    }

    class StringNullAdapter : TypeAdapter<String>() {
        override fun write(out: JsonWriter?, value: String?) {
            if (value == null) {
                out?.nullValue()
                return;
            }
            out?.value(value)
        }

        override fun read(`in`: JsonReader?): String? {
            if (`in`?.peek() == JsonToken.NULL) {
                `in`.nextNull()
                return ""
            }
            return `in`?.nextString()
        }
    }

}


inline fun <reified D : Any, T : ResponseBody> Observable<T>.senderAsync(clazz: KClass<D>, component: BindingComponent<*, *>, ctx: Context, refresh: RefreshLayout? = null) {
    val ld = LoadingDialog(ctx)
    (component.viewModel as? BaseObservable)?.startRequest(ld)
    this.subscribeOn(Schedulers.newThread())//请求在新的线程中执行
            .observeOn(Schedulers.io())         //请求完成后在io线程中执行
            .map(object : Function<T, D?> {
                override fun apply(t: T): D? {
                    try {
                        val model: D? = GsonBuilder()
                                .serializeNulls()
                                .registerTypeAdapterFactory(NullStringToEmptyAdapterFactory())//然后用上面一行写的gson来序列化和反序列化实体类type
                                .setLenient().create().fromJson(json = t.string()?.trim()
                                ?: "")
                        Debuger.printMsg(this, model ?: "null")
                        return model
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    return null
                }

            })
            .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
            .subscribe(object : Observer<D?> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: D) {
                    refresh?.finishRefresh()
                    //请求成功
                    //在你代码中合适的位置调用反馈
                    (component.viewModel as? BaseObservable)?.requestSuccess(ld, t)
                }

                override fun onError(e: Throwable) {
                    refresh?.finishRefresh()
                    (component.viewModel as? BaseObservable)?.requestFailed(ld, e)
                    //请求失败
                    Debuger.printMsg(this, e.message ?: "null")
                }

            })

    Debuger.printMsg(this, "开始异步")
}
