@file:JvmName("SmartRefreshLayoutViewsKt")

package com.superfactory.library.Bridge.Anko.DslView

import android.app.Activity
import android.content.Context
import android.view.ViewManager
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import org.jetbrains.anko.AnkoViewDslMarker
import org.jetbrains.anko.custom.ankoView

@PublishedApi
internal object `$$Anko$Factories$SmartRefreshLayout` {
    val SMART_REFRESH_LAYOUT = { ctx: Context -> _SmartRefreshLayout(ctx).apply {
        isEnableRefresh = false
        isEnableLoadmore = false
    } }
}

inline fun ViewManager.refresh(): SmartRefreshLayout = refresh() {}
inline fun ViewManager.refresh(init: (@AnkoViewDslMarker _SmartRefreshLayout).() -> Unit): SmartRefreshLayout {
    return ankoView(`$$Anko$Factories$SmartRefreshLayout`.SMART_REFRESH_LAYOUT, theme = 0) { init() }
}

inline fun ViewManager.themedRefresh(theme: Int = 0): SmartRefreshLayout = themedRefresh(theme) {}
inline fun ViewManager.themedRefresh(theme: Int = 0, init: (@AnkoViewDslMarker _SmartRefreshLayout).() -> Unit): SmartRefreshLayout {
    return ankoView(`$$Anko$Factories$SmartRefreshLayout`.SMART_REFRESH_LAYOUT, theme) { init() }
}

inline fun Context.refresh(): SmartRefreshLayout = refresh() {}
inline fun Context.refresh(init: (@AnkoViewDslMarker _SmartRefreshLayout).() -> Unit): SmartRefreshLayout {
    return ankoView(`$$Anko$Factories$SmartRefreshLayout`.SMART_REFRESH_LAYOUT, theme = 0) { init() }
}

inline fun Context.themedRefresh(theme: Int = 0): SmartRefreshLayout = themedRefresh(theme) {}
inline fun Context.themedRefresh(theme: Int = 0, init: (@AnkoViewDslMarker _SmartRefreshLayout).() -> Unit): SmartRefreshLayout {
    return ankoView(`$$Anko$Factories$SmartRefreshLayout`.SMART_REFRESH_LAYOUT, theme) { init() }
}

inline fun Activity.refresh(): SmartRefreshLayout = refresh() {}
inline fun Activity.refresh(init: (@AnkoViewDslMarker _SmartRefreshLayout).() -> Unit): SmartRefreshLayout {
    return ankoView(`$$Anko$Factories$SmartRefreshLayout`.SMART_REFRESH_LAYOUT, theme = 0) { init() }
}

inline fun Activity.themedRefresh(theme: Int = 0): SmartRefreshLayout = themedRefresh(theme) {}
inline fun Activity.themedRefresh(theme: Int = 0, init: (@AnkoViewDslMarker _SmartRefreshLayout).() -> Unit): SmartRefreshLayout {
    return ankoView(`$$Anko$Factories$SmartRefreshLayout`.SMART_REFRESH_LAYOUT, theme) { init() }
}

