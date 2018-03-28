package com.superfactory.sunyatsen.Struct.MattersStruct

import android.os.Parcel
import android.os.Parcelable
import com.superfactory.sunyatsen.Struct.Base.BaseBody
import com.superfactory.sunyatsen.Struct.Base.BaseStruct
import com.superfactory.sunyatsen.Struct.CreateBy

/**
 * Created by vicky on 2018.02.05.
 *
 * @Author vicky
 * @Date 2018年02月05日  16:04:03
 * @ClassName 这里输入你的类名(或用途)
 */
data class MattersStruct(val body: MattersBody) : BaseStruct() {
    constructor(parcel: Parcel) : this(parcel.readParcelable<MattersBody>(MattersBody::class.java.classLoader)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(body, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MattersStruct> {
        override fun createFromParcel(parcel: Parcel): MattersStruct {
            return MattersStruct(parcel)
        }

        override fun newArray(size: Int): Array<MattersStruct?> {
            return arrayOfNulls(size)
        }
    }
}


data class MattersBody(
        val bzMatterInfos: List<BzMatterInfo>
) : BaseBody() {
    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(BzMatterInfo)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(bzMatterInfos)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MattersBody> {
        override fun createFromParcel(parcel: Parcel): MattersBody {
            return MattersBody(parcel)
        }

        override fun newArray(size: Int): Array<MattersBody?> {
            return arrayOfNulls(size)
        }
    }
}

data class BzMatterInfo(
        val id: String,
        val createBy: CreateBy,
        val createDate: String,
        val updateBy: UpdateBy,
        val updateDate: String,
        val matterCode: String,
        val matterContent: String,
        val dutyId: String,
        val deptTypeId: String,
        val deptTypeName: String,
        val jobTypeId: String,
        val jobTypeName: String,
        val dutyTypeName: String,
        val dutyName: String
) : BaseBody() {
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
        parcel.writeString(matterCode)
        parcel.writeString(matterContent)
        parcel.writeString(dutyId)
        parcel.writeString(deptTypeId)
        parcel.writeString(deptTypeName)
        parcel.writeString(jobTypeId)
        parcel.writeString(jobTypeName)
        parcel.writeString(dutyTypeName)
        parcel.writeString(dutyName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BzMatterInfo> {
        override fun createFromParcel(parcel: Parcel): BzMatterInfo {
            return BzMatterInfo(parcel)
        }

        override fun newArray(size: Int): Array<BzMatterInfo?> {
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