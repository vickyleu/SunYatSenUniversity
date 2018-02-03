package com.superfactory.sunyatsin.Interface.BindingActivity.MessageActivity

import android.view.View
import com.superfactory.library.Bridge.Anko.Adapt.BaseToolBar
import com.superfactory.library.Context.BaseToolBarActivity

/**
 * Created by vicky on 2018.02.03.
 *
 * @Author vicky
 * @Date 2018年02月03日  21:09:34
 * @ClassName 这里输入你的类名(或用途)
 */
class MessageActivity : BaseToolBarActivity<MessageActivityViewModel, MessageActivity>() {
    override fun newViewModel() = MessageActivityViewModel(intent)

    override fun newComponent(v: MessageActivityViewModel) = MessageActivityComponent(v)

    override fun performToolbarClickEvent(view: View, event: BaseToolBar.Companion.ToolbarEvent) {
        super.performToolbarClickEvent(view, event)
        when (event) {
            BaseToolBar.Companion.ToolbarEvent.RIGHT -> {

            }
        }
    }
}