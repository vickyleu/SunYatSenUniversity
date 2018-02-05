package com.superfactory.sunyatsin.Interface.BindingActivity.NoteByDayActivity

import android.content.Intent
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import com.superfactory.library.Bridge.Anko.observable
import com.superfactory.library.Bridge.Anko.observableNullable
import com.superfactory.library.Bridge.Model.ToolbarBindingModel
import com.superfactory.library.Utils.TimeUtil
import com.superfactory.sunyatsin.Interface.BindingFragment.Note.NoteItemDataViewModel
import com.superfactory.sunyatsin.Interface.BindingFragment.Note.NoteSnapShot
import com.superfactory.sunyatsin.Interface.BindingFragment.Note.TitleBody
import com.superfactory.sunyatsin.R
import com.superfactory.sunyatsin.Struct.Note.NoteStruct
import com.xiasuhuei321.loadingdialog.view.LoadingDialog

/**
 * Created by vicky on 2018/2/4.
 */
class NoteByDayActivityViewModel(val intent: Intent) : ToolbarBindingModel() {
    override fun setToolbar(toolbarBindingModel: ToolbarBindingModel) {
        toolbarBindingModel.backgroundColor.value = Color.parseColor("#1688ff")
        val ctx = getStaticsContextRef()
        toolbarBindingModel.rightIcon.value = ContextCompat.getDrawable(ctx, R.drawable.date_selection_icon)
        toolbarBindingModel.leftIcon.value = ContextCompat.getDrawable(ctx, R.drawable.back_stack_icon)
    }

    val itemList = observableNullable<ArrayList<NoteSnapShot>>(arrayListOf<NoteSnapShot>())

    init {
        val struct = intent.extras.getParcelable<NoteItemDataViewModel>("data")
        setData(struct)
    }

    val tips = observable("")
    override fun requestSuccess(ld: LoadingDialog, model: Any?, witch: Int?) {
        if (model == null) {
            ld.close()
            tips.value = "无法解析数据"
            return
        }
        if (model is NoteStruct) {
            if (model.success) {
                ld.close()
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
                    setData(modelIntent)
                    return
                }
            } else {
                ld.close()
                tips.value = model.msg ?: "未知错误"
            }
        }
    }

    override fun requestFailed(ld: LoadingDialog, error: Throwable?, witch: Int?) {
        ld.close()
        if (!TextUtils.isEmpty(error?.message)) {
            tips.value = error?.message!!
        }
    }


    private fun setData(struct: NoteItemDataViewModel) {
        title.value = "${struct.titleBody.startData ?: ""}工作日志"
        val snap = struct.noteSnapShot
        itemList.value = (snap as ArrayList<NoteSnapShot>)
    }
}