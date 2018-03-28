package com.superfactory.sunyatsen.Struct.Login

import android.os.Parcel
import android.os.Parcelable
import com.superfactory.sunyatsen.Struct.Base.BaseBody
import com.superfactory.sunyatsen.Struct.Base.BaseStruct

/**
 * Created by vicky on 2018.01.30.
 *
 * @Author vicky
 * @Date 2018年01月30日  14:05:16
 * @ClassName 这里输入你的类名(或用途)
 */
data class LoginStruct(val body: LoginBody?) : BaseStruct() {

    constructor(parcel: Parcel) : this(parcel.readParcelable<LoginBody>(LoginBody::class.java.classLoader)) {
    }

    fun isValidLogin(): Boolean {
        return success && body?.JSESSIONID != null
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(body, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LoginStruct> {
        override fun createFromParcel(parcel: Parcel): LoginStruct {
            return LoginStruct(parcel)
        }

        override fun newArray(size: Int): Array<LoginStruct?> {
            return arrayOfNulls(size)
        }
    }

}

data class LoginBody(val username: String?, val name: String, val mobileLogin: Boolean?, val JSESSIONID: String) : BaseBody() {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
            parcel.readString()) {
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(username)
        dest.writeString(name)
        dest.writeValue(mobileLogin)
        dest.writeString(JSESSIONID)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LoginBody> {
        override fun createFromParcel(parcel: Parcel): LoginBody {
            return LoginBody(parcel)
        }

        override fun newArray(size: Int): Array<LoginBody?> {
            return arrayOfNulls(size)
        }
    }

}