package com.superfactory.sunyatsin.Interface.BindingFragment.Profile

import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import com.superfactory.library.Bridge.Anko.BaseObservable
import com.superfactory.library.Bridge.Anko.observable
import com.superfactory.library.Bridge.Anko.observableNullable
import com.superfactory.library.Bridge.Model.ToolbarBindingModel
import com.superfactory.library.Debuger
import com.superfactory.library.Graphics.Badge.Badge
import com.superfactory.sunyatsin.R
import com.superfactory.sunyatsin.Struct.Const
import com.superfactory.sunyatsin.Struct.Login.LoginAfterStruct
import com.superfactory.sunyatsin.Struct.QuestionaireStruct.QuestionnaireStruct
import com.xiasuhuei321.loadingdialog.view.LoadingDialog

/**
 * Created by vicky on 2018.01.19.
 *
 * @Author vicky
 * @Date 2018年01月19日  13:41:34
 * @ClassName 这里输入你的类名(或用途)
 */
class ProfileFragmentViewModel(bundle: Bundle?) : ToolbarBindingModel() {
    override fun setToolbar(toolbarBindingModel: ToolbarBindingModel) {
        toolbarBindingModel.backgroundColor.value = Color.parseColor("#1688ff")
        toolbarBindingModel.title.value = "APP"
        val ctx = getStaticsContextRef()
        toolbarBindingModel.rightIcon.value = ContextCompat.getDrawable(ctx, R.drawable.alarm_icon)
    }

    override fun requestFailed(ld: LoadingDialog, error: Throwable?, witch: Int?) {
        ld.close()
        if (!TextUtils.isEmpty(error?.message)) {
            tips.value = error?.message!!
        }
    }

    val tips = observable("")

    override fun requestSuccess(ld: LoadingDialog, model: Any?, witch: Int?) {
        ld.close()
        if (model != null) {
            if (model is QuestionnaireStruct) {
                if (model.success) {
                    ld.close()
                    ownerNotifier?.invoke(0, model)
                } else {
                    ld.close()
                    tips.value = model.msg ?: "未知错误"
                }
            }
        }
    }

    val avatar = observable("")//头像
    val profileName = observable("")//账户名称
    val profileNo = observable("")//警号
    val employ = observable("")//部门
    val station = observable("")//岗位
    val position = observable("")//职务
    val notificationTotalObserva = observable(0)

    override fun toString(): String {
        return "{notificationTotalObserva:" + notificationTotalObserva.value + "}" + "{" +
                "profileName" + profileName.value + "}"
    }

    val profileItemsList = arrayListOf(
            ProfileFragmentItemViewModel(0, R.drawable.police_number_icon, "警号", ""),
            ProfileFragmentItemViewModel(1, R.drawable.department_icon, "部门", ""),
            ProfileFragmentItemViewModel(2, R.drawable.station_icon, "岗位", "")
//  ,ProfileFragmentItemViewModel(3, R.drawable.duty_icon_ignored, "职务", "")

    )

    val profileSettingsList = arrayListOf(
            ProfileFragmentItemViewModel(3, R.drawable.questionnaire_icon, "问卷", "", 1),
            ProfileFragmentItemViewModel(4, R.drawable.setting_up_icon, "设置", "", 1)
    )

    var onItemClicked: ((Int, ProfileFragmentItemViewModel) -> Unit)? = null

    init {
        Debuger.printMsg(this, bundle ?: "bundle是空的")
        if (bundle != null) {
            val loginStruct = bundle.getParcelable<LoginAfterStruct>("data")
            val data = loginStruct.body.data
            profileName.value = data.name
            profileNo.value = data.no
            employ.value = data.office.name//部门
            station.value = data.jobTypeName//岗位
            position.value = ""//职务 ignored
            avatar.value = Const.AvatarPrefix + data.photo
            Debuger.printMsg(this, avatar.value)
        }
    }
}


data class ProfileFragmentItemViewModel(val index: Int, val icon: Int, val name: String, var description: String, val type: Int = 0) : BaseObservable() {
    var notificationTotal = observable(0)
    var badge = observableNullable<Badge>(null)
    var dragging = observable(false)
}
