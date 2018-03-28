package com.superfactory.sunyatsen.Interface.BindingActivity.GenderActivity

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.ViewGroup
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.DslView.refresh
import com.superfactory.library.Bridge.Anko.ViewExtensions.getLineDividerItemDecoration
import com.superfactory.library.Bridge.Anko.widget.AnkoViewHolder
import com.superfactory.library.Bridge.Anko.widget.AutoBindAdapter
import com.superfactory.library.Context.Extensions.ToolbarExtensions.Companion.setRightTextColor
import com.superfactory.sunyatsen.R
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * Created by vicky on 2018.01.31.
 *
 * @Author vicky
 * @Date 2018年01月31日  20:11:48
 * @ClassName 这里输入你的类名(或用途)
 */
class GenderActivityComponent(viewModel: GenderActivityViewModel) :
        BindingComponent<GenderActivity, GenderActivityViewModel>(viewModel) {
    override fun createViewWithBindings(ui: AnkoContext<GenderActivity>) = with(ui) {
        refresh {
            backgroundColor = Color.parseColor("#f8f8f8")
            recyclerView {
                backgroundResource = R.drawable.profile_recycle_shader
                leftPadding = dip(10)

                val bindAdapter = AutoBindAdapter { viewGroup, _ ->
                    AnkoViewHolder(viewGroup, GenderActivityItemComponent())
                }.apply {
                    onItemClickListener = { i, viewModel, holder ->
                        viewModel.checked = true
                        viewModelSafe.selected.value = i
                        holder.component.notifyChanges()
                    }
                }.assignment { holder, _, position ->
                            if (viewModelSafe.selected.value != -1)
                                holder.component.viewModel?.checked = viewModelSafe.selected.value == position
                        }
                bindSelf(GenderActivityViewModel::genderList) { it.genderList }
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
            val color1 = Color.parseColor("#b4b3b3")
            val color2 = Color.parseColor("#ffffff")
            bindSelf(GenderActivityViewModel::selected) {
                it.selected.value
            }.toView(this) { view, value ->
                if (value != null) {
                    if (value == -1) {
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
                viewModelSafe.ownerNotifier?.invoke(101, viewModelSafe.selected.value)
            }
            lparams {
                width = matchParent
                height = matchParent
            }
        }
    }
}

class GenderActivityItemComponent : BindingComponent<ViewGroup, GenderActivityItemViewModel>() {
    override fun createViewWithBindings(ui: AnkoContext<ViewGroup>) = with(ui) {
        relativeLayout {
            rightPadding = dip(10)
            textView {
                textSize = 14f
                textColor = Color.parseColor("#222222")
                bindSelf(GenderActivityItemViewModel::gender) {
                    it.gender
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


            imageView {
                backgroundColor = Color.TRANSPARENT
                bindSelf(GenderActivityItemViewModel::checked) {
                    it.checked
                }.toView(this) { view, value ->
                    if (value != null && value) {
                        view.imageResource = R.drawable.gender_checked_icon
                    } else view.imageResource = 0
                }
            }.lparams {
                width = wrapContent
                height = wrapContent
                alignParentRight()
                centerVertically()
            }

            lparams {
                width = matchParent
                height = wrapContent
                bottomPadding = dip(10)
                topPadding = dip(10)
            }
        }
    }
}