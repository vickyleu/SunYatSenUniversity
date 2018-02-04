package com.superfactory.sunyatsin.Interface.BindingPrompt.DutyPrompt

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
import com.superfactory.sunyatsin.Communication.RetrofitImpl
import com.superfactory.sunyatsin.R
import com.superfactory.sunyatsin.Struct.Const
import com.superfactory.sunyatsin.Struct.Duty.BzDutyInfo
import com.superfactory.sunyatsin.Struct.Duty.DutyStruct
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * Created by vicky on 2018/2/5.
 */
class DutyPromptComponent(viewModel: DutyPromptViewModel) :
        BindingComponent<DutyPrompt, DutyPromptViewModel>(viewModel) {
    override fun createViewWithBindings(ui: AnkoContext<DutyPrompt>) = with(ui) {
        verticalLayout {
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
                    backgroundResource=R.drawable.profile_recycle_shader
                    val bindAdapter = AutoBindAdapter { viewGroup, _ ->
                        AnkoViewHolder(viewGroup, DutyPromptItemComponent())
                    }.apply {
                        onItemClickListener = { i, viewModel, _ ->
                            this@DutyPromptComponent.viewModelSafe.onItemClickListener?.invoke(i, viewModel)
                        }
                    }

                    bindSelf(DutyPromptViewModel::itemList) { it.itemList.value }
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

            takeApi(RetrofitImpl::class)?.dutyList(ConfigXmlAccessor.restoreValue(
                    context, Const.SignInInfo, Const.SignInSession, "")
                    ?: "", true)?.senderAsync(DutyStruct::class, this@DutyPromptComponent, context)

        }
    }
}

class DutyPromptItemComponent : BindingComponent<ViewGroup, BzDutyInfo>() {

    override fun createViewWithBindings(ui: AnkoContext<ViewGroup>) = with(ui) {
        verticalLayout {
            textView {
                bindSelf(BzDutyInfo::dutyName) { it.dutyName }.toText(this)
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