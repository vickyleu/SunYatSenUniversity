package com.superfactory.sunyatsen.Interface.BindingActivity.PasswordActivity

import android.content.Context
import android.graphics.Color
import android.support.design.widget.Snackbar
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.DslView.OnTextCleanListener
import com.superfactory.library.Bridge.Anko.DslView.cleanUpEditText
import com.superfactory.library.Bridge.Anko.DslView.refresh
import com.superfactory.library.Bridge.Anko.ViewExtensions.removeWatcher
import com.superfactory.library.Bridge.Anko.bindSelf
import com.superfactory.library.Bridge.Anko.bindings.toObservable
import com.superfactory.library.Communication.Sender.senderAsync
import com.superfactory.library.Context.Extensions.ToolbarExtensions.Companion.setRightTextColor
import com.superfactory.library.Context.Extensions.takeApi
import com.superfactory.library.Graphics.Adapt.SimpleWatcher
import com.superfactory.library.Utils.ConfigXmlAccessor
import com.superfactory.sunyatsen.Bean.ChangePswStruct
import com.superfactory.sunyatsen.Communication.RetrofitImpl
import com.superfactory.sunyatsen.R
import com.superfactory.sunyatsen.Struct.BaseStructImpl
import com.superfactory.sunyatsen.Struct.Const
import org.jetbrains.anko.*
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.textInputLayout

/**
 * Created by vicky on 2018.01.31.
 *
 * @Author vicky
 * @Date 2018年01月31日  20:55:54
 * @ClassName 这里输入你的类名(或用途)
 */
