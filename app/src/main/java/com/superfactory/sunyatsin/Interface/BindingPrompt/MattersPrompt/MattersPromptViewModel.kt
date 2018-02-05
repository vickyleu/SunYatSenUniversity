package com.superfactory.sunyatsin.Interface.BindingPrompt.MattersPrompt

import android.text.TextUtils
import com.superfactory.library.Bridge.Anko.observable
import com.superfactory.library.Bridge.Anko.observableNullable
import com.superfactory.library.Graphics.KDialog.Prompt.BasePromptParams
import com.superfactory.sunyatsin.Struct.MattersStruct.BzMatterInfo
import com.superfactory.sunyatsin.Struct.MattersStruct.MattersStruct
import com.xiasuhuei321.loadingdialog.view.LoadingDialog

/**
 * Created by vicky on 2018.02.05.
 *
 * @Author vicky
 * @Date 2018年02月05日  15:16:40
 * @ClassName 这里输入你的类名(或用途)
 */
class MattersPromptViewModel(val id: String) : BasePromptParams() {
    override fun setPrompt(promptParams: BasePromptParams) {
        promptParams.width.value = (promptParams.screenSize.width * 0.6f).toInt()
        promptParams.height.value = (promptParams.screenSize.height * 0.8f).toInt()
        promptParams.cancelable.value = false
        promptParams.touchCancelable.value = true
    }

    var errorInterrupt = false
    val tips = observable("")
    override fun requestFailed(ld: LoadingDialog, error: Throwable?, witch: Int?) {
        ld.close()
        errorInterrupt = true
        if (!TextUtils.isEmpty(error?.message)) {
            tips.value = error?.message!!
        }
    }

    override fun requestSuccess(ld: LoadingDialog, model: Any?, witch: Int?) {
        if (model == null) {
            ld.close()
            errorInterrupt = true
            tips.value = "无法解析数据"
            return
        }
        if (model is MattersStruct) {
            if (model.success) {
                ld.close()
                title.value = "职责类型(${model.body.bzMatterInfos.size})"
                itemList.value = model.body.bzMatterInfos
            } else {
                ld.close()
                errorInterrupt = true
                tips.value = model.msg ?: "未知错误"
            }
        }
    }

    val title = observable("")
    var onItemClickListener: (((Int, Any?) -> Unit)?) = null
    val itemList = observableNullable<List<BzMatterInfo>>(arrayListOf<BzMatterInfo>())
}