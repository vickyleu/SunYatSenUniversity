package com.superfactory.sunyatsin.Interface.BindingPrompt.DutyPrompt

import android.text.TextUtils
import com.superfactory.library.Bridge.Anko.observable
import com.superfactory.library.Bridge.Anko.observableNullable
import com.superfactory.library.Graphics.KDialog.Prompt.BasePromptParams
import com.superfactory.sunyatsin.Struct.Duty.BzDutyInfo
import com.superfactory.sunyatsin.Struct.Duty.DutyStruct
import com.xiasuhuei321.loadingdialog.view.LoadingDialog

/**
 * Created by vicky on 2018/2/5.
 */
class DutyPromptViewModel : BasePromptParams() {
    override fun setPrompt(promptParams: BasePromptParams) {
        promptParams.width.value = (promptParams.screenSize.width * 0.6f).toInt()
        promptParams.height.value = (promptParams.screenSize.height * 0.8f).toInt()
        promptParams.cancelable.value = false
        promptParams.touchCancelable.value = false
    }

    val tips = observable("")
    override fun requestFailed(ld: LoadingDialog, error: Throwable?, witch: Int?) {
        ld.close()
        if (!TextUtils.isEmpty(error?.message)) {
            tips.value = error?.message!!
        }
    }

    override fun requestSuccess(ld: LoadingDialog, model: Any?, witch: Int?) {
        if (model == null) {
            ld.close()
            tips.value = "无法解析数据"
            return
        }
        if (model is DutyStruct) {
            if (model.success) {
                ld.close()
                title.value = "职责类型(${model.body.bzDutyInfos.size})"
                (itemList.value as ArrayList<BzDutyInfo>).clear()
                (itemList.value as ArrayList<BzDutyInfo>).addAll(model.body.bzDutyInfos)
                itemList.notifyChange()
            } else {
                ld.close()
                tips.value = model.msg ?: "未知错误"
            }
        }
    }

    val title = observable("")
    var onItemClickListener: (((Int, Any?) -> Unit)?) = null
    val itemList = observableNullable<List<BzDutyInfo>>(arrayListOf<BzDutyInfo>())
}