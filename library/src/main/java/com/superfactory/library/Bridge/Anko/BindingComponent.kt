package com.superfactory.library.Bridge.Anko

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN
import android.support.design.widget.CoordinatorLayout
import android.view.View
import android.widget.*
import com.superfactory.library.Bridge.Anko.BindingExtensions.getAttrDrawablValue
import com.superfactory.library.Bridge.Anko.bindings.bind
import com.superfactory.library.Bridge.Anko.bindings.onSelf
import com.superfactory.library.Bridge.Anko.widget.BaseViewHolder
import com.superfactory.library.R
import org.jetbrains.anko.*
import org.jetbrains.anko.design.collapsingToolbarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.themedAppBarLayout
import java.util.*


fun <T, Data> BindingComponent<T, Data>.bind(v: View, any: View.OnClickListener?) = register.bind(v, any)


fun <T, Data> BindingComponent<T, Data>.bind(v: TextView) = register.bind(v)
fun <T, Data> BindingComponent<T, Data>.bind(v: CompoundButton) = register.bind(v)
fun <T, Data> BindingComponent<T, Data>.bind(v: DatePicker, initialValue: Calendar = Calendar.getInstance()) = register.bind(v, initialValue)
fun <T, Data> BindingComponent<T, Data>.bind(v: TimePicker) = register.bind(v)
fun <T, Data> BindingComponent<T, Data>.bind(v: RatingBar) = register.bind(v)
fun <T, Data> BindingComponent<T, Data>.bind(v: SeekBar) = register.bind(v)


fun <T, Data> BindingComponent<T, Data>.bindSelf(v: View, any: View.OnClickListener?) = bind(v, any).onSelf()

fun <T, Data> BindingComponent<T, Data>.bindSelf(v: TextView) = bind(v).onSelf()
fun <T, Data> BindingComponent<T, Data>.bindSelf(v: CompoundButton) = bind(v).onSelf()
fun <T, Data> BindingComponent<T, Data>.bindSelf(v: DatePicker, initialValue: Calendar = Calendar.getInstance()) = bind(v, initialValue).onSelf()
fun <T, Data> BindingComponent<T, Data>.bindSelf(v: TimePicker) = bind(v).onSelf()
fun <T, Data> BindingComponent<T, Data>.bindSelf(v: RatingBar) = bind(v).onSelf()
fun <T, Data> BindingComponent<T, Data>.bindSelf(v: SeekBar) = bind(v).onSelf()

abstract class BindingComponent<in T, V>
(viewModel: V? = null, val register: BindingRegister<V> = BindingHolder(viewModel)
) : AnkoComponent<T>, BindingRegister<V> by register {



    override var viewModel: V?
        set(value) {
            register.viewModel = value
        }
        get() {
            return register.viewModel
        }

    override var isBound: Boolean
        get() = register.isBound
        set(value) {
            register.isBound = value
        }

    override final fun createView(ui: AnkoContext<T>) = createViewWithBindings(ui).apply { register.bindAll() }

    final fun createView(ui: AnkoContext<T>, toolbar: View?, ctx: Context, owner: T): View {
        val attach = createViewWithBindings(AnkoContextImpl(ctx, owner, false))
        return if (toolbar == null) {
            createView(ui)
        } else {
            with(ui) {
                var useCoordinatorLayout = false

                if (owner is Activity) {
                    useCoordinatorLayout = false
                }
                if (!useCoordinatorLayout) {
                    verticalLayout {
                        backgroundColor= Color.WHITE
                        addView(toolbar)
                        addView(attach)
                        lparams {
                            width = matchParent
                            height = matchParent
                        }
                    }
                } else {
                    coordinatorLayout {
                        fitsSystemWindows = true
                        backgroundColor=Color.WHITE
                        themedAppBarLayout(R.style.AppTheme_AppBarOverlay) {
                            id = R.id.appbar
                            backgroundColor=Color.TRANSPARENT
                            collapsingToolbarLayout {
                                id = R.id.collapsing_toolbar
                                backgroundColor=Color.TRANSPARENT
                                contentScrim = getAttrDrawablValue(ctx, R.attr.colorPrimary)
                                expandedTitleMarginEnd = dip(64)
                                expandedTitleMarginStart = dip(48)
                                addView(toolbar)
                                ((toolbar.layoutParams) as CollapsingToolbarLayout.LayoutParams).collapseMode = COLLAPSE_MODE_PIN
                            }.lparams {
                                scrollFlags = 0//SCROLL_FLAG_SCROLL and SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
                                width = matchParent
                                height = wrapContent
                                fitsSystemWindows = true
                            }
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                        }
                        addView(attach)
                        val attachLp = attach.layoutParams as CoordinatorLayout.LayoutParams
                        (attachLp).apply {
                            behavior = AppBarLayout.ScrollingViewBehavior()
                        }
                        lparams {
                            width = matchParent
                            height = matchParent
                        }
                    }
                }
            }.apply { register.bindAll() }
        }
    }


    abstract fun createViewWithBindings(ui: AnkoContext<T>): View




    fun destroyView() = register.unbindAll()
    open fun onRequestPermissionsResult(ctx: Activity?,requestCode: Int, permissions: Array<out String>, grantResults: IntArray){

    }



}