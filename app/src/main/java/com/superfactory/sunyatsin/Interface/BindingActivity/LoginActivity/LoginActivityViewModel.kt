package com.superfactory.sunyatsin.Interface.BindingActivity.LoginActivity

import android.graphics.Color
import android.text.TextUtils
import com.superfactory.library.Bridge.Anko.BaseObservable
import com.superfactory.library.Bridge.Anko.observable
import com.superfactory.library.Bridge.Model.PostModel
import com.superfactory.sunyatsin.Struct.Base.BaseStruct
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

//    override fun postResult(model: Any): PostModel {
//        if (model is LoginStruct) {
//            return PostModel(model.success, model.msg)
//        }
//        return super.postResult(model)
//    }

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

    override fun requestFailed(ld: LoadingDialog, error: Throwable?) {
        if (!TextUtils.isEmpty(error?.message)) {
            tips.value = error?.message!!
        }
    }

    override fun requestSuccess(ld: LoadingDialog, t: Any?) {
//        ld.close()
        if (t==null){
            ld.close()
            tips.value = "无法解析数据"
            return
        }
        if (t is LoginStruct){
            if (t.success){
                ld.close()
                ownerNotifier?.invoke(0, t)
            }else{
                ld.close()
                tips.value =t.errorCode?:"未知错误"
            }
        }else if (t is BaseStruct){
            if (t.success){
                ld.close()
                ownerNotifier?.invoke(0, t)
            }else{
                ld.close()
                tips.value =t.errorCode?:"未知错误"
            }
        }
    }

//    override fun appendingRequest(ld: LoadingDialog, t: Any?) {
//        super.appendingRequest(ld, t)
//    }


//    override fun async(model: Any?) {
//        if ((model as?LoginStruct)?.success == true) {
//            ownerNotifier?.invoke(0, model)
//        }
//    }
}