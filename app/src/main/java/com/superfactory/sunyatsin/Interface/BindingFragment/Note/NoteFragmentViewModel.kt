package com.superfactory.sunyatsin.Interface.BindingFragment.Note

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import com.superfactory.library.Bridge.Anko.observable
import com.superfactory.library.Bridge.Model.ToolbarBindingModel
import com.superfactory.library.Utils.TimeUtil
import com.superfactory.sunyatsin.R
import com.superfactory.sunyatsin.Struct.BaseStructImpl
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
        toolbarBindingModel.leftIcon.value = ContextCompat.getDrawable(ctx, R.drawable.alarm_icon)//todo 缺icon
        toolbarBindingModel.rightIcon.value = ContextCompat.getDrawable(ctx, R.drawable.alarm_icon)
    }
    var pageNo=0
    var pageSize=20

    val itemList = observable<ArrayList<NoteItemDataViewModel>>(arrayListOf())

    var onItemClicked: ((Int, Any) -> Unit)? = null


    val currDate = observable(TimeUtil.takeNowTime("yyyy年MM月dd日"))
    val isEditToday = observable(false)
    val tips = observable("")

    override fun requestFailed(ld: LoadingDialog, error: Throwable?) {
        ld.close()
        if (!TextUtils.isEmpty(error?.message)) {
            tips.value = error?.message!!
        }
    }

    override fun requestSuccess(ld: LoadingDialog, model: Any?) {
        if (model == null) {
            ld.close()
            tips.value = "无法解析数据"
            return
        }
        if (model is NoteStruct) {
            if (model.success) {
                ld.close()
                ownerNotifier?.invoke(101, model)
            } else {
                ld.close()
                tips.value = model.msg ?: "未知错误"
            }
        }else if (model is MessageStruct){
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
        itemList.value.clear()
        val rows = noteStruct.body.rows

        for (i in 0 until rows.size) {
            val row = rows[i]

            R.drawable.note_start_time_icon//todo

            val body1 = TitleBody(R.drawable.date_icon, TimeUtil.takeNowTime("yyyy年MM月dd",
                    "yyyy-MM-dd HH:mm:ss",
                    row.startTime), "1条日志")
            val list = arrayListOf<NoteSnapShot>()

            row.dutyName = "12121"
            row.matterContent = "121212121212111111111111111111111111111111111111111111111111111111133333333333333333332"


            list.add(NoteSnapShot(R.drawable.projection_icon__,
                    row.dutyName, row.matterContent,
                    "${TimeUtil.takeNowTime("HH:mm",
                            "yyyy-MM-dd HH:mm:ss",
                            row.startTime)}-${
                    TimeUtil.takeNowTime("HH:mm",
                            "yyyy-MM-dd HH:mm:ss",
                            row.endTime)}",
                    TimeUtil.takeNowTime("HH:mm",
                            "yyyy-MM-dd HH:mm:ss",
                            row.createDate) ?: "")
            )
            itemList.value.add(NoteItemDataViewModel(body1, list))
        }
    }
}

data class NoteItemDataViewModel(val titleBody: TitleBody, val noteSnapShot: List<NoteSnapShot>?)


data class TitleBody(val leftImage: Int, var startData: String?, var desc: String, val rightImage: Int? = R.drawable.right_arrow_icon)

data class NoteSnapShot(val image: Int, var title: String = "无标题", var content: String = "无内容", val dateRange: String, val createDate: String)