package com.superfactory.sunyatsin.Communication

import android.content.Context
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Communication.Sender.senderAsync
import com.superfactory.library.Communication.Sender.senderAsyncMultiple
import io.reactivex.Observable
import okhttp3.ResponseBody
import kotlin.reflect.KClass

/**
 * Created by vicky on 2018.01.30.
 *
 * @Author vicky
 * @Date 2018年01月30日  20:48:54
 * @ClassName 这里输入你的类名(或用途)
 */
/**
 * 异步请求
 */
inline fun <reified D1 : Any, reified D2 : Any, T1 : ResponseBody, T2 : ResponseBody> RetrofitImpl.
        senderAsyncMultiple(fun0: ((RetrofitImpl) -> Observable<T1>), clazzA: KClass<D1>,
                            component: BindingComponent<*, *>, ctx: Context,
                            crossinline fun1: ((RetrofitImpl,D1) -> Observable<T2>?), clazzB: KClass<D2> ) {
    val fun2:(D1)->Observable<T2>? ={
        d1 ->
        fun1(this,d1)
    }
    fun0(this).senderAsyncMultiple(clazzA,component,ctx,clazzB,fun2)
}