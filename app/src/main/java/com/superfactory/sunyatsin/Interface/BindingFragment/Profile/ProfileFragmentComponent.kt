package com.superfactory.sunyatsin.Interface.BindingFragment.Profile

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.BindingExtensions.getAppNoStatusBarSize
import com.superfactory.library.Bridge.Anko.DslView.circleImage
import com.superfactory.library.Bridge.Anko.DslView.refresh
import com.superfactory.library.Bridge.Anko.ViewExtensions.getLineDividerItemDecoration
import com.superfactory.library.Bridge.Anko.bindings.toText
import com.superfactory.library.Bridge.Anko.widget.AnkoViewHolder
import com.superfactory.library.Bridge.Anko.widget.AutoBindAdapter
import com.superfactory.library.Debuger
import com.superfactory.sunyatsin.R
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.nestedScrollView

/**
 * Created by vicky on 2018.01.19.
 *
 * @Author vicky
 * @Date 2018年01月19日  13:41:48
 * @ClassName 这里输入你的类名(或用途)
 */
class ProfileFragmentComponent(viewModel: ProfileFragmentViewModel) : BindingComponent<ProfileFragment, ProfileFragmentViewModel>(viewModel) {
    override fun createViewWithBindings(ui: AnkoContext<ProfileFragment>): View = with(ui) {

        val screenHeight = getAppNoStatusBarSize(ctx).height
        verticalLayout {
            verticalLayout {
                backgroundColor = Color.parseColor("#1688ff")
                circleImage {
                    id = R.id.avatar
                    circleBackgroundColor = Color.BLUE
                    borderColor = Color.WHITE
                    borderWidth = dip(2)
                    imageResource = R.drawable.note_icon
                    onClick {
                        this@ProfileFragmentComponent.viewModelSafe.onItemClicked?.invoke(-1,
                                ProfileFragmentItemViewModel(-1, 0, "", "", -1))
                    }
                }.lparams {
                    height = (screenHeight * 0.15).toInt()
                    width = (screenHeight * 0.15).toInt()
                    setVerticalGravity(Gravity.CENTER_VERTICAL)
                    setHorizontalGravity(Gravity.CENTER_HORIZONTAL)
                }
                textView {
                    text = context.getString(R.string.profile_name)
                    textSize = 14f
                    textColor = Color.parseColor("#ffffff")
                }.lparams {
                    setVerticalGravity(Gravity.CENTER_VERTICAL)
                    setHorizontalGravity(Gravity.CENTER_HORIZONTAL)
                }

            }.lparams {
                width = matchParent
                height = (screenHeight * 0.2f).toInt()
//                height = wrapContent
            }
            refresh {
                backgroundColor = Color.parseColor("#f8f8f8")
                isEnableRefresh = false
                isEnableLoadmore = false

                nestedScrollView {
                    fitsSystemWindows = true
                    verticalLayout {
                        backgroundColor = Color.TRANSPARENT
//                        fitsSystemWindows=true
                        recyclerView {
                            backgroundResource = R.drawable.profile_recycle_shader
                            leftPadding = dip(10)
                            val bindAdapter = AutoBindAdapter { viewGroup, _ ->
                                AnkoViewHolder(viewGroup, ProfileFragmentItemComponent())
                            }.apply {
                                onItemClickListener = { i, viewModel, _ ->
                                    this@ProfileFragmentComponent.viewModelSafe.onItemClicked?.invoke(i, viewModel)
                                }
                            }
                            bindSelf(ProfileFragmentViewModel::profileItemsList) { it.profileItemsList }
                                    .toView(this) { _, value ->
                                        bindAdapter.setItemsList(value)
                                    }
                            layoutManager = LinearLayoutManager(context)!!
                            addItemDecoration(getLineDividerItemDecoration(1, ContextCompat.getColor(context, R.color.gray)))
                            adapter = bindAdapter
                        }.lparams {
                            width = matchParent
                            height = wrapContent

                            topMargin = dip(10)
//                    bottomMargin = dip(10)
                        }
                        verticalLayout {
                            backgroundColor = Color.TRANSPARENT
                            for (i in 0 until viewModel!!.profileSettingsList.size) {
                                val compnent = ProfileFragmentItemComponent()
                                bindSelf(ProfileFragmentViewModel::profileSettingsList) { it.profileSettingsList }
                                        .toView(this) { _, value ->
                                            if (value == null) return@toView
                                            compnent.viewModel = value[i]
                                            Debuger.printMsg(this, "日你妹啊")
//                                            value.profileSettingsList[i].notifyChange()
                                        }
                                val v = compnent.createViewWithBindings(AnkoContextImpl(this.context,
                                        this, false))
                                v.backgroundResource = R.drawable.profile_recycle_shader
                                val lp = LinearLayout.LayoutParams(matchParent, wrapContent)
                                lp.topMargin = dip(10)
                                v.leftPadding = dip(10)
                                addView(v, lp)
                                v.apply {
                                    onClick {
                                        this@ProfileFragmentComponent.viewModelSafe.onItemClicked?.invoke(viewModel!!.profileItemsList.size + i - 1, compnent.viewModelSafe)
                                    }
                                }
//                        print("i => $i \t")
                            }
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                        }
                    }.lparams {
                        height = matchParent
                        width = matchParent
                        bottomMargin=dip(30)
                    }
                }.lparams {
                    height = matchParent
                    width = matchParent
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

class ProfileFragmentItemComponent : BindingComponent<ViewGroup, ProfileFragmentItemViewModel>() {

    override fun createViewWithBindings(ui: AnkoContext<ViewGroup>) = with(ui) {
        relativeLayout {
            imageView {
                id = R.id.left_icon
                backgroundColor = Color.TRANSPARENT
                imageResource = R.drawable.note_icon
                scaleType = ImageView.ScaleType.FIT_XY
            }.lparams {
                //                width = wrapContent
                //                height = wrapContent
                width = dip(20)
                height = dip(20)
                centerVertically()
                alignParentLeft()
            }
            textView {
                bindSelf(ProfileFragmentItemViewModel::name) { it.name }.toText(this)
                Debuger.printMsg(this, "viewModel" + (if (viewModel == null) "空" else viewModelSafe.name + viewModelSafe.type))
                padding = dip(12)
                leftPadding = 0
                textSize = 16.0f
                textColor = Color.BLACK
            }.lparams {
                width = wrapContent
                height = wrapContent
                leftMargin = dip(10)
                centerVertically()
                addRule(RelativeLayout.RIGHT_OF, R.id.left_icon)
            }

            textView {
                id = R.id.right_text
                bindSelf(ProfileFragmentItemViewModel::description) { it.description }.toText(this)
            }.lparams {
                width = wrapContent
                height = wrapContent
                centerVertically()
                alignParentRight()
            }
            lparams {
                width = matchParent
                height = wrapContent
                gravity = Gravity.CENTER_VERTICAL and Gravity.LEFT
                rightPadding=dip(12)
            }
        }
    }

}