package com.superfactory.library.Bridge.Anko.DslView

import android.content.Context
import android.view.ViewManager
import com.superfactory.library.Graphics.CircleImageView
import org.jetbrains.anko.AnkoViewDslMarker
import org.jetbrains.anko.custom.ankoView

/**
 * Created by vicky on 2018.01.22.
 *
 * @Author vicky
 * @Date 2018年01月22日  20:17:00
 * @ClassName 这里输入你的类名(或用途)
 */
@PublishedApi
internal object `$$Anko$Factories$CircleImageView` {
    val IMAGE_VIEW = { ctx: Context -> CircleImageView(ctx) }
}

inline fun ViewManager.circleImage(): CircleImageView = circleImage() {}
inline fun ViewManager.circleImage(init: (@AnkoViewDslMarker CircleImageView).() -> Unit): CircleImageView {
    return ankoView(`$$Anko$Factories$CircleImageView`.IMAGE_VIEW, theme = 0) { init() }
}

inline fun ViewManager.themedCircleImage(theme: Int = 0): CircleImageView = themedCircleImage(theme) {}
inline fun ViewManager.themedCircleImage(theme: Int = 0, init: (@AnkoViewDslMarker CircleImageView).() -> Unit): CircleImageView {
    return ankoView(`$$Anko$Factories$CircleImageView`.IMAGE_VIEW, theme) { init() }
}

inline fun ViewManager.circleImage(imageDrawable: android.graphics.drawable.Drawable?): CircleImageView {
    return ankoView(`$$Anko$Factories$CircleImageView`.IMAGE_VIEW, theme = 0) {
        setImageDrawable(imageDrawable)
    }
}

inline fun ViewManager.circleImage(imageDrawable: android.graphics.drawable.Drawable?, init: (@AnkoViewDslMarker CircleImageView).() -> Unit): CircleImageView {
    return ankoView(`$$Anko$Factories$CircleImageView`.IMAGE_VIEW, theme = 0) {
        init()
        setImageDrawable(imageDrawable)
    }
}

inline fun ViewManager.themedCircleImage(imageDrawable: android.graphics.drawable.Drawable?, theme: Int): CircleImageView {
    return ankoView(`$$Anko$Factories$CircleImageView`.IMAGE_VIEW, theme) {
        setImageDrawable(imageDrawable)
    }
}

inline fun ViewManager.themedCircleImage(imageDrawable: android.graphics.drawable.Drawable?, theme: Int, init: (@AnkoViewDslMarker CircleImageView).() -> Unit): CircleImageView {
    return ankoView(`$$Anko$Factories$CircleImageView`.IMAGE_VIEW, theme) {
        init()
        setImageDrawable(imageDrawable)
    }
}

inline fun ViewManager.circleImage(imageResource: Int): CircleImageView {
    return ankoView(`$$Anko$Factories$CircleImageView`.IMAGE_VIEW, theme = 0) {
        setImageResource(imageResource)
    }
}

inline fun ViewManager.circleImage(imageResource: Int, init: (@AnkoViewDslMarker CircleImageView).() -> Unit): CircleImageView {
    return ankoView(`$$Anko$Factories$CircleImageView`.IMAGE_VIEW, theme = 0) {
        init()
        setImageResource(imageResource)
    }
}

inline fun ViewManager.themedCircleImage(imageResource: Int, theme: Int): CircleImageView {
    return ankoView(`$$Anko$Factories$CircleImageView`.IMAGE_VIEW, theme) {
        setImageResource(imageResource)
    }
}

inline fun ViewManager.themedCircleImage(imageResource: Int, theme: Int, init: (@AnkoViewDslMarker CircleImageView).() -> Unit): CircleImageView {
    return ankoView(`$$Anko$Factories$CircleImageView`.IMAGE_VIEW, theme) {
        init()
        setImageResource(imageResource)
    }
}