package com.superfactory.library.Context.Extensions

import android.app.Service
import android.content.Context
import android.view.View
import com.superfactory.library.Bridge.Anko.Adapt.BaseAnko
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Context.BaseActivity
import com.superfactory.library.Context.BaseApp
import com.superfactory.library.Context.BaseFragment
import kotlin.reflect.KClass

/**
 * Created by vicky on 2018/1/23.
 */

fun BaseAnko<*, *>.takeApiInternal(impl: KClass<Any>): Any? {
    val delegate = appDelegate()
    return delegate?.takeApi(impl)
}

fun BaseAnko<*, *>.takeApiSafeInternal(impl: KClass<Any>): Any {
    val delegate = appDelegate()
    return delegate!!.takeApiSafe(impl)
}


fun BindingComponent<*, *>.takeApiInternal(ctx: Context?, impl: KClass<Any>): Any? {
    val delegate = appDelegate(ctx)
    return delegate?.takeApi(impl)
}


fun BindingComponent<*, *>.takeApiSafeInternal(ctx: Context?, impl: KClass<Any>): Any {
    val delegate = appDelegate(ctx)
    return delegate!!.takeApiSafe(impl)
}


fun <T : Any> BaseAnko<*, *>.takeApi(impl: KClass<T>): T? {
    val delegate = appDelegate()
    return delegate?.takeApi(impl)
}

fun <T : Any> BaseAnko<*, *>.takeApiSafe(impl: KClass<T>): T {
    val delegate = appDelegate()
    return delegate!!.takeApiSafe(impl)
}

fun BaseAnko<*, *>.appDelegate(): BaseApp? {
    if (this is BaseActivity) {
        return (this.getApplication() as? BaseApp)
    } else if (this is BaseFragment) {
        return (this.getContext()?.applicationContext as? BaseApp)
    } else {
        return BaseApp.appDelegate
    }
}

fun <T : Any> BindingComponent<*, *>.takeApi(ctx: Context?, impl: KClass<T>): T? {
    val delegate = appDelegate(ctx)
    return delegate?.takeApi(impl)
}

fun <T : Any> View.takeApi(impl: KClass<T>): T? {
    val delegate = appDelegate()
    return delegate?.takeApi(impl)
}

fun <T : Any> View.takeApiSafe(impl: KClass<T>): T {
    val delegate = appDelegate()
    return delegate!!.takeApiSafe(impl)
}

fun <T : Any> View.takeApi(impl: KClass<T>, url: String): T? {
    val delegate = appDelegate()
    return delegate?.takeApiOnce(impl, url)
}

fun <T : Any> View.takeApiSafe(impl: KClass<T>, url: String): T {
    return takeApi(impl, url)!!
}

fun <T : Any> Context.takeApiSafe(impl: KClass<T>): T {
    val delegate = appDelegate()
    return delegate!!.takeApiSafe(impl)
}

fun <T : Any> Context.takeApi(impl: KClass<T>, url: String): T? {
    val delegate = appDelegate()
    return delegate?.takeApiOnce(impl, url)
}

fun <T : Any> Context.takeApiSafe(impl: KClass<T>, url: String): T {
    return takeApi(impl, url)!!
}


fun Context.appDelegate(): BaseApp? {
    return (this)?.applicationContext as? BaseApp
}

fun View.appDelegate(): BaseApp? {
    return (this.context)?.applicationContext as? BaseApp
}


fun <T : Any> BindingComponent<*, *>.takeApiSafe(ctx: Context?, impl: KClass<T>): T {
    val delegate = appDelegate(ctx)
    return delegate!!.takeApiSafe(impl)
}

fun BindingComponent<*, *>.appDelegate(ctx: Context?): BaseApp? {
    if (ctx == null) return null
    if (ctx is BaseActivity<*, *>) {
        return (ctx.getApplication() as? BaseApp)
    } else if (ctx is Service) {
        return (ctx.application as? BaseApp)
    } else {
        return BaseApp.appDelegate
    }
}

