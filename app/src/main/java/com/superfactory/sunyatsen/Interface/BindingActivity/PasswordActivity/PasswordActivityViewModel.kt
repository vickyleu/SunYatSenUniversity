package com.superfactory.sunyatsen.Interface.BindingActivity.PasswordActivity

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import com.superfactory.library.Bridge.Anko.ObservableFieldImpl
import com.superfactory.library.Bridge.Anko.observable
import com.superfactory.library.Bridge.Model.ToolbarBindingModel
import com.superfactory.sunyatsen.R
import com.superfactory.sunyatsen.Struct.BaseStructImpl
import com.xiasuhuei321.loadingdialog.view.LoadingDialog

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
        toolbarBindingModel.rightText.value = "修改"
        toolbarBindingModel.rightTextColor.value = Color.parseColor("#b4b3b3")
    }

    override fun requestFailed(ld: LoadingDialog, error: Throwable?, witch: Int?) {
        ld.close()
        if (!TextUtils.isEmpty(error?.message)) {
            tips.value = error?.message!!
        }
    }

    val tips = observable("")
    override fun requestSuccess(ld: LoadingDialog, model: Any?, witch: Int?) {
        if (model == null) {
            ld.close()
            tips.value = "无法解析数据"
            return
        }
        if (model is BaseStructImpl) {
            if (model.success) {
                ld.close()
                ownerNotifier?.invoke(0, model)
            } else {
                ld.close()
                tips.value = model.msg ?: "未知错误"
            }
        }
    }

    val newPsw = observable("")
    val confirmPsw = observable("")
    val passwordCorrect = observable(false)
    val wrong = observable("")
    val passData1 = PasswordItemData(0, "请输入新密码")
    val passData2 = PasswordItemData(1, "再次输入新密码")

    data class PasswordItemData(val index: Int, val hint: String, var psw: ObservableFieldImpl<String> = observable(""))
}