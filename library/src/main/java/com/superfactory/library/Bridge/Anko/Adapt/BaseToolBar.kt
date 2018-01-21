package com.superfactory.library.Bridge.Anko.Adapt

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.BindingExtensions.getActionBarColor
import com.superfactory.library.Bridge.Anko.BindingExtensions.getActionBarSize
import com.superfactory.library.Bridge.Anko.viewextensions.themedToolbar_v7
import com.superfactory.library.Bridge.Model.ToolbarBindingModel
import com.superfactory.library.Context.BaseActivity
import com.superfactory.library.Context.BaseFragment
import com.superfactory.library.R
import com.superfactory.library.Utils.StatusBarUtil
import org.jetbrains.anko.*


/**
 * Created by vicky on 2018.01.19.
 *
 * @Author vicky
 * @Date 2018年01月19日  15:57:43
 * @ClassName 这里输入你的类名(或用途)
 */
open class BaseToolBar<V, A>(model: V) : BindingComponent<A, V>(model) {
    override fun createViewWithBindings(ui: AnkoContext<A>): View = with(ui) {
        themedToolbar_v7(R.style.mToolbarStyle) {
            id = R.id.toolbar
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                popupTheme = R.style.ThemeOverlay_AppCompat_Light
            }
            lparams {
                width = matchParent
                height = wrapContent
                topPadding = StatusBarUtil.getStatusBarHeight(ctx)
                backgroundColor = getActionBarColor(ctx)
                minimumHeight = getActionBarSize(ctx)
            }

            var tv: TextView? = null
            val root = relativeLayout {
                backgroundColor = Color.TRANSPARENT
                tv = textView {
                    //            val tv = themedTextView(R.style.AppTheme_AppBarOverlay_TitleTextStyle) {
                    text = ""
                }.lparams {
                    width = wrapContent
                    height = wrapContent
                    centerInParent()
//                    gravity = Gravity.CENTER
                }
            }.lparams {
                width = matchParent
                height = wrapContent
                gravity = Gravity.CENTER
                bindSelf{
                    (it as ToolbarBindingModel).leftPadding
                }.toView(this@themedToolbar_v7){view,value->
                    if (value==null)return@toView
                    leftPadding = this@themedToolbar_v7.contentInsetStart + this@themedToolbar_v7.paddingLeft
                    +dip(value)
                }

                 bindSelf{
                    (it as ToolbarBindingModel).rightPadding
                }.toView(this@themedToolbar_v7){view,value->
                    if (value==null)return@toView
                    leftPadding = this@themedToolbar_v7.contentInsetEnd + this@themedToolbar_v7.paddingRight
                    +dip(value)
                }
            }


            if (viewModel != null && viewModel is ToolbarBindingModel) {
                bindSelf {
                    (it as ToolbarBindingModel).backgroundColor
                }.toView(this) { view, value ->
                    if (value == null) return@toView
                    view.setBackgroundColor(value)
                }

                bindSelf {
                    (it as ToolbarBindingModel).title
                }.toView(tv!!) { view, value ->
                    if (value == null) return@toView
                    view.setText(value)
                }

                bindSelf {
                    (it as ToolbarBindingModel).rightView
                }.toView(root) { view, value ->
                    if (value != null) {
                        try {
                            val old = view.find<View>(R.id.toolbar_right)
                            view.removeView(old)
                        } catch (ignored: Exception) {
                        }
                        value.id = R.id.toolbar_right

                        val lp = RelativeLayout.LayoutParams(wrapContent, wrapContent)
//                        lp.gravity=Gravity.END
                        lp.centerVertically()
                        lp.alignParentEnd()

//                        lp.rightMargin +=
                        view.addView(value, lp)
                    }
                }

                bindSelf {
                    (it as ToolbarBindingModel).rightIcon
                }.toView(root) { view, value ->
                    if (value == null) return@toView
                    val v: ImageView?
                    if (value is Int) {
                        if (value > 0) {
                            v = ImageView(ctx)
                            v.backgroundResource = value
                        } else return@toView
                    } else if (value is Drawable) {
                        v = ImageView(ctx)
                        v.backgroundDrawable = value
                    } else {
                        return@toView
                    }
                    v.id = R.id.toolbar_right
                    if (viewModel == null) return@toView
                    v.scaleType = ImageView.ScaleType.FIT_XY
                    (viewModel as ToolbarBindingModel).rightView.value = v
                    (viewModel as ToolbarBindingModel).rightView.notifyChange(ToolbarBindingModel::rightView)
                }



                bindSelf {
                    (it as ToolbarBindingModel).leftView
                }.toView(root) { view, value ->
                    if (value != null) {
                        try {
                            val old = view.find<View>(R.id.toolbar_left)
                            view.removeView(old)
                        } catch (ignored: Exception) {
                        }
                        value.id = R.id.toolbar_left

                        value.backgroundColor = Color.YELLOW
                        val lp = RelativeLayout.LayoutParams(wrapContent, wrapContent)
                        lp.centerVertically()
                        lp.alignParentStart()

//                        lp.gravity=Gravity.START
//                        lp.leftMargin
                        view.addView(value, lp)
                    }
                }

                bindSelf {
                    (it as ToolbarBindingModel).leftIcon
                }.toView(root) { view, value ->
                    if (value == null) return@toView
                    val v: ImageView?
                    if (value is Int) {
                        if (value > 0) {
                            v = ImageView(ctx)
                            v.backgroundResource = value
                        } else return@toView
                    } else if (value is Drawable) {
                        v = ImageView(ctx)
                        v.backgroundDrawable = value
                    } else {
                        return@toView
                    }
                    v.id = R.id.toolbar_left
                    if (viewModel == null) return@toView
                    v.scaleType = ImageView.ScaleType.FIT_XY
                    (viewModel as ToolbarBindingModel).leftView.value = v
                    (viewModel as ToolbarBindingModel).leftView.notifyChange(ToolbarBindingModel::leftView)
                }

            }
        }
    }

    fun <V, A : BaseActivity<V, A>> initToolbar(owner: A, toolbar: Toolbar) {
        owner.setSupportActionBar(toolbar)
    }

    fun <V, A : BaseFragment<V, A>> initToolbar(owner: A, toolbar: Toolbar) {
        (owner.activity as BaseActivity<*, *>).setSupportActionBar(toolbar)
    }

    fun <V, A : BaseActivity<V, A>> getActionBar(owner: A): ActionBar? {
        return owner.supportActionBar
    }

    fun <V, A : BaseFragment<V, A>> getActionBar(owner: A): ActionBar? {
        return (owner.activity as BaseActivity<*, *>).getSupportActionBar()
    }

    fun setAttribution(actionBar: ActionBar, toolbarView: Toolbar) {
        bindSelf {
            (it as ToolbarBindingModel).displayNavigator
        }.toView(toolbarView) { view, value ->
            if (value == null) return@toView
            if (actionBar == null) return@toView
            displayToolbar(actionBar, value)
        }

        displayToolbar(actionBar, (viewModel as ToolbarBindingModel).displayNavigator.value)
    }

    private fun displayToolbar(actionBar: ActionBar, value: Boolean) {
        // 显示应用的Logo
        actionBar.setDisplayShowHomeEnabled(value)
        actionBar.setDisplayUseLogoEnabled(value)
        //            actionBar.setLogo(R.mipmap.ic_launcher)
        // 显示标题和子标题
        actionBar.setDisplayShowTitleEnabled(value)
    }

}
