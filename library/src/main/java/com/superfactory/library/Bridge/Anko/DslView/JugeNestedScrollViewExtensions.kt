package com.superfactory.library.Bridge.Anko.DslView

import android.app.Activity
import android.content.Context
import android.view.ViewManager
import com.superfactory.library.Graphics.Scroller.JudgeNestedScrollView
import org.jetbrains.anko.AnkoViewDslMarker
import org.jetbrains.anko.custom.ankoView

/**
 * Created by vicky on 2018.02.02.
 *
 * @Author vicky
 * @Date 2018年02月02日  19:57:33
 * @ClassName 这里输入你的类名(或用途)
 */

inline fun ViewManager.judgeNestedScrollView(): JudgeNestedScrollView = judgeNestedScrollView() {}

inline fun ViewManager.judgeNestedScrollView(init: (@AnkoViewDslMarker JudgeNestedScrollView).() -> Unit): JudgeNestedScrollView {
    return ankoView(::JudgeNestedScrollView, theme = 0) { init() }
}

inline fun ViewManager.themedJudgeNestedScrollView(theme: Int = 0): JudgeNestedScrollView = themedJudgeNestedScrollView(theme) {}
inline fun ViewManager.themedJudgeNestedScrollView(theme: Int = 0, init: (@AnkoViewDslMarker JudgeNestedScrollView).() -> Unit): JudgeNestedScrollView {
    return ankoView(::JudgeNestedScrollView, theme) { init() }
}

inline fun Context.judgeNestedScrollView(): JudgeNestedScrollView = judgeNestedScrollView() {}
inline fun Context.judgeNestedScrollView(init: (@AnkoViewDslMarker JudgeNestedScrollView).() -> Unit): JudgeNestedScrollView {
    return ankoView(::JudgeNestedScrollView, theme = 0) { init() }
}

inline fun Context.themedJudgeNestedScrollView(theme: Int = 0): JudgeNestedScrollView = themedJudgeNestedScrollView(theme) {}
inline fun Context.themedJudgeNestedScrollView(theme: Int = 0, init: (@AnkoViewDslMarker JudgeNestedScrollView).() -> Unit): JudgeNestedScrollView {
    return ankoView(::JudgeNestedScrollView, theme) { init() }
}

inline fun Activity.judgeNestedScrollView(): JudgeNestedScrollView = judgeNestedScrollView() {}
inline fun Activity.judgeNestedScrollView(init: (@AnkoViewDslMarker JudgeNestedScrollView).() -> Unit): JudgeNestedScrollView {
    return ankoView(::JudgeNestedScrollView, theme = 0) { init() }
}

inline fun Activity.themedJudgeNestedScrollView(theme: Int = 0): JudgeNestedScrollView = themedJudgeNestedScrollView(theme) {}
inline fun Activity.themedJudgeNestedScrollView(theme: Int = 0, init: (@AnkoViewDslMarker JudgeNestedScrollView).() -> Unit): JudgeNestedScrollView {
    return ankoView(::JudgeNestedScrollView, theme) { init() }
}
