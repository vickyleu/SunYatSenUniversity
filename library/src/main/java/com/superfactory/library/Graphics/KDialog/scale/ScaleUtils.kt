package com.superfactory.library.Graphics.KDialog.scale

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.util.DisplayMetrics
import android.view.Display
import android.view.WindowManager


/**
 * Created by hupei on 2016/3/8 17:19.
 */
object ScaleUtils {
    fun scaleValue(`val`: Int): Int {
        val scale=(ScaleLayoutConfig.instance?.scale?:0f).toInt()
        return (`val` *  scale)
    }

    fun getRealScreenSize(context: Context): IntArray {

        val size = IntArray(2)

        val w = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val d = w.defaultDisplay
        val metrics = DisplayMetrics()
        d.getMetrics(metrics)

        // since SDK_INT = 1;
        var widthPixels = metrics.widthPixels
        var heightPixels = metrics.heightPixels
        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17) {
            try {
                widthPixels = Display::class.java.getMethod("getRawWidth").invoke(d) as Int
                heightPixels = Display::class.java.getMethod("getRawHeight").invoke(d) as Int
            } catch (ignored: Exception) {
            }

        } else if (Build.VERSION.SDK_INT >= 17) {// includes window decorations (statusbar bar/menu bar)
            val realSize = Point()
            d.getRealSize(realSize)
            widthPixels = realSize.x
            heightPixels = realSize.y
        }

        size[0] = widthPixels
        size[1] = heightPixels
        return size
    }


    fun getDevicePhysicalSize(context: Context): Double {
        val size = getRealScreenSize(context)
        val dm = context.resources.displayMetrics
        val x = Math.pow((size[0] / dm.xdpi).toDouble(), 2.0)
        val y = Math.pow((size[1] / dm.ydpi).toDouble(), 2.0)

        return Math.sqrt(x + y)
    }
}
