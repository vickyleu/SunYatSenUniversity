package com.superfactory.sunyatsin.Interface.BindingFragment.Note

import android.graphics.Color
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v4.content.ContextCompat
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import cn.qqtheme.framework.picker.DatePicker
import cn.qqtheme.framework.util.ConvertUtils
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.BindingExtensions.getAppNoStatusBarSize
import com.superfactory.library.Bridge.Anko.BindingExtensions.getAppStatusBarSize
import com.superfactory.library.Bridge.Anko.BindingExtensions.getAttrDrawablValue
import com.superfactory.library.Bridge.Anko.DslView.classicsHeader
import com.superfactory.library.Bridge.Anko.DslView.refresh
import com.superfactory.library.Bridge.Anko.bindings.toText
import com.superfactory.library.Bridge.Anko.observable
import com.superfactory.library.Bridge.Anko.widget.AnkoViewHolder
import com.superfactory.library.Bridge.Anko.widget.AutoBindAdapter
import com.superfactory.library.Bridge.Model.ToolbarBindingModel
import com.superfactory.library.Communication.Sender.senderAsync
import com.superfactory.library.Context.Extensions.takeApi
import com.superfactory.library.Debuger
import com.superfactory.library.Utils.ConfigXmlAccessor
import com.superfactory.library.Utils.TimeUtil
import com.superfactory.sunyatsin.Bean.MsgBean
import com.superfactory.sunyatsin.Bean.NoteListBean
import com.superfactory.sunyatsin.Communication.RetrofitImpl
import com.superfactory.sunyatsin.R
import com.superfactory.sunyatsin.Struct.Const
import com.superfactory.sunyatsin.Struct.Message.MessageStruct
import com.superfactory.sunyatsin.Struct.Note.NoteStruct
import org.jetbrains.anko.*
import org.jetbrains.anko.design.collapsingToolbarLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.nestedScrollView

/**
 * Created by vicky on 2018.01.18.
 *
 * @Author vicky
 * @Date 2018年01月18日  11:57:26
 * @ClassName 这里输入你的类名(或用途)
 */
class NoteFragmentComponent(viewModel: NoteFragmentViewModel) : BindingComponent<NoteFragment, NoteFragmentViewModel>(viewModel) {
    private var mOffset = 0
    private var mScrollY = 0
    override fun createViewWithBindings(ui: AnkoContext<NoteFragment>): View = with(ui) {
        frameLayout {
            backgroundColor = Color.parseColor("#f8f8f8")
            var parallax: LinearLayout? = null
            val screenHeight = getAppNoStatusBarSize(context).height
            val barSize = getAppStatusBarSize(context)


            val normalCollapsing = (screenHeight / 2.0).toInt()//默认日志未填写,完全展开的可折叠高度
            val backgroundCollapsing = (screenHeight * 1.1).toInt()//默认日志未填写,完全展开的可折叠高度

            val topMarginHidden = -(normalCollapsing * 0.3).toInt()//顶部隐藏的空间,固定值

            val hoverCollapsing = observable((normalCollapsing * 0.5).toInt())

            val pinAreaCollapsing = dip(60)//悬停区域可折叠高度
            val totalCollapsing = observable(normalCollapsing)







            parallax = verticalLayout {
                backgroundColor = Color.parseColor("#1688FF")
                val lp = CollapsingToolbarLayout.LayoutParams(matchParent, backgroundCollapsing)
                lp.collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PARALLAX
                lp.topMargin = topMarginHidden
                layoutParams = lp
                verticalLayout {
                    backgroundColor = Color.TRANSPARENT
                    addView(with(AnkoContextImpl(context, this, false)) {
                        calendarAreaComponent(screenHeight)
                    })
                }.lparams {
                    height = matchParent
                    width = matchParent
                    topPadding = Math.abs(topMarginHidden)
                    bottomPadding = backgroundCollapsing - (totalCollapsing.value + topMarginHidden) - pinAreaCollapsing / 2
                    setHorizontalGravity(Gravity.CENTER_HORIZONTAL)
                    setVerticalGravity(Gravity.CENTER_VERTICAL)
                }


            }


            refresh {
                isEnableRefresh = true

                val ch = classicsHeader {
                    setAccentColor(Color.WHITE)
                    bindSelf(NoteFragmentViewModel::isEditToday) { it.isEditToday.value }.toView(this) { view, value ->
                        if (value != null) {
                            view.setAccentColor(if (value) Color.parseColor("#222222") else Color.parseColor("#303F9F"))
                        }
                    }
                }.lparams {
                    width = matchParent
                    height = wrapContent
                }
                removeView(ch)
                setRefreshHeader(ch)


                nestedScrollView {
                    overScrollMode = View.OVER_SCROLL_NEVER
                    isNestedScrollingEnabled = false
                    backgroundColor = Color.TRANSPARENT
                    fitsSystemWindows = true
                    verticalLayout {
                        backgroundColor = Color.TRANSPARENT
                        collapsingToolbarLayout {
                            isTitleEnabled = false
                            backgroundColor = Color.TRANSPARENT
                            contentScrim = getAttrDrawablValue(context, R.attr.colorPrimary)
                            val lp = AppBarLayout.LayoutParams(matchParent, wrapContent)
                            lp.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or
                                    AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
                            layoutParams = lp

                            verticalLayout {
                                addView(with(AnkoContextImpl(context, this, false)) {
                                    buttonAreaComponent(screenHeight)
                                })
                                backgroundColor = Color.TRANSPARENT
                            }.lparams {
                                width = matchParent
                                height = pinAreaCollapsing
                                val margins = totalCollapsing.value - (pinAreaCollapsing - topMarginHidden)
                                topMargin = margins
                                gravity = Gravity.BOTTOM
                            }
                            bindSelf(NoteFragmentViewModel::isEditToday) { it.isEditToday.value }.toView(this) { view, value ->
                                if (value != null) {
                                    view.visibility = if (value) {
                                        View.GONE
                                    } else {
                                        owner.bar?.alpha = 1f
                                        View.VISIBLE
                                    }
                                }
                            }

                        }.lparams {
                            width = matchParent
                            height = wrapContent
                            gravity = Gravity.FILL_VERTICAL
                        }


                        recyclerView {
                            isNestedScrollingEnabled = false
                            fitsSystemWindows = true
                            backgroundColor = Color.parseColor("#f8f8f8")
                            val bindAdapter = AutoBindAdapter { viewGroup, _ ->
                                AnkoViewHolder(viewGroup, NoteItemViewComponent())
                            }.apply {
                                onItemClickListener = { i, viewModel, _ ->
                                    Debuger.printMsg(this, "invoke  1  " + viewModelSafe.onItemClicked)
//                                    this@NoteFragmentComponent.viewModelSafe.onItemClicked?.invoke(i, viewModel)
                                    (this@NoteFragmentComponent.viewModel as? ToolbarBindingModel)?.ownerNotifier?.invoke(103, viewModel)
                                }
                            }
                            layoutManager = LinearLayoutManager(context)
                            adapter = bindAdapter
                            bindSelf(NoteFragmentViewModel::itemList) { it.itemList.value }
                                    .toView(this) { _, value ->
                                        bindAdapter.setItemsList(value)
                                    }
                        }.lparams {
                            bottomPadding = (screenHeight * 0.06).toInt()
                            width = matchParent
                            height = matchParent
                        }

                    }
                    val lp = CoordinatorLayout.LayoutParams(matchParent, matchParent)
                    lp.behavior = AppBarLayout.ScrollingViewBehavior()
                    lp.topMargin = (hoverCollapsing.value)
                    lp.gravity = Gravity.FILL_VERTICAL
                    layoutParams = lp
                }.apply {
                    this.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener {
                        var lastScrollY = 0
                        val h = DensityUtil.dp2px(170f)
                        val color = ContextCompat.getColor(context, R.color.colorPrimary) and 0x00ffffff
                        override fun onScrollChange(v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
                            if (lastScrollY < h) {
                                val scrollY2 = Math.min(h, scrollY)
                                mScrollY = if (scrollY2 > h) h else scrollY2
//                                toolbar_header.setAlpha(1f * mScrollY / h)
                                if (viewModelSafe.isEditToday.value) {
                                    owner.bar?.setBackgroundColor(255 * mScrollY / h shl 24 or color)
                                }
                                parallax.translationY = (mOffset - mScrollY).toFloat()
                            }
                            lastScrollY = scrollY;
                        }
                    })
                }


                setOnMultiPurposeListener(object : SimpleMultiPurposeListener() {
                    override fun onHeaderPulling(header: RefreshHeader?, percent: Float, offset: Int, headerHeight: Int, extendHeight: Int) {
                        mOffset = offset / 2
                        parallax.translationY = (mOffset - mScrollY).toFloat()
                        if (viewModelSafe.isEditToday.value) {
                            owner.bar?.alpha = 1 - Math.min(percent, 1f)
                        }
                    }

                    override fun onHeaderReleasing(header: RefreshHeader?, percent: Float, offset: Int, footerHeight: Int, extendHeight: Int) {
                        mOffset = offset / 2
                        parallax.translationY = (mOffset - mScrollY).toFloat()
                        if (viewModelSafe.isEditToday.value) {
                            owner.bar?.alpha = 1 - Math.min(percent, 1f)
                        }
                    }
                })

                setOnRefreshListener {
                    takeApi(RetrofitImpl::class)?.queryNoteList(ConfigXmlAccessor.restoreValue(
                            context, Const.SignInInfo, Const.SignInSession, "")
                            ?: "", true, NoteListBean(TimeUtil.takeNowTime("yyyy-MM-dd")
                            ?: ""))?.senderAsync(NoteStruct::class, this@NoteFragmentComponent, context, it)
                }
            }

            lparams {
                width = matchParent
                height = matchParent
            }
            viewModel?.rightClickable?.value = {
                val msgBean = MsgBean()
                takeApi(RetrofitImpl::class)?.loadMsg(ConfigXmlAccessor.restoreValue(
                        context, Const.SignInInfo, Const.SignInSession, "")
                        ?: "", true, msgBean)?.senderAsync(MessageStruct::class, this@NoteFragmentComponent, context)
            }


            viewModel?.leftClickable?.value = {
                val picker = DatePicker(owner.activity)
                picker.setCanceledOnTouchOutside(false)
                picker.setUseWeight(false)
                picker.setTitleText("日期筛选")
                picker.setTopPadding(ConvertUtils.toPx(context, 10f))
                picker.setRangeEnd(2111, 1, 11)
                picker.setRangeStart(2016, 8, 29)
                picker.setSelectedItem(2050, 10, 14)
                picker.setResetWhileWheel(false)
                picker.setCancelTextColor(Color.parseColor("#222222"))
                picker.setAnimationStyle(R.style.Animation_CustomPopup)
                picker.setOnDatePickListener(DatePicker.OnYearMonthDayPickListener { year, month, day ->
                    takeApi(RetrofitImpl::class)?.queryNoteList(ConfigXmlAccessor.restoreValue(
                            context, Const.SignInInfo, Const.SignInSession, "")
                            ?: "", true, NoteListBean(TimeUtil.takeNowTime("yyyy-MM-dd", "yyyy-MM-dd", "$year-$month-$day")
                            ?: ""))?.senderAsync(NoteStruct::class, this@NoteFragmentComponent, context, witch = 1)
                })
                picker.show()
            }


            bindSelf(NoteFragmentViewModel::isEditToday) { it.isEditToday.value }.toView(parallax) { view, value ->
                if (value != null) {
                    view.visibility = if (value) {
                        hoverCollapsing.value = barSize
                        View.GONE
                    } else {
                        hoverCollapsing.value = (normalCollapsing * 0.5).toInt()
                        View.VISIBLE
                    }
                }
            }

            takeApi(RetrofitImpl::class)?.queryNoteList(ConfigXmlAccessor.restoreValue(
                    context, Const.SignInInfo, Const.SignInSession, "")
                    ?: "", true, NoteListBean(TimeUtil.takeNowTime("yyyy-MM-dd")
                    ?: ""))?.senderAsync(NoteStruct::class, this@NoteFragmentComponent, context)
        }

    }

    private fun AnkoContextImpl<@AnkoViewDslMarker _LinearLayout>.calendarAreaComponent(screenHeight: Int): LinearLayout {
        return verticalLayout {
            textView {
                textColor = Color.WHITE
                bindSelf(NoteFragmentViewModel::currDate) { it.currDate.value }.toText(this)
            }.lparams {
                width = wrapContent
                gravity = Gravity.CENTER_HORIZONTAL
                height = wrapContent
            }
            imageView {
                backgroundColor = Color.TRANSPARENT
                scaleType = ImageView.ScaleType.FIT_XY
                imageResource = R.drawable.solar_calender_icon
            }.lparams {
                width = wrapContent
                gravity = Gravity.CENTER_HORIZONTAL
                height = wrapContent
            }
            textView {
                text = "您今天还未填写日志"
            }.lparams {
                width = wrapContent
                height = wrapContent
                gravity = Gravity.CENTER_HORIZONTAL
            }
            backgroundColor = Color.TRANSPARENT
            lparams {
                setHorizontalGravity(Gravity.CENTER_HORIZONTAL)
                setVerticalGravity(Gravity.CENTER_VERTICAL)
                width = matchParent
                height = matchParent
            }
        }
    }

    private fun AnkoContextImpl<@AnkoViewDslMarker _LinearLayout>.buttonAreaComponent(screenHeight: Int): LinearLayout {
        return verticalLayout {
            button {
                text = "立即填写"
                background = ContextCompat.getDrawable(context, R.drawable.circle_btn)
                minHeight = 0
                minWidth = 0
                padding = 0
                leftPadding = dip(30)
                rightPadding = dip(30)
                textSize = 15f
                setTextColor(ContextCompat.getColorStateList(context, R.color.circle_btn))
                onClick {
                    viewModelSafe.onItemClicked?.invoke(-1, Unit)
                }
            }.lparams {
                width = wrapContent
                height = (screenHeight * 0.055).toInt()
                topMargin = dip(10)
                bottomMargin = dip(10)
                gravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
            }
            backgroundColor = Color.TRANSPARENT
            lparams {
                width = matchParent
                height = matchParent
            }
        }
    }

}

class NoteItemViewComponent : BindingComponent<ViewGroup, NoteItemDataViewModel>() {
    override fun createViewWithBindings(ui: AnkoContext<ViewGroup>) = with(ui) {
        verticalLayout {
            backgroundColor = Color.WHITE

            relativeLayout {
                topPadding = dip(5)
                bottomPadding = dip(5)
                val iv = imageView {
                    id = R.id.left_icon
                    backgroundColor = Color.TRANSPARENT
                    scaleType = ImageView.ScaleType.FIT_XY
                    bindSelf(TitleBody::leftImage) { it.titleBody.leftImage }.toView(this) { view, value ->
                        if (value != null && value != 0) {
                            view.imageResource = value
                        }
                    }
                }.lparams {
                    width = wrapContent
                    height = wrapContent
                    leftMargin = dip(15)
                    alignParentLeft()
                    centerVertically()
                }


                textView {
                    id = R.id.text
                    singleLine = true
                    textColor = Color.parseColor("#222222")
                    ellipsize = TextUtils.TruncateAt.valueOf(TextUtils.TruncateAt.END.name)
                    bindSelf(TitleBody::startData) { it.titleBody.startData }.toText(this)
                    leftPadding = dip(10)
                }.lparams {
                    addRule(RelativeLayout.RIGHT_OF, iv.id)
                    centerVertically()

                }

                val iv2 = imageView {
                    id = R.id.right_icon
                    backgroundColor = Color.TRANSPARENT
                    bindSelf(TitleBody::rightImage) { it.titleBody.rightImage }.toView(this) { view, value ->
                        if (value != null && value != 0) {
                            view.imageResource = value
                        }
                    }
                    scaleType = ImageView.ScaleType.FIT_XY
                }.lparams {
                    width = wrapContent
                    height = wrapContent
                    rightMargin = dip(15)
                    alignParentRight()
                    centerVertically()
                }

                textView {
                    singleLine = true
                    textColor = Color.parseColor("#222222")
                    ellipsize = TextUtils.TruncateAt.valueOf(TextUtils.TruncateAt.END.name)
                    bindSelf(TitleBody::desc) { it.titleBody.desc }.toView(this) { view, value ->
                        if (!TextUtils.isEmpty(value)) {
                            view.text = value
                        }
                    }
                    rightPadding = dip(5)
                }.lparams {
                    addRule(RelativeLayout.LEFT_OF, iv2.id)
                    centerVertically()
                }
            }.lparams {
                width = matchParent

                height = wrapContent
            }
            view {
                backgroundColor = Color.parseColor("#CCCCCC")
            }.lparams {
                width = matchParent
                height = 1
            }

            recyclerView {
                val bindAdapter = AutoBindAdapter { viewGroup, _ ->
                    AnkoViewHolder(viewGroup, NoteItemViewSnapComponent())
                }
                bindSelf(NoteItemDataViewModel::noteSnapShot) { it.noteSnapShot }
                        .toView(this) { _, value ->
                            bindAdapter.setItemsList(value as List<NoteSnapShot>)
                        }
                layoutManager = LinearLayoutManager(context)
                adapter = bindAdapter
                leftPadding = dip(15)
                rightPadding = dip(15)
            }.lparams {
                width = matchParent
                height = wrapContent
            }

            lparams {
                width = matchParent
                topMargin = dip(10)
                height = wrapContent
            }
        }
    }
}

class NoteItemViewSnapComponent : BindingComponent<ViewGroup, NoteSnapShot>() {
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
            }
        }
    }
}