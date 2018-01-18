package com.superfactory.sunyatsin.Interface.BindingActivity.MainActivity

import android.content.Intent
import com.superfactory.library.Context.BaseActivity

class MainActivity : BaseActivity<MainActivityViewModel, MainActivity>() {
    override fun newViewModel() = MainActivityViewModel(intent,supportFragmentManager)


    override fun newComponent(v: MainActivityViewModel) = MainActivityComponent(v)
}
