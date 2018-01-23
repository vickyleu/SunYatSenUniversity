package com.superfactory.library.Bridge.Anko.PropertyExtensions

import android.content.Context
import org.jetbrains.anko.dip

/**
 * Created by vicky on 2018.01.22.
 *
 * @Author vicky
 * @Date 2018年01月22日  17:27:55
 * @ClassName 这里输入你的类名(或用途)
 */
fun dipValue(value: Int, ctx: Context): Int = ctx.dip(value)

fun dp2px(context: Context, dp: Float): Int {
    val scale = context.resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}

fun px2dp(context: Context, pxValue: Float): Int {
    val scale = context.resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}