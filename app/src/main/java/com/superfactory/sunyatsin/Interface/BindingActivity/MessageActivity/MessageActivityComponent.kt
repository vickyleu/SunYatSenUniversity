package com.superfactory.sunyatsin.Interface.BindingActivity.MessageActivity

import android.view.View
import com.superfactory.library.Bridge.Anko.BindingComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.verticalLayout

/**
 * Created by vicky on 2018.02.03.
 *
 * @Author vicky
 * @Date 2018年02月03日  21:10:01
 * @ClassName 这里输入你的类名(或用途)
 */
class MessageActivityComponent(viewModel: MessageActivityViewModel) :
        BindingComponent<MessageActivity, MessageActivityViewModel>(viewModel) {
    override fun createViewWithBindings(ui: AnkoContext<MessageActivity>)= with(ui){
        verticalLayout {

        }
    }

}