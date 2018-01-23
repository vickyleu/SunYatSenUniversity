package com.superfactory.library.Context.Extensions

import android.app.Service
import android.content.Context
import com.superfactory.library.Bridge.Anko.Adapt.BaseAnko
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Communication.IRetrofit
import com.superfactory.library.Context.BaseActivity
import com.superfactory.library.Context.BaseApp
import com.superfactory.library.Context.BaseFragment

/**
 * Created by vicky on 2018/1/23.
 */

fun BaseAnko<*, *>.takeApiInternal(): IRetrofit? {
    val delegate = appDelegate()
    return delegate?.takeApi()
}

fun BaseAnko<*, *>.takeApiSafeInternal(): IRetrofit {
    val delegate = appDelegate()
    return delegate!!.takeApiSafe()
}


fun BindingComponent<*, *>.takeApiInternal(ctx: Context?): IRetrofit? {
    val delegate = appDelegate(ctx)
    return delegate?.takeApi()
}


fun BindingComponent<*, *>.takeApiSafeInternal(ctx: Context?): IRetrofit {
    val delegate = appDelegate(ctx)
    return delegate!!.takeApiSafe()
}


fun <T: IRetrofit> BaseAnko<*, *>.takeApi(): T? {
    val delegate = appDelegate()
    return delegate?.takeApi() as? T
}

fun <T : IRetrofit> BaseAnko<*, *>.takeApiSafe(): T {
    val delegate = appDelegate()
    return delegate!!.takeApiSafe() as T
}

fun BaseAnko<*, *>.appDelegate(): BaseApp<*>? {
    if (this is BaseActivity) {
        return (this.getApplication() as? BaseApp<*>)
    } else if (this is BaseFragment) {
        return (this.getContext()?.applicationContext as? BaseApp<*>)
    } else {
        return BaseApp.appDelegate
    }
}

fun <T : IRetrofit> BindingComponent<*, *>.takeApi(ctx: Context?): T? {
    val delegate = appDelegate(ctx)
    return delegate?.takeApi() as? T
}


fun <T : IRetrofit> BindingComponent<*, *>.takeApiSafe(ctx: Context?): T {
    val delegate = appDelegate(ctx)
    return delegate!!.takeApiSafe() as T
}

fun BindingComponent<*, *>.appDelegate(ctx: Context?): BaseApp<*>? {
    if (ctx == null) return null
    if (ctx is BaseActivity<*, *>) {
        return (ctx.getApplication() as? BaseApp<*>)
    } else if (ctx is Service) {
        return (ctx.application as? BaseApp<*>)
    } else {
        return BaseApp.appDelegate
    }
}

