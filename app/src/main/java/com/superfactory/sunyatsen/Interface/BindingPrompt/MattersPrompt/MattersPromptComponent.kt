package com.superfactory.sunyatsen.Interface.BindingPrompt.MattersPrompt

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.ViewGroup
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.DslView.refresh
import com.superfactory.library.Bridge.Anko.ViewExtensions.getLineDividerItemDecoration
import com.superfactory.library.Bridge.Anko.bindings.toText
import com.superfactory.library.Bridge.Anko.widget.AnkoViewHolder
import com.superfactory.library.Bridge.Anko.widget.AutoBindAdapter
import com.superfactory.library.Communication.Sender.senderAsync
import com.superfactory.library.Context.Extensions.takeApi
import com.superfactory.library.Context.takeRoundRectShape
import com.superfactory.library.Utils.ConfigXmlAccessor
import com.superfactory.sunyatsen.Bean.MattersBean
import com.superfactory.sunyatsen.Communication.RetrofitImpl
import com.superfactory.sunyatsen.Interface.BindingPrompt.DutyPrompt.DutyPromptViewModel
import com.superfactory.sunyatsen.R
import com.superfactory.sunyatsen.Struct.Const
import com.superfactory.sunyatsen.Struct.MattersStruct.BzMatterInfo
import com.superfactory.sunyatsen.Struct.MattersStruct.MattersStruct
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * Created by vicky on 2018.02.05.
 *
 * @Author vicky
 * @Date 2018年02月05日  15:16:50
 * @ClassName 这里输入你的类名(或用途)
 */
class MattersPromptComponent(viewModel: MattersPromptViewModel) :
        BindingComponent<MattersPrompt, MattersPromptViewModel>(viewModel) {
    override fun createViewWithBindings(ui: AnkoContext<MattersPrompt>) = with(ui) {
        verticalLayout {
            backgroundColor = Color.TRANSPARENT
            backgroundDrawable = takeRoundRectShape(dip(5))

            textView {
                textSize = 14f
                textColor = Color.parseColor("#222222")
                bindSelf(DutyPromptViewModel::title) { it.title.value }.toText(this)
                backgroundColor = Color.TRANSPARENT
            }.lparams {
                topMargin = dip(5)
                bottomMargin = dip(5)
                width = wrapContent
                gravity = Gravity.CENTER/*_HORIZONTAL*/
                setHorizontalGravity(Gravity.CENTER_HORIZONTAL)
                height = wrapContent
            }
            refresh {
                backgroundColor = Color.TRANSPARENT
                recyclerView {
                    leftPadding = dip(10)
                    rightPadding = dip(10)
                    backgroundColor = Color.TRANSPARENT
                    backgroundResource = R.drawable.profile_recycle_shader
                    val bindAdapter = AutoBindAdapter { viewGroup, _ ->
                        AnkoViewHolder(viewGroup, MattersPromptItemComponent())
                    }.apply {
                        onItemClickListener = { i, viewModel, _ ->
                            this@MattersPromptComponent.viewModelSafe.onItemClickListener?.invoke(i, viewModel)
                        }
                    }

                    bindSelf(MattersPromptViewModel::itemList) { it.itemList.value }
                            .toView(this) { _, value ->
                                bindAdapter.setItemsList(value)
                            }

                    layoutManager = LinearLayoutManager(context)
                    addItemDecoration(getLineDividerItemDecoration(1, ContextCompat.getColor(context, R.color.gray)))
                    adapter = bindAdapter
                }.lparams {
                    width = matchParent
                    height = matchParent
                }
            }.lparams {
                width = matchParent
                height = matchParent
            }

            lparams {
                width = matchParent
                height = matchParent
            }

            takeApi(RetrofitImpl::class)?.mattersList(ConfigXmlAccessor.restoreValue(
                    context, Const.SignInInfo, Const.SignInSession, "")
                    ?: "", true, MattersBean(viewModelSafe.id))?.senderAsync(MattersStruct::class, this@MattersPromptComponent, context)

        }
    }
}

class MattersPromptItemComponent : BindingComponent<ViewGroup, BzMatterInfo>() {

    override fun createViewWithBindings(ui: AnkoContext<ViewGroup>) = with(ui) {
        verticalLayout {
            textView {
                bindSelf(BzMatterInfo::matterContent) { it.matterContent }.toText(this)
                padding = dip(12)
                textSize = 16.0f
                textColor = Color.BLACK
            }.lparams {
                width = wrapContent
                gravity = Gravity.CENTER_HORIZONTAL
                setHorizontalGravity(Gravity.CENTER_HORIZONTAL)
                height = wrapContent
            }
            lparams {
                width = matchParent
                height = wrapContent
            }
        }

    }
}