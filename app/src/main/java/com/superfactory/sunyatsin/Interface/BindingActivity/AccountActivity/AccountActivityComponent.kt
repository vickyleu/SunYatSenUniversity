package com.superfactory.sunyatsin.Interface.BindingActivity.AccountActivity

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.DslView.refresh
import com.superfactory.library.Bridge.Anko.ViewExtensions.getLineDividerItemDecoration
import com.superfactory.library.Bridge.Anko.widget.AnkoViewHolder
import com.superfactory.library.Bridge.Anko.widget.AutoBindAdapter
import com.superfactory.library.Debuger
import com.superfactory.sunyatsin.R
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * Created by vicky on 2018.01.31.
 *
 * @Author vicky
 * @Date 2018年01月31日  18:57:18
 * @ClassName 这里输入你的类名(或用途)
 */
class AccountActivityComponent(viewModel: AccountActivityViewModel) :
        BindingComponent<AccountActivity, AccountActivityViewModel>(viewModel) {
    override fun createViewWithBindings(ui: AnkoContext<AccountActivity>) = with(ui) {
        refresh {
            isEnableRefresh = false
            isEnableLoadmore = false
            backgroundColor = Color.parseColor("#f8f8f8")

            recyclerView {
                backgroundResource = R.drawable.profile_recycle_shader
                leftPadding = dip(10)
                val bindAdapter = AutoBindAdapter { viewGroup, _ ->
                    AnkoViewHolder(viewGroup, AccountActivityItemComponent())
                }.apply {
                    onItemClickListener={ i, viewModel, _ ->
                        this@AccountActivityComponent.viewModelSafe.onItemClicked?.invoke(i, viewModel)
                    }
                }.assignment { holder, _, position ->
                    when (position) {
                        0 -> {
                            Debuger.printMsg(this, "2")
//                            holder.component.viewModel?.placeHolder = viewModel?.avatar.value ?: ""
                        }
                        1 -> {
                            Debuger.printMsg(this, "2")
                            holder.component.viewModel?.description = viewModel?.name?.value ?: ""
                        }
                        2 -> {
                            Debuger.printMsg(this, "3")
                            holder.component.viewModel?.description = viewModel?.gender?.value ?: ""
                        }
                        else -> {
                            return@assignment
                        }
                    }
                    holder.component.notifyChanges()
                }
                bindSelf(AccountActivityViewModel::accountItemsList) { it.accountItemsList }
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

class AccountActivityItemComponent : BindingComponent<ViewGroup, AccountActivityItemViewModel>() {
    override fun createViewWithBindings(ui: AnkoContext<ViewGroup>) = with(ui) {
        relativeLayout {

            textView {
                textSize = 14f
                textColor = Color.parseColor("#222222")
                bindSelf(AccountActivityItemViewModel::name) {
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
                bindSelf(AccountActivityItemViewModel::right) {
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
                bindSelf(AccountActivityItemViewModel::description) {
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

            imageView {
                backgroundColor = Color.TRANSPARENT
                bindSelf(AccountActivityItemViewModel::placeHolder) {
                    it.placeHolder
                }.toView(this) { view, value ->
                    if (value != null && value != 0) {
                        view.imageResource = value
                    }
                }
            }.lparams {
                width = wrapContent
                height = wrapContent
                minimumHeight = 0
                minimumWidth = 0
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