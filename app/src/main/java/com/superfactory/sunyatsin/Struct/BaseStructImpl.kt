package com.superfactory.sunyatsin.Struct

import android.os.Parcel
import android.os.Parcelable
import com.superfactory.sunyatsin.Struct.Base.BaseStruct

/**
 * Created by vicky on 2018.02.04.
 *
 * @Author vicky
 * @Date 2018年02月04日  14:51:02
 * @ClassName 这里输入你的类名(或用途)
 */
class BaseStructImpl() : BaseStruct() {
    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BaseStructImpl> {
        override fun createFromParcel(parcel: Parcel): BaseStructImpl {
            return BaseStructImpl(parcel)
        }

        override fun newArray(size: Int): Array<BaseStructImpl?> {
            return arrayOfNulls(size)
        }
    }
}