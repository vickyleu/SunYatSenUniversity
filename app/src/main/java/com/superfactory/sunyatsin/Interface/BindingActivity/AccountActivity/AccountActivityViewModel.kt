package com.superfactory.sunyatsin.Interface.BindingActivity.AccountActivity

import android.content.Intent
import android.graphics.Color
import android.support.v4.content.ContextCompat
import com.superfactory.library.Bridge.Anko.observable
import com.superfactory.library.Bridge.Anko.observableNullable
import com.superfactory.library.Bridge.Model.ToolbarBindingModel
import com.superfactory.sunyatsin.R
import com.superfactory.sunyatsin.Struct.BaseStructImpl
import com.superfactory.sunyatsin.Struct.Const
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

//    override fun requestSuccess(ld: LoadingDialog, model: Any?, witch: Int?) {
//        if (model==null){
//
//        }else if (model is BaseStructImpl){
//            if (witch?:0==5){
//
//            }else{
//
//            }
//        }
//    }

    val avatar = observable("")

    val accountItemsList = observableNullable<List<AccountActivityItemViewModel>?>(null)

    init {
        val data = intent.extras.getParcelable<Data>("login")
        accountItemsList.value = arrayListOf(AccountActivityItemViewModel(0, "头像", R.drawable.right_arrow_icon,
                description = "${Const.AvatarPrefix}${data.photo}"),
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
