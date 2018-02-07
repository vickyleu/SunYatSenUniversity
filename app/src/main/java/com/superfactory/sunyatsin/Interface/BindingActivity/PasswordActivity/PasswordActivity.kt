package com.superfactory.sunyatsin.Interface.BindingActivity.PasswordActivity

import com.superfactory.library.Context.BaseToolBarActivity
import com.superfactory.sunyatsin.Interface.BindingActivity.LoginActivity.LoginActivity
import com.yayandroid.theactivitymanager.TheActivityManager
import org.jetbrains.anko.startActivity

/**
 * Created by vicky on 2018.01.31.
 *
 * @Author vicky
 * @Date 2018年01月31日  20:54:15
 * @ClassName 这里输入你的类名(或用途)
 */
class PasswordActivity : BaseToolBarActivity<PasswordActivityViewModel, PasswordActivity>() {
    override fun newViewModel() = PasswordActivityViewModel().apply {
        ownerNotifier = { i, any ->
            TheActivityManager.getInstance().finishAll()
            startActivity<LoginActivity>()
        }
    }

    override fun newComponent(v: PasswordActivityViewModel) = PasswordActivityComponent(v)

}