package com.superfactory.sunyatsin.Interface.BindingActivity.SettingsActivity

import android.graphics.Color
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RelativeLayout
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.DslView.refresh
import com.superfactory.library.Bridge.Anko.ViewExtensions.getLineDividerItemDecoration
import com.superfactory.library.Bridge.Anko.widget.AnkoViewHolder
import com.superfactory.library.Bridge.Anko.widget.AutoBindAdapter
import com.superfactory.library.Communication.Sender.senderAsync
import com.superfactory.library.Context.Extensions.takeApi
import com.superfactory.library.Debuger
import com.superfactory.library.Graphics.KDialog.CircleDialog
import com.superfactory.library.Graphics.KDialog.callback.ConfigButton
import com.superfactory.library.Graphics.KDialog.callback.Interrupter
import com.superfactory.library.Graphics.KDialog.params.ButtonParams
import com.superfactory.library.Utils.ConfigXmlAccessor
import com.superfactory.sunyatsin.Communication.RetrofitImpl
import com.superfactory.sunyatsin.R
import com.superfactory.sunyatsin.Struct.Const
import com.superfactory.sunyatsin.Struct.Login.LoginStruct
import org.jetbrains.anko.*
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by vicky on 2018.01.31.
 *
 * @Author vicky
 * @Date 2018年01月31日  17:20:36
 * @ClassName 这里输入你的类名(或用途)
 */
class SettingsActivityComponent(viewModel: SettingsActivityViewModel) : BindingComponent<SettingsActivity, SettingsActivityViewModel>(viewModel) {
    override fun createViewWithBindings(ui: AnkoContext<SettingsActivity>) = with(ui) {
        coordinatorLayout {
            refresh {
                isEnableRefresh = false
                isEnableLoadmore = false
                backgroundColor = Color.parseColor("#f8f8f8")
                bindSelf(SettingsActivityViewModel::tips) { it.tips.value }.toView(this) { view, value ->
                    if (!TextUtils.isEmpty(value)) {
                        Snackbar.make(view, value!!, Snackbar.LENGTH_SHORT).show()
                    }
                }
                recyclerView {
                    backgroundResource = R.drawable.profile_recycle_shader
                    leftPadding = dip(10)
                    val bindAdapter = AutoBindAdapter { viewGroup, _ ->
                        AnkoViewHolder(viewGroup, SettingsActivityItemComponent())
                    }.assignment { holder, _, position ->
                        when (position) {
                            2 -> {
                                Debuger.printMsg(this, "2")
                                holder.component.viewModel?.description = viewModel?.cacheSize?.value ?: ""
                            }
                            3 -> {
                                Debuger.printMsg(this, "3")
                                holder.component.viewModel?.description = viewModel?.version?.value ?: ""
                            }
                            else -> {
                                return@assignment
                            }
                        }
                        holder.component.notifyChanges()
                    }.apply {
                                onItemClickListener = { i, viewModel, _ ->
                                    if (viewModel.index == 1) {
                                        CircleDialog.Builder(owner)
                                                .setCanceledOnTouchOutside(false)
                                                .setCancelable(true)
                                                .setTitle("验证原密码")
                                                .setTitleColor(Color.BLUE)
                                                .setText("为保障您的数据安全,修改密码前请填写原密码")
                                                .setInputHint("请输入原密码")
                                                .setPositive("确定", null, object : Interrupter {
                                                    override fun dismissMission(text: String?, dialog: CircleDialog, inputView: View?) {
                                                        if (TextUtils.isEmpty(text)) {
                                                            if (inputView != null && inputView is EditText) {
                                                                inputView.error = "字符长度不足"
                                                            }
                                                            return
                                                        }
                                                        if (inputView != null && inputView is EditText) {
                                                            inputView.error = null
                                                        }
                                                        dialog.dismiss()
                                                        val account = ConfigXmlAccessor.restoreValue(context, Const.SignInInfo, Const.SignInAccount, "")
                                                        takeApi(RetrofitImpl::class)?.login(account!!, text!!, true)?.senderAsync(LoginStruct::class,
                                                                this@SettingsActivityComponent,
                                                                context)
                                                    }
                                                }
                                                )
                                                .configPositive(object : ConfigButton() {
                                                    override fun onConfig(params: ButtonParams) {
                                                        //取消按钮字体颜色
                                                        params.textColor = Color.parseColor("#1688ff")
                                                        params.dismiss()
                                                    }
                                                })
                                                .setNegative("取消", null)
                                                .configNegative(object : ConfigButton() {
                                                    override fun onConfig(params: ButtonParams) {
                                                        //取消按钮字体颜色
                                                        params.textColor = Color.parseColor("#1688ff")
                                                    }
                                                })
                                                .show()
                                    } else {
                                        this@SettingsActivityComponent.viewModelSafe.onItemClicked?.invoke(i, viewModel)
                                    }
                                }
                            }
                    bindSelf(SettingsActivityViewModel::settingsItemsList) { it.settingsItemsList }
                            .toView(this) { _, value ->
                                bindAdapter.setItemsList(value)
                            }
                    layoutManager = LinearLayoutManager(context)
                    addItemDecoration(getLineDividerItemDecoration(1, ContextCompat.getColor(context, R.color.gray)))
                    adapter = bindAdapter
                }.lparams {
                    topMargin = dip(10)
                    width = matchParent
                    height = wrapContent
                }

                button {
                    text = "退出登录"
                    backgroundResource = R.drawable.profile_recycle_shader
                    textColor = Color.BLACK
                    textSize = 16f
                    topPadding = dip(10)
                    bottomPadding = dip(10)
                    gravity = Gravity.CENTER
                    onClick {
                        Debuger.printMsg(this, "退出登录")
                    }
                }.lparams {
                    width = matchParent
                    height = wrapContent
                }
                lparams {
                    width = matchParent
                    height = matchParent
                }
            }
        }

    }
}

class SettingsActivityItemComponent : BindingComponent<ViewGroup, SettingsActivityViewModel.SettingsItemViewModel>() {
    override fun createViewWithBindings(ui: AnkoContext<ViewGroup>) = with(ui) {
        relativeLayout {

            textView {
                textSize = 14f
                textColor = Color.parseColor("#222222")
                bindSelf(SettingsActivityViewModel.SettingsItemViewModel::name) {
                    it.name
                }.toView(this) { view, value ->
                    if (!TextUtils.isEmpty(value)) {
                        view.text = value
                    }
                }
            }.lparams {
                width = wrapContent
                height = wrapContent
                alignParentLeft()
                centerVertically()
            }


            val iv = imageView {
                id = R.id.left_icon
                backgroundColor = Color.TRANSPARENT
                bindSelf(SettingsActivityViewModel.SettingsItemViewModel::right) {
                    it.right
                }.toView(this) { view, value ->
                    if (value != null && value != 0) {
                        view.imageResource = value
                    }
                }
            }.lparams {
                width = wrapContent
                height = wrapContent
                alignParentRight()
                centerVertically()
            }

            textView {
                textSize = 14f
                textColor = Color.parseColor("#222222")
                bindSelf(SettingsActivityViewModel.SettingsItemViewModel::description) {
                    it.description
                }.toView(this) { view, value ->
                    if (!TextUtils.isEmpty(value)) {
                        view.text = value
                    }
                }
            }.lparams {
                width = wrapContent
                height = wrapContent
                addRule(RelativeLayout.LEFT_OF, iv.id)
                centerVertically()
            }
            lparams {
                width = matchParent
                height = wrapContent
                bottomPadding = dip(5)
                topPadding = dip(5)
            }
        }
    }

}