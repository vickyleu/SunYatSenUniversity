package com.superfactory.library.Bridge.Anko.ViewExtensions

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.view.View.OnClickListener
import android.view.inputmethod.InputMethodManager
import com.superfactory.library.Debuger

fun View.setVisibilityIfNeeded(visibility: Int?) {
    if (visibility != null && this.visibility != visibility) {
        this.visibility = visibility
    }
}

// 使用扩展属性(extension property)
var View.onClickListener: OnClickListener?
    get() = privateClickExtensions
    set(value) {
        privateClickExtensions = value
    }

private var privateClickExtensions: OnClickListener? = null
    get() = field
    set(value) {
        field = value
    }

fun View.string(resId: Int, vararg args: Any?) = context.getString(resId, *args)!!

fun View.plural(resId: Int, quantity: Int, vararg args: Any?) = context.resources.getQuantityString(resId, quantity, *args)!!

fun View.int(resId: Int) = context.resources.getInteger(resId)

fun View.boolean(resId: Int) = context.resources.getBoolean(resId)

fun View.text(resId: Int) = context.getText(resId)!!

@Suppress("DEPRECATION")
fun View.color(colorRes: Int): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        context.getColor(colorRes)
    } else {
        resources.getColor(colorRes)
    }
}

fun View.setInlineOnClickListener(click: OnClickListener?) {
   onClickListener = click
    this.setOnClickListener(click)
}

fun View.onClickListener(): OnClickListener? {
    return this.onClickListener
}

@Suppress("DEPRECATION")
fun View.drawable(drawableRes: Int): Drawable {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        context.getDrawable(drawableRes)!!
    } else {
        resources.getDrawable(drawableRes)
    }
}


fun View.setClickedIfNecessary(clickListener: OnClickListener?) {
    Debuger.printMsg(this ,"setClickedIfNecessary="+clickListener)
    if (clickListener != null)
        clickListener.onClick(this)
}

/**
 * 强制隐藏输入法键盘
 */
fun View.hideInput(context: Context) {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

