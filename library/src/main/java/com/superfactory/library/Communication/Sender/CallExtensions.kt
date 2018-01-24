package com.superfactory.library.Communication.Sender

import com.google.gson.Gson
import com.superfactory.library.Bridge.Anko.Adapt.BaseAnko
import com.superfactory.library.Bridge.Anko.BaseObservable
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Communication.Responder.fromJson
import com.superfactory.library.Debuger
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

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
inline fun <reified D : Any,T,V> Call<ResponseBody>.senderAwait(component: BindingComponent<T,V>): D? {
    var body: ResponseBody? = null
    try {
        Debuger.printMsg(this, "开始同步")
        val exec = this.execute()
        if (exec.isSuccessful) {
            body = exec.body()
        } else {
            body = exec.errorBody()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Debuger.printMsg(this, e.message ?: "null")
    }
    val any: D? = Gson().fromJson(body?.toString() ?: "")
    Debuger.printMsg(this, any?.toString() ?: "null")
    return any
}

/**
 * 异步请求
 */
inline fun <reified D : Any,T,V> Call<ResponseBody>.senderAsync(component: BindingComponent<T,V>) {
    val viewModel=component.viewModel
    this.enqueue(object : Callback<ResponseBody> {
        /**
         * Invoked for a received HTTP response.
         *
         *
         * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
         * Call [Response.isSuccessful] to determine if the response indicates success.
         */
        override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
            try {
                val model: D? = Gson().fromJson(json = response?.body()?.string() ?: "")
                Debuger.printMsg(this, model ?: "null")
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            Debuger.printMsg(this, t.message ?: "null")
        }
    })
    Debuger.printMsg(this, "开始异步")
}
