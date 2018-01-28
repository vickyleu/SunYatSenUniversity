//package com.superfactory.library.Bridge.Anko.DslView
//
//import android.content.Context
//import android.view.ViewManager
//import com.superfactory.library.Graphics.HUD.View.LVCircularRing
//import com.superfactory.library.Graphics.HUD.View.LoadCircleView
//import com.superfactory.library.Graphics.HUD.View.RightDiaView
//import com.superfactory.library.Graphics.HUD.View.WrongDiaView
//import org.jetbrains.anko.AnkoViewDslMarker
//import org.jetbrains.anko.custom.ankoView
//
///**
// * Created by vicky on 2018.01.22.
// *
// * @Author vicky
// * @Date 2018年01月22日  20:17:00
// * @ClassName 这里输入你的类名(或用途)
// */
//@PublishedApi
//internal object `$$Anko$Factories$Ring` {
//    val CircularRING = { ctx: Context -> LVCircularRing(ctx) }
//    val CircularView = { ctx: Context -> LoadCircleView(ctx) }
//    val Right = { ctx: Context -> RightDiaView(ctx) }
//    val Wrong = { ctx: Context -> WrongDiaView(ctx) }
//}
//
//inline fun ViewManager.circularRing(): LVCircularRing = circularRing() {}
//inline fun ViewManager.circularRing(init: (@AnkoViewDslMarker LVCircularRing).() -> Unit): LVCircularRing {
//    return ankoView(`$$Anko$Factories$Ring`.CircularRING, theme = 0) { init() }
//}
//
//inline fun ViewManager.themedCircularRing(theme: Int = 0): LVCircularRing = themedCircularRing(theme) {}
//inline fun ViewManager.themedCircularRing(theme: Int = 0, init: (@AnkoViewDslMarker LVCircularRing).() -> Unit): LVCircularRing {
//    return ankoView(`$$Anko$Factories$Ring`.CircularRING, theme) { init() }
//}
//
//inline fun ViewManager.circularView(): LoadCircleView = circularView() {}
//inline fun ViewManager.circularView(init: (@AnkoViewDslMarker LoadCircleView).() -> Unit): LoadCircleView {
//    return ankoView(`$$Anko$Factories$Ring`.CircularView, theme = 0) { init() }
//}
//
//inline fun ViewManager.themedCircularView(theme: Int = 0): LoadCircleView = themedCircularView(theme) {}
//inline fun ViewManager.themedCircularView(theme: Int = 0, init: (@AnkoViewDslMarker LoadCircleView).() -> Unit): LoadCircleView {
//    return ankoView(`$$Anko$Factories$Ring`.CircularView, theme) { init() }
//}
//
//inline fun ViewManager.rightDiaView(): RightDiaView = rightDiaView() {}
//inline fun ViewManager.rightDiaView(init: (@AnkoViewDslMarker RightDiaView).() -> Unit): RightDiaView {
//    return ankoView(`$$Anko$Factories$Ring`.Right, theme = 0) { init() }
//}
//
//inline fun ViewManager.themedRightDiaView(theme: Int = 0): RightDiaView = themedRightDiaView(theme) {}
//inline fun ViewManager.themedRightDiaView(theme: Int = 0, init: (@AnkoViewDslMarker RightDiaView).() -> Unit): RightDiaView {
//    return ankoView(`$$Anko$Factories$Ring`.Right, theme) { init() }
//}
//
//inline fun ViewManager.wrongDiaView(): WrongDiaView = wrongDiaView() {}
//inline fun ViewManager.wrongDiaView(init: (@AnkoViewDslMarker WrongDiaView).() -> Unit): WrongDiaView {
//    return ankoView(`$$Anko$Factories$Ring`.Wrong, theme = 0) { init() }
//}
//
//inline fun ViewManager.themedWrongDiaView(theme: Int = 0): WrongDiaView = themedWrongDiaView(theme) {}
//inline fun ViewManager.themedWrongDiaView(theme: Int = 0, init: (@AnkoViewDslMarker WrongDiaView).() -> Unit): WrongDiaView {
//    return ankoView(`$$Anko$Factories$Ring`.Wrong, theme) { init() }
//}
