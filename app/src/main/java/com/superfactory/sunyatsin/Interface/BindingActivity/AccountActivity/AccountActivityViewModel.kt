package com.superfactory.sunyatsin.Interface.BindingActivity.AccountActivity

import android.content.Intent
import android.graphics.Color
import android.support.v4.content.ContextCompat
import com.superfactory.library.Bridge.Anko.observable
import com.superfactory.library.Bridge.Anko.observableNullable
import com.superfactory.library.Bridge.Model.ToolbarBindingModel
import com.superfactory.library.Communication.Sender.TakeParamsBack
import com.superfactory.sunyatsin.R
import com.superfactory.sunyatsin.Struct.BaseStructImpl
import com.superfactory.sunyatsin.Struct.Login.Data
import com.xiasuhuei321.loadingdialog.view.LoadingDialog

/**
 * Created by vicky on 2018.01.31.
 *
 * @Author vicky
 * @Date 2018年01月31日  18:56:13
 * @ClassName 这里输入你的类名(或用途)
 */
class AccountActivityViewModel(intent: Intent) : ToolbarBindingModel() {
    override fun setToolbar(toolbarBindingModel: ToolbarBindingModel) {
        toolbarBindingModel.title.value = "个人资料"
        toolbarBindingModel.backgroundColor.value = Color.parseColor("#1688ff")
        val ctx = getStaticsContextRef()
        toolbarBindingModel.leftIcon.value = ContextCompat.getDrawable(ctx, R.drawable.back_stack_icon)
    }

    var onItemClicked: ((Int, AccountActivityItemViewModel) -> Unit)? = null


    val tips = observable("")
    override fun requestSuccess(ld: LoadingDialog, model: Any?, witch: Int?) {
        if (model == null) {
            ld.close()
            tips.value = "无法解析数据"
            return
        } else if (model is BaseStructImpl || model is TakeParamsBack) {
            when (witch ?: 0) {
                5 -> {
                    if (model is BaseStructImpl) {
                        if (model.success) {
                            ld.close()
//                            accountItemsList.value?.get(0)?.description=model.msg
                        } else {
                            ld.close()
                            tips.value = model.msg ?: "未知错误"
                        }
                    }
                }
                6 -> {
                    val param = (model as? TakeParamsBack)
                    if ((param?.any as? BaseStructImpl)?.success == true) {
                        ld.close()
                        accountItemsList.value?.get(1)?.description = param.params as? String
                    } else {
                        ld.close()
                        tips.value = (param?.any as? BaseStructImpl)?.msg ?: "未知错误"
                    }
                }
                7 -> {
                    val param = (model as? TakeParamsBack)
                    if ((param?.any as? BaseStructImpl)?.success == true) {
                        ld.close()
                        accountItemsList.value?.get(2)?.description = (param.params as? Int).toString()
                    } else {
                        ld.close()
                        tips.value = (param?.any as? BaseStructImpl)?.msg ?: "未知错误"
                    }
                }
            }

        }
    }

    val avatar = observable("")

    val accountItemsList = observableNullable<List<AccountActivityItemViewModel>?>(null)

    init {
        val data = intent.extras.getParcelable<Data>("login")
        accountItemsList.value = arrayListOf(AccountActivityItemViewModel(0, "头像", R.drawable.right_arrow_icon,
                description = null/*"${Const.AvatarPrefix}${data.photo}"*/),
                AccountActivityItemViewModel(1, "姓名", R.drawable.right_arrow_icon,
                        description = data.name),
                AccountActivityItemViewModel(2, "性别", R.drawable.right_arrow_icon,
                        description = if ((data.sex.equals("0"))) "男" else "女"))

    }
}

data class AccountActivityItemViewModel(val index: Int,
                                        val name: String,
                                        val right: Int,
                                        var description: String? = null) {

}
