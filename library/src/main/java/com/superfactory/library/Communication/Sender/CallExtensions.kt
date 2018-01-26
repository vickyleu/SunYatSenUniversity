package com.superfactory.library.Communication.Sender

import com.google.gson.GsonBuilder
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Communication.Responder.fromJson
import com.superfactory.library.Communication.Responder.fromJsonList
import com.superfactory.library.Debuger
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Func1
import rx.schedulers.Schedulers
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
inline fun <reified D : Any, T : ResponseBody> Call<T>.senderAwait(component: BindingComponent<*, *>): D? {
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
            any = GsonBuilder().setLenient().create().fromJson(body?.toString()?.trim() ?: "")
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
inline fun <reified D : Any, T : ResponseBody> Call<T>.senderAsync(clazz: KClass<D>, component: BindingComponent<*, *>) {
    val viewModel = component.viewModel
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
                val model: D? = GsonBuilder().setLenient().create().fromJson(json = response?.body()?.string()?.trim()
                        ?: "")
                Debuger.printMsg(this, model ?: "null")
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            Debuger.printMsg(this, t.message ?: "null")
        }
    })
    Debuger.printMsg(this, "开始异步")
}

/**
 * 同步请求,会阻塞线程
 */

inline fun <reified D : Any, T : ResponseBody> Observable<T>.senderAwait(component: BindingComponent<*, *>): D? {
    try {
        return subscribeOn(Schedulers.newThread())//请求在新的线程中执行
                .observeOn(Schedulers.io())         //请求完成后在io线程中执行
                .map(object : Func1<T, D?> {
                    override fun call(t: T): D? {
                        try {
                            val model: D? = GsonBuilder().setLenient().create().fromJson(json = t.string()?.trim()
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
                .toBlocking().single()
    } catch (ex: RuntimeException) {
        if (ex.cause is IOException) {
            // handle IOException
        } else {
            throw ex // something other happened
        }
    }
    return null
}

inline fun <reified D : Any, T : ResponseBody> Observable<T>.senderListAwait(component: BindingComponent<*, *>): List<D>? {
    try {
        return subscribeOn(Schedulers.newThread())//请求在新的线程中执行
                .observeOn(Schedulers.io())         //请求完成后在io线程中执行
                .map(object : Func1<T, List<D>?> {
                    override fun call(t: T): List<D>? {
                        try {
                            val model: List<D>? = GsonBuilder().setLenient().create().fromJsonList(json = t.string()?.trim()
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
                .toBlocking()
                .single()
    } catch (ex: RuntimeException) {
        if (ex.cause is IOException) {
            // handle IOException
        } else {
            throw ex // something other happened
        }
    }
    return null
}

inline fun <reified D : Any, T : ResponseBody> Observable<T>.senderListAsync(clazz: KClass<D>, component: BindingComponent<*, *>) {
    this.subscribeOn(Schedulers.newThread())//请求在新的线程中执行
            .observeOn(Schedulers.io())         //请求完成后在io线程中执行
            .map(object : Func1<T, List<D>?> {
                override fun call(t: T): List<D>? {
                    try {
                        val model: List<D>? = GsonBuilder().setLenient().create().fromJsonList(json = t.string()?.trim()
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
            .subscribe(object : Subscriber<List<D>?>() {
                /**
                 * Provides the Observer with a new item to observe.
                 *
                 *
                 * The [Observable] may call this method 0 or more times.
                 *
                 *
                 * The `Observable` will not call this method again after it calls either [.onCompleted] or
                 * [.onError].
                 *
                 * @param t
                 * the item emitted by the Observable
                 */
                override fun onNext(t: List<D>?) {
                    //请求成功
                }

                /**
                 * Notifies the Observer that the [Observable] has finished sending push-based notifications.
                 *
                 *
                 * The [Observable] will not call this method if it calls [.onError].
                 */
                override fun onCompleted() {
                }

                /**
                 * Notifies the Observer that the [Observable] has experienced an error condition.
                 *
                 *
                 * If the [Observable] calls this method, it will not thereafter call [.onNext] or
                 * [.onCompleted].
                 *
                 * @param e
                 * the exception encountered by the Observable
                 */
                override fun onError(e: Throwable?) {
                    //请求失败
                    Debuger.printMsg(this, e?.message ?: "null")
                }
            })
    Debuger.printMsg(this, "开始异步")
}

/**
 * 异步请求
 */
inline fun <reified D : Any, T : ResponseBody> Observable<T>.senderAsync(clazz: KClass<D>, component: BindingComponent<*, *>) {
    this.subscribeOn(Schedulers.newThread())//请求在新的线程中执行
            .observeOn(Schedulers.io())         //请求完成后在io线程中执行
            .map(object : Func1<T, D?> {
                override fun call(t: T): D? {
                    try {
                        val model: D? = GsonBuilder().setLenient().create().fromJson(json = t.string()?.trim()
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
            .subscribe(object : Subscriber<D?>() {
                /**
                 * Provides the Observer with a new item to observe.
                 *
                 *
                 * The [Observable] may call this method 0 or more times.
                 *
                 *
                 * The `Observable` will not call this method again after it calls either [.onCompleted] or
                 * [.onError].
                 *
                 * @param t
                 * the item emitted by the Observable
                 */
                override fun onNext(t: D?) {
                    //请求成功
                }

                /**
                 * Notifies the Observer that the [Observable] has finished sending push-based notifications.
                 *
                 *
                 * The [Observable] will not call this method if it calls [.onError].
                 */
                override fun onCompleted() {
                }

                /**
                 * Notifies the Observer that the [Observable] has experienced an error condition.
                 *
                 *
                 * If the [Observable] calls this method, it will not thereafter call [.onNext] or
                 * [.onCompleted].
                 *
                 * @param e
                 * the exception encountered by the Observable
                 */
                override fun onError(e: Throwable?) {
                    //请求失败
                    Debuger.printMsg(this, e?.message ?: "null")
                }
            })
    Debuger.printMsg(this, "开始异步")
}
