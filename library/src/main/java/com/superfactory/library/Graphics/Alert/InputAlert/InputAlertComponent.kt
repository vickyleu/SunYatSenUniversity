package com.superfactory.library.Graphics.Alert.InputAlert

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.Gravity
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.DslView.horizontalLayout
import com.superfactory.library.Bridge.Anko.bindSelf
import com.superfactory.library.Bridge.Anko.bindings.toObservable
import com.superfactory.library.Bridge.Anko.bindings.toText
import com.superfactory.library.Context.takeRoundRectShape
import com.superfactory.library.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick


/**
 * Created by vicky on 2018.02.06.
 *
 * @Author vicky
 * @Date 2018年02月06日  17:21:59
 * @ClassName 这里输入你的类名(或用途)
 */
class InputAlertComponent(viewModel: InputAlertViewModel) :
        BindingComponent<InputAlert, InputAlertViewModel>(viewModel) {

    override fun createViewWithBindings(ui: AnkoContext<InputAlert>) = with(ui) {
        verticalLayout {
            backgroundDrawable = takeRoundRectShape(dip(5), Color.parseColor("#f8f8f8"))

            textView {
                bindSelf(InputAlertViewModel::title) { it.title.value }.toText(this)
                bindSelf(InputAlertViewModel::titleColor) { it.titleColor.value }.toView(this) { view, value ->
                    if (value != null && value != 0) {
                        view.textColor = value
                    }
                }
                bindSelf(InputAlertViewModel::titleSize) { it.titleSize.value }.toView(this) { view, value ->
                    if (value != null && value != 0f) {
                        view.textSize = dip(value).toFloat()
                    }
                }
                backgroundColor = Color.TRANSPARENT
                paint.isFakeBoldText = true
            }.lparams {
                topMargin = dip(20)
                bottomMargin = dip(10)
                width = wrapContent
                gravity = Gravity.CENTER/*_HORIZONTAL*/
                setHorizontalGravity(Gravity.CENTER_HORIZONTAL)
                height = wrapContent
            }

            textView {
                bindSelf(InputAlertViewModel::msg) { it.msg.value }.toText(this)
                bindSelf(InputAlertViewModel::msgColor) { it.msgColor.value }.toView(this) { view, value ->
                    if (value != null && value != 0) {
                        view.textColor = value
                    }
                }
                bindSelf(InputAlertViewModel::msgSize) { it.msgSize.value }.toView(this) { view, value ->
                    if (value != null && value != 0f) {
                        view.textSize = dip(value).toFloat()
                    }
                }
                backgroundColor = Color.TRANSPARENT
            }.lparams {
                topMargin = dip(5)
                bottomMargin = dip(5)
                width = wrapContent
                gravity = Gravity.CENTER/*_HORIZONTAL*/
                setHorizontalGravity(Gravity.CENTER_HORIZONTAL)
                height = wrapContent
            }

            editText {
                singleLine = true
                bindSelf(InputAlertViewModel::hint) { it.msg.value }.toView(this) { view, value ->
                    if (!TextUtils.isEmpty(value)) {
                        view.hint = value
                    }
                }
                bindSelf(InputAlertViewModel::inputColor) { it.inputColor.value }.toView(this) { view, value ->
                    if (value != null && value != 0) {
                        view.textColor = value
                    }
                }
                bindSelf(InputAlertViewModel::inputSize) { it.inputSize.value }.toView(this) { view, value ->
                    if (value != null && value != 0f) {
                        view.textSize = dip(value).toFloat()
                    }
                }
                bindSelf(this).toObservable {
                    it.output
                }
                bindSelf(InputAlertViewModel::error) { it.error.value }.toView(this) { view, value ->
                    view.error = value
                }
//                cursorDrawable = Color.parseColor("#1688ff")
                backgroundDrawable = ContextCompat.getDrawable(context, R.drawable.textfield_frame)
            }.lparams {
                width = matchParent
                height = wrapContent
                leftMargin = dip(15)
                rightMargin = dip(15)
                topMargin = dip(20)
                bottomMargin = dip(15)
            }

            view {
                backgroundColor = Color.parseColor("#CCCCCC")
            }.lparams {
                width = matchParent
                height = px2dip(1).toInt()
            }

            horizontalLayout {
                backgroundColor = Color.TRANSPARENT
                weightSum = 20f

                button {
                    minHeight = 0
                    minWidth = 0
                    padding = 0
                    backgroundColor = Color.TRANSPARENT
                    bindSelf(InputAlertViewModel::negative) { it.negative.value }.toText(this)
                    bindSelf(InputAlertViewModel::negativeColor) { it.negativeColor.value }.toView(this) { view, value ->
                        if (value != null && value != 0) {
                            view.textColor = value
                        }
                    }
                    bindSelf(InputAlertViewModel::negativeSize) { it.negativeSize.value }.toView(this) { view, value ->
                        if (value != null && value != 0f) {
                            view.textSize = dip(value).toFloat()
                        }
                    }
                    onClick {
                        viewModelSafe.negativeClick.value?.invoke(this@button, viewModelSafe, owner)
                    }
                    paint.isFakeBoldText = true
                }.lparams {
                    width = 0
                    weight = 9.8f
                    height = matchParent
                }
                view {
                    backgroundColor = Color.parseColor("#CCCCCC")
                }.lparams {
                    width = px2dip(1).toInt()
                    height = matchParent
                }
                button {
                    minHeight = 0
                    minWidth = 0
                    padding = 0
                    backgroundColor = Color.TRANSPARENT
                    bindSelf(InputAlertViewModel::positive) { it.positive.value }.toText(this)
                    bindSelf(InputAlertViewModel::positiveColor) { it.positiveColor.value }.toView(this) { view, value ->
                        if (value != null && value != 0) {
                            view.textColor = value
                        }
                    }
                    bindSelf(InputAlertViewModel::positiveSize) { it.positiveSize.value }.toView(this) { view, value ->
                        if (value != null && value != 0f) {
                            view.textSize = dip(value).toFloat()
                        }
                    }
                    onClick {
                        viewModelSafe.positiveClick.value?.invoke(this@button, viewModelSafe, owner)
                    }
                    paint.isFakeBoldText = true
                }.lparams {
                    width = 0
                    weight = 9.8f
                    height = matchParent
                }

            }.lparams {
                setHorizontalGravity(Gravity.CENTER_HORIZONTAL)
                width = matchParent
                height = dip(50)
            }


            lparams {
                width = matchParent
                height = matchParent
            }

        }
    }
}