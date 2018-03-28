package com.superfactory.sunyatsen.Interface.BindingActivity.MessageActivity

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import android.widget.ImageView
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.DslView.refresh
import com.superfactory.library.Bridge.Anko.ViewExtensions.getLineDividerItemDecoration
import com.superfactory.library.Bridge.Anko.bindings.toText
import com.superfactory.library.Bridge.Anko.widget.AnkoViewHolder
import com.superfactory.library.Bridge.Anko.widget.AutoBindAdapter
import com.superfactory.library.Communication.Sender.senderAsync
import com.superfactory.library.Context.Extensions.takeApi
import com.superfactory.library.Utils.ConfigXmlAccessor
import com.superfactory.sunyatsen.Communication.RetrofitImpl
import com.superfactory.sunyatsen.R
import com.superfactory.sunyatsen.Struct.BaseStructImpl
import com.superfactory.sunyatsen.Struct.Const
import org.jetbrains.anko.*
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * Created by vicky on 2018.02.03.
 *
 * @Author vicky
 * @Date 2018年02月03日  21:10:01
 * @ClassName 这里输入你的类名(或用途)
 */
open class MessageActivityComponent(viewModel: MessageActivityViewModel) :
        BindingComponent<MessageActivity, MessageActivityViewModel>(viewModel) {
    override fun createViewWithBindings(ui: AnkoContext<MessageActivity>) = with(ui) {
        coordinatorLayout {

            refresh {
                backgroundColor = Color.parseColor("#f8f8f8")

                recyclerView {
                    backgroundResource = R.drawable.profile_recycle_shader
                    leftPadding = dip(15)
                    val bindAdapter = AutoBindAdapter { viewGroup, _ ->
                        AnkoViewHolder(viewGroup, MessageActivityItemComponent())
                    }.apply {
                        onItemClickListener={i,v,h->
                            viewModel?.ownerNotifier?.invoke(i,v)
                        }
                    }
                    bindSelf(MessageActivityViewModel::itemList) { it.itemList.value }
                            .toView(this) { _, value ->
                                bindAdapter.setItemsList(value)
                            }
                    viewModelSafe.rightClickable.value = {
                        takeApi(RetrofitImpl::class)?.eraseMsg(ConfigXmlAccessor.restoreValue(
                                context, Const.SignInInfo, Const.SignInSession, "")
                                ?: "", true)?.senderAsync(BaseStructImpl::class, this@MessageActivityComponent, context)
                    }
                    layoutManager = LinearLayoutManager(context)
                    addItemDecoration(getLineDividerItemDecoration(1, ContextCompat.getColor(context, R.color.gray)))
                    adapter = bindAdapter
                }.lparams {
                    topMargin = dip(10)
                    width = matchParent
                    height = wrapContent
                }


            }.lparams {
                width = matchParent
                height = matchParent
            }

            lparams {
                width = matchParent
                height = matchParent
            }
        }
    }

}

class MessageActivityItemComponent : BindingComponent<ViewGroup, MessageItemView>() {
    override fun createViewWithBindings(ui: AnkoContext<ViewGroup>) = with(ui) {
        verticalLayout {
            topPadding = dip(10)
            bottomPadding = dip(10)
            relativeLayout {
                imageView {
                    id = R.id.left_icon
                    backgroundColor = Color.TRANSPARENT
                    bindSelf(MessageItemView::image) { it.image }.toView(this) { view, value ->
                        if (value != null && value != 0) {
                            view.imageResource = value
                        }
                    }
                    scaleType = ImageView.ScaleType.FIT_XY
                }.lparams {
                    width = wrapContent
                    height = wrapContent
                    alignParentLeft()
                    centerVertically()
                }

                textView {
                    bindSelf(MessageItemView::title) { it.title }.toText(this)
                    textSize = 15f
                    textColor = Color.parseColor("#222222")
                    leftPadding = dip(25)
                }.lparams {
                    alignParentLeft()

                    centerVertically()
                }

                textView {
                    bindSelf(MessageItemView::date) { it.date }.toText(this)
                }.lparams {
                    alignParentRight()
                    rightMargin = dip(10)
                    centerVertically()
                }

            }
            textView {
                bindSelf(MessageItemView::content) { it.content }.toText(this)
            }.lparams {
                leftMargin = dip(25)
            }


            lparams {
                width = matchParent
                height = wrapContent
            }
        }
    }
}