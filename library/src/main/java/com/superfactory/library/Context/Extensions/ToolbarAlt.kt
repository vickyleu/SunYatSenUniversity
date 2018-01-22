package com.superfactory.library.Context.Extensions

import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.view.View

/**
 * Created by vicky on 2018/1/22.
 * 快速生成扩展函数的工具
 */

//open class ToolbarExtensions:ToolbarAlt<BaseToolbarFragment<*, *>>{
//
//
//}


interface ToolbarAlt<Base> {
    fun Base.setTitle(str: String?)

    fun Base.setBackIcon(@DrawableRes res: Int?)

    fun Base.setBackTextSize(size: Int?)

    fun Base.setBackTextColor(@ColorInt color: Int?)


    fun Base.setBackIcon(res: String?)

    fun Base.setBackIcon(drawable: Drawable?)


    fun Base.setRightIcon(@DrawableRes res: Int?)

    fun Base.setRightTextSize(size: Int?)

    fun Base.setRightTextColor(@ColorInt color: Int?)

    /**
     * 设置文字会转化成图形,所以必须在转换之前设置好文字大小和颜色,否则将使用默认颜色和字体大小
     */
    fun Base.setRightIcon(res: String?)

    fun Base.setRightIcon(drawable: Drawable?)


    fun Base.setBackView(view: View?)

    fun Base.setRightView(view: View?)


    fun Base.setBackgroundColor(@ColorInt color: Int?)


//    fun <Input : KProperty<Value>, Value : Any> Base.setToolbarProperty(input: Input, any: Value)
//
//    fun <Data : ObservableField<Input>, Input> Base.setToolbarPropertyBase(field: Data?, any: Input?)
}