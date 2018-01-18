@file:JvmName("HorizontalLayoutViewsKt")

package com.superfactory.library.Bridge.Anko.DslView

import android.app.Activity
import android.content.Context
import android.view.ViewManager
import android.widget.LinearLayout
import org.jetbrains.anko.AnkoViewDslMarker
import org.jetbrains.anko._LinearLayout
import org.jetbrains.anko.custom.ankoView

@PublishedApi
internal object `$$Anko$Factories$HorizontalLayout` {
    val HORIZONTAL_LAYOUT_FACTORY = { ctx: Context ->
        val view = _LinearLayout(ctx)
        view.orientation = LinearLayout.HORIZONTAL
        view
    }
}

inline fun ViewManager.horizontalLayout(): LinearLayout = horizontalLayout() {}
inline fun ViewManager.horizontalLayout(init: (@AnkoViewDslMarker _LinearLayout).() -> Unit): LinearLayout {
    return ankoView(`$$Anko$Factories$HorizontalLayout`.HORIZONTAL_LAYOUT_FACTORY, theme = 0) { init() }
}

inline fun ViewManager.themedHorizontalLayout(theme: Int = 0): LinearLayout = themedHorizontalLayout(theme) {}
inline fun ViewManager.themedHorizontalLayout(theme: Int = 0, init: (@AnkoViewDslMarker _LinearLayout).() -> Unit): LinearLayout {
    return ankoView(`$$Anko$Factories$HorizontalLayout`.HORIZONTAL_LAYOUT_FACTORY, theme) { init() }
}

inline fun Context.horizontalLayout(): LinearLayout = horizontalLayout() {}
inline fun Context.horizontalLayout(init: (@AnkoViewDslMarker _LinearLayout).() -> Unit): LinearLayout {
    return ankoView(`$$Anko$Factories$HorizontalLayout`.HORIZONTAL_LAYOUT_FACTORY, theme = 0) { init() }
}

inline fun Context.themedHorizontalLayout(theme: Int = 0): LinearLayout = themedHorizontalLayout(theme) {}
inline fun Context.themedHorizontalLayout(theme: Int = 0, init: (@AnkoViewDslMarker _LinearLayout).() -> Unit): LinearLayout {
    return ankoView(`$$Anko$Factories$HorizontalLayout`.HORIZONTAL_LAYOUT_FACTORY, theme) { init() }
}

inline fun Activity.horizontalLayout(): LinearLayout = horizontalLayout() {}
inline fun Activity.horizontalLayout(init: (@AnkoViewDslMarker _LinearLayout).() -> Unit): LinearLayout {
    return ankoView(`$$Anko$Factories$HorizontalLayout`.HORIZONTAL_LAYOUT_FACTORY, theme = 0) { init() }
}

inline fun Activity.themedHorizontalLayout(theme: Int = 0): LinearLayout = themedHorizontalLayout(theme) {}
inline fun Activity.themedHorizontalLayout(theme: Int = 0, init: (@AnkoViewDslMarker _LinearLayout).() -> Unit): LinearLayout {
    return ankoView(`$$Anko$Factories$HorizontalLayout`.HORIZONTAL_LAYOUT_FACTORY, theme) { init() }
}

