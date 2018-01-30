package com.superfactory.library.Utils

import android.app.Activity
import android.app.Dialog
import android.content.Context

/**
 * Created by vicky on 2018.01.30.
 *
 * @Author vicky
 * @Date 2018年01月30日  12:03:38
 * @ClassName 这里输入你的类名(或用途)
 */
object ResourceIdUtil {
    var focusLast = ""

    fun <T : Activity> findFocus(`object`: T): String? {
        try {
            val decorView = `object`.window.decorView
            val focus = decorView.findFocus() ?: return null
            val focusId = focus.id
            if (focusLast != `object`.resources.getResourceName(focusId)) {
                focusLast = `object`.resources.getResourceName(focusId)
            }
            return getResourceName(`object`, focusId)
        } catch (e: Exception) {
        }

        return null
    }

    fun <T : Activity> getId(`object`: T, name: String): Int {
        try {
            return `object`.resources.getIdentifier(name, "id", `object`.packageName)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return -1
    }

    fun <T : Dialog> findFocus(`object`: T): String? {
        try {
            val decorView = `object`.window!!.decorView
            val focus = decorView.findFocus()
            val focusId = focus.id
            return getResourceName(`object`.context, focusId)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    fun getResourceName(context: Context, resId: Int): String {
        return context.resources.getResourceName(resId)
    }

}