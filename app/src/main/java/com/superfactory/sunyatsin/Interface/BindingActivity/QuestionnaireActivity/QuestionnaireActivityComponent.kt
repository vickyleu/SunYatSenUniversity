package com.superfactory.sunyatsin.Interface.BindingActivity.QuestionnaireActivity

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.ViewExtensions.getLineDividerItemDecoration
import com.superfactory.library.Bridge.Anko.widget.AnkoViewHolder
import com.superfactory.library.Bridge.Anko.widget.AutoBindAdapter
import com.superfactory.library.Communication.Sender.senderAsync
import com.superfactory.library.Context.Extensions.takeApi
import com.superfactory.library.Utils.ConfigXmlAccessor
import com.superfactory.sunyatsin.Communication.RetrofitImpl
import com.superfactory.sunyatsin.R
import com.superfactory.sunyatsin.Struct.Base.BaseStruct
import com.superfactory.sunyatsin.Struct.Const
import com.superfactory.sunyatsin.Struct.QuestionaireStruct.QuestionnaireDetailStruct
import org.jetbrains.anko.*
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * Created by vicky on 2018.02.01.
 *
 * @Author vicky
 * @Date 2018年02月01日  13:51:11
 * @ClassName 这里输入你的类名(或用途)
 */
class QuestionnaireActivityComponent(viewModel: QuestionnaireActivityViewModel) :
        BindingComponent<QuestionnaireActivity, QuestionnaireActivityViewModel>(viewModel) {
    override fun createViewWithBindings(ui: AnkoContext<QuestionnaireActivity>) = with(ui) {
        coordinatorLayout {

            backgroundColor = Color.parseColor("#F8F8F8")

            recyclerView {
                backgroundResource = R.drawable.profile_recycle_shader
                leftPadding = dip(10)
                val bindAdapter = AutoBindAdapter { viewGroup, _ ->
                    AnkoViewHolder(viewGroup, QuestionnaireActivityItemComponent())
                }.apply {
                    onItemClickListener = { i, viewModel, _ ->
                        takeApi(RetrofitImpl::class)?.questionnaireDetail(ConfigXmlAccessor.restoreValue(
                                context, Const.SignInInfo, Const.SignInSession, "")
                                ?: "", viewModel.row.id,true)?.senderAsync(
                                QuestionnaireDetailStruct::class,
                                this@QuestionnaireActivityComponent,
                                context)
                    }
                }
                bindSelf(QuestionnaireActivityViewModel::list) { it.list.value }
                        .toView(this) { _, value ->
                            bindAdapter.setItemsList(value as List<QuestionnaireItem>)
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

class QuestionnaireActivityItemComponent : BindingComponent<ViewGroup, QuestionnaireItem>() {
    override fun createViewWithBindings(ui: AnkoContext<ViewGroup>) = with(ui) {
        relativeLayout {
            topPadding = dip(5)
            bottomPadding = dip(5)
            val iv = imageView {
                id = R.id.left_icon
                backgroundColor = Color.TRANSPARENT
                bindSelf(QuestionnaireItem::leftIcon) { it.leftIcon }.toView(this) { view, value ->
                    if (value != null && value != 0) {
                        view.imageResource = value
                    }
                }
            }.lparams {
                width = wrapContent
                height = wrapContent
                alignParentLeft()
                alignParentTop()
            }


            val tv = textView {
                id = R.id.text
                singleLine = true
                textColor = Color.parseColor("#222222")
                ellipsize = TextUtils.TruncateAt.valueOf(TextUtils.TruncateAt.END.name)
                bindSelf(QuestionnaireItem::msg) { it.msg }.toView(this) { view, value ->
                    if (!TextUtils.isEmpty(value)) {
                        view.text = value
                    }
                }
                bindSelf(QuestionnaireItem::haveToSee) { it.haveToSee }.toView(this) { view, value ->
                    if (value != null) {
                        view.textColor = Color.parseColor(if (value) {
                            "#b4b3b3"
                        } else {
                            "#222222"
                        })
                    }
                }

            }.lparams {
                addRule(RelativeLayout.RIGHT_OF, iv.id)
                addRule(RelativeLayout.ALIGN_TOP, iv.id)
                alignParentRight()
                leftMargin = dip(10)
            }

            textView {
                singleLine = true
                ellipsize = TextUtils.TruncateAt.valueOf(TextUtils.TruncateAt.END.name)
                bindSelf(QuestionnaireItem::date) { it.date }.toView(this) { view, value ->
                    if (!TextUtils.isEmpty(value)) {
                        view.text = value
                    }
                }
            }.lparams {
                addRule(RelativeLayout.ALIGN_LEFT, tv.id)
                addRule(RelativeLayout.BELOW, tv.id)
                alignParentRight()
            }

            lparams {
                width = matchParent
                height = wrapContent
            }
        }
    }
}