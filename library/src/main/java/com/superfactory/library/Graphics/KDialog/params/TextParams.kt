package com.superfactory.library.Graphics.KDialog.params

import android.os.Parcel
import android.os.Parcelable
import android.view.Gravity

import com.superfactory.library.Graphics.KDialog.res.values.CircleColor
import com.superfactory.library.Graphics.KDialog.res.values.CircleDimen


/**
 * 文本内容参数
 * Created by hupei on 2017/3/30.
 */
class TextParams : Parcelable {
    /**
     * body文本内间距
     */
    var padding: IntArray?=null
    /**
     * 文本
     */
    var text: String?=null
    /**
     * 文本高度
     */
    var height = CircleDimen.TITLE_HEIGHT
    /**
     * 文本背景颜色
     */
    var backgroundColor: Int = 0
    /**
     * 文本字体颜色
     */
    var textColor = CircleColor.content
    /**
     * 文本字体大小
     */
    var textSize = CircleDimen.CONTENT_TEXT_SIZE

    var gravity = Gravity.CENTER

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeIntArray(this.padding)
        dest.writeString(this.text)
        dest.writeInt(this.height)
        dest.writeInt(this.backgroundColor)
        dest.writeInt(this.textColor)
        dest.writeInt(this.textSize)
        dest.writeInt(this.gravity)
    }

    constructor() {}

    protected constructor(`in`: Parcel) {
        this.padding = `in`.createIntArray()
        this.text = `in`.readString()
        this.height = `in`.readInt()
        this.backgroundColor = `in`.readInt()
        this.textColor = `in`.readInt()
        this.textSize = `in`.readInt()
        this.gravity = `in`.readInt()
    }

    companion object {

        val CREATOR: Parcelable.Creator<TextParams> = object : Parcelable.Creator<TextParams> {
            override fun createFromParcel(source: Parcel): TextParams {
                return TextParams(source)
            }

            override fun newArray(size: Int): Array<TextParams?> {
                return arrayOfNulls(size)
            }
        }
    }
}
