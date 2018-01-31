package com.superfactory.sunyatsin.Struct.Login

import android.os.Parcel
import android.os.Parcelable
import com.superfactory.sunyatsin.Struct.Base.BaseBody
import com.superfactory.sunyatsin.Struct.Base.BaseStruct


/**
 * Created by vicky on 2018.01.31.
 *
 * @Author vicky
 * @Date 2018年01月31日  14:21:37
 * @ClassName 这里输入你的类名(或用途)
 */
data class LoginAfterStruct(val body: LoginAfterBody) : BaseStruct() {
    constructor(parcel: Parcel) : this(parcel.readParcelable<LoginAfterBody>(LoginAfterBody::class.java.classLoader)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(body, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LoginAfterStruct> {
        override fun createFromParcel(parcel: Parcel): LoginAfterStruct {
            return LoginAfterStruct(parcel)
        }

        override fun newArray(size: Int): Array<LoginAfterStruct?> {
            return arrayOfNulls(size)
        }
    }
}

data class LoginAfterBody(
        val data: Data
) : BaseBody() {
    constructor(parcel: Parcel) : this(parcel.readParcelable<Data>(Data::class.java.classLoader)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(data, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LoginAfterBody> {
        override fun createFromParcel(parcel: Parcel): LoginAfterBody {
            return LoginAfterBody(parcel)
        }

        override fun newArray(size: Int): Array<LoginAfterBody?> {
            return arrayOfNulls(size)
        }
    }
}

data class Data(
        val id: String,
        val remarks: String,
        val createBy: CreateBy,
        val createDate: String,
        val updateBy: UpdateBy,
        val updateDate: String,
        val office: Office,
        val loginName: String,
        val no: String,
        val name: String,
        val email: String,
        val phone: String,
        val mobile: String,
        val loginIp: String,
        val loginDate: String,
        val loginFlag: String,
        val photo: String,
        val qrCode: String,
        val sign: String,
        val oldLoginIp: String,
        val oldLoginDate: String,
        val regId: String,
        val jobType: String,
        val jobTypeName: String,
        val deptTypeId: String,
        val deptTypeName: String,
        val sex: String,
        val roleNames: String,
        val admin: Boolean
) : BaseBody() {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(CreateBy::class.java.classLoader),
            parcel.readString(),
            parcel.readParcelable(UpdateBy::class.java.classLoader),
            parcel.readString(),
            parcel.readParcelable(Office::class.java.classLoader),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(remarks)
        parcel.writeParcelable(createBy, flags)
        parcel.writeString(createDate)
        parcel.writeParcelable(updateBy, flags)
        parcel.writeString(updateDate)
        parcel.writeParcelable(office, flags)
        parcel.writeString(loginName)
        parcel.writeString(no)
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeString(phone)
        parcel.writeString(mobile)
        parcel.writeString(loginIp)
        parcel.writeString(loginDate)
        parcel.writeString(loginFlag)
        parcel.writeString(photo)
        parcel.writeString(qrCode)
        parcel.writeString(sign)
        parcel.writeString(oldLoginIp)
        parcel.writeString(oldLoginDate)
        parcel.writeString(regId)
        parcel.writeString(jobType)
        parcel.writeString(jobTypeName)
        parcel.writeString(deptTypeId)
        parcel.writeString(deptTypeName)
        parcel.writeString(sex)
        parcel.writeString(roleNames)
        parcel.writeByte(if (admin) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Data> {
        override fun createFromParcel(parcel: Parcel): Data {
            return Data(parcel)
        }

        override fun newArray(size: Int): Array<Data?> {
            return arrayOfNulls(size)
        }
    }
}

data class CreateBy(
        val id: String,
        val loginFlag: String,
        val roleNames: String,
        val admin: Boolean
) : BaseBody() {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(loginFlag)
        parcel.writeString(roleNames)
        parcel.writeByte(if (admin) 1 else 0)
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

data class UpdateBy(
        val id: String,
        val loginFlag: String,
        val roleNames: String,
        val admin: Boolean
) : BaseBody() {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(loginFlag)
        parcel.writeString(roleNames)
        parcel.writeByte(if (admin) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UpdateBy> {
        override fun createFromParcel(parcel: Parcel): UpdateBy {
            return UpdateBy(parcel)
        }

        override fun newArray(size: Int): Array<UpdateBy?> {
            return arrayOfNulls(size)
        }
    }
}

data class Office(
        val id: String,
        val name: String,
        val sort: Int,
        val hasChildren: Boolean,
        val type: String,
        val parentId: String
) : BaseBody() {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeInt(sort)
        parcel.writeByte(if (hasChildren) 1 else 0)
        parcel.writeString(type)
        parcel.writeString(parentId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Office> {
        override fun createFromParcel(parcel: Parcel): Office {
            return Office(parcel)
        }

        override fun newArray(size: Int): Array<Office?> {
            return arrayOfNulls(size)
        }
    }
}