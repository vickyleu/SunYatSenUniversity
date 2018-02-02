package com.superfactory.sunyatsin.Interface.BindingFragment.Note

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.design.widget.AppBarLayout
import android.support.design.widget.AppBarLayout.LayoutParams.*
import android.support.design.widget.CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN
import android.view.View
import android.view.ViewGroup
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.BindingExtensions.getActionBarColor
import com.superfactory.library.Bridge.Anko.BindingExtensions.getActionBarSize
import com.superfactory.library.Bridge.Anko.BindingExtensions.getAttrDrawablValue
import com.superfactory.library.Bridge.Anko.DslView.refresh
import com.superfactory.library.Context.Extensions.*
import com.superfactory.library.Utils.StatusBarUtil
import com.superfactory.sunyatsin.R
import org.jetbrains.anko.*
import org.jetbrains.anko.design.collapsingToolbarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.themedAppBarLayout
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by vicky on 2018.01.18.
 *
 * @Author vicky
 * @Date 2018年01月18日  11:57:26
 * @ClassName 这里输入你的类名(或用途)
 */
class NoteFragmentComponent(viewModel: NoteFragmentViewModel) : BindingComponent<NoteFragment, NoteFragmentViewModel>(viewModel) {
    override fun createViewWithBindings(ui: AnkoContext<NoteFragment>): View = with(ui) {
            coordinatorLayout {
                fitsSystemWindows=true
            themedAppBarLayout(R.style.ThemeOverlay_AppCompat_Dark_ActionBar) {
                fitsSystemWindows=true
                backgroundColor=getActionBarColor(context)
                collapsingToolbarLayout {
                    expandedTitleMarginEnd=getActionBarSize(context)+dip(164)
                    expandedTitleMarginStart=getActionBarSize(context)
                    fitsSystemWindows=true
                    isTitleEnabled=false
                    statusBarScrim=ColorDrawable(Color.TRANSPARENT)
                    contentScrim=getAttrDrawablValue(context,R.attr.colorPrimary)
                    verticalLayout {
                        backgroundColor = Color.parseColor("#1688ff")
                        fitsSystemWindows=true
                    }.lparams {
                        width = matchParent
                        height = dip(64)
                        collapseMode=COLLAPSE_MODE_PIN
                    }
                }.lparams{
                    width = matchParent
                    height = dip(64)
                    scrollFlags = SCROLL_FLAG_SCROLL or SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
                    //or SCROLL_FLAG_ENTER_ALWAYS
                }
            }.lparams {
                width = matchParent
                height = wrapContent
            }
            refresh {
                backgroundColor = Color.WHITE
                verticalLayout {
                    backgroundColor = Color.RED
                    lparams {
                        width = matchParent
                        height = matchParent
                    }
                    onClick {
                        doAsync {
                            ui.owner.setTitle("1122111")
//                    ui.owner.setBackIcon(R.mipmap.ic_launcher)
                            ui.owner.setBackTextSize(16)
                            ui.owner.setBackTextColor(Color.WHITE)
                            ui.owner.setRightTextSize(16)
                            ui.owner.setRightTextColor(Color.WHITE)


                            ui.owner.setBackgroundColor(Color.parseColor("#222222"))

//                        val left=TextView(ctx)
//                        left.text="jbok返回"
//                        left.textSize=14f
//                        ui.owner.setBackView(left)
                            ui.owner.setBackIcon("123")
                            ui.owner.setRightIcon("返回ojbk")
                        }
//                    viewModelSafe.notifyChange()

                    }
                }
                lparams {
                    width = ViewGroup.LayoutParams.MATCH_PARENT
                    height = ViewGroup.LayoutParams.MATCH_PARENT
                }
            }
            lparams {
                width = ViewGroup.LayoutParams.MATCH_PARENT
                height = ViewGroup.LayoutParams.MATCH_PARENT

                behavior = AppBarLayout.ScrollingViewBehavior()
            }
        }

    }

}