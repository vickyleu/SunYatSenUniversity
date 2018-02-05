//package com.superfactory.sunyatsin.Interface.FuckingCode
//
//import android.app.Activity
//import com.jph.takephoto.app.TakePhoto
//import com.jph.takephoto.app.TakePhotoImpl
//import com.jph.takephoto.model.InvokeParam
//import com.jph.takephoto.model.TContextWrap
//import com.jph.takephoto.model.TResult
//import com.jph.takephoto.permission.InvokeListener
//import com.jph.takephoto.permission.PermissionManager
//import com.jph.takephoto.permission.TakePhotoInvocationHandler
//
///**
// * Created by vicky on 2018.02.05.
// *
// * @Author vicky
// * @Date 2018年02月05日  18:52:47
// * @ClassName 这里输入你的类名(或用途)
// */
//class TakePhoto(val activity: Activity) : TakePhoto.TakeResultListener, InvokeListener {
//    private var takePhoto: TakePhoto? = null
//    private var invokeParam: InvokeParam? = null
//    init {
//        getTakePhoto().onCreate(null)
//    }
//    override fun invoke(invokeParam: InvokeParam?): PermissionManager.TPermissionType {
//        val type = PermissionManager.checkPermission(TContextWrap.of(activity), invokeParam!!.method)
//        if (PermissionManager.TPermissionType.WAIT == type) {
//            this.invokeParam = invokeParam
//        }
//        return type
//    }
//
//    override fun takeSuccess(result: TResult?) {
//
//    }
//
//    override fun takeCancel() {
//    }
//
//    override fun takeFail(result: TResult?, msg: String?) {
//    }
//
//
//    fun getTakePhoto(): TakePhoto {
//        if (takePhoto == null) {
//            takePhoto = TakePhotoInvocationHandler.of(this).bind(TakePhotoImpl(activity, this)) as TakePhoto
//        }
//        return takePhoto as TakePhoto
//    }
//}