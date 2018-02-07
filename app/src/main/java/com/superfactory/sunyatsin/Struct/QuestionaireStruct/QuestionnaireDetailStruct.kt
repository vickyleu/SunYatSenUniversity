package com.superfactory.sunyatsin.Struct.QuestionaireStruct

import android.os.Parcel
import android.os.Parcelable
import com.superfactory.library.Context.Extensions.writeStringNotNull
import com.superfactory.sunyatsin.Struct.Base.BaseStruct
import com.superfactory.sunyatsin.Struct.CreateBy

/**
 * Created by vicky on 2018.02.01.
 *
 * @Author vicky
 * @Date 2018年02月01日  15:57:22
 * @ClassName 这里输入你的类名(或用途)
 */
class QuestionnaireDetailStruct(val body: QuestionnaireDetailBody) : BaseStruct() {
    constructor(parcel: Parcel) : this(parcel.readParcelable<QuestionnaireDetailBody>(QuestionnaireDetailBody::class.java.classLoader)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(body, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<QuestionnaireDetailStruct> {
        override fun createFromParcel(parcel: Parcel): QuestionnaireDetailStruct {
            return QuestionnaireDetailStruct(parcel)
        }

        override fun newArray(size: Int): Array<QuestionnaireDetailStruct?> {
            return arrayOfNulls(size)
        }
    }
}


data class QuestionnaireDetailBody(
        val qNaire: QNaire,
        val questionList: List<Question>
) : BaseStruct() {
    constructor(parcel: Parcel) : this(
            parcel.readParcelable(QNaire::class.java.classLoader),
            parcel.createTypedArrayList(Question)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(qNaire, flags)
        parcel.writeTypedList(questionList)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<QuestionnaireDetailBody> {
        override fun createFromParcel(parcel: Parcel): QuestionnaireDetailBody {
            return QuestionnaireDetailBody(parcel)
        }

        override fun newArray(size: Int): Array<QuestionnaireDetailBody?> {
            return arrayOfNulls(size)
        }
    }
}

data class QNaire(
        val id: String,//
        val createBy: CreateBy,
        val createDate: String,
        val updateDate: String,
        val title: String,
        val direction: String,
        val operate: Int,
        val deptTypeId: String,
        val officId: String,
        val jobType: String
) : BaseStruct() {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readParcelable(CreateBy::class.java.classLoader),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
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
        parcel.writeStringNotNull(deptTypeId)
        parcel.writeStringNotNull(officId)
        parcel.writeStringNotNull(jobType)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<QNaire> {
        override fun createFromParcel(parcel: Parcel): QNaire {
            return QNaire(parcel)
        }

        override fun newArray(size: Int): Array<QNaire?> {
            return arrayOfNulls(size)
        }
    }
}



data class Question(
        val id: String,
        val parentId: String,
        val title: String,
        val type: String="0",
        val sort: Int,
        val normId: String,
        val qOptionsList: List<QOptions>
) : BaseStruct() {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.createTypedArrayList(QOptions)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStringNotNull(id)
        parcel.writeStringNotNull(parentId)
        parcel.writeStringNotNull(title)
        parcel.writeStringNotNull(type)
        parcel.writeInt(sort)
        parcel.writeStringNotNull(normId)
        parcel.writeTypedList(qOptionsList)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Question> {
        override fun createFromParcel(parcel: Parcel): Question {
            return Question(parcel)
        }

        override fun newArray(size: Int): Array<Question?> {
            return arrayOfNulls(size)
        }
    }
}

data class QOptions(
        val id: String,
        val parentParentId: String,
        val optionName: String,
        val content: String,
        val score: String,
        val sort: Int,
        val anwserNum: Int
) : BaseStruct() {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStringNotNull(id)
        parcel.writeStringNotNull(parentParentId)
        parcel.writeStringNotNull(optionName)
        parcel.writeStringNotNull(content)
        parcel.writeStringNotNull(score)
        parcel.writeInt(sort)
        parcel.writeInt(anwserNum)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<QOptions> {
        override fun createFromParcel(parcel: Parcel): QOptions {
            return QOptions(parcel)
        }

        override fun newArray(size: Int): Array<QOptions?> {
            return arrayOfNulls(size)
        }
    }
}