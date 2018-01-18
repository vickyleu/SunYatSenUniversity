package com.superfactory.library


import android.text.TextUtils
import android.util.Log

/**
 * Created by VickyLeu on 2016/7/14.
 *
 * @Author Vickyleu
 * @Company Esapos
 */
object Debuger {
    private val TAG = "com.vicky.http"
    var LOG = BuildConfig.DEBUG

    fun printMsg(tag: Any?, anything: Any) {
        if (tag != null)
            printMsg(tag.javaClass.simpleName, anything, null)
        else {
            throw RuntimeException("静态方法不支持传this啊老铁")
        }
    }

    /**
     * 获取exception详情信息
     *
     * @param e
     * Excetipn type
     * @return String type
     */
    fun getExceptionDetail(e: Exception?): String {
        var msg = StringBuffer("null")
        if (e != null) {
            msg = StringBuffer("")
            val message = e.toString()
            val length = e.stackTrace.size
            if (length > 0) {
                msg.append(message + "\n")
                for (i in 0 until length) {
                    msg.append("\t" + e.stackTrace[i] + "\n")
                }
            } else {
                msg.append(message)
            }
        }
        return msg.toString()
    }

    fun printMsg(anything: Any, throwable: Throwable?) {
        printMsg(TAG, anything, throwable)
    }
    fun printMsg(anything: String, throwable: Throwable?) {
        printMsg(anything, anything, throwable)
    }

    fun printMsg(tag: String, anything: Any, throwable: Throwable?) {
        if (LOG) {
            if (anything != null) {
                if (anything is String) {
                    val s = anything as String?
                    if (TextUtils.isEmpty(s)) return
                    if (throwable != null) {
                        Log.wtf(tag, s, throwable)
                    } else
                        Log.wtf(tag, s)
                } else {
                    if (throwable != null) {
                        Log.wtf(tag, anything.toString(), throwable)
                    } else
                        Log.wtf(tag, anything.toString())
                }
            }
        }
    }
}

