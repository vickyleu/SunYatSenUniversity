package com.superfactory.sunyatsin.Interface.BindingActivity.MessageDetailActivity

import com.superfactory.library.Context.BaseToolBarActivity

/**
 * Created by vicky on 2018/2/4.
 */
class MessageDetailActivity : BaseToolBarActivity<MessageDetailViewModel, MessageDetailActivity>() {
    override fun newViewModel() = MessageDetailViewModel(intent)

    override fun newComponent(v: MessageDetailViewModel) =MessageDetailActivityComponent(v)
}