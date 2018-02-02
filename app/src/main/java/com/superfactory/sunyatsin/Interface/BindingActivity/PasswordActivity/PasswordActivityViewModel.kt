package com.superfactory.sunyatsin.Interface.BindingActivity.PasswordActivity

import android.graphics.Color
import android.support.v4.content.ContextCompat
import com.superfactory.library.Bridge.Anko.ObservableFieldImpl
import com.superfactory.library.Bridge.Anko.observable
import com.superfactory.library.Bridge.Model.ToolbarBindingModel
import com.superfactory.sunyatsin.R

/**
 * Created by vicky on 2018.01.31.
 *
 * @Author vicky
 * @Date 2018年01月31日  20:54:54
 * @ClassName 这里输入你的类名(或用途)
 */
class PasswordActivityViewModel : ToolbarBindingModel() {
    override fun setToolbar(toolbarBindingModel: ToolbarBindingModel) {
        toolbarBindingModel.title.value = "个人资料"
        toolbarBindingModel.backgroundColor.value = Color.parseColor("#1688ff")
        val ctx = getStaticsContextRef()
        toolbarBindingModel.leftIcon.value = ContextCompat.getDrawable(ctx, R.drawable.back_stack_icon)
    }

    val newPsw = observable("")
    val confirmPsw = observable("")
    val passwordList = arrayListOf(
            PasswordItemData("请输入新密码"),
            PasswordItemData("再次输入新密码")
    )

    data class PasswordItemData(val hint: String, var psw: ObservableFieldImpl<String> = observable(""))
}