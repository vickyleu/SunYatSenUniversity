package com.superfactory.sunyatsin.Interface.BindingActivity.AccountActivity

import android.graphics.Color
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import com.superfactory.library.Bridge.Anko.observable
import com.superfactory.library.Bridge.Model.ToolbarBindingModel
import com.superfactory.sunyatsin.Interface.BindingActivity.SettingsActivity.SettingsActivityViewModel
import com.superfactory.sunyatsin.R

/**
 * Created by vicky on 2018.01.31.
 *
 * @Author vicky
 * @Date 2018年01月31日  18:56:13
 * @ClassName 这里输入你的类名(或用途)
 */
class AccountActivityViewModel : ToolbarBindingModel() {
    override fun setToolbar(toolbarBindingModel: ToolbarBindingModel) {
        toolbarBindingModel.title.value = "个人资料"
        toolbarBindingModel.backgroundColor.value = Color.parseColor("#1688ff")
        val ctx = getStaticsContextRef()
        toolbarBindingModel.leftIcon.value = ContextCompat.getDrawable(ctx, R.drawable.back_stack_icon)
    }

    var onItemClicked: ((Int, AccountActivityItemViewModel) -> Unit)? = null

    val name = observable("")
    val gender = observable("")
    val avatar = observable("")

    val accountItemsList = arrayListOf(
            AccountActivityItemViewModel(0, "头像", 0, R.drawable.right_arrow_icon, placeHolder = R.drawable.camera_icon),
            AccountActivityItemViewModel(1, "姓名", 0, R.drawable.right_arrow_icon),
            AccountActivityItemViewModel(2, "性别", 0, R.drawable.right_arrow_icon)
    )
}

data class AccountActivityItemViewModel(val index: Int,
                                        val name: String,
                                        val type: Int,
                                        val right: Int,
                                        var description: String? = null,
                                        @DrawableRes var placeHolder: Int? = null)