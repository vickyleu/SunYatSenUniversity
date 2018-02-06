package com.superfactory.library.Context

import android.app.Application
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.ScreenSizeExtension
import com.superfactory.library.Debuger
import com.superfactory.library.Graphics.KDialog.Prompt.BasePromptParams
import com.superfactory.library.Utils.StatusBarUtil
import org.jetbrains.anko.*


/**
 * Created by vicky on 2018/2/1.
 */
abstract class BasePrompt<V : BasePromptParams, A : BasePrompt<V, A>> : Dialog {

    constructor(ctx: Context) : super(ctx) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        doAsync {
            uiThread {
                setContentView(createView())
            }
        }
    }

    constructor(ctx: Context, theme: Int) : super(ctx, theme) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        doAsync {
            uiThread {
                setContentView(createView())
            }
        }
    }

    private val TAG = javaClass.simpleName
    private var layout: BindingComponent<A, V>? = null
    var viewModel: V? = null
    protected open var binder: BindingComponent<*, *>? = null
        get() = layout

    protected open fun onLoadedModel(viewModel: V) {}


    private fun createView(): View? {
        var view: View? = null
        if (viewModel == null) {
            viewModel = newViewModel()
        } else {
            Debuger.printMsg(this, TAG)
        }
        if (viewModel == null) return null
        viewModel!!.apply {
            this.screenSize = getAppNoStatusBarSize(context)
            setPrompt(this)
        }.apply {
                    layout = newComponent(this).apply {
                        val that = this
                        @Suppress("UNCHECKED_CAST")
                        view = createViewWithBindings(AnkoContextImpl(this@BasePrompt.context, this@BasePrompt as A,
                                false)).apply {
                            initBaseBinding(this, that, viewModel = viewModelSafe)
                        }.apply {
                                    register.bindAll()
                                }
                        notifyChanges()
                    }
                }
        if (viewModel != null) {
            onLoadedModel(viewModel!!)
        }
        if (view == null) {
            throw RuntimeException(javaClass.simpleName + "创建view为空")
        }
        return view
    }


    private fun getAppNoStatusBarSize(ctx: Context): ScreenSizeExtension {
        val noStatusSize = ScreenSizeExtension()
        val height = StatusBarUtil.getStatusBarHeight(ctx)
        val screenSizeExtension = getAppOverSize(ctx)
        noStatusSize.width = screenSizeExtension.width
        noStatusSize.height = screenSizeExtension.height - height
        noStatusSize.density = screenSizeExtension.density
        noStatusSize.densityDpi = screenSizeExtension.densityDpi
        return noStatusSize;
    }

    private fun getAppOverSize(ctx: Context?): ScreenSizeExtension {
        if (ctx == null) {
            return ScreenSizeExtension()
        }
        var appCtx: BaseApp? = null
        if (ctx is Application) {
            appCtx = ctx as BaseApp?
        } else {
            appCtx = ctx.applicationContext as BaseApp?
        }
        if (appCtx == null) {
            return ScreenSizeExtension()
        }
        return appCtx.mScreenSizeExtension;
    }

    private fun initBaseBinding(rootView: View, bindingComponent: BindingComponent<A, V>, viewModel: V) {
        var lp = (rootView as? ViewGroup)?.layoutParams
        if (lp == null) {
            lp = ViewGroup.LayoutParams(matchParent, wrapContent)
            rootView.layoutParams = lp
        }

        bindingComponent.bindSelf(viewModel::backgroundColor) { it.backgroundColor.value }.toView(rootView) { view, value ->
            if (value != null && value != 0) {
                view.backgroundColor = value
            }
        }
        bindingComponent.bindSelf(viewModel::cancelable) { it.cancelable.value }.toView(rootView) { view, value ->
            if (value != null) {
                this.setCancelable(value)
            }
        }
        bindingComponent.bindSelf(viewModel::touchCancelable) { it.touchCancelable.value }.toView(rootView) { view, value ->
            if (value != null) {
                this.setCancelable(value)
            }
        }

        bindingComponent.bindSelf(viewModel::width) { it.width.value }.toView(rootView) { view, value ->
            if (value != null) {
                var len: Int = value
                if (value == 0) {
                    len = wrapContent
                }
                view.layoutParams.width = len
                val window = this.window
                val p = window.attributes // 获取对话框当前的参数值
                p.width = len// 改变的是dialog框在屏幕中的位置而不是大小
                window.attributes = p
            }
        }
        bindingComponent.bindSelf(viewModel::height) { it.height.value }.toView(rootView) { view, value ->
            if (value != null) {
                var len: Int = value
                if (value == 0) {
                    len = wrapContent
                }
                view.layoutParams.height = len
                val window = this.window
                val p = window.attributes // 获取对话框当前的参数值
                p.height = len// 改变的是dialog框在屏幕中的位置而不是大小
                window.attributes = p
            }
        }

    }


    abstract fun newViewModel(): V

    abstract fun newComponent(viewModel: V): BindingComponent<A, V>

    override fun onSaveInstanceState(): Bundle {
        return super.onSaveInstanceState()
    }

    override fun cancel() {
        super.cancel()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun show() {

        super.show()
    }

    override fun isShowing(): Boolean {
        return super.isShowing()
    }

    override fun setCanceledOnTouchOutside(cancel: Boolean) {
        super.setCanceledOnTouchOutside(cancel)
    }

    final override fun setContentView(view: View?) {
        super.setContentView(view)
    }

    override fun setCancelable(flag: Boolean) {
        super.setCancelable(flag)
    }

    override fun create() {
        super.create()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun dismiss() {
        super.dismiss()
        layout?.destroyView()
        layout = null
    }
}

typealias PromptTransfer = ((Int, Any?) -> Unit)

inline fun takeRoundRectShape(radius: Int): ShapeDrawable {
    return takeRoundRectShape(radius.toFloat())
}

inline fun takeRoundRectShape(radius: Float): ShapeDrawable {
    return takeRoundRectShape(radius, Color.WHITE)
}

inline fun takeRoundRectShape(radius: Int, color: Int): ShapeDrawable {
    return takeRoundRectShape(radius.toFloat(), color)
}

inline fun takeRoundRectShape(radius: Float, color: Int): ShapeDrawable {
    val radii = floatArrayOf(radius, radius, radius, radius, radius, radius, radius, radius)
    val shapeDrawable = ShapeDrawable(RoundRectShape(radii, null, null))
    shapeDrawable.paint.color = color
    shapeDrawable.paint.style = Paint.Style.FILL
    shapeDrawable.paint.isAntiAlias = true
    return shapeDrawable
}
