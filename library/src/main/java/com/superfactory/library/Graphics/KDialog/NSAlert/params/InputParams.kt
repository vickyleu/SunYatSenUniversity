package com.superfactory.library.Graphics.KDialog.params

import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable

import com.superfactory.library.Graphics.KDialog.res.values.CircleColor
import com.superfactory.library.Graphics.KDialog.res.values.CircleDimen


/**
 * 输入框参数
 * Created by hupei on 2017/3/31.
 */
class InputParams : Parcelable {

    /**
     * 输入框与body视图的距离
     */
    var margins = MARGINS
    /**
     * 输入框的高度
     */
    var inputHeight = CircleDimen.INPUT_HEIGHT
    /**
     * 输入框提示语
     */
    var hintText: String?=null
    /**
     * 输入框提示语颜色
     */
    var hintTextColor = CircleColor.content
    /**
     * 输入框背景资源文件
     */
    var inputBackgroundResourceId: Int = 0
    /**
     * 输入框边框线条粗细
     */
    var strokeWidth = 1
    /**
     * 输入框边框线条颜色
     */
    var strokeColor = CircleColor.inputStroke
    /**
     * 输入框的背景
     */
    var inputBackgroundColor = Color.TRANSPARENT
    /**
     * body视图的背景色
     */
    var backgroundColor: Int = 0
    /**
     * 输入框字体大小
     */
    var textSize = CircleDimen.CONTENT_TEXT_SIZE
    /**
     * 输入框字体颜色
     */
    var textColor = CircleColor.title

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeIntArray(this.margins)
        dest.writeInt(this.inputHeight)
        dest.writeString(this.hintText)
        dest.writeInt(this.hintTextColor)
        dest.writeInt(this.inputBackgroundResourceId)
        dest.writeInt(this.strokeWidth)
        dest.writeInt(this.strokeColor)
        dest.writeInt(this.inputBackgroundColor)
        dest.writeInt(this.backgroundColor)
        dest.writeInt(this.textSize)
        dest.writeInt(this.textColor)
    }

    constructor() {}

    protected constructor(`in`: Parcel) {
        this.margins = `in`.createIntArray()
        this.inputHeight = `in`.readInt()
        this.hintText = `in`.readString()
        this.hintTextColor = `in`.readInt()
        this.inputBackgroundResourceId = `in`.readInt()
        this.strokeWidth = `in`.readInt()
        this.strokeColor = `in`.readInt()
        this.inputBackgroundColor = `in`.readInt()
        this.backgroundColor = `in`.readInt()
        this.textSize = `in`.readInt()
        this.textColor = `in`.readInt()
    }

    companion object {
        private val MARGINS = intArrayOf(50, 20, 50, 40)

        val CREATOR: Parcelable.Creator<InputParams> = object : Parcelable.Creator<InputParams> {
            override fun createFromParcel(source: Parcel): InputParams {
                return InputParams(source)
            }

            override fun newArray(size: Int): Array<InputParams?> {
                return arrayOfNulls(size)
            }
        }
    }
}
