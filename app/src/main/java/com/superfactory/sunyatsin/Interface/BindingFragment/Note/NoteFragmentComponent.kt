package com.superfactory.sunyatsin.Interface.BindingFragment.Note

import android.graphics.Color
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v4.content.ContextCompat
import android.support.v4.widget.NestedScrollView
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.BindingExtensions.getAppNoStatusBarSize
import com.superfactory.library.Bridge.Anko.BindingExtensions.getAttrDrawablValue
import com.superfactory.library.Bridge.Anko.DslView.refresh
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
            var parallax: LinearLayout? = null

            parallax = verticalLayout {
                backgroundColor = Color.parseColor("#1688FF")
                val lp = CollapsingToolbarLayout.LayoutParams(matchParent, (getAppNoStatusBarSize(context).height / 2.4).toInt())
                lp.collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PARALLAX
                lp.topMargin = -(getAppNoStatusBarSize(context).height / 6.4).toInt()
                layoutParams = lp

                verticalLayout {
                    textView {
                        text = "121212"
                    }
                }.lparams {
                    topMargin = (getAppNoStatusBarSize(context).height / 6.4).toInt()
                }
            }


            refresh {
                setEnableNestedScroll(false)
                isEnableRefresh = true


//                <com.scwang.smartrefresh.layout.header.ClassicsHeader
//                android:layout_width="match_parent"
//                android:layout_height="wrap_content"
//                app:srlAccentColor="@android:color/white"/>


                nestedScrollView {
                    overScrollMode = View.OVER_SCROLL_NEVER
                    verticalLayout {
                        verticalLayout {
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
                                    height = dip(100)
                                    topMargin = dip(200)
                                    gravity = Gravity.BOTTOM
                                }
                            }

                            recyclerView {
                                backgroundColor = Color.RED
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
                    lp.topMargin = -(getAppNoStatusBarSize(context).height / 8.4).toInt()
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