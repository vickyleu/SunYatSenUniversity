package com.superfactory.sunyatsin.Interface.BindingFragment.Profile

import android.graphics.Color
import android.support.v4.content.ContextCompat
import com.superfactory.library.Bridge.Anko.BaseObservable
import com.superfactory.library.Bridge.Anko.observable
import com.superfactory.library.Bridge.Anko.observableNullable
import com.superfactory.library.Bridge.Model.ToolbarBindingModel
import com.superfactory.library.Graphics.Badge.Badge
import com.superfactory.sunyatsin.R

/**
 * Created by vicky on 2018.01.19.
 *
 * @Author vicky
 * @Date 2018年01月19日  13:41:34
 * @ClassName 这里输入你的类名(或用途)
 */
class ProfileFragmentViewModel : ToolbarBindingModel() {
    override fun setToolbar(toolbarBindingModel: ToolbarBindingModel) {
        toolbarBindingModel.backgroundColor.value = Color.parseColor("#1688ff")
        toolbarBindingModel.title.value = "APP"
        val ctx = getStaticsContextRef()
        toolbarBindingModel.rightIcon.value = ContextCompat.getDrawable(ctx, R.drawable.note_icon)
    }

    val avatar = observable("")//头像
    val profileName = observable("")//账户名称
    val profileNo = observable("")//警号
    val employ = observable("")//部门
    val station = observable("")//岗位
    val position = observable("")//职务
    val notificationTotalObserva = observable(3)

    val profileItemsList = arrayListOf(
            ProfileFragmentItemViewModel(0, R.drawable.note_icon, "警号", ""),
            ProfileFragmentItemViewModel(1, R.drawable.note_icon, "部门", ""),
            ProfileFragmentItemViewModel(2, R.drawable.note_icon, "岗位", ""),
            ProfileFragmentItemViewModel(3, R.drawable.note_icon, "职务", "")
    )

    val profileSettingsList = arrayListOf(
            ProfileFragmentItemViewModel(4, R.drawable.note_icon, "问卷", "", 1),
            ProfileFragmentItemViewModel(5, R.drawable.note_icon, "设置", "", 1)
    )

    var onItemClicked: ((Int, ProfileFragmentItemViewModel) -> Unit)? = null
    //observableNullable<((Int, ProfileFragmentItemViewModel) -> Unit)>(null)

}


data class ProfileFragmentItemViewModel(val index: Int,val icon: Int,val  name: String,var description: String,val type: Int = 0):BaseObservable(){
    var notificationTotal=observable(0)
    var badge=observableNullable<Badge>(null)
    var dragging =observable(false)
}
