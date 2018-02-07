package com.superfactory.sunyatsin.Interface.BindingActivity.QuestionnaireDetailActivity

import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.Gravity
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.DslView.horizontalLayout
import com.superfactory.library.Bridge.Anko.DslView.refresh
import com.superfactory.library.Bridge.Anko.widget.AnkoViewHolder
import com.superfactory.library.Bridge.Anko.widget.AutoBindAdapter
import com.superfactory.library.Communication.Sender.senderAsync
import com.superfactory.library.Context.Extensions.ToolbarExtensions.Companion.setRightTextColor
import com.superfactory.library.Context.Extensions.takeApi
import com.superfactory.library.Utils.ConfigXmlAccessor
import com.superfactory.sunyatsin.Bean.QuestionnaireCommitBean
import com.superfactory.sunyatsin.Bean.QuestionnaireCommitItem
import com.superfactory.sunyatsin.Communication.RetrofitImpl
import com.superfactory.sunyatsin.R
import com.superfactory.sunyatsin.Struct.BaseStructImpl
import com.superfactory.sunyatsin.Struct.Const
import org.jetbrains.anko.*
import org.jetbrains.anko.collections.forEachWithIndex
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.recyclerview.v7._RecyclerView
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * Created by vicky on 2018.02.01.
 *
 * @Author vicky
 * @Date 2018年02月01日  16:28:59
 * @ClassName 这里输入你的类名(或用途)
 */
