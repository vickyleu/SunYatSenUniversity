package com.superfactory.sunyatsin.Interface.BindingFragment.Note

import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import com.superfactory.library.Bridge.Anko.observable
import com.superfactory.library.Bridge.Model.ToolbarBindingModel
import com.superfactory.library.Utils.TimeUtil
import com.superfactory.sunyatsin.R
import com.superfactory.sunyatsin.Struct.Base.BaseBody
import com.superfactory.sunyatsin.Struct.Message.MessageStruct
import com.superfactory.sunyatsin.Struct.Note.NoteStruct
import com.xiasuhuei321.loadingdialog.view.LoadingDialog

/**
 * Created by vicky on 2018.01.18.
 *
 * @Author vicky
 * @Date 2018年01月18日  11:55:35
 * @ClassName 这里输入你的类名(或用途)
 */
class NoteFragmentViewModel : ToolbarBindingModel() {
    override fun setToolbar(toolbarBindingModel: ToolbarBindingModel) {
        toolbarBindingModel.backgroundColor.value = Color.parseColor("#1688ff")
        toolbarBindingModel.title.value = "APP"
        val ctx = getStaticsContextRef()
        toolbarBindingModel.leftIcon.value = ContextCompat.getDrawable(ctx, R.drawable.date_selection_icon)//todo 缺icon
        toolbarBindingModel.rightIcon.value = ContextCompat.getDrawable(ctx, R.drawable.alarm_icon)
    }

    var pageNo = 0
    var pageSize = 20

    val itemList = observable<List<NoteItemDataViewModel>>(arrayListOf())

    var onItemClicked: ((Int, Any) -> Unit)? = null


    val currDate = observable(TimeUtil.takeNowTime("yyyy年MM月dd日"))
    val isEditToday = observable(false)
    val tips = observable("")

    override fun requestFailed(ld: LoadingDialog, error: Throwable?, witch: Int?) {
        ld.close()
        if (!TextUtils.isEmpty(error?.message)) {
            tips.value = error?.message!!
        }
    }

    override fun requestSuccess(ld: LoadingDialog, model: Any?, witch: Int?) {
        if (model == null) {
            ld.close()
            tips.value = "无法解析数据"
            return
        }
        if (model is NoteStruct) {
            if (model.success) {
                ld.close()
                if (witch != null && witch == 2) {
                    isEditToday.value = true
                } else
                    if (witch != null && witch != 0 && model.body.rows.size >= 0) {
                        val row = model.body.rows[0]
                        val body1 = TitleBody(R.drawable.date_icon, TimeUtil.takeNowTime("yyyy年MM月dd日",
                                "yyyy-MM-dd HH:mm:ss",
                                row.startTime), "1条日志")
                        val list = arrayListOf<NoteSnapShot>()
                        row.dutyName = "12121"
                        row.matterContent = "121212121212111111111111111111111111111111111111111111111111111111133333333333333333332"
                        list.add(NoteSnapShot(row.id, R.drawable.projection_icon__,
                                row.dutyName, row.matterContent,
                                "${TimeUtil.takeNowTime("HH:mm",
                                        "yyyy-MM-dd HH:mm:ss",
                                        row.startTime)}-${
                                TimeUtil.takeNowTime("HH:mm",
                                        "yyyy-MM-dd HH:mm:ss",
                                        row.endTime)}", row.startTime, row.endTime,
                                row.createDate,
                                TimeUtil.takeNowTime("HH:mm",
                                        "yyyy-MM-dd HH:mm:ss",
                                        row.createDate) ?: "")
                        )
                        val modelIntent = NoteItemDataViewModel(body1, list)
                        ownerNotifier?.invoke(104, modelIntent)
                        return
                    }
                ownerNotifier?.invoke(101, model)
            } else {
                ld.close()
                tips.value = model.msg ?: "未知错误"
            }
        } else if (model is MessageStruct) {
            if (model.success) {
                ld.close()
                ownerNotifier?.invoke(102, model)
            } else {
                ld.close()
                tips.value = model.msg ?: "未知错误"
            }
        }
    }


    fun updateItemList(noteStruct: NoteStruct) {
//        (itemList.value as ArrayList<*>).clear()
//        itemList.notifyChange(NoteFragmentViewModel::itemList)
        val rows = noteStruct.body.rows

        val noteLis: ArrayList<NoteItemDataViewModel> = arrayListOf<NoteItemDataViewModel>()
        for (i in 0 until rows.size) {
            val row = rows[i]
            val body1 = TitleBody(R.drawable.date_icon, TimeUtil.takeNowTime("yyyy年MM月dd日",
                    "yyyy-MM-dd HH:mm:ss",
                    row.startTime), "1条日志")
            val list = arrayListOf<NoteSnapShot>()

            row.dutyName = "12121"
            row.matterContent = "121212121212111111111111111111111111111111111111111111111111111111133333333333333333332"


            list.add(NoteSnapShot(row.id, R.drawable.projection_icon__,
                    row.dutyName, row.matterContent,
                    "${TimeUtil.takeNowTime("HH:mm",
                            "yyyy-MM-dd HH:mm:ss",
                            row.startTime)}-${
                    TimeUtil.takeNowTime("HH:mm",
                            "yyyy-MM-dd HH:mm:ss",
                            row.endTime)}", row.startTime, row.endTime,
                    row.createDate,
                    TimeUtil.takeNowTime("HH:mm",
                            "yyyy-MM-dd HH:mm:ss",
                            row.createDate) ?: "")
            )
            noteLis.add(NoteItemDataViewModel(body1, list))
        }
        itemList.value = noteLis
//        notifyChange(this::itemList)
//        itemList.notifyChange()
    }
}

data class NoteItemDataViewModel(val titleBody: TitleBody, val noteSnapShot: List<NoteSnapShot>?) : BaseBody() {
    constructor(parcel: Parcel) : this(
            parcel.readParcelable(TitleBody::class.java.classLoader),
            parcel.createTypedArrayList(NoteSnapShot)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(titleBody, flags)
        parcel.writeTypedList(noteSnapShot)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NoteItemDataViewModel> {
        override fun createFromParcel(parcel: Parcel): NoteItemDataViewModel {
            return NoteItemDataViewModel(parcel)
        }

        override fun newArray(size: Int): Array<NoteItemDataViewModel?> {
            return arrayOfNulls(size)
        }
    }
}


data class TitleBody(val leftImage: Int, var startData: String?, var desc: String, val rightImage: Int? = R.drawable.right_arrow_icon) : BaseBody() {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(leftImage)
        parcel.writeString(startData)
        parcel.writeString(desc)
        parcel.writeValue(rightImage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TitleBody> {
        override fun createFromParcel(parcel: Parcel): TitleBody {
            return TitleBody(parcel)
        }

        override fun newArray(size: Int): Array<TitleBody?> {
            return arrayOfNulls(size)
        }
    }
}

data class NoteSnapShot(val id: String, val image: Int, var title: String = "无标题", var content: String = "无内容", val dateRange: String, val startData: String, val endData: String, val createOrigin: String, val createDate: String) : BaseBody() {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readInt(),
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
        parcel.writeInt(image)
        parcel.writeString(title)
        parcel.writeString(content)
        parcel.writeString(dateRange)
        parcel.writeString(startData)
        parcel.writeString(endData)
        parcel.writeString(createOrigin)
        parcel.writeString(createDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NoteSnapShot> {
        override fun createFromParcel(parcel: Parcel): NoteSnapShot {
            return NoteSnapShot(parcel)
        }

        override fun newArray(size: Int): Array<NoteSnapShot?> {
            return arrayOfNulls(size)
        }
    }
}