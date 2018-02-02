package com.superfactory.sunyatsin.Interface.BindingFragment.Note

import android.graphics.Color
import android.support.v4.content.ContextCompat
import com.superfactory.library.Bridge.Model.ToolbarBindingModel
import com.superfactory.sunyatsin.R

/**
 * Created by vicky on 2018.01.18.
 *
 * @Author vicky
 * @Date 2018年01月18日  11:55:35
 * @ClassName 这里输入你的类名(或用途)
 */
class NoteFragmentViewModel : ToolbarBindingModel() {
    override fun setToolbar(toolbarBindingModel: ToolbarBindingModel) {
        toolbarBindingModel.backgroundColor.value = Color.parseColor("#1688ff")
        toolbarBindingModel.title.value = "APP"
        val ctx = getStaticsContextRef()
        toolbarBindingModel.leftIcon.value = ContextCompat.getDrawable(ctx, R.drawable.alarm_icon)
        toolbarBindingModel.rightIcon.value = ContextCompat.getDrawable(ctx, R.drawable.alarm_icon)
    }

}