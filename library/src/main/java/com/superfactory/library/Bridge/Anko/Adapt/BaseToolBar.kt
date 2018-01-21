package com.superfactory.library.Bridge.Anko.Adapt

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
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
import kotlin.reflect.full.declaredMemberFunctions


/**
 * Created by vicky on 2018.01.19.
 *
 * @Author vicky
 * @Date 2018年01月19日  15:57:43
 * @ClassName 这里输入你的类名(或用途)
 */
open class BaseToolBar<V, A>(model: V) : BindingComponent<A, V>(model) {
    override fun createViewWithBindings(ui: AnkoContext<A>): View = with(ui) {
        themedToolbar_v7(R.style.ThemeOverlay_AppCompat_Dark_ActionBar) {
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

            val tv = textView {
                //            val tv = themedTextView(R.style.AppTheme_AppBarOverlay_TitleTextStyle) {
                text = ""
            }.lparams {
                width = wrapContent
                height = wrapContent
                gravity = Gravity.CENTER
            }


            val right = frameLayout {
                backgroundColor=Color.TRANSPARENT
                visibility=View.GONE
            }.lparams {
                width = wrapContent
                height = wrapContent
                gravity = Gravity.CENTER_VERTICAL and Gravity.RIGHT
            }


            if (viewModel != null && viewModel is ToolbarBindingModel) {
//                bindSelf {
//                    (it as ToolbarBindingModel).backgroundColor
//                }.toToolbarColor(this)

//                bindSelf {
//                    (it as ToolbarBindingModel).title
//                }.toToolbarTitle(tv)


                bindSelf {
                    (it as ToolbarBindingModel).backgroundColor
                }.toView(this) { view, value ->
                    if (value == null) return@toView
                    view.setBackgroundColor(value)
                }

                bindSelf {
                    (it as ToolbarBindingModel).title
                }.toView(tv) { view, value ->
                    if (value == null) return@toView
                    view.setText(value)
                }

                bindSelf {
                    (it as ToolbarBindingModel).rightView
                }.toView(right) { view, value ->
                    if (value != null) {
                        try {
                            val old = view.find<View>(R.id.toolbar_right)
                            view.removeView(old)
                        } catch (ignored: Exception) {
                        }
                        value.id = R.id.toolbar_right
                        val lp = Toolbar.LayoutParams(wrapContent, wrapContent)
                        lp.gravity=Gravity.CENTER
                        view.addView(value,lp)
                        view.visibility=View.VISIBLE
                    }
                }

                bindSelf {
                    (it as ToolbarBindingModel).rightIcon
                }.toView(right) { view, value ->
                    if (value == null) return@toView
                    val v: View?
                    if (value is Int) {
                        if (value > 0) {
                            v = View(ctx)
                            v.backgroundResource = value
                        } else return@toView
                    } else if (value is Drawable) {
                        v = View(ctx)
                        v.backgroundDrawable = value
                    } else {
                        return@toView
                    }
                    v.id = R.id.toolbar_right
                    if (viewModel == null) return@toView
                    (viewModel as ToolbarBindingModel).rightView.value = v
                    (viewModel as ToolbarBindingModel).rightView.notifyChange(ToolbarBindingModel::rightView)
                }

                bindSelf {
                    (it as ToolbarBindingModel).navigationIcon
                }.toView(this) { view, value ->
                    if (value == null) return@toView
                    if (value is Int) {
                        if (value > 0)
                            view.setNavigationIcon(value)
                    } else if (value is Drawable) {
                        view.setNavigationIcon(value)
                    } else {

                    }
                }

//                bindSelf {
//                    (it as ToolbarBindingModel).navigationIcon
//                }.toView(this) { view, value ->
//                    if (value == null) return@toView
//
//                }

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
