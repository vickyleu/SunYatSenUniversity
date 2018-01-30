package com.superfactory.sunyatsin.Interface.BindingActivity.LoginActivity

import android.graphics.Color
import android.text.TextUtils
import com.superfactory.library.Bridge.Anko.BaseObservable
import com.superfactory.library.Bridge.Anko.observable
import com.superfactory.library.Bridge.Model.PostModel
import com.superfactory.sunyatsin.Struct.LoginStruct
import com.xiasuhuei321.loadingdialog.view.LoadingDialog

/**
 * Created by vicky on 2018.01.29.
 *
 * @Author vicky
 * @Date 2018年01月29日  10:32:45
 * @ClassName 这里输入你的类名(或用途)
 */
class LoginActivityViewModel : BaseObservable() {
    val tips = observable("")
    override fun postResult(model: Any): PostModel {
        if (model is LoginStruct) {
            return PostModel(model.success, model.msg)
        }
        return super.postResult(model)
    }

    override fun postFailure(error: String?) {
        if (!TextUtils.isEmpty(error)) {
            tips.value = error!!
        }
    }

    override fun startRequest(ld: LoadingDialog) {
        ld.setLoadingText("加载中")
                .setSuccessText("加载成功")//显示加载成功时的文字
                //.setFailedText("加载失败")
                .setInterceptBack(false)
                .setLoadSpeed(LoadingDialog.Speed.SPEED_ONE)
                .setRepeatCount(0)
                .setDrawColor(Color.parseColor("#f8f8f8"))
                .show()
    }

    override fun async(model: Any?) {
        if ((model as?LoginStruct)?.success == true) {
            ownerNotifier?.invoke(0, model)
        }
    }
}