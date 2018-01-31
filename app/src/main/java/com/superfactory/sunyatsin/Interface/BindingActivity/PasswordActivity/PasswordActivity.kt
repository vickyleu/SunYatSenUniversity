package com.superfactory.sunyatsin.Interface.BindingActivity.PasswordActivity

import android.view.View
import com.superfactory.library.Bridge.Anko.Adapt.BaseToolBar
import com.superfactory.library.Context.BaseToolBarActivity

/**
 * Created by vicky on 2018.01.31.
 *
 * @Author vicky
 * @Date 2018年01月31日  20:54:15
 * @ClassName 这里输入你的类名(或用途)
 */
class PasswordActivity : BaseToolBarActivity<PasswordActivityViewModel, PasswordActivity>() {
    override fun newViewModel() = PasswordActivityViewModel()

    override fun newComponent(v: PasswordActivityViewModel) = PasswordActivityComponent(v)

    override fun performToolbarClickEvent(view: View, event: BaseToolBar.Companion.ToolbarEvent) {
        super.performToolbarClickEvent(view, event)
        when (event) {
            BaseToolBar.Companion.ToolbarEvent.RIGHT -> {
                val n = viewModel?.newPsw?.value ?: ""
                val c=viewModel?.confirmPsw?.value?:""
                if ( n===c&& c != ""){

                }
            }
        }
    }
}