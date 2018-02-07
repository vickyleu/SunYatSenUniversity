package com.superfactory.sunyatsin.Interface.BindingActivity.CompellationActivity

import android.graphics.Color
import android.support.v4.content.ContextCompat
import com.superfactory.library.Bridge.Anko.observable
import com.superfactory.library.Bridge.Model.ToolbarBindingModel
import com.superfactory.sunyatsin.R

/**
 * Created by vicky on 2018.01.31.
 *
 * @Author vicky
 * @Date 2018年01月31日  20:07:00
 * @ClassName 这里输入你的类名(或用途)
 */
class CompellationActivityViewModel : ToolbarBindingModel() {
    override fun setToolbar(toolbarBindingModel: ToolbarBindingModel) {
        toolbarBindingModel.title.value = "个人资料"
        toolbarBindingModel.backgroundColor.value = Color.parseColor("#1688ff")
        val ctx = getStaticsContextRef()
        toolbarBindingModel.leftIcon.value = ContextCompat.getDrawable(ctx, R.drawable.back_stack_icon)
        toolbarBindingModel.rightText.value="保存"
        toolbarBindingModel.rightTextColor.value=Color.parseColor("#b4b3b3")
    }

    val input=observable("")
}