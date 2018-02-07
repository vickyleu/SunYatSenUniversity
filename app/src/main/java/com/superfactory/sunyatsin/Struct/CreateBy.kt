package com.superfactory.sunyatsin.Struct

import android.os.Parcel
import android.os.Parcelable
import com.superfactory.library.Context.Extensions.writeStringNotNull
import com.superfactory.sunyatsin.Struct.Base.BaseStruct

/**
 * Created by vicky on 2018.02.07.
 *
 * @Author vicky
 * @Date 2018年02月07日  15:34:26
 * @ClassName 这里输入你的类名(或用途)
 */
 data class CreateBy(
        val id: String,
        val loginFlag: String,
        val admin: Boolean,
        val roleNames: String
) : BaseStruct() {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStringNotNull(id)
        parcel.writeStringNotNull(loginFlag)
        parcel.writeByte(if (admin) 1 else 0)
        parcel.writeStringNotNull(roleNames)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CreateBy> {
        override fun createFromParcel(parcel: Parcel): CreateBy {
            return CreateBy(parcel)
        }

        override fun newArray(size: Int): Array<CreateBy?> {
            return arrayOfNulls(size)
        }
    }
}