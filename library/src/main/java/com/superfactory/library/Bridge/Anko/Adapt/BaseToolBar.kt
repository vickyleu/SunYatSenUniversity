package com.superfactory.library.Bridge.Anko.Adapt

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Parcelable
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.BindingExtensions.getActionBarColor
import com.superfactory.library.Bridge.Anko.BindingExtensions.getActionBarSize
import com.superfactory.library.Bridge.Anko.DslView.horizontalLayout
import com.superfactory.library.Bridge.Anko.ObservableFieldImpl
import com.superfactory.library.Bridge.Anko.ViewExtensions.themedToolbar_v7
import com.superfactory.library.Bridge.Anko.bindings.toText
import com.superfactory.library.Bridge.Model.ToolbarBindingModel
import com.superfactory.library.Context.BaseActivity
import com.superfactory.library.Context.BaseFragment
import com.superfactory.library.Context.Extensions.ToolbarExtensions
import com.superfactory.library.Context.Extensions.ToolbarExtensions.Companion.setBackIcon
import com.superfactory.library.Context.Extensions.ToolbarExtensions.Companion.setRightIcon
import com.superfactory.library.Context.Extensions.ToolbarExtensions.Companion.setRightTextColor
import com.superfactory.library.R
import com.superfactory.library.Utils.StatusBarUtil
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick


/**
 * Created by vicky on 2018.01.19.
 *
 * @Author vicky
 * @Date 2018年01月19日  15:57:43
 * @ClassName 这里输入你的类名(或用途)
 */
open class BaseToolBar<V, A>(model: V) : BindingComponent<A, V>(model) {
    companion object {
        enum class ToolbarEvent {
            LEFT,
            RIGHT,
            NONE
        }
    }

