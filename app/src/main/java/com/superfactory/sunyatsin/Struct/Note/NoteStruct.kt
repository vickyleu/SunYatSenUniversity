package com.superfactory.sunyatsin.Struct.Note

import android.os.Parcel
import android.os.Parcelable
import com.superfactory.library.Context.Extensions.writeStringNotNull
import com.superfactory.sunyatsin.Struct.Base.BaseBody
import com.superfactory.sunyatsin.Struct.Base.BaseStruct

/**
 * Created by vicky on 2018.02.02.
 *
 * @Author vicky
 * @Date 2018年02月02日  16:05:36
 * @ClassName 这里输入你的类名(或用途)
 */
class NoteStruct(val body: NoteStructBody) : BaseStruct() {
    constructor(parcel: Parcel) : this(parcel.readParcelable<NoteStructBody>(NoteStructBody::class.java.classLoader)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(body, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NoteStruct> {
        override fun createFromParcel(parcel: Parcel): NoteStruct {
            return NoteStruct(parcel)
        }

        override fun newArray(size: Int): Array<NoteStruct?> {
            return arrayOfNulls(size)
        }
    }

}


data class NoteStructBody(
        val rows: List<Row>,
        val total: Int
) : BaseBody() {
    constructor(parcel: Parcel) : this(
            parcel.createTypedArrayList(Row),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(rows)
        parcel.writeInt(total)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NoteStructBody> {
        override fun createFromParcel(parcel: Parcel): NoteStructBody {
            return NoteStructBody(parcel)
        }

        override fun newArray(size: Int): Array<NoteStructBody?> {
            return arrayOfNulls(size)
        }
    }
}

data class Row(
        val id: String,
        val createBy: CreateBy,
        val createDate: String,
        val updateDate: String,
        val jobType: String,
        var matterContent: String,//事项内容
        var dutyName: String,//事项名称
        val itemType: String,
        val startTime: String,
        val endTime: String
) : BaseBody() {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readParcelable(CreateBy::class.java.classLoader),
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
        parcel.writeStringNotNull(id)
        parcel.writeParcelable(createBy, flags)
        parcel.writeStringNotNull(createDate)
        parcel.writeStringNotNull(updateDate)
        parcel.writeStringNotNull(jobType)
        parcel.writeStringNotNull(matterContent)
        parcel.writeStringNotNull(dutyName)
        parcel.writeStringNotNull(itemType)
        parcel.writeStringNotNull(startTime)
        parcel.writeStringNotNull(endTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Row> {
        override fun createFromParcel(parcel: Parcel): Row {
            return Row(parcel)
        }

        override fun newArray(size: Int): Array<Row?> {
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
        parcel.writeStringNotNull(id)
        parcel.writeStringNotNull(loginFlag)
        parcel.writeStringNotNull(roleNames)
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


