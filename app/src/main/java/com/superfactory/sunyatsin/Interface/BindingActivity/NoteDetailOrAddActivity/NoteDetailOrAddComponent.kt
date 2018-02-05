package com.superfactory.sunyatsin.Interface.BindingActivity.NoteDetailOrAddActivity

import android.graphics.Color
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.DslView.refresh
import com.superfactory.library.Bridge.Anko.ViewExtensions.getLineDividerItemDecoration
import com.superfactory.library.Bridge.Anko.bindings.toText
import com.superfactory.library.Bridge.Anko.widget.AnkoViewHolder
import com.superfactory.library.Bridge.Anko.widget.AutoBindAdapter
import com.superfactory.sunyatsin.Interface.BindingFragment.Profile.ProfileFragmentItemViewModel
import com.superfactory.sunyatsin.R
import org.jetbrains.anko.*
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.nestedScrollView

/**
 * Created by vicky on 2018/2/4.
 */
class NoteDetailOrAddComponent(viewModel: NoteDetailOrAddViewModel) :
        BindingComponent<NoteDetailOrAddActivity, NoteDetailOrAddViewModel>(viewModel) {
    override fun createViewWithBindings(ui: AnkoContext<NoteDetailOrAddActivity>) = with(ui) {
        coordinatorLayout {

            refresh {
                backgroundColor = Color.parseColor("#f8f8f8")
                nestedScrollView {
                    backgroundColor = Color.TRANSPARENT
                    verticalLayout {
                        backgroundColor = Color.TRANSPARENT


                        relativeLayout {
                            backgroundResource = R.drawable.profile_recycle_shader
                            topPadding = dip(10)
                            bottomPadding = dip(10)
                            onClick {
                                this@NoteDetailOrAddComponent.viewModel?.onItemClickListener?.invoke(0, null)
                            }
                            val iv = imageView {
                                id = R.id.left_icon
                                backgroundColor = Color.TRANSPARENT
                                scaleType = ImageView.ScaleType.FIT_XY
                                bindSelf(NoteDetailOrAddViewModel::dutyImage) { it.dutyImage }.toView(this) { view, value ->
                                    if (value != null && value != 0) {
                                        view.imageResource = value
                                    }
                                }
                            }.lparams {
                                centerVertically()
                                leftMargin = dip(10)
                                alignParentLeft()
                                width = wrapContent
                                height = wrapContent
                            }



                            textView {
                                textColor = Color.parseColor("#222222")
                                textSize = 14f
                                bindSelf(NoteDetailOrAddViewModel::dutyType) { it.dutyType.value }.toText(this)
                            }.lparams {
                                centerVertically()
                                addRule(RelativeLayout.RIGHT_OF, iv.id)
                                leftMargin = dip(10)
                                width = wrapContent
                                height = wrapContent
                            }

                            val iv2 = imageView {
                                id = R.id.right_icon
                                backgroundColor = Color.TRANSPARENT
                                scaleType = ImageView.ScaleType.FIT_XY
                                bindSelf(NoteDetailOrAddViewModel::rightImage) { it.rightImage }.toView(this) { view, value ->
                                    if (value != null && value != 0) {
                                        view.imageResource = value
                                    }
                                }
                            }.lparams {
                                centerVertically()
                                rightMargin = dip(10)
                                alignParentRight()
                                width = wrapContent
                                height = wrapContent
                            }

                            textView {
                                textSize = 14f
                                bindSelf(NoteDetailOrAddViewModel::dutyText) { it.dutyText.value }.toView(this) { view, value ->
                                    if (value != null) {
                                        if (TextUtils.isEmpty(value)) {
                                            view.text = "请点击进行选择"
                                        } else
                                            view.text = value
                                    }
                                }

                            }.lparams {
                                centerVertically()
                                addRule(RelativeLayout.LEFT_OF, iv2.id)
                                rightMargin = dip(5)
                                width = wrapContent
                                height = wrapContent
                            }


                        }

                        relativeLayout {
                            topPadding = dip(10)
                            bottomPadding = dip(10)

                            backgroundResource = R.drawable.profile_recycle_shader
                            onClick {
                                this@NoteDetailOrAddComponent.viewModel?.onItemClickListener?.invoke(1, null)
                            }
                            val iv = imageView {
                                id = R.id.left_icon
                                backgroundColor = Color.TRANSPARENT
                                scaleType = ImageView.ScaleType.FIT_XY
                                bindSelf(NoteDetailOrAddViewModel::mattersImage) { it.mattersImage }.toView(this) { view, value ->
                                    if (value != null && value != 0) {
                                        view.imageResource = value
                                    }
                                }
                            }.lparams {
                                centerVertically()
                                alignParentLeft()
                                topMargin = dip(10)
                                leftMargin = dip(10)
                                width = wrapContent
                                height = wrapContent
                            }


                            val tx = textView {
                                id = R.id.text
                                textColor = Color.parseColor("#222222")
                                textSize = 14f
                                bindSelf(NoteDetailOrAddViewModel::mattersType) { it.mattersType.value }.toView(this) { view, value ->
                                    if (value != null) {
                                        if (TextUtils.isEmpty(value)) {
                                            view.text = "请点击进行选择"
                                        } else
                                            view.text = value
                                    }
                                }
                            }.lparams {
                                centerVertically()
                                addRule(RelativeLayout.RIGHT_OF, iv.id)
                                leftMargin = dip(10)
                                width = wrapContent
                                height = wrapContent
                            }

                            val iv2 = imageView {
                                id = R.id.right_icon
                                backgroundColor = Color.TRANSPARENT
                                scaleType = ImageView.ScaleType.FIT_XY
                                bindSelf(NoteDetailOrAddViewModel::rightImage) { it.rightImage }.toView(this) { view, value ->
                                    if (value != null && value != 0) {
                                        view.imageResource = value
                                    }
                                }
                            }.lparams {
                                alignParentRight()
                                rightMargin = dip(10)
                                bottomMargin = dip(14)
                                alignParentBottom()
                                width = wrapContent
                                height = wrapContent
                            }

                            textView {
                                maxLines = 3
                                ellipsize = TextUtils.TruncateAt.valueOf(TextUtils.TruncateAt.END.name)
                                textSize = 14f
                                minHeight = 0
                                bindSelf(NoteDetailOrAddViewModel::mattersText) { it.mattersText.value }.toText(this)
                            }.lparams {
                                addRule(RelativeLayout.LEFT_OF, iv2.id)
                                addRule(RelativeLayout.ALIGN_BOTTOM, iv2.id)
                                addRule(RelativeLayout.ALIGN_LEFT, tx.id)
                                addRule(RelativeLayout.BELOW, tx.id)
                                rightMargin = dip(5)
                                topMargin = dip(15)
                                width = wrapContent
                                height = wrapContent
                            }


                        }

                        recyclerView {
                            backgroundResource = R.drawable.profile_recycle_shader
                            val bindAdapter = AutoBindAdapter { viewGroup, _ ->
                                AnkoViewHolder(viewGroup, NoteItemSelectViewComponent())
                            }.apply {
                                onItemClickListener = { i, v, h ->
                                    this@NoteDetailOrAddComponent.viewModel?.onItemClickListener?.invoke(i + 2, v)
                                }
                            }.assignment { holder, item, position ->
                                        when (position) {
                                            0 -> {
                                                item.content = viewModel?.createDate?.value ?: ""
                                            }
                                            1 -> {
                                                item.content = viewModel?.startTime?.value ?: ""
                                            }
                                            2 -> {
                                                item.content = viewModel?.endTime?.value ?: ""
                                            }
                                        }
                                    }
                            bindSelf(NoteDetailOrAddViewModel::createDate) { it.createDate.value }.toView(this) { view, value ->
                                if (value != null) {
                                    bindAdapter.notifyDataSetChanged()
                                }
                            }
                            bindSelf(NoteDetailOrAddViewModel::startTime) { it.startTime.value }.toView(this) { view, value ->
                                if (value != null) {
                                    bindAdapter.notifyDataSetChanged()
                                }
                            }
                            bindSelf(NoteDetailOrAddViewModel::endTime) { it.endTime.value }.toView(this) { view, value ->
                                if (value != null) {
                                    bindAdapter.notifyDataSetChanged()
                                }
                            }

                            bindSelf(NoteDetailOrAddViewModel::dateSelectList) { it.dateSelectList.value }
                                    .toView(this) { _, value ->
                                        bindAdapter.setItemsList(value)
                                    }
                            layoutManager = LinearLayoutManager(context)
                            adapter = bindAdapter
                            addItemDecoration(getLineDividerItemDecoration(1, ContextCompat.getColor(context, R.color.gray)))
                            leftPadding = dip(10)
                            rightPadding = dip(10)
                        }.lparams {
                            width = matchParent
                            topMargin = dip(10)
                            height = wrapContent
                        }
                    }.lparams {
                        topMargin = dip(10)
                        width = matchParent
                        height = matchParent
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
            viewModelSafe.rightClickable.value = {
                if (viewModelSafe.canCommitData.value) {

                }
            }
            bindSelf(NoteDetailOrAddViewModel::canCommitData) {
                it.canCommitData.value
            }.toView(this) { view, value ->
                if (value != null) {
                    if (value) {
                        viewModelSafe.rightTextColor.value = Color.parseColor("#469ced")
                    } else {
                        viewModelSafe.rightTextColor.value = Color.parseColor("#ffffff")
                    }
                }
            }

            bindSelf(NoteDetailOrAddViewModel::tips) { it.tips.value }.toView(this) { view, value ->
                if (value != null) {
                    if(!TextUtils.isEmpty(value)){
                        Snackbar.make(view, value, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }
}


class NoteItemSelectViewComponent : BindingComponent<ViewGroup, DateSelectItem>() {
    override fun createViewWithBindings(ui: AnkoContext<ViewGroup>) = with(ui) {
        relativeLayout {
            val icon = imageView {
                id = R.id.left_icon
                backgroundColor = Color.TRANSPARENT
                scaleType = ImageView.ScaleType.FIT_XY
                bindSelf(DateSelectItem::image) { it.image }
                        .toView(this) { view, value ->
                            if (value != null && value != 0) {
                                view.imageResource = value
                            }
                        }
            }.lparams {
                width = wrapContent
                height = wrapContent
                centerVertically()
                alignParentLeft()
            }

            textView {
                bindSelf(ProfileFragmentItemViewModel::name) { it.name }.toText(this)
                padding = dip(12)
                leftPadding = 0
                textSize = 16.0f
                textColor = Color.BLACK
            }.lparams {
                width = wrapContent
                height = wrapContent
                leftMargin = dip(10)
                centerVertically()
                addRule(RelativeLayout.RIGHT_OF, icon.id)
            }
            val iv2 = imageView {
                id = R.id.right_icon
                backgroundColor = Color.TRANSPARENT
                scaleType = ImageView.ScaleType.FIT_XY
                bindSelf(DateSelectItem::right) { it.right }.toView(this) { view, value ->
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
                bindSelf(DateSelectItem::content) { it.content }.toView(this) { view, value ->
                    if (value != null) {
                        if (TextUtils.isEmpty(value)) {
                            view.text = "请点击进行选择"
                        } else
                            view.text = value
                    }
                }
            }.lparams {
                width = wrapContent
                height = wrapContent
                centerVertically()
                addRule(RelativeLayout.LEFT_OF, iv2.id)
            }



            lparams {
                width = matchParent
                height = wrapContent
                gravity = Gravity.CENTER_VERTICAL and Gravity.START
//                rightPadding = dip(12)
            }
        }
    }
}