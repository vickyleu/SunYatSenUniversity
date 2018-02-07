package com.superfactory.sunyatsin.Interface.BindingActivity.CompellationActivity

import android.graphics.Color
import android.text.TextUtils
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.DslView.cleanUpEditText
import com.superfactory.library.Bridge.Anko.DslView.refresh
import com.superfactory.library.Bridge.Anko.bindSelf
import com.superfactory.library.Bridge.Anko.bindings.toObservable
import com.superfactory.library.Context.Extensions.ToolbarExtensions
import com.superfactory.library.Context.Extensions.ToolbarExtensions.Companion.setRightTextColor
import com.superfactory.sunyatsin.R
import org.jetbrains.anko.*

/**
 * Created by vicky on 2018.01.31.
 *
 * @Author vicky
 * @Date 2018年01月31日  20:07:45
 * @ClassName 这里输入你的类名(或用途)
 */
class CompellationActivityComponent(viewModel: CompellationActivityViewModel) :
        BindingComponent<CompellationActivity, CompellationActivityViewModel>(viewModel) {
    override fun createViewWithBindings(ui: AnkoContext<CompellationActivity>) = with(ui) {
        refresh {
            backgroundColor = Color.parseColor("#f8f8f8")

            cleanUpEditText {
                backgroundResource = R.drawable.profile_recycle_shader
                bindSelf(this).toObservable { it.input }
                val color1 = Color.parseColor("#b4b3b3")
                val color2 = Color.parseColor("#ffffff")
                bindSelf(CompellationActivityViewModel::input) { it.input.value }.toView(this) { view, value ->
                    if (TextUtils.isEmpty(value) || value!!.length < 3) {
                        if (viewModelSafe.rightTextColor.value != color1) {
                            viewModelSafe.eraseRight.value = true
                            doAsync {
                                setRightTextColor(color1, context, viewModel)
                            }

                        }
                    } else {
                        if (viewModelSafe.rightTextColor.value != color2) {
                            viewModelSafe.eraseRight.value = true
                            doAsync {
                                setRightTextColor(color2, context, viewModel)
                            }

                        }
                    }
                }

                singleLine = true
                maxLength = 10
                leftPadding = dip(10)
            }.lparams {
                width = matchParent
                height = wrapContent
            }

            viewModelSafe.rightClickable.value = {
                if (!TextUtils.isEmpty(viewModelSafe.input.value)) {
                    viewModelSafe.ownerNotifier?.invoke(101, viewModelSafe.input.value)
                }
            }
            lparams {
                width = matchParent
                height = matchParent
            }
        }
    }

}