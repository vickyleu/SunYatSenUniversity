package com.superfactory.sunyatsen.Interface.BindingActivity.MessageDetailActivity

import android.graphics.Color
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.DslView.refresh
import com.superfactory.library.Bridge.Anko.bindings.toText
import org.jetbrains.anko.*
import org.jetbrains.anko.design.coordinatorLayout

/**
 * Created by vicky on 2018/2/4.
 */
class MessageDetailActivityComponent(viewModel: MessageDetailViewModel) :
        BindingComponent<MessageDetailActivity, MessageDetailViewModel>(viewModel) {
    override fun createViewWithBindings(ui: AnkoContext<MessageDetailActivity>) = with(ui) {
        coordinatorLayout {

            refresh {
                backgroundColor = Color.parseColor("#f8f8f8")
                scrollView {
                    padding = dip(15)
                    bottomPadding = dip(0)
                    textView {
                        textSize = 16f
                        textColor = Color.parseColor("#222222")
                        bindSelf(MessageDetailViewModel::content) { it.content.value }.toText(this)
                    }.lparams {
                        width = matchParent
                        height = wrapContent
                    }
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
        }
    }

}