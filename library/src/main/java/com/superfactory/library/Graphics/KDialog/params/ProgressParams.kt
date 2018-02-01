package com.superfactory.library.Graphics.KDialog.params

import android.os.Parcel
import android.os.Parcelable

import com.superfactory.library.Graphics.KDialog.res.values.CircleColor
import com.superfactory.library.Graphics.KDialog.res.values.CircleDimen


/**
 * 进度条参数
 * Created by hupei on 2017/3/31.
 */
class ProgressParams : Parcelable {

    /**
     * 进度条样式，默认水平样式
     */
    var style = STYLE_HORIZONTAL
    /**
     * 进度条与body的边距
     */
    var margins = MARGINS
    /**
     * 底部文字内边距
     */
    var padding = TEXT_PADDING
    /**
     * 进度条资源背景
     */
    var progressDrawableId: Int = 0
    /**
     * 进度条高度
     */
    var progressHeight: Int = 0
    /**
     * 最大刻度
     */
    var max: Int = 0
    /**
     * 刻度
     */
    var progress: Int = 0
    /**
     * 进度条显示的文字，支持String.format() 例如：已经下载%s
     */
    var text = ""

    /**
     * body背景颜色
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

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(this.style)
        dest.writeIntArray(this.margins)
        dest.writeIntArray(this.padding)
        dest.writeInt(this.progressDrawableId)
        dest.writeInt(this.progressHeight)
        dest.writeInt(this.max)
        dest.writeInt(this.progress)
        dest.writeString(this.text)
        dest.writeInt(this.backgroundColor)
        dest.writeInt(this.textColor)
        dest.writeInt(this.textSize)
    }

    constructor() {}

    protected constructor(`in`: Parcel) {
        this.style = `in`.readInt()
        this.margins = `in`.createIntArray()
        this.padding = `in`.createIntArray()
        this.progressDrawableId = `in`.readInt()
        this.progressHeight = `in`.readInt()
        this.max = `in`.readInt()
        this.progress = `in`.readInt()
        this.text = `in`.readString()
        this.backgroundColor = `in`.readInt()
        this.textColor = `in`.readInt()
        this.textSize = `in`.readInt()
    }

    companion object {

        private val MARGINS = intArrayOf(20, 45, 20, 45)
        private val TEXT_PADDING = intArrayOf(0, 0, 0, 45)

        /**
         * 水平进度条
         */
        val STYLE_HORIZONTAL = 0
        /**
         * 旋转进度条
         */
        val STYLE_SPINNER = 1

        val CREATOR: Parcelable.Creator<ProgressParams> = object : Parcelable.Creator<ProgressParams> {
            override fun createFromParcel(source: Parcel): ProgressParams {
                return ProgressParams(source)
            }

            override fun newArray(size: Int): Array<ProgressParams?> {
                return arrayOfNulls(size)
            }
        }
    }
}
