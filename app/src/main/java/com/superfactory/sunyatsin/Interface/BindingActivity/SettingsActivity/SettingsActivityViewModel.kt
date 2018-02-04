package com.superfactory.sunyatsin.Interface.BindingActivity.SettingsActivity

import android.graphics.Color
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import com.superfactory.library.Bridge.Anko.observable
import com.superfactory.library.Bridge.Model.ToolbarBindingModel
import com.superfactory.sunyatsin.R
import com.superfactory.sunyatsin.Struct.Login.LoginStruct
import com.xiasuhuei321.loadingdialog.view.LoadingDialog

/**
 * Created by vicky on 2018.01.31.
 *
 * @Author vicky
 * @Date 2018年01月31日  17:20:15
 * @ClassName 这里输入你的类名(或用途)
 */
class SettingsActivityViewModel : ToolbarBindingModel() {
    override fun setToolbar(toolbarBindingModel: ToolbarBindingModel) {
        toolbarBindingModel.title.value = "设置"
        toolbarBindingModel.backgroundColor.value = Color.parseColor("#1688ff")
        val ctx = getStaticsContextRef()
        toolbarBindingModel.leftIcon.value = ContextCompat.getDrawable(ctx, R.drawable.back_stack_icon)
    }

    override fun requestSuccess(ld: LoadingDialog, model: Any?, witch: Int?) {
        if (model == null) {
            ld.close()
            tips.value = "无法解析数据"
            return
        }
        if (model is LoginStruct) {
            if (model.success) {
                ld.close()
                ownerNotifier?.invoke(0, model)
            } else {
                ld.close()
                tips.value = model.msg ?: "未知错误"
            }
        }
    }

    override fun requestFailed(ld: LoadingDialog, error: Throwable?, witch: Int?) {
        ld.close()
        if (!TextUtils.isEmpty(error?.message)) {
            tips.value = error?.message!!
        }
    }

    val cacheSize = observable("")
    val version = observable("")


    val tips = observable("")
    val settingsItemsList = arrayListOf(
            SettingsItemViewModel(0, "个人资料", R.drawable.right_arrow_icon),
            SettingsItemViewModel(1, "密码修改", R.drawable.right_arrow_icon),
            SettingsItemViewModel(2, "清除缓存", R.drawable.right_arrow_icon),
            SettingsItemViewModel(3, "版本说明", R.drawable.right_arrow_icon)
    )

    var onItemClicked: ((Int, SettingsItemViewModel) -> Unit)? = null

    data class SettingsItemViewModel(val index: Int, val name: String, @DrawableRes val right: Int, var description: String? = null)

}