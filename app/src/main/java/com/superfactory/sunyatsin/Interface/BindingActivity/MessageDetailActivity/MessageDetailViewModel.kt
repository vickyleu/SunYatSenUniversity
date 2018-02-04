package com.superfactory.sunyatsin.Interface.BindingActivity.MessageDetailActivity

import android.content.Intent
import android.graphics.Color
import android.support.v4.content.ContextCompat
import com.superfactory.library.Bridge.Anko.observable
import com.superfactory.library.Bridge.Model.ToolbarBindingModel
import com.superfactory.sunyatsin.Interface.BindingActivity.MessageActivity.MessageItemView
import com.superfactory.sunyatsin.R

/**
 * Created by vicky on 2018/2/4.
 */
class MessageDetailViewModel(val intent: Intent) : ToolbarBindingModel() {
    override fun setToolbar(toolbarBindingModel: ToolbarBindingModel) {
        toolbarBindingModel.title.value = "消息详情"
        toolbarBindingModel.backgroundColor.value = Color.parseColor("#1688ff")
        val ctx = getStaticsContextRef()
        toolbarBindingModel.leftIcon.value = ContextCompat.getDrawable(ctx, R.drawable.back_stack_icon)
    }

    val content = observable("")

    init {
        val model = intent.extras.getParcelable<MessageItemView>("data")
        content.value = model.content
    }
}