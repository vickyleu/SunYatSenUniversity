package com.superfactory.sunyatsin.Interface.BindingFragment.Note

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import com.superfactory.library.Bridge.Anko.observable
import com.superfactory.library.Bridge.Model.ToolbarBindingModel
import com.superfactory.sunyatsin.R
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
        toolbarBindingModel.leftIcon.value = ContextCompat.getDrawable(ctx, R.drawable.alarm_icon)
        toolbarBindingModel.rightIcon.value = ContextCompat.getDrawable(ctx, R.drawable.alarm_icon)
    }

    val itemList = observable<ArrayList<NoteItemDataViewModel>>(arrayListOf())


    val isEditToday=observable(false)
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
        }
    }

    data class NoteItemDataViewModel(val titleBody: TitleBody, val noteSnapShot: List<NoteSnapShot>?)

    data class TitleBody(val image: Int, var startData: String, var desc: String)

    data class NoteSnapShot(val image: Int, val title: String, var content: String, val startData: String, val endDate: String, val createDate: String)

    fun updateItemList(noteStruct: NoteStruct) {
        itemList.value.clear()
        val rows = noteStruct.body.rows

//        for (i in 0 until rows.size) {
//            val row = rows[i]
//            row.
//            val body1 = TitleBody(R.drawable.note_start_time_icon, "", "")
//            val list = arrayListOf<NoteSnapShot>()
//            list.add(NoteSnapShot(R.drawable.projection_icon, row., "", row.startTime, row.endTime, row.createDate))
//            itemList.value.add(NoteItemDataViewModel(body1, list))
//        }
    }
}