package com.superfactory.sunyatsin.Interface.BindingActivity.LoginActivity

import com.superfactory.library.Context.BaseActivity

class LoginActivity : BaseActivity<LoginActivityViewModel, LoginActivity>() {

    override fun newViewModel() = LoginActivityViewModel()

    override fun newComponent(v: LoginActivityViewModel) = LoginActivityComponent(v)

}
