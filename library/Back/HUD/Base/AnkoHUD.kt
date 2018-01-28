package com.superfactory.library.Graphics.HUD.Base

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.LinearLayout
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.BindingExtensions.getAppNoStatusBarSize
import com.superfactory.library.R
import org.jetbrains.anko.AnkoContextImpl

/**
 * Created by vicky on 2018/1/28.
 */
abstract class AnkoHUD<V : HudModel, A : AnkoHUD<V, A>>(var ctx: Context) {

    private val interceptBack = arrayOf(true)

    internal fun setInterceptBack(intercept: Boolean) {
        this.interceptBack[0] = intercept
    }

    private var myNameIsHUD: Dialog? = null

    internal fun hud(): Dialog? {
        return myNameIsHUD
    }

    private var layout: BindingComponent<A, V>? = null
    var viewModel: V? = null

    init {
        viewModel = newViewModel(interceptBack)
        var view: View? = null
        layout = newComponent(viewModel).apply {
            view = createView(AnkoContextImpl(ctx, this@AnkoHUD as A, false))
            notifyChanges()
        }
        if (view == null) throw IllegalArgumentException("AnkoDialog无法加载视图")
        if (viewModel != null) {
            onLoadedModel(viewModel!!)
        }

        initView(view!!)
        // 创建自定义样式的Dialog
        myNameIsHUD = object : Dialog(view!!.context, R.style.loading_dialog) {
            override fun onBackPressed() {
                if (interceptBack[0]) {
                    return
                }
                this@AnkoHUD.close()
            }
        }
        // 设置返回键无效
        myNameIsHUD!!.setCancelable(!interceptBack[0])
        myNameIsHUD!!.setContentView(view!!, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT))
        myNameIsHUD!!.setOnDismissListener {

        }
        if (view?.context!=null){
            if (viewModel?._width ?:-3==-3){
                val size=layout?.getAppNoStatusBarSize(view?.context!!)
                if (size!=null){
                    viewModel?.sizeOfHud( size.width,size.height)
                }
            }
        }
        if (myNameIsHUD != null) {
            onLoadedDialog(myNameIsHUD!!)
        }
        viewModel?.hud = myNameIsHUD
    }

    abstract fun initView(view: View)

    abstract fun onLoadedDialog(hud: Dialog)


    protected open fun onLoadedModel(viewModel: V) {

    }

    abstract fun newComponent(v: V?): BindingComponent<A, V>

    abstract fun newViewModel(interceptBack: Array<Boolean>): V?


    /**
     * 让这个dialog消失，在拦截back事件的情况下一定要调用这个方法！
     * 在调用了该方法之后如需再次使用loadingDialog，请新创建一个
     * LoadingDialog对象。
     */
    protected open fun close() {
        if (myNameIsHUD != null) {
            myNameIsHUD!!.dismiss()
            myNameIsHUD = null
        }
    }


}