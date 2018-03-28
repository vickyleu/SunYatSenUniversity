package com.superfactory.sunyatsen.Struct.QuestionaireStruct

import android.os.Parcel
import android.os.Parcelable
import com.superfactory.library.Context.Extensions.writeStringNotNull
import com.superfactory.sunyatsen.Struct.Base.BaseBody
import com.superfactory.sunyatsen.Struct.Base.BaseStruct
import com.superfactory.sunyatsen.Struct.CreateBy

/**
 * Created by vicky on 2018.02.01.
 *
 * @Author vicky
 * @Date 2018年02月01日  14:39:33
 * @ClassName 这里输入你的类名(或用途)
 */
class QuestionnaireStruct(val body: QuestionnaireBody) : BaseStruct() {
    constructor(parcel: Parcel) : this(parcel.readParcelable<QuestionnaireBody>(QuestionnaireBody::class.java.classLoader)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(body, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<QuestionnaireStruct> {
        override fun createFromParcel(parcel: Parcel): QuestionnaireStruct {
            return QuestionnaireStruct(parcel)
        }

        override fun newArray(size: Int): Array<QuestionnaireStruct?> {
            return arrayOfNulls(size)
        }
    }


}


data class QuestionnaireBody(
        val total: Int,
        val rows: List<Row>
) : BaseBody() {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.createTypedArrayList(Row)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(total)
        parcel.writeTypedList(rows)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<QuestionnaireBody> {
        override fun createFromParcel(parcel: Parcel): QuestionnaireBody {
            return QuestionnaireBody(parcel)
        }

        override fun newArray(size: Int): Array<QuestionnaireBody?> {
            return arrayOfNulls(size)
        }
    }
}

data class Row(
        val id: String,
        val createBy: CreateBy,
        val createDate: String,
        val updateDate: String,
        val title: String,
        val direction: String,
        val operate: Int,
        val status: Int,
        val wirteCount: Int,
        val deptTypeId: String,
        val officId: String,
        val jobType: String
) : BaseBody() {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readParcelable(CreateBy::class.java.classLoader),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStringNotNull(id)
        parcel.writeParcelable(createBy, flags)
        parcel.writeStringNotNull(createDate)
        parcel.writeStringNotNull(updateDate)
        parcel.writeStringNotNull(title)
        parcel.writeStringNotNull(direction)
        parcel.writeInt(operate)
        parcel.writeInt(status)
        parcel.writeInt(wirteCount)
        parcel.writeStringNotNull(deptTypeId)
        parcel.writeStringNotNull(officId)
        parcel.writeStringNotNull(jobType)
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