    var eventDelegate: ObservableFieldImpl<View.OnClickListener>? = null
    override fun createViewWithBindings(ui: AnkoContext<A>): View = with(ui) {
        themedToolbar_v7(R.style.mToolbarStyle) {
            id = R.id.toolbar
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                popupTheme = R.style.ThemeOverlay_AppCompat_Light
            }
            setContentInsetsAbsolute(0, 0)
            setContentInsetsRelative(0, 0)
            contentInsetStartWithNavigation = 0
            contentInsetEndWithActions = 0


            lparams {
                width = matchParent
                height = getActionBarSize(ctx)//wrapContent
                topPadding = StatusBarUtil.getStatusBarHeightHigherSdk19(ctx)
//                topPadding = StatusBarUtil.getStatusBarHeight(ctx)
                backgroundColor = getActionBarColor(ctx)
                minimumHeight = getActionBarSize(ctx)
            }

            var center: TextView? = null
            var left: LinearLayout? = null
            var right: LinearLayout? = null
            val root = relativeLayout {
                backgroundColor = Color.TRANSPARENT
                center = textView {
                    //            val tv = themedTextView(R.style.AppTheme_AppBarOverlay_TitleTextStyle) {
                    text = ""
                }.lparams {
                    width = wrapContent
                    height = wrapContent
                    centerInParent()
//                    gravity = Gravity.CENTER
                }

                right = horizontalLayout {
                    backgroundColor = Color.TRANSPARENT
                    setVerticalGravity(Gravity.CENTER)
                }.lparams {
                    width = wrapContent
                    height = wrapContent
                    addRule(RelativeLayout.RIGHT_OF, center!!.id)
                    centerVertically()
                    alignParentRight()
                }



                left = horizontalLayout {
                    backgroundColor = Color.TRANSPARENT
                    setVerticalGravity(Gravity.CENTER)
                }.lparams {
                    width = wrapContent
                    height = wrapContent
                    addRule(RelativeLayout.LEFT_OF, center!!.id)
                    centerVertically()
                    alignParentLeft()
                }

            }.lparams {
                width = matchParent
                height = wrapContent
                gravity = Gravity.CENTER
            }

            bindSelf {
                (it as ToolbarBindingModel).leftPadding
            }.toView(root) { view, value ->
                if (value == null) return@toView
                view.leftPadding = (this@themedToolbar_v7.contentInsetStart + this@themedToolbar_v7.paddingLeft + dip(value))
            }

            bindSelf {
                (it as ToolbarBindingModel).rightPadding
            }.toView(root) { view, value ->
                if (value == null) return@toView
                view.rightPadding = (this@themedToolbar_v7.contentInsetEnd + this@themedToolbar_v7.paddingRight + dip(value))
            }

            if (viewModel != null && viewModel is ToolbarBindingModel) {

                val model = (viewModel as? ToolbarBindingModel)
                if (model?.leftView?.value == null &&
                        model?.leftIcon?.value != null) {
                    val property = model.leftIcon
                    property.value = property.value
                } else if (model?.leftView?.value != null) {
                    val property = model.leftView
                    property.value = property.value
                }

                if (model?.rightView?.value == null &&
                        model?.rightIcon?.value != null) {
                    val property = model.rightIcon
                    property.value = property.value
                } else if (model?.rightView?.value != null) {
                    val property = model.rightView
                    property.value = property.value
                }

                if (model?.backgroundColor?.value != null) {
                    val property = model.backgroundColor
                    property.value = property.value
                }

                bindSelf {
                    (it as ToolbarBindingModel).backgroundColor
                }.toView(this) { view, value ->
                    if (value == null) return@toView
                    view.setBackgroundColor(value)
                }

                bindSelf(ToolbarBindingModel::title) {
                    (it as ToolbarBindingModel).title.value
                }.toText(center!!)

//                bindSelf(ToolbarBindingModel::rightTextColor) {
//                    (it as ToolbarBindingModel).rightTextColor.value
//                }.toView(this) { _, value ->
//                    if (value != null && value != 0)
//                        if (!TextUtils.isEmpty((viewModel as? ToolbarBindingModel)?.rightText?.value)) {
//                            setRightIcon((viewModel as? ToolbarBindingModel)?.rightText?.value, context, viewModel)
//                        }
//                }


                if (!TextUtils.isEmpty((viewModel as? ToolbarBindingModel)?.rightText?.value)) {
//                   setRightTextColor((viewModel as? ToolbarBindingModel)?.rightTextColor?.value, context, viewModel)
                    setRightIcon((viewModel as? ToolbarBindingModel)?.rightText?.value, context, viewModel)
                }

                bindSelf(ToolbarBindingModel::eraseRight) { (it as? ToolbarBindingModel)?.eraseRight?.value }.toView(this) { view, value ->
                    if (value != null && value) {
                        try {
                            val old = right?.find<View>(R.id.toolbar_right)
                            view.removeView(old)
                        } catch (ignored: Exception) {
                        }
                        (viewModelSafe as ToolbarBindingModel).eraseRight.setStableValue(false)
                    }
                }

                if (!TextUtils.isEmpty((viewModel as? ToolbarBindingModel)?.leftText?.value)) {
                    setBackIcon((viewModel as? ToolbarBindingModel)?.leftText?.value, context, viewModel)
                }

                bindSelf {
                    (it as ToolbarBindingModel).leftIcon
                }.toView(left!!) { view, value ->
                    if (value == null) return@toView
                    val v: View?
                    val lp = LinearLayout.LayoutParams(wrapContent, wrapContent)
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
                    v.layoutParams = lp
                    v.scaleType = ImageView.ScaleType.FIT_XY
                    (viewModel as ToolbarBindingModel).leftView.value = v
                }

                bindSelf {
                    (it as ToolbarBindingModel).rightIcon
                }.toView(right!!) { view, value ->
                    if (value == null) return@toView
                    val v: View?
                    val lp = LinearLayout.LayoutParams(wrapContent, wrapContent)
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
                    v.layoutParams = lp
                    v.scaleType = ImageView.ScaleType.FIT_XY
                    (viewModel as ToolbarBindingModel).rightView.value = v
                }



                bindSelf {
                    (it as ToolbarBindingModel).leftView
                }.toView(left!!) { view, value ->
                    if (value != null) {
                        try {
                            val old = view.find<View>(R.id.toolbar_left)
                            view.removeView(old)
                        } catch (ignored: Exception) {
                        }
                        value.id = R.id.toolbar_left

                        if (value.parent != null) {
                            val parent = value.parent as ViewGroup
                            parent.removeView(value)
                        }
                        view.addView(value)
                        value.onClick {
                            if (eventDelegate != null && eventDelegate!!.value != null) {
                                if ((viewModelSafe as ToolbarBindingModel).leftClickable.value == null) {
                                    eventDelegate!!.value.onClick(value)
                                } else {
                                    (viewModelSafe as ToolbarBindingModel).leftClickable.value!!.invoke(value)
                                }
                            }
                        }
                    }
                }


                bindSelf {
                    (it as ToolbarBindingModel).rightView
                }.toView(right!!) { view, value ->
                    if (value != null) {
                        try {
                            val old = view.find<View>(R.id.toolbar_right)
                            view.removeView(old)
                        } catch (ignored: Exception) {
                        }
                        value.id = R.id.toolbar_right
                        if (value.parent != null) {
                            val parent = value.parent as ViewGroup
                            parent.removeView(value)
                        }
                        view.addView(value)
                        value.onClick {
                            if (eventDelegate != null && eventDelegate!!.value != null) {
                                if ((viewModelSafe as ToolbarBindingModel).rightClickable.value == null) {
                                    eventDelegate!!.value.onClick(value)
                                } else {
                                    (viewModelSafe as ToolbarBindingModel).rightClickable.value!!.invoke(value)
                                }
                            }
                        }
                    }
                }

            }
        }
    }

    fun <V : Parcelable, A : BaseActivity<V, A>> initToolbar(owner: A, toolbar: Toolbar) {
        owner.setSupportActionBar(toolbar)
    }

    fun <V : Parcelable, A : BaseFragment<V, A>> initToolbar(owner: A, toolbar: Toolbar) {
        (owner.activity as BaseActivity<*, *>).setSupportActionBar(toolbar)
    }

    fun <V : Parcelable, A : BaseActivity<V, A>> getActionBar(owner: A): ActionBar? {
        return owner.supportActionBar
    }

    fun <V : Parcelable, A : BaseFragment<V, A>> getActionBar(owner: A): ActionBar? {
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