class PasswordActivityComponent(viewModel: PasswordActivityViewModel) :
        BindingComponent<PasswordActivity, PasswordActivityViewModel>(viewModel) {
    override fun createViewWithBindings(ui: AnkoContext<PasswordActivity>) = with(ui) {
        coordinatorLayout {
            refresh {
                backgroundColor = Color.parseColor("#F8F8F8")

                verticalLayout {
                    backgroundResource = R.drawable.profile_recycle_shader
                    leftPadding = dip(10)
                    val watcher = object : SimpleWatcher() {
                        override fun afterTextChanged(s: Editable?) {
                            super.afterTextChanged(s)
                            val n = viewModel?.newPsw?.value ?: ""
                            val c = viewModel?.confirmPsw?.value ?: ""
                            if (n.equals(c) && !c.equals("") && c.length >= 5) {
                                viewModel?.passwordCorrect?.value = true
                                viewModelSafe.wrong.value = ""
                            } else {
                                if (n.equals(c) && !c.equals("")) {
                                    viewModelSafe.wrong.value = "密码过短"
                                } else if (n.equals(c) && c.equals("")) {
                                    viewModelSafe.wrong.value = ""
                                } else if (!n.equals(c) && c.length < n.length) {
                                    viewModelSafe.wrong.value = ""
                                } else if (!c.equals("")) {
                                    viewModelSafe.wrong.value = "密码不匹配"
                                }
                                if ((viewModel?.passwordCorrect?.value ?: false) == true) {
                                    viewModel?.passwordCorrect?.value = false
                                } else {
                                }
                            }
                        }

                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) =
                                if (TextUtils.isEmpty(s)) {
                                    if ((viewModel?.passwordCorrect?.value ?: false) == true) {
                                        viewModel?.passwordCorrect?.value = false
                                    } else {
                                    }
                                } else {
                                }
                    }
                    var view1: View? = null
                    var view2: View? = null
                    verticalLayout {
                        rightPadding = dip(10)
                        textInputLayout {
                            view1 = cleanUpEditText {
                                maxLength = 20
                                singleLine = true
                                bindSelf(PasswordActivityViewModel.PasswordItemData::hint) { it.passData1.hint }.toView(this) { view, value ->
                                    if (!TextUtils.isEmpty(value)) {
                                        view.hint = value
                                    }
                                }
                                setRightClick(object : OnTextCleanListener {
                                    override fun onClean() {
                                        requestFocus()
                                    }
                                })

                                setOnClickListener {
                                    view2?.clearFocus()
                                    isFocusable = true
                                    isFocusableInTouchMode = true
                                    requestFocus()
                                    val imm = this@cleanUpEditText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                                    imm.toggleSoftInputFromWindow(windowToken, 0, 0)
                                }


                                bindSelf(this).toObservable { it.newPsw }
                                bindSelf { viewModelSafe.wrong }.toView(this) { view, value ->
                                    if (TextUtils.isEmpty(value)) {
                                        error = null
                                    } else {
                                        error = value
                                    }
                                }

                                imeOptions = EditorInfo.IME_ACTION_NEXT

                                removeWatcher()
                                addTextChangedListener(watcher)
                                inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                                layoutParams = LinearLayout.LayoutParams(matchParent, wrapContent)
                            }
                        }.lparams {
                            bottomPadding = dip(5)
                            topPadding = dip(5)
                            width = matchParent
                            height = wrapContent
                        }

                        lparams {
                            width = matchParent
                            height = wrapContent
                        }
                    }
                    verticalLayout {
                        rightPadding = dip(10)
                        textInputLayout {
                            view2 = cleanUpEditText {
                                maxLength = 20
                                singleLine = true
                                bindSelf(PasswordActivityViewModel.PasswordItemData::hint) { it.passData2.hint }.toView(this) { view, value ->
                                    if (!TextUtils.isEmpty(value)) {
                                        view.hint = value

                                    }
                                }

                                setRightClick(object : OnTextCleanListener {
                                    override fun onClean() {
                                        requestFocus()
                                    }
                                })

                                setOnClickListener {
                                    view1?.clearFocus()
                                    isFocusable = true
                                    isFocusableInTouchMode = true
                                    requestFocus()
                                    val imm = this@cleanUpEditText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                                    imm.toggleSoftInputFromWindow(windowToken, 0, 0)
                                }


                                bindSelf(this).toObservable { it.confirmPsw }
                                bindSelf { viewModelSafe.wrong }.toView(this) { view, value ->
                                    if (TextUtils.isEmpty(value)) {
                                        error = null
                                    } else {
                                        error = value
                                    }
                                }


                                imeOptions = EditorInfo.IME_ACTION_DONE

                                removeWatcher()
                                addTextChangedListener(watcher)
                                inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                                layoutParams = LinearLayout.LayoutParams(matchParent, wrapContent)
                            }
                        }.lparams {
                            bottomPadding = dip(5)
                            topPadding = dip(5)
                            width = matchParent
                            height = wrapContent
                        }

                        lparams {
                            width = matchParent
                            height = wrapContent
                        }
                    }
                }.lparams {
                    topMargin = dip(10)
                    width = matchParent
                    height = wrapContent
                }
                lparams {
                    width = matchParent
                    height = matchParent
                }
            }
            val color1 = Color.parseColor("#b4b3b3")
            val color2 = Color.parseColor("#ffffff")
            bindSelf(PasswordActivityViewModel::passwordCorrect) {
                it.passwordCorrect.value
            }.toView(this) { view, value ->
                if (value != null) {
                    if (!value) {
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
            }

            viewModelSafe.rightClickable.value = {
                if (viewModelSafe.passwordCorrect.value) {
                    takeApi(RetrofitImpl::class)?.changePsw(ConfigXmlAccessor.restoreValue(
                            context, Const.SignInInfo, Const.SignInSession, "")
                            ?: "", ChangePswStruct(viewModelSafe.newPsw.value,
                            viewModelSafe.confirmPsw.value), true)?.senderAsync(BaseStructImpl::class,
                            this@PasswordActivityComponent,
                            context)
                }
            }

            bindSelf(PasswordActivityViewModel::tips) {
                it.tips.value
            }.toView(this) { view, value ->
                if (!TextUtils.isEmpty(value)) {
                    Snackbar.make(view, value!!, Snackbar.LENGTH_SHORT).show()
                }
            }
            lparams {
                width = matchParent
                height = matchParent
            }
        }

    }
}
