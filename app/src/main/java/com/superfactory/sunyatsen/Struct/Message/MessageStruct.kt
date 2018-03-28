package com.superfactory.sunyatsen.Struct.Message

import android.os.Parcel
import android.os.Parcelable
import com.superfactory.library.Context.Extensions.writeStringNotNull
import com.superfactory.sunyatsen.Struct.Base.BaseBody
import com.superfactory.sunyatsen.Struct.Base.BaseStruct

/**
 * Created by vicky on 2018.02.04.
 *
 * @Author vicky
 * @Date 2018年02月04日  15:19:10
 * @ClassName 这里输入你的类名(或用途)
 */
data class MessageStruct(val body: MessageBody) : BaseStruct() {
    constructor(parcel: Parcel) : this(parcel.readParcelable<MessageBody>(MessageBody::class.java.classLoader)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(body, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MessageStruct> {
        override fun createFromParcel(parcel: Parcel): MessageStruct {
            return MessageStruct(parcel)
        }

        override fun newArray(size: Int): Array<MessageStruct?> {
            return arrayOfNulls(size)
        }
    }
}


data class MessageBody(
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

    companion object CREATOR : Parcelable.Creator<MessageBody> {
        override fun createFromParcel(parcel: Parcel): MessageBody {
            return MessageBody(parcel)
        }

        override fun newArray(size: Int): Array<MessageBody?> {
            return arrayOfNulls(size)
        }
    }
}

data class Row(
        val id: String,
        val createDate: String,
        val regId: String="",
        val typpe: String,
        val content: String,
        val title: String,
        val user: User,
        val status: String,
        val naireId: String
) : BaseBody() {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(User::class.java.classLoader),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStringNotNull(id)
        parcel.writeStringNotNull(createDate)
        parcel.writeStringNotNull(regId)
        parcel.writeStringNotNull(typpe)
        parcel.writeStringNotNull(content)
        parcel.writeStringNotNull(title)
        parcel.writeParcelable(user, flags)
        parcel.writeStringNotNull(status)
        parcel.writeStringNotNull(naireId)
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

data class User(
        val id: String,
        val name: String,
        val loginFlag: String,
        val roleNames: String,
        val admin: Boolean
) : BaseBody() {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStringNotNull(id)
        parcel.writeStringNotNull(name)
        parcel.writeStringNotNull(loginFlag)
        parcel.writeStringNotNull(roleNames)
        parcel.writeByte(if (admin) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}