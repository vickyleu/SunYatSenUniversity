package com.superfactory.library.Context

import android.app.Application
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.BindingExtensions.getAppOverSize
import com.superfactory.library.Bridge.Anko.ScreenSizeExtension
import com.superfactory.library.Debuger
import com.superfactory.library.Graphics.KDialog.Prompt.BasePromptParams
import com.superfactory.library.Utils.StatusBarUtil
import org.jetbrains.anko.AnkoContextImpl
import org.jetbrains.anko.backgroundColor

/**
 * Created by vicky on 2018/2/1.
 */
abstract class BasePrompt<V : BasePromptParams, A : BasePrompt<V, A>>(ctx: Context) : Dialog(ctx) {
    private val TAG = javaClass.simpleName
    private var layout: BindingComponent<A, V>? = null
    var viewModel: V? = null
    protected open var binder: BindingComponent<*, *>? = layout

    protected open fun onLoadedModel(viewModel: V) {}


    init {
        setContentView(createView())
    }

    private fun createView(): View? {
        var view: View? = null
        if (viewModel == null) {
            viewModel = newViewModel()
        } else {
            Debuger.printMsg(this, TAG)
        }
        if (viewModel == null) return null
        viewModel!!.apply {
            this.screenSize=getAppNoStatusBarSize(context)
            setPrompt(this)
        }.apply {
                    layout = newComponent(this).apply {
                        view = createViewWithBindings(AnkoContextImpl(this@BasePrompt.context, this@BasePrompt as A,
                                false)).apply {
                            initBaseBinding(this, binder!! as BindingComponent<A, V>, viewModelSafe)
                        }.apply { register.bindAll() }
                        notifyChanges()
                    }
                }
        if (viewModel != null) {
            onLoadedModel(viewModel!!)
        }
        if (view == null) {
            throw RuntimeException(javaClass.simpleName + "创建view为空")
        }
        return view;
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
        var appCtx: BaseApp ? = null
        if (ctx is Application) {
            appCtx = ctx as BaseApp ?
        } else {
            appCtx = ctx.applicationContext as BaseApp ?
        }
        if (appCtx == null) {
            return ScreenSizeExtension()
        }
        return appCtx.mScreenSizeExtension;
    }

    private fun initBaseBinding(rootView: View, bindingComponent: BindingComponent<A, V>, viewModel: V) {
        bindingComponent.bindSelf(viewModel::backgroundColor) { it.backgroundColor.value }.toView(rootView) { view, value ->
            if (value != null && value != 0) {
                view.backgroundColor = value
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