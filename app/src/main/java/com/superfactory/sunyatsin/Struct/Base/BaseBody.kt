package com.superfactory.sunyatsin.Struct.Base

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by vicky on 2018.01.30.
 *
 * @Author vicky
 * @Date 2018年01月30日  15:56:06
 * @ClassName 这里输入你的类名(或用途)
 */
open class BaseBody() :Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     * May be 0 or [.PARCELABLE_WRITE_RETURN_VALUE].
     */
    override fun writeToParcel(dest: Parcel?, flags: Int) {

    }

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of [.writeToParcel],
     * the return value of this method must include the
     * [.CONTENTS_FILE_DESCRIPTOR] bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BaseBody> {
        override fun createFromParcel(parcel: Parcel): BaseBody {
            return BaseBody(parcel)
        }

        override fun newArray(size: Int): Array<BaseBody?> {
            return arrayOfNulls(size)
        }
    }
}