package com.superfactory.library.Graphics.KDialog.params

import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import android.view.Gravity


import com.superfactory.library.Graphics.KDialog.res.values.CircleDimen

/**
 * 对话框参数
 * Created by hupei on 2017/3/30.
 */
class DialogParams : Parcelable {
    /**
     * 对话框的位置
     */
    var gravity = Gravity.NO_GRAVITY
    /**
     * 是否触摸外部关闭
     */
    var canceledOnTouchOutside = true
    /**
     * 返回键是否关闭
     */
    var cancelable = true
    /**
     * 对话框透明度，范围：0-1；1不透明
     */
    var alpha = 1f
    /**
     * 对话框宽度，范围：0-1；1整屏宽
     */
    var width = 0.9f
    /**
     * 对话框与屏幕边距
     */
    var mPadding: IntArray?=null
    /**
     * 对话框弹出动画,StyleRes
     */
    var animStyle: Int = 0
    /**
     * 对话框刷新动画，AnimRes
     */
    var refreshAnimation: Int = 0
    /**
     * 对话框背景是否昏暗，默认true
     */
    var isDimEnabled = true
    /**
     * 对话框的背景色透明，因为列表模式情况，内容与按钮中间有距离
     */
    var backgroundColor = Color.TRANSPARENT
    /**
     * 对话框的圆角半径
     */
    var radius = CircleDimen.RADIUS
    /**
     * 对话框 x 坐标偏移
     */
    var xOff: Int = 0
    /**
     * 对话框 y 坐标偏移
     */
    var yOff: Int = 0

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(this.gravity)
        dest.writeByte(if (this.canceledOnTouchOutside) 1.toByte() else 0.toByte())
        dest.writeByte(if (this.cancelable) 1.toByte() else 0.toByte())
        dest.writeFloat(this.alpha)
        dest.writeFloat(this.width)
        dest.writeIntArray(this.mPadding)
        dest.writeInt(this.animStyle)
        dest.writeInt(this.refreshAnimation)
        dest.writeByte(if (this.isDimEnabled) 1.toByte() else 0.toByte())
        dest.writeInt(this.backgroundColor)
        dest.writeInt(this.radius)
        dest.writeInt(this.xOff)
        dest.writeInt(this.yOff)
    }

    constructor() {}

    protected constructor(`in`: Parcel) {
        this.gravity = `in`.readInt()
        this.canceledOnTouchOutside = `in`.readByte().toInt() != 0
        this.cancelable = `in`.readByte().toInt() != 0
        this.alpha = `in`.readFloat()
        this.width = `in`.readFloat()
        this.mPadding = `in`.createIntArray()
        this.animStyle = `in`.readInt()
        this.refreshAnimation = `in`.readInt()
        this.isDimEnabled = `in`.readByte().toInt() != 0
        this.backgroundColor = `in`.readInt()
        this.radius = `in`.readInt()
        this.xOff = `in`.readInt()
        this.yOff = `in`.readInt()
    }

    companion object {

        val CREATOR: Parcelable.Creator<DialogParams> = object : Parcelable.Creator<DialogParams> {
            override fun createFromParcel(source: Parcel): DialogParams {
                return DialogParams(source)
            }

            override fun newArray(size: Int): Array<DialogParams?> {
                return arrayOfNulls(size)
            }
        }
    }
}
