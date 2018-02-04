package com.superfactory.sunyatsin.Interface.BindingActivity.LoginActivity

import android.graphics.Color
import android.text.TextUtils
import com.superfactory.library.Bridge.Anko.BaseObservable
import com.superfactory.library.Bridge.Anko.observable
import com.superfactory.sunyatsin.Struct.Login.LoginAfterStruct
import com.superfactory.sunyatsin.Struct.Login.LoginStruct
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
        if (model is LoginStruct) {
            if (model.success) {
//                ld.close()
//                ownerNotifier?.invoke(0, model)
            } else {
                ld.close()
                tips.value = model.msg ?: "未知错误"
            }
        } else if (model is LoginAfterStruct) {
            if (model.success) {
                ld.close()
                ownerNotifier?.invoke(0, model)
            } else {
                ld.close()
                tips.value = model.msg ?: "未知错误"
            }
        }
    }

    override fun appendingRequest(ld: LoadingDialog, model: Any?): Boolean {
        if (model != null) {
            if (model is LoginStruct) {
                if (model.success) {
                    return super.appendingRequest(ld, model)
                } else {
                    ld.close()
                    tips.value = model.msg ?: "未知错误"
                    return false
                }
            } else {
                ld.close()
                tips.value = "未知错误"
                return false
            }
        } else {
            ld.close()
            tips.value = "解析错误"
            return false
        }
    }


}