package com.superfactory.library.Graphics.HUD.Base

import android.graphics.Color
import android.util.Log
import android.view.Gravity
import android.view.View
import com.superfactory.library.Bridge.Anko.DslView.circularRing
import com.superfactory.library.Bridge.Anko.DslView.circularView
import com.superfactory.library.Bridge.Anko.DslView.rightDiaView
import com.superfactory.library.Bridge.Anko.DslView.wrongDiaView
import com.superfactory.library.Graphics.HUD.LoadingHUD
import com.superfactory.library.R
import org.jetbrains.anko.*

/**
 * Created by vicky on 2018/1/28.
 */
class LoadingHUDComponent(viewModel: LoadingHudModel?) : HUDComponent<LoadingHudModel, LoadingHUD>(viewModel) {
    override fun createViewWithBindings(ui: AnkoContext<LoadingHUD>): View = with(ui) {
        verticalLayout {
            id = R.id.dialog_view
            backgroundResource = R.drawable.dialog_bg

            val mLoadingView = circularRing {
                id = R.id.lv_circularring
                visibility = View.GONE
            }.lparams {
                width = dip(50)
                height = dip(50)
            }
            val mCircleLoadView = circularView {
                id = R.id.lcv_circleload
                visibility = View.GONE
            }.lparams {
                width = dip(50)
                height = dip(50)
            }
            rightDiaView {
                id = R.id.rdv_right
                visibility = View.GONE
            }.lparams {
                width = dip(50)
                height = dip(50)
            }
            wrongDiaView {
                id = R.id.wv_wrong
                visibility = View.GONE
            }.lparams {
                width = dip(50)
                height = dip(50)
            }

            textView {
                id = R.id.loading_text
                textSize = sp(15).toFloat()
                textColor = Color.parseColor("#ffffff")
            }.lparams {
                width = wrapContent
                height = wrapContent
                topMargin = dip(5)
            }

            bindSelf(LoadingHudModel::visibility) { it.visibility }.toView(this) { view, value ->
                hideAll()
                if (viewModel?.loadStyle == LoadingHUD.STYLE_RING) {
                    mLoadingView.visibility = View.VISIBLE
                    mCircleLoadView.visibility = View.GONE
                    viewModel?.hud!!.show()
                    mLoadingView.startAnim()
                    Log.e("show", "style_ring")
                } else if (viewModel?.loadStyle == LoadingHUD.STYLE_LINE) {
                    mCircleLoadView.visibility = View.VISIBLE
                    mLoadingView.visibility = View.GONE
                    viewModel?.hud!!.show()
                    Log.e("show", "style_line")
                }
            }


            lparams {
                width = matchParent
                gravity = Gravity.CENTER
                padding = dip(20)
                height = matchParent
            }
        }
    }
}