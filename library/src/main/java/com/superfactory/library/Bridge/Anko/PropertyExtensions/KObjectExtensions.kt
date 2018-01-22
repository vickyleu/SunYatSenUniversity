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