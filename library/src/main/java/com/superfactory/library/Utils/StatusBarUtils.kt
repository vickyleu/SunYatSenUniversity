package com.superfactory.library.Utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import com.superfactory.library.R

/**
 * Created by vicky on 2018.01.17.
 *
 * @Author vicky
 * @Date 2018年01月17日  17:44:51
 * @ClassName 这里输入你的类名(或用途)
 */

object StatusBarUtil {
    @SuppressLint("PrivateApi")
            /**
             * 获取状态栏高度——方法2
             */
    fun getStatusBarHeight(context: Context): Int {
        var statusBarHeight = -1
        try {
            val clazz = Class.forName("com.android.internal.R\$dimen")
            val `object` = clazz.newInstance()
            val height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(`object`).toString())
            statusBarHeight = context.resources.getDimensionPixelSize(height)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return statusBarHeight
    }

    fun getStatusBarHeightHigherSdk19(context: Context): Int {
        var statusBarHeight = -1
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                val clazz = Class.forName("com.android.internal.R\$dimen")
                val `object` = clazz.newInstance()
                val height = Integer.parseInt(clazz.getField("status_bar_height")
                        .get(`object`).toString())
                statusBarHeight = context.resources.getDimensionPixelSize(height)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return statusBarHeight
    }

}
