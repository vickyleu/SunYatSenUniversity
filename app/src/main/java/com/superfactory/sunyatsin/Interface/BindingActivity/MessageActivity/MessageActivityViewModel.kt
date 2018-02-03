package com.superfactory.sunyatsin.Interface.BindingActivity.MessageActivity

import android.content.Intent
import android.graphics.Color
import android.support.v4.content.ContextCompat
import com.superfactory.library.Bridge.Anko.observable
import com.superfactory.library.Bridge.Model.ToolbarBindingModel
import com.superfactory.sunyatsin.R

/**
 * Created by vicky on 2018.02.03.
 *
 * @Author vicky
 * @Date 2018年02月03日  21:09:46
 * @ClassName 这里输入你的类名(或用途)
 */
class MessageActivityViewModel(val intent: Intent) : ToolbarBindingModel() {
    override fun setToolbar(toolbarBindingModel: ToolbarBindingModel) {
        toolbarBindingModel.title.value = "消息"
        toolbarBindingModel.backgroundColor.value = Color.parseColor("#1688ff")
        val ctx = getStaticsContextRef()
        toolbarBindingModel.leftIcon.value = ContextCompat.getDrawable(ctx, R.drawable.back_stack_icon)
    }
    val itemList=observable<List<String>>(arrayListOf<String>())
    init {

    }
}