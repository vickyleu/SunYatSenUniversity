package com.superfactory.library.Graphics.KDialog.params

import android.os.Parcel
import android.os.Parcelable
import android.view.View

import com.superfactory.library.Graphics.KDialog.res.values.CircleColor
import com.superfactory.library.Graphics.KDialog.res.values.CircleDimen
import com.superfactory.library.Graphics.KDialog.view.listener.OnInputClickListener

/**
 * 按钮参数
 * Created by hupei on 2017/3/30.
 */
open class ButtonParams : Parcelable {

    /**
     * 按钮点击事件
     */
    var listener: View.OnClickListener? = null
    /**
     * 输入框确定事件
     */
    var inputListener: OnInputClickListener? = null
    /**
     * 按钮框与顶部距离
     */
    var topMargin: Int = 0
    /**
     * 按钮文本颜色
     */
    var textColor = CircleColor.button
    /**
     * 按钮文本大小
     */
    var textSize = CircleDimen.FOOTER_TEXT_SIZE
    /**
     * 按钮高度
     */
    var height = CircleDimen.FOOTER_HEIGHT
    /**
     * 按钮背景颜色
     */
    var backgroundColor: Int = 0
    /**
     * 按钮文本
     */
    var text: String = ""

    open fun dismiss() {

    }


    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(this.topMargin)
        dest.writeInt(this.textColor)
        dest.writeInt(this.textSize)
        dest.writeInt(this.height)
        dest.writeInt(this.backgroundColor)
        dest.writeString(this.text)
    }

    constructor() {}

    protected constructor(`in`: Parcel) {
        this.topMargin = `in`.readInt()
        this.textColor = `in`.readInt()
        this.textSize = `in`.readInt()
        this.height = `in`.readInt()
        this.backgroundColor = `in`.readInt()
        this.text = `in`.readString()
    }

    companion object {

        val CREATOR: Parcelable.Creator<ButtonParams> = object : Parcelable.Creator<ButtonParams> {
            override fun createFromParcel(source: Parcel): ButtonParams {
                return ButtonParams(source)
            }

            override fun newArray(size: Int): Array<ButtonParams?> {
                return arrayOfNulls(size)
            }
        }
    }
}
