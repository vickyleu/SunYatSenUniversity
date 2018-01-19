package com.superfactory.sunyatsin.Interface.BindingFragment.Profile

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.DslView.refresh
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.verticalLayout

/**
 * Created by vicky on 2018.01.19.
 *
 * @Author vicky
 * @Date 2018年01月19日  13:41:48
 * @ClassName 这里输入你的类名(或用途)
 */
class ProfileFragmentComponent(viewModel: ProfileFragmentViewModel) : BindingComponent<ProfileFragment, ProfileFragmentViewModel>(viewModel) {
    override fun createViewWithBindings(ui: AnkoContext<ProfileFragment>): View = with(ui) {
        refresh {
            backgroundColor = Color.WHITE
            setEnableRefresh(false)
            setEnableLoadmore(false)
            verticalLayout {
                backgroundColor = Color.BLUE
                lparams {
                    width = matchParent
                    height = matchParent
                }
            }
            lparams {
                width = ViewGroup.LayoutParams.MATCH_PARENT
                height = ViewGroup.LayoutParams.MATCH_PARENT
            }
        }
    }

}