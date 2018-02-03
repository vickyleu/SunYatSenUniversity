package com.superfactory.sunyatsin.Interface.BindingFragment.Note

import android.graphics.Color
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.design.widget.Snackbar.LENGTH_SHORT
import android.support.v4.content.ContextCompat
import android.support.v4.widget.NestedScrollView
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.BindingExtensions.getAppNoStatusBarSize
import com.superfactory.library.Bridge.Anko.BindingExtensions.getAppStatusBarSize
import com.superfactory.library.Bridge.Anko.BindingExtensions.getAttrDrawablValue
import com.superfactory.library.Bridge.Anko.DslView.classicsHeader
import com.superfactory.library.Bridge.Anko.DslView.refresh
import com.superfactory.library.Bridge.Anko.observable
import com.superfactory.library.Communication.Sender.senderAsync
import com.superfactory.library.Context.Extensions.takeApi
import com.superfactory.library.Utils.ConfigXmlAccessor
import com.superfactory.library.Utils.TimeUtil
import com.superfactory.sunyatsin.Communication.RetrofitImpl
import com.superfactory.sunyatsin.Interface.BindingActivity.QuestionnaireActivity.QuestionnaireItem
import com.superfactory.sunyatsin.R
import com.superfactory.sunyatsin.Struct.Const
import com.superfactory.sunyatsin.Struct.Note.NoteStruct
import org.jetbrains.anko.*
import org.jetbrains.anko.design.collapsingToolbarLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onTouch
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
    internal var toolBarPositionY = 0
    override fun createViewWithBindings(ui: AnkoContext<NoteFragment>): View = with(ui) {
        frameLayout {
            backgroundColor = Color.parseColor("#f8f8f8")
            var parallax: LinearLayout? = null
            val screenHeight = getAppNoStatusBarSize(context).height
            val barSize = getAppStatusBarSize(context)


            val normalCollapsing = (screenHeight / 2.0).toInt()//默认日志未填写,完全展开的可折叠高度
            val backgroundCollapsing = (screenHeight / 1.0).toInt()//默认日志未填写,完全展开的可折叠高度

            val topMarginHidden = -(normalCollapsing * 0.3).toInt()//顶部隐藏的空间,固定值

            val hoverCollapsing = observable((normalCollapsing * 0.5).toInt())

            val pinAreaCollapsing = dip(60)//悬停区域可折叠高度
            val totalCollapsing = observable(normalCollapsing)
            var count=0
            parallax = verticalLayout {
                backgroundColor = Color.parseColor("#1688FF")
                val lp = CollapsingToolbarLayout.LayoutParams(matchParent, backgroundCollapsing)
                lp.collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PARALLAX
                lp.topMargin = topMarginHidden
                layoutParams = lp
                bindSelf(NoteFragmentViewModel::isEditToday) { it.isEditToday.value }.toView(this) { view, value ->
                    if (value != null) {
                        view.visibility=if (value){
                            hoverCollapsing.value=barSize
                            count=0
                            View.GONE
                        } else {
                            hoverCollapsing.value=(normalCollapsing * 0.5).toInt()
                            View.VISIBLE
                        }
                    }
                }

                verticalLayout {
                    textView {
                        text = "121212"
                    }
                }.lparams {
                    setHorizontalGravity(Gravity.CENTER_HORIZONTAL)
                    setVerticalGravity(Gravity.CENTER_VERTICAL)
                }
            }


            refresh {
                setEnableNestedScroll(false)
                isEnableRefresh = true
//                classicsHeader {
//                    setAccentColor(Color.WHITE)
//                }.lparams{
//                    width= wrapContent
//                    height= matchParent
//                }


                nestedScrollView {
                    overScrollMode = View.OVER_SCROLL_NEVER

                    verticalLayout {
                        verticalLayout {
                            backgroundColor = Color.TRANSPARENT
                            collapsingToolbarLayout {
                                isTitleEnabled = false
                                contentScrim = getAttrDrawablValue(context, R.attr.colorPrimary)
                                val lp = AppBarLayout.LayoutParams(matchParent, wrapContent)
                                lp.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or
                                        AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
                                layoutParams = lp

                                verticalLayout {
                                    backgroundColor = Color.parseColor("#222222")
                                }.lparams {
                                    width = matchParent
                                    height = pinAreaCollapsing
                                   val margins= totalCollapsing.value - (pinAreaCollapsing - topMarginHidden)
                                    topMargin =margins
                                    gravity = Gravity.BOTTOM
                                }
                                bindSelf(NoteFragmentViewModel::isEditToday) { it.isEditToday.value }.toView(this) { view, value ->
                                    if (value != null) {
                                        view.visibility=if (value){
                                            View.GONE
                                        } else {
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
                                backgroundColor = Color.RED
                                onTouch { v, event ->
                                    if (count<1){
                                        viewModelSafe.isEditToday.value=!viewModelSafe.isEditToday.value
                                        count++
                                    }else{
                                        count=-1
                                    }
                                }
                            }.lparams {
                                width = matchParent
                                height = dip(550)
                            }
                        }
                    }.lparams {
                        width = matchParent
                        height = matchParent
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
                                owner.bar?.setBackgroundColor(255 * mScrollY / h shl 24 or color)
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
                        owner.bar?.alpha = 1 - Math.min(percent, 1f)
                    }

                    override fun onHeaderReleasing(header: RefreshHeader?, percent: Float, offset: Int, footerHeight: Int, extendHeight: Int) {
                        mOffset = offset / 2
                        parallax.translationY = (mOffset - mScrollY).toFloat()
                        owner.bar?.alpha = 1 - Math.min(percent, 1f)
                    }
                })
//                toolbar_header.setAlpha(0f)
                owner.bar?.setBackgroundColor(0)
            }

            lparams {
                width = matchParent
                height = matchParent
            }

            takeApi(RetrofitImpl::class)?.queryNoteList(ConfigXmlAccessor.restoreValue(
                    context, Const.SignInInfo, Const.SignInSession, "")
                    ?: "", true, TimeUtil.takeNowTime("yyyy-MM-dd")
                    ?: "")?.senderAsync(NoteStruct::class, this@NoteFragmentComponent, context)
        }

    }

}

class NoteItemViewComponent : BindingComponent<ViewGroup, QuestionnaireItem>() {
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
                leftMargin = dip(5)
            }

            textView {
                singleLine = true
                ellipsize = TextUtils.TruncateAt.valueOf(TextUtils.TruncateAt.END.name)
                bindSelf(QuestionnaireItem::msg) { it.msg }.toView(this) { view, value ->
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