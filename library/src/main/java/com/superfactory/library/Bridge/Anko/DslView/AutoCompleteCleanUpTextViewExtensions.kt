@file:JvmName("AutoCompleteCleanUpViewsKt")

package com.superfactory.library.Bridge.Anko.DslView

import android.view.ViewManager
import com.superfactory.library.Graphics.AutoCompleteCleanUpTextView
import com.superfactory.library.Graphics.CleanUpEditText
import org.jetbrains.anko.custom.ankoView

/**
 * Created by vicky on 2018.01.29.
 *
 * @Author vicky
 * @Date 2018年01月29日  11:09:05
 * @ClassName 这里输入你的类名(或用途)
 */

inline fun ViewManager.cleanUpEditText(): CleanUpEditText = cleanUpEditText() {}

inline fun ViewManager.cleanUpEditText(init: CleanUpEditText.() -> Unit):
        CleanUpEditText {
    return ankoView(::CleanUpEditText, theme = 0) {
        init().apply {
            setRightClick(null)
        }
    }
}

inline fun ViewManager.themedCleanUpEditText(theme: Int = 0): CleanUpEditText = themedCleanUpEditText(theme) {}
inline fun ViewManager.themedCleanUpEditText(theme: Int = 0, init: CleanUpEditText.() ->
Unit): CleanUpEditText {
    return ankoView(::CleanUpEditText, theme) { init() }
}


inline fun ViewManager.autoCompleteCleanUpTextView(): AutoCompleteCleanUpTextView = autoCompleteCleanUpTextView() {}

inline fun ViewManager.autoCompleteCleanUpTextView(init: AutoCompleteCleanUpTextView.() -> Unit):
        AutoCompleteCleanUpTextView {
    return ankoView(::AutoCompleteCleanUpTextView, theme = 0) { init() }
}


//inline fun ViewManager.autoCompleteCleanUpTextView(init: AutoCompleteCleanUpTextView.() -> Unit):AutoCompleteCleanUpTextView {
//    return ankoView({ AutoCompleteCleanUpTextView(it,null,0)},theme = 0) { init()}
//}

inline fun ViewManager.themedAutoCompleteCleanUpTextView(theme: Int = 0): AutoCompleteCleanUpTextView = themedAutoCompleteCleanUpTextView(theme) {}

inline fun ViewManager.themedAutoCompleteCleanUpTextView(theme: Int = 0, init: AutoCompleteCleanUpTextView.() ->
Unit): AutoCompleteCleanUpTextView {
    return ankoView(::AutoCompleteCleanUpTextView, theme) { init() }
}

//inline fun ViewManager.themedAutoCompleteCleanUpTextView(theme: Int = 0, init: AutoCompleteCleanUpTextView.() ->
//Unit): AutoCompleteCleanUpTextView {
//    return ankoView({ AutoCompleteCleanUpTextView(it, null, 0) }, theme) { init() }
//}

interface OnTextCleanListener {
    fun onClean()
}
