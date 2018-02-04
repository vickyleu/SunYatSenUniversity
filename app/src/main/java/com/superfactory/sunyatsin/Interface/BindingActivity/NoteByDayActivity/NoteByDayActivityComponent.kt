package com.superfactory.sunyatsin.Interface.BindingActivity.NoteByDayActivity

import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.DslView.refresh
import com.superfactory.library.Bridge.Anko.bindings.toText
import com.superfactory.library.Bridge.Anko.widget.AnkoViewHolder
import com.superfactory.library.Bridge.Anko.widget.AutoBindAdapter
import com.superfactory.sunyatsin.Interface.BindingFragment.Note.NoteSnapShot
import com.superfactory.sunyatsin.R
import org.jetbrains.anko.*
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * Created by vicky on 2018/2/4.
 */
class NoteByDayActivityComponent(viewModel: NoteByDayActivityViewModel) :
        BindingComponent<NoteByDayActivity, NoteByDayActivityViewModel>(viewModel) {
    override fun createViewWithBindings(ui: AnkoContext<NoteByDayActivity>) = with(ui) {
        coordinatorLayout {

            refresh {
                backgroundColor = Color.parseColor("#f8f8f8")

                recyclerView {
                    backgroundColor = Color.TRANSPARENT
                    val bindAdapter = AutoBindAdapter { viewGroup, _ ->
                        AnkoViewHolder(viewGroup, NoteItemViewSnapWithGapComponent())
                    }.apply {
                        onItemClickListener = { i, v, h ->
                            this@NoteByDayActivityComponent.viewModel?.ownerNotifier?.invoke(i, v)
                        }
                    }
                    bindSelf(NoteByDayActivityViewModel::itemList) { it.itemList.value }
                            .toView(this) { _, value ->
                                bindAdapter.setItemsList(value as List<NoteSnapShot>)
                            }

                    layoutManager = LinearLayoutManager(context)
                    adapter = bindAdapter
                }.lparams {
                    width = matchParent
                    height = matchParent
                }
            }.lparams {
                width = matchParent
                height = matchParent
            }

        }
    }
}

class NoteItemViewSnapWithGapComponent : BindingComponent<ViewGroup, NoteSnapShot>() {
    override fun createViewWithBindings(ui: AnkoContext<ViewGroup>) = with(ui) {
        relativeLayout {

            backgroundColor = Color.WHITE

            val iv = imageView {
                id = R.id.left_icon
                bindSelf(NoteSnapShot::image) { it.image }.toView(this) { view, value ->
                    if (value != null && value != 0) {
                        view.imageResource = value
                    }
                }
                backgroundColor = Color.TRANSPARENT
                scaleType = ImageView.ScaleType.FIT_XY
            }.lparams {
                alignParentTop()
                alignParentLeft()
                width = wrapContent
                height = wrapContent
            }

            val tv = textView {
                id = R.id.left_text
                singleLine = true
                textSize = 14f
                bindSelf(NoteSnapShot::title) { it.title }.toText(this)
                textColor = Color.parseColor("#222222")
            }.lparams {
                addRule(RelativeLayout.RIGHT_OF, iv.id)
                alignParentTop()
                leftMargin = dip(10)
                width = wrapContent
                height = wrapContent
            }

            textView {
                singleLine = true
                textSize = 12f
                bindSelf(NoteSnapShot::createDate) { it.createDate }.toText(this)
            }.lparams {
                alignParentRight()
                alignParentTop()
                width = wrapContent
                height = wrapContent
            }


            val tv2 = textView {
                id = R.id.right_text
                maxLines = 2
                ellipsize = TextUtils.TruncateAt.valueOf(TextUtils.TruncateAt.END.name)
                setLineSpacing(0f, 1f)
                textSize = 14f
                bindSelf(NoteSnapShot::content) { it.content }.toText(this)
            }.lparams {
                addRule(RelativeLayout.BELOW, tv.id)
                addRule(RelativeLayout.ALIGN_LEFT, tv.id)
                topMargin = dip(5)
                width = wrapContent
                height = wrapContent
            }

            textView {
                singleLine = true
                ellipsize = TextUtils.TruncateAt.valueOf(TextUtils.TruncateAt.END.name)
                textSize = 14f
                bindSelf(NoteSnapShot::dateRange) { it.dateRange }.toText(this)
            }.lparams {
                addRule(RelativeLayout.BELOW, tv2.id)
                addRule(RelativeLayout.ALIGN_LEFT, tv2.id)
                topMargin = dip(5)
                width = wrapContent
                height = wrapContent
            }

            topPadding = dip(10)
            bottomPadding = dip(10)
            lparams {
                width = matchParent
                height = wrapContent
                leftMargin = dip(10)
                rightMargin = dip(10)
                topMargin = dip(10)
            }
        }
    }
}