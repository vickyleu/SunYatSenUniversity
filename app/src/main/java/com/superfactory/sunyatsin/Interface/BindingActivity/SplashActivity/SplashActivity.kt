package com.superfactory.sunyatsin.Interface.BindingActivity.SplashActivity

import com.superfactory.library.Context.BaseActivity

/**
 * Created by vicky on 2018.01.29.
 *
 * @Author vicky
 * @Date 2018年01月29日  10:37:28
 * @ClassName 这里输入你的类名(或用途)
 */
class SplashActivity : BaseActivity<SplashActivityViewModel, SplashActivity>() {
    override fun newViewModel() = SplashActivityViewModel()

    override fun newComponent(v: SplashActivityViewModel) = SplashActivityComponent(v).apply {

    }

}
