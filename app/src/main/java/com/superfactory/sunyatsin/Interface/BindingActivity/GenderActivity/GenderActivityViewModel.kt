package com.superfactory.sunyatsin.Interface.BindingActivity.GenderActivity

import android.graphics.Color
import android.support.v4.content.ContextCompat
import com.superfactory.library.Bridge.Anko.observable
import com.superfactory.library.Bridge.Model.ToolbarBindingModel
import com.superfactory.sunyatsin.R

/**
 * Created by vicky on 2018.01.31.
 *
 * @Author vicky
 * @Date 2018年01月31日  20:10:03
 * @ClassName 这里输入你的类名(或用途)
 */
class GenderActivityViewModel : ToolbarBindingModel() {
    override fun setToolbar(toolbarBindingModel: ToolbarBindingModel) {
        toolbarBindingModel.title.value = "个人资料"
        toolbarBindingModel.backgroundColor.value = Color.parseColor("#1688ff")
        val ctx = getStaticsContextRef()
        toolbarBindingModel.leftIcon.value = ContextCompat.getDrawable(ctx, R.drawable.avatar_icon)
    }

    var selected=observable(-1)
    val genderList = arrayListOf(
            GenderActivityItemViewModel("头像"),
            GenderActivityItemViewModel("头像")
    )
}

data class GenderActivityItemViewModel(val gender: String, var checked: Boolean = false)