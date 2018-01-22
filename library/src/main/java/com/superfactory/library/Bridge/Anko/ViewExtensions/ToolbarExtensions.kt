package com.superfactory.library.Bridge.Anko.ViewExtensions

import android.app.Activity
import android.content.Context
import android.support.v7.widget.Toolbar
import android.view.ViewManager
import com.superfactory.library.Bridge.Anko.DslView.Toolbar_v7
import org.jetbrains.anko.AnkoViewDslMarker
import org.jetbrains.anko.custom.ankoView

/**
 * Created by vicky on 2018.01.20.
 *
 * @Author vicky
 * @Date 2018年01月20日  14:01:05
 * @ClassName 这里输入你的类名(或用途)
 */


@PublishedApi
internal object `$$Anko$Factories$extentionsViewGroup` {
    val TOOLBAR = { ctx: Context -> Toolbar_v7(ctx) }
}

inline fun ViewManager.toolbar(): Toolbar = toolbar_v7() {}
inline fun ViewManager.toolbar_v7(init: (@AnkoViewDslMarker Toolbar_v7).() -> Unit): Toolbar {
    return ankoView(`$$Anko$Factories$extentionsViewGroup`.TOOLBAR, theme = 0) { init() }
}

inline fun ViewManager.themedToolbar(theme: Int = 0): Toolbar = themedToolbar_v7(theme) {}
inline fun ViewManager.themedToolbar_v7(theme: Int = 0, init: (@AnkoViewDslMarker Toolbar_v7).() -> Unit): Toolbar {
    return ankoView(`$$Anko$Factories$extentionsViewGroup`.TOOLBAR, theme) { init() }
}

inline fun Context.toolbar(): Toolbar = toolbar_v7() {}
inline fun Context.toolbar_v7(init: (@AnkoViewDslMarker Toolbar_v7).() -> Unit): Toolbar {
    return ankoView(`$$Anko$Factories$extentionsViewGroup`.TOOLBAR, theme = 0) { init() }
}

inline fun Context.themedToolbar(theme: Int = 0): Toolbar = themedToolbar_v7(theme) {}
inline fun Context.themedToolbar_v7(theme: Int = 0, init: (@AnkoViewDslMarker Toolbar_v7).() -> Unit): Toolbar {
    return ankoView(`$$Anko$Factories$extentionsViewGroup`.TOOLBAR, theme) { init() }
}

inline fun Activity.toolbar(): Toolbar = toolbar_v7() {}
inline fun Activity.toolbar_v7(init: (@AnkoViewDslMarker Toolbar_v7).() -> Unit): Toolbar {
    return ankoView(`$$Anko$Factories$extentionsViewGroup`.TOOLBAR, theme = 0) { init() }
}

inline fun Activity.themedToolbar(theme: Int = 0): Toolbar = themedToolbar_v7(theme) {}
inline fun Activity.themedToolbar_v7(theme: Int = 0, init: (@AnkoViewDslMarker Toolbar_v7).() -> Unit): Toolbar {
    return ankoView(`$$Anko$Factories$extentionsViewGroup`.TOOLBAR, theme) { init() }
}
