package com.superfactory.sunyatsin.Interface.BindingActivity.SplashActivity

import com.superfactory.library.Bridge.Anko.BindingComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.verticalLayout

/**
 * Created by vicky on 2018.01.29.
 *
 * @Author vicky
 * @Date 2018年01月29日  10:38:50
 * @ClassName 这里输入你的类名(或用途)
 */
class SplashActivityComponent(viewModel: SplashActivityViewModel) : BindingComponent<SplashActivity, SplashActivityViewModel>(viewModel) {
    override fun createViewWithBindings(ui: AnkoContext<SplashActivity>) = with(ui) {
        verticalLayout {

        }
    }

}