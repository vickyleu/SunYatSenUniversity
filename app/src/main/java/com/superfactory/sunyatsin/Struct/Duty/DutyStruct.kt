package com.superfactory.sunyatsin.Struct.Duty

import android.os.Parcel
import android.os.Parcelable
import com.superfactory.sunyatsin.Struct.Base.BaseBody
import com.superfactory.sunyatsin.Struct.Base.BaseStruct

/**
 * Created by vicky on 2018/2/5.
 */
data class DutyStruct(val body:DutyBody):BaseStruct() {
    constructor(parcel: Parcel) : this(parcel.readParcelable<DutyBody>(DutyBody::class.java.classLoader)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(body, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DutyStruct> {
        override fun createFromParcel(parcel: Parcel): DutyStruct {
            return DutyStruct(parcel)
        }

        override fun newArray(size: Int): Array<DutyStruct?> {
            return arrayOfNulls(size)
        }
    }
}


data class DutyBody(
		val bzDutyInfos: List<BzDutyInfo>
):BaseBody() {
    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(BzDutyInfo)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(bzDutyInfos)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DutyBody> {
        override fun createFromParcel(parcel: Parcel): DutyBody {
            return DutyBody(parcel)
        }

        override fun newArray(size: Int): Array<DutyBody?> {
            return arrayOfNulls(size)
        }
    }
}

data class BzDutyInfo(
		val id: String, //1ba982957cc745fead0d0741633cd241
		val createBy: CreateBy,
		val createDate: String, //2018-01-15 17:17:34
		val updateBy: UpdateBy,
		val updateDate: String, //2018-01-15 17:17:34
		val dutyCode: String, //PJ02
		val dutyName: String, //社区测试
		val jobTypeId: String, //5734c99a89f94a87818ff6d8130b5561
		val deptTypeId: String, //e27b423fdc304d7b9368f9fe3ad3a426
		val deptTypeName: String, //社区民警中队
		val jobTypeName: String, //中队长1
		val dutyTypeName: String //社区民警中队
):BaseBody() {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readParcelable(CreateBy::class.java.classLoader),
            parcel.readString(),
            parcel.readParcelable(UpdateBy::class.java.classLoader),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeParcelable(createBy, flags)
        parcel.writeString(createDate)
        parcel.writeParcelable(updateBy, flags)
        parcel.writeString(updateDate)
        parcel.writeString(dutyCode)
        parcel.writeString(dutyName)
        parcel.writeString(jobTypeId)
        parcel.writeString(deptTypeId)
        parcel.writeString(deptTypeName)
        parcel.writeString(jobTypeName)
        parcel.writeString(dutyTypeName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BzDutyInfo> {
        override fun createFromParcel(parcel: Parcel): BzDutyInfo {
            return BzDutyInfo(parcel)
        }

        override fun newArray(size: Int): Array<BzDutyInfo?> {
            return arrayOfNulls(size)
        }
    }
}

data class CreateBy(
		val id: String, //1
		val loginFlag: String, //1
		val roleNames: String,
		val admin: Boolean //true
):BaseBody() {
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
		val id: String, //1
		val loginFlag: String, //1
		val roleNames: String,
		val admin: Boolean //true
):BaseBody() {
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