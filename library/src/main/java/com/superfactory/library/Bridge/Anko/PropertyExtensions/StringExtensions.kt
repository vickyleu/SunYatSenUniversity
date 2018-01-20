package com.superfactory.library.Bridge.Anko.PropertyExtensions

import com.superfactory.library.Debuger

/**
 * Created by vicky on 2018.01.20.
 *
 * @Author vicky
 * @Date 2018年01月20日  15:38:15
 * @ClassName 这里输入你的类名(或用途)
 */
fun String.unicodeToUtf8(): String? {
    val src = this
    val out = StringBuilder()
    var i = 0
    while (i < src.length) {
        val c = src[i]
        if (i + 6 < src.length && c == '\\' && src[i + 1] == 'u') {
            val hex = src.substring(i + 2, i + 6)
            try {
                out.append(Integer.parseInt(hex, 16).toChar())
            } catch (nfe: NumberFormatException) {
                nfe.fillInStackTrace()
            }
            i = i + 6
        } else {
            out.append(src[i])
            ++i
        }
    }
    return out.toString()
}
