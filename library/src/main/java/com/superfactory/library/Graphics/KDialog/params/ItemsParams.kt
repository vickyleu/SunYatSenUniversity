package com.superfactory.library.Graphics.KDialog.params

import android.os.Parcel
import android.os.Parcelable
import android.widget.AdapterView


import com.superfactory.library.Graphics.KDialog.res.values.CircleColor
import com.superfactory.library.Graphics.KDialog.res.values.CircleDimen

/**
 * items 内容参数
 * Created by hupei on 2017/3/30.
 */
open class ItemsParams : Parcelable {

    /**
     * item点击事件
     */
    var listener: AdapterView.OnItemClickListener? = null
    /**
     * item高度
     */
    var itemHeight = CircleDimen.ITEM_HEIGHT
    /**
     * item内间距
     */
    var padding: IntArray?=null
    /**
     * 数据源：array or list
     */
    var items: Any? = null
    /**
     * item背景色
     */
    var backgroundColor: Int = 0
    /**
     * item字体色
     */
    var textColor = CircleColor.content
    /**
     * item字体大小
     */
    var textSize = CircleDimen.CONTENT_TEXT_SIZE

    open fun dismiss() {}

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(this.itemHeight)
        dest.writeIntArray(this.padding)
        dest.writeInt(this.backgroundColor)
        dest.writeInt(this.textColor)
        dest.writeInt(this.textSize)
    }

    constructor() {}

    protected constructor(`in`: Parcel) {
        this.itemHeight = `in`.readInt()
        this.padding = `in`.createIntArray()
        this.backgroundColor = `in`.readInt()
        this.textColor = `in`.readInt()
        this.textSize = `in`.readInt()
    }

    companion object {

        val CREATOR: Parcelable.Creator<ItemsParams> = object : Parcelable.Creator<ItemsParams> {
            override fun createFromParcel(source: Parcel): ItemsParams {
                return ItemsParams(source)
            }

            override fun newArray(size: Int): Array<ItemsParams?> {
                return arrayOfNulls(size)
            }
        }
    }
}
