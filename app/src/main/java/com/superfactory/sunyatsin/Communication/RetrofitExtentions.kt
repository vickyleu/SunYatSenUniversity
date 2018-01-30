package com.superfactory.sunyatsin.Communication

import android.content.Context
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Communication.Sender.senderAsync
import com.superfactory.library.Communication.Sender.senderAsyncMultiple
import okhttp3.ResponseBody
import rx.Observable
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
inline fun <reified D1 : Any, reified D2 : Any, T1 : ResponseBody, SE : Any, T2 : ResponseBody> RetrofitImpl.
        senderAsyncMultiple(fun0: ((RetrofitImpl) -> Observable<T1>),clazz: KClass<D1>, component: BindingComponent<*, *>, ctx: Context,
                            clazzB: KClass<D2>, crossinline fun1: ((RetrofitImpl) -> Observable<T2?>)) {
    fun0(this).senderAsyncMultiple(clazz,component,ctx,clazzB,fun1(this),)
            //.senderAsyncMultiple(clazz, component, ctx, clazzB, fun1)
}