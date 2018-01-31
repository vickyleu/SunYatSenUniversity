package com.superfactory.sunyatsin.Interface.BindingActivity.PasswordActivity

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.ViewGroup
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.DslView.cleanUpEditText
import com.superfactory.library.Bridge.Anko.DslView.refresh
import com.superfactory.library.Bridge.Anko.ViewExtensions.getLineDividerItemDecoration
import com.superfactory.library.Bridge.Anko.bindSelf
import com.superfactory.library.Bridge.Anko.bindings.toObservable
import com.superfactory.library.Bridge.Anko.widget.AnkoViewHolder
import com.superfactory.library.Bridge.Anko.widget.AutoBindAdapter
import com.superfactory.sunyatsin.R
import org.jetbrains.anko.*
import org.jetbrains.anko.design.textInputLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView

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
        refresh {
            backgroundColor = Color.parseColor("#F8F8F8")

            recyclerView {
                backgroundResource = R.drawable.profile_recycle_shader
                leftPadding = dip(10)
                val bindAdapter = AutoBindAdapter { viewGroup, _ ->
                    AnkoViewHolder(viewGroup, PasswordActivityItemComponent())
                }.assignment { holder, model, position ->
                    when (position) {
                        0 -> {
                            viewModel?.newPsw?.value = model.psw.value
                        }
                        1 -> {
                            viewModel?.confirmPsw?.value = model.psw.value
                        }
                    }
                }
                bindSelf(PasswordActivityViewModel::passwordList) { it.passwordList }
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

            lparams {
                width = matchParent
                height = matchParent
            }
        }
    }
}

class PasswordActivityItemComponent : BindingComponent<ViewGroup, PasswordActivityViewModel.PasswordItemData>() {
    override fun createViewWithBindings(ui: AnkoContext<ViewGroup>) = with(ui) {
        verticalLayout {
            textInputLayout {
                cleanUpEditText {
                    backgroundColor = Color.TRANSPARENT
                    maxLength = 20
                    bindSelf(PasswordActivityViewModel.PasswordItemData::hint) { it.hint }.toView(this) { view, value ->
                        if (!TextUtils.isEmpty(value)) {
                            view.hint = value
                        }
                    }
                    bindSelf(this).toObservable { it.psw }
                }.lparams {
                    width = matchParent
                    height = wrapContent

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
    }
}