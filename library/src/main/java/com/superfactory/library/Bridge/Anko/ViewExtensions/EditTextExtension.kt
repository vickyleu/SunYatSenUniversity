package com.superfactory.library.Bridge.Anko.ViewExtensions

import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView


/**
 * Created by vicky on 2018.02.06.
 *
 * @Author vicky
 * @Date 2018年02月06日  18:14:05
 * @ClassName 这里输入你的类名(或用途)
 */
class EditTextExtension {
}

var EditText.cursorDrawable: Int?
    get() = getCursorDrawableColor()
    set(value) {
        if (value != null)
            setCursorDrawableColor(value)
    }

inline fun EditText.setCursorDrawableColor(color: Int) {
    try {
        val fCursorDrawableRes = TextView::class.java.getDeclaredField("mCursorDrawableRes")
        fCursorDrawableRes.isAccessible = true
        val mCursorDrawableRes = fCursorDrawableRes.getInt(this)
        val fEditor = TextView::class.java.getDeclaredField("mEditor")
        fEditor.isAccessible = true
        val editor = fEditor.get(this)
        val clazz = editor.javaClass
        val fCursorDrawable = clazz.getDeclaredField("mCursorDrawable")
        fCursorDrawable.isAccessible = true
        val drawables = arrayOfNulls<Drawable>(2)
        drawables[0] = this.context.resources.getDrawable(mCursorDrawableRes)
        drawables[1] = this.context.resources.getDrawable(mCursorDrawableRes)
        drawables[0]?.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        drawables[1]?.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        fCursorDrawable.set(editor, drawables)
    } catch (ignored: Throwable) {
    }
}

inline fun EditText.getCursorDrawableColor(): Int? {
    try {
        val fCursorDrawableRes = TextView::class.java.getDeclaredField("mCursorDrawableRes")
        fCursorDrawableRes.isAccessible = true
        val mCursorDrawableRes = fCursorDrawableRes.getInt(this)
        val fEditor = TextView::class.java.getDeclaredField("mEditor")
        fEditor.isAccessible = true
        val editor = fEditor.get(this)
        val clazz = editor.javaClass
        val fCursorDrawable = clazz.getDeclaredField("mCursorDrawable")
        fCursorDrawable.isAccessible = true
        val drawables: Array<Drawable?>? = fCursorDrawable.get(editor) as? Array<Drawable?>
        return (drawables?.get(0)  as? ColorDrawable)?.color
    } catch (ignored: Throwable) {
    }
    return null
}


fun TextView.getWatcher(): List<TextWatcher>? {
    try {
        val mListeners = TextView::class.java.getDeclaredField("mListeners")
        mListeners.isAccessible = true
        return mListeners.get(this) as List<TextWatcher>
    } catch (ignored: Throwable) {
    }
    return null
}

fun TextView.removeWatcher() {
    try {
        val watcher = this.getWatcher()
        if (watcher != null) {
            (watcher as ArrayList).clear()
        }
    } catch (ignored: Throwable) {
    }
}
