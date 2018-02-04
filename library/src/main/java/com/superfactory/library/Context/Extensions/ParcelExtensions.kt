package com.superfactory.library.Context.Extensions

import android.os.Parcel

/**
 * Created by vicky on 2018.02.04.
 *
 * @Author vicky
 * @Date 2018年02月04日  16:26:48
 * @ClassName 这里输入你的类名(或用途)
 */
inline fun Parcel.writeStringNotNull(string: String?) {
    this.writeString(if (string == null) "" else string)
}