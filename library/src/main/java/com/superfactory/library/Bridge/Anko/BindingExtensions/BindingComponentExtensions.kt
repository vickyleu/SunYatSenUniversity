package com.superfactory.library.Bridge.Anko.BindingExtensions

import android.app.Application
import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.ScreenSizeExtension
import com.superfactory.library.Context.BaseActivity
import com.superfactory.library.Context.BaseApp
import com.superfactory.library.R
import com.superfactory.library.Utils.StatusBarUtil
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.dip

/**
 * Created by vicky on 2018.01.19.
 *
 * @Author vicky
 * @Date 2018年01月19日  19:35:35
 * @ClassName 这里输入你的类名(或用途)
 */

fun BindingComponent<*, *>.getAppNoStatusBarSize(ctx: Context): ScreenSizeExtension {
    val noStatusSize = ScreenSizeExtension()
    val height = StatusBarUtil.getStatusBarHeight(ctx)
    val screenSizeExtension = getAppOverSize(ctx)
    noStatusSize.width = screenSizeExtension.width
    noStatusSize.height = screenSizeExtension.height - height
    noStatusSize.density = screenSizeExtension.density
    noStatusSize.densityDpi = screenSizeExtension.densityDpi
    return noStatusSize;
}

fun BindingComponent<*, *>.getAppOverSize(ctx: Context?): ScreenSizeExtension {
    if (ctx == null) {
        return ScreenSizeExtension()
    }
    var appCtx: BaseApp ? = null
    if (ctx is Application) {
        appCtx = ctx as BaseApp ?
    } else {
        appCtx = ctx.applicationContext as BaseApp ?
    }
    if (appCtx == null) {
        return ScreenSizeExtension()
    }
    return appCtx.mScreenSizeExtension;
}

fun BindingComponent<*, *>.getAppStatusBarSize(ctx: Context?): Int {
    if (ctx == null) {
        return 0
    }
    return  StatusBarUtil.getStatusBarHeight(ctx)
}


fun BindingComponent<*, *>.getAttrSizeValue(context: Context, attr: Int): Int {
    val values = getAttrValue(context, attr)
    try {
        return values.getDimensionPixelSize(0, 0)//第一个参数数组索引，第二个参数 默认值
    } finally {
        values.recycle()
    }
}

fun BindingComponent<*, *>.getAttrStringValue(context: Context, attr: Int): String? {
    val values = getAttrValue(context, attr)
    try {
        return values.getString(0)//第一个参数数组索引，第二个参数 默认值
    } finally {
        values.recycle()
    }
}

fun BindingComponent<*, *>.getAttrColorValue(context: Context, attr: Int): Int {
    val values = getAttrValue(context, attr)
    try {
        return values.getColor(0, 0)//第一个参数数组索引，第二个参数 默认值
    } finally {
        values.recycle()
    }
}

fun BindingComponent<*, *>.getAttrIntValue(context: Context, attr: Int): Int {
    val values = getAttrValue(context, attr)
    try {
        return values.getInteger(0, 0)//第一个参数数组索引，第二个参数 默认值
    } finally {
        values.recycle()
    }
}


fun BindingComponent<*, *>.getAttrDrawablValue(context: Context, attr: Int): Drawable {
    val values = getAttrValue(context, attr)
    try {
        return values.getDrawable(0)//第一个参数数组索引，第二个参数 默认值
    } finally {
        values.recycle()
    }
}


fun BindingComponent<*, *>.getAttrValue(context: Context, attr: Int): TypedArray {
    val attrs = intArrayOf(attr)
    val values = context.getTheme().obtainStyledAttributes(attrs)
    return values
}

fun BindingComponent<*, *>.getActionBarSize(context: Context): Int {
    return getAttrSizeValue(context, android.R.attr.actionBarSize)
}

fun BindingComponent<*, *>.getActionBarColor(context: Context): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        if (getAttrColorValue(context, android.R.attr.colorPrimary) == 0)
            getAttrColorValue(context, android.R.attr.colorPrimary)
        else
            ContextCompat.getColor(context, R.color.colorPrimary)
    } else {
        return 0
    }
}

inline fun <V> BindingComponent<*, V>.getModel(): V = this.viewModel!! as V

inline fun BindingComponent<*, *>.dipValue(value: Int, ctx: Context): Int = ctx.dip(value)
inline fun BindingComponent<*, *>.supportFragmentManager(ui: AnkoContext<*>): FragmentManager = (ui.owner as BaseActivity<*, *>).getSupportFragmentManager()
inline fun BindingComponent<*, *>.fragmentManager(ui: AnkoContext<*>): android.app.FragmentManager = (ui.owner as BaseActivity<*, *>).getFragmentManager()