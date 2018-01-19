package com.superfactory.sunyatsin.Interface.BindingActivity.MainActivity

//import com.superfactory.library.Context.BaseActivity
import com.superfactory.library.Context.BaseToolBarActivity

class MainActivity : BaseToolBarActivity<MainActivityViewModel, MainActivity>() {

    override fun newViewModel() = MainActivityViewModel(intent, supportFragmentManager)


    override fun newComponent(v: MainActivityViewModel) = MainActivityComponent(v)
}