class QuestionnaireDetailActivityComponent(viewModel: QuestionnaireDetailActivityViewModel) :
        BindingComponent<QuestionnaireDetailActivity, QuestionnaireDetailActivityViewModel>(viewModel) {
    override fun createViewWithBindings(ui: AnkoContext<QuestionnaireDetailActivity>) = with(ui) {
        coordinatorLayout {
            refresh {
                backgroundColor = Color.parseColor("#f8f8f8")
                verticalLayout {
                    backgroundColor = Color.TRANSPARENT
                    verticalLayout {
                        backgroundColor = Color.WHITE
                        topPadding = dip(20)
                        bottomPadding = dip(20)
                        textView {
                            textSize = 15f
                            textColor = Color.parseColor("#222222")
                            paint.isFakeBoldText = true
                            gravity = Gravity.CENTER_HORIZONTAL
                            bindSelf(QuestionnaireDetailActivityViewModel::questionnaireTitle) { it.questionnaireTitle.value }
                                    .toView(this) { view, value ->
                                        if (!TextUtils.isEmpty(value)) {
                                            view.text = value
                                        }
                                    }
                        }.lparams {
                            setHorizontalGravity(Gravity.CENTER_HORIZONTAL)
                            width = matchParent
                            height = wrapContent
                        }
                        relativeLayout {
                            val tv = textView {
                                id = R.id.text2
                                textSize = 12f
//                                textColor = Color.parseColor("#222222")
                                gravity = Gravity.CENTER_HORIZONTAL
                                bindSelf(QuestionnaireDetailActivityViewModel::questionnaireAmount) { it.questionnaireAmount.value }
                                        .toView(this) { view, value ->
                                            if (!TextUtils.isEmpty(value)) {
                                                view.text = value
                                            }
                                        }
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                centerHorizontally()
                            }
                            view {
                                backgroundColor = Color.parseColor("#1688ff")
                            }.lparams {
                                height = dip(3)
                                addRule(RelativeLayout.ALIGN_LEFT, tv.id)
                                addRule(RelativeLayout.BELOW, tv.id)
                                addRule(RelativeLayout.ALIGN_RIGHT, tv.id)
                                centerHorizontally()
                            }
                        }.lparams {
                            width = matchParent
                            topMargin = dip(15)
                            height = wrapContent
                        }

                    }.lparams {
                        width = matchParent
                        height = wrapContent
                        topPadding = dip(10)
                        bottomPadding = dip(10)
                        setHorizontalGravity(Gravity.CENTER)
                    }

                    recyclerView {
                        backgroundColor = Color.TRANSPARENT
                        val bindAdapter = AutoBindAdapter { viewGroup, _ ->
                            AnkoViewHolder(viewGroup, QuestionnaireDetailActivityItemComponent({
                                if (it == -1) {
                                    viewModelSafe.amount.value -= 1
                                } else {
                                    viewModelSafe.amount.value = it
                                }
                            }))
                        }
                        val color1 = Color.parseColor("#b4b3b3")
                        val color2 = Color.parseColor("#ffffff")
                        bindSelf(QuestionnaireDetailActivityViewModel::amount) {
                            it.amount.value
                        }.toView(this) { view, value ->
                            if (value != null) {
                                if ((value <= 0) || !mustSelectOne(viewModelSafe.itemList.value)) {
                                    if (viewModelSafe.rightTextColor.value != color1) {
                                        viewModelSafe.eraseRight.value = true
                                        doAsync { setRightTextColor(color1, context, viewModel) }
                                    }
                                } else {
                                    if (viewModelSafe.rightTextColor.value != color2) {
                                        viewModelSafe.eraseRight.value = true
                                        doAsync { setRightTextColor(color2, context, viewModel) }

                                    }
                                }
                            }
                        }
                        viewModelSafe.rightClickable.value = {
                            val size = viewModel?.itemList?.value?.size ?: 0
                            if (size > 0) {
                                if (viewModelSafe.amount.value > 0 && mustSelectOne(viewModelSafe.itemList.value)) {
                                    doStoreQuestionnaire()
                                }
                            }
                        }

                        bindSelf(QuestionnaireDetailActivityViewModel::itemList) { it.itemList.value }
                                .toView(this) { _, value ->
                                    bindAdapter.setItemsList(value as List<QuestionDetailActItemViewModel>)
                                }
                        layoutManager = LinearLayoutManager(context)
//                        addItemDecoration(getLineDividerItemDecoration(dip(10), ContextCompat.getColor(context, R.color.transparent)))
                        adapter = bindAdapter
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

    private fun mustSelectOne(value: List<QuestionDetailActItemViewModel>?): Boolean {
        var flag = false
        if (value == null) return flag
        value.forEachWithIndex { i, e ->
            val answerList = e.answerList
            if (answerList != null && answerList.isNotEmpty()) {
                var flag2 = true
                answerList.forEachWithIndex { i2, e2 ->
                    if (e2.checked) {
                        flag2 = false
                    }
                }
                if (!flag2) {
                    flag = true
                }
            }
        }
        return flag
    }

    private fun @AnkoViewDslMarker _RecyclerView.doStoreQuestionnaire() {
        val list = arrayListOf<QuestionnaireCommitItem>()
        for (i in 0 until (viewModel!!.itemList.value?.size?:0)) {
            val entity = viewModel!!.itemList.value!![i]
            for (j in 0 until (entity.answerList?.size ?: 0)) {
                val answer = entity.answerList!![j]
                list.add(QuestionnaireCommitItem(answer.optionId, "", answer.scope, entity.questionId))
            }
        }
        takeApi(RetrofitImpl::class)?.storeQuestionnaire(ConfigXmlAccessor.restoreValue(
                context, Const.SignInInfo, Const.SignInSession, "")
                ?: "", QuestionnaireCommitBean(list, viewModel?.parentId
                ?: ""), true)?.senderAsync(BaseStructImpl::class, this@QuestionnaireDetailActivityComponent, context)
    }
}

class QuestionnaireDetailActivityItemComponent(private val fun0: (Int) -> Unit) : BindingComponent<ViewGroup, QuestionDetailActItemViewModel>() {
    override fun createViewWithBindings(ui: AnkoContext<ViewGroup>) = with(ui) {
        verticalLayout {
            padding = dip(10)
            backgroundColor = Color.WHITE

            textView {
                backgroundColor = Color.TRANSPARENT
                singleLine = true
                textSize = 14f
                textColor = Color.parseColor("#222222")
                ellipsize = TextUtils.TruncateAt.valueOf(TextUtils.TruncateAt.END.name)
                bindSelf(QuestionDetailActItemViewModel::question) { it.question }.toView(this) { view, value ->
                    if (!TextUtils.isEmpty(value)) {
                        view.text = value
                    }
                }
            }.lparams {
                width = wrapContent
                height = wrapContent
            }


            recyclerView {
                backgroundColor = Color.TRANSPARENT

                val bindAdapter = AutoBindAdapter { viewGroup, _ ->
                    AnkoViewHolder(viewGroup, QuestionnaireDetailActivityItemChildComponent())
                }.assignment { holder, v, i ->
                    v.checked = (viewModelSafe.selected!![i]?.isChecked ?: false)
                }.apply {
                            onItemClickListener = { i, viewModel, h ->
                                val result = !(viewModelSafe.selected!![i]?.isChecked ?: true)
                                viewModelSafe.selected!![i]?.isChecked = result
                                var size = 0
                                when (viewModelSafe.type) {
                                    1 -> {
                                        size = 1
                                    }
                                    2 -> {
                                        size = 2
                                    }
                                    else -> {
                                        size = viewModelSafe.selected!!.size
                                    }
                                }
                                if (result) {
                                    val selectAmount = unSelectOther(viewModelSafe.selected!!, i, size)
                                    fun0(selectAmount)
                                } else {
                                    fun0(-1)
                                }


                                h.component.notifyChanges()
                            }
                        }
                bindSelf(QuestionDetailActItemViewModel::answerList) { it.answerList }
                        .toView(this) { _, value ->
                            bindAdapter.setItemsList(value as List<Answer>)
                        }
                layoutManager = LinearLayoutManager(context)
//                addItemDecoration(getLineDividerItemDecoration(dip(10), ContextCompat.getColor(context, R.color.gray)))
                adapter = bindAdapter
            }.lparams {
                topMargin = dip(10)
                width = matchParent
                height = wrapContent
            }



            lparams {
                width = matchParent
                height = wrapContent
                topMargin = dip(10)
            }
        }
    }

    private fun unSelectOther(selected: Array<AnswerCheckBox?>, i: Int, size: Int): Int {
        if (size == 0) return 0
        val arr = arrayOfNulls<Int>(size)
        var j = 0
        var temp = -1
        for (idx in 0 until i) {
            if (selected[idx]?.isChecked ?: false) {
                if (j >= size) {
                    temp = j
                } else {
                    arr[j] = idx
                    j++
                }
            }
        }
        if (i + 1 < selected.size) {
            for (idx in i + 1 until selected.size) {
                if (selected[idx]?.isChecked ?: false) {
                    if (j >= size) {
                        temp = j
                    } else {
                        arr[j] = idx
                        j++
                    }
                }
            }
        }
        if (temp != -1 && temp != i) {
            selected[temp]?.isChecked = false
        } else if (arr[size - 1] != null) {
            selected[size]?.isChecked = false
        }
        return j
    }
}

class QuestionnaireDetailActivityItemChildComponent : BindingComponent<ViewGroup, Answer>() {
    override fun createViewWithBindings(ui: AnkoContext<ViewGroup>) = with(ui) {
        horizontalLayout {
            leftPadding = dip(10)
            backgroundColor = Color.WHITE
            imageView {
                backgroundColor = Color.TRANSPARENT
                bindSelf(Answer::checked) { it.checked }.toView(this) { view, value ->
                    if (value != null) {
                        view.imageResource = if (value) R.drawable.answer_checked_icon else R.drawable.answer_uncheck_icon
                    }
                }
            }.lparams {
                width = wrapContent
                height = wrapContent
            }
            textView {
                backgroundColor = Color.TRANSPARENT
                singleLine = true
                textSize = 14f
                textColor = Color.parseColor("#222222")
                ellipsize = TextUtils.TruncateAt.valueOf(TextUtils.TruncateAt.END.name)
                bindSelf(Answer::name) { it.name }.toView(this) { view, value ->
                    if (!TextUtils.isEmpty(value)) {
                        view.text = value
                    }
                }
            }.lparams {
                width = wrapContent
                height = wrapContent
            }

            lparams {
                setHorizontalGravity(Gravity.CENTER_VERTICAL)
                width = matchParent
                height = wrapContent
            }
        }
    }
}