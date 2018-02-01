package com.superfactory.library.Graphics.KDialog.params

import android.os.Parcel
import android.os.Parcelable
import android.view.Gravity

import com.superfactory.library.Graphics.KDialog.res.values.CircleColor
import com.superfactory.library.Graphics.KDialog.res.values.CircleDimen


/**
 * 标题参数
 * Created by hupei on 2017/3/30.
 */
class TitleParams : Parcelable {
    /**
     * 标题
     */
    var text: String?=null
    /**
     * 标题高度
     */
    var height = CircleDimen.TITLE_HEIGHT
    /**
     * 标题字体大小
     */
    var textSize = CircleDimen.TITLE_TEXT_SIZE
    /**
     * 标题字体颜色
     */
    var textColor = CircleColor.title
    /**
     * 标题背景颜色
     */
    var backgroundColor: Int = 0

    var gravity = Gravity.CENTER

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.text)
        dest.writeInt(this.height)
        dest.writeInt(this.textSize)
        dest.writeInt(this.textColor)
        dest.writeInt(this.backgroundColor)
        dest.writeInt(this.gravity)
    }

    constructor() {}

    protected constructor(`in`: Parcel) {
        this.text = `in`.readString()
        this.height = `in`.readInt()
        this.textSize = `in`.readInt()
        this.textColor = `in`.readInt()
        this.backgroundColor = `in`.readInt()
        this.gravity = `in`.readInt()
    }

    companion object {

        val CREATOR: Parcelable.Creator<TitleParams> = object : Parcelable.Creator<TitleParams> {
            override fun createFromParcel(source: Parcel): TitleParams {
                return TitleParams(source)
            }

            override fun newArray(size: Int): Array<TitleParams?> {
                return arrayOfNulls(size)
            }
        }
    }
}
