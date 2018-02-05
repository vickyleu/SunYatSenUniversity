package com.superfactory.sunyatsin.Interface.BindingActivity.NoteDetailOrAddActivity

import android.content.Intent
import android.graphics.Color
import android.support.v4.content.ContextCompat
import com.superfactory.library.Bridge.Anko.observable
import com.superfactory.library.Bridge.Anko.observableNullable
import com.superfactory.library.Bridge.Model.ToolbarBindingModel
import com.superfactory.library.Utils.TimeUtil
import com.superfactory.sunyatsin.Interface.BindingFragment.Note.NoteSnapShot
import com.superfactory.sunyatsin.R

/**
 * Created by vicky on 2018/2/4.
 */
class NoteDetailOrAddViewModel(val intent: Intent) : ToolbarBindingModel() {
    override fun setToolbar(toolbarBindingModel: ToolbarBindingModel) {
        toolbarBindingModel.title.value = "新建日志"
        toolbarBindingModel.rightTextColor.value = Color.WHITE
        toolbarBindingModel.rightText.value = "提交"
        toolbarBindingModel.backgroundColor.value = Color.parseColor("#1688ff")
        val ctx = getStaticsContextRef()
        toolbarBindingModel.leftIcon.value = ContextCompat.getDrawable(ctx, R.drawable.back_stack_icon)
    }

    val tips=observable("")
    val dutyImage = R.drawable.duty_big_icon
    val rightImage = R.drawable.right_arrow_icon
    val dutyType = observable("职责类型")
    val dutyText = observable("")
    val dutyID = observable("")

    val mattersImage = R.drawable.matters_icon
    val mattersType = observable("事项类型")
    val mattersText = observable("")

    var onItemClickListener: ((Int, Any?) -> Unit)? = null

    val dateSelectList = observableNullable<List<DateSelectItem>>(arrayListOf<DateSelectItem>())

    val createDate = observable("")
    val startTime = observable("")
    val endTime = observable("")

    val canCommitData=observable(false)
    init {
        var snap: NoteSnapShot? = null
        try {
            snap = intent.extras.getParcelable<NoteSnapShot>("data")
        } catch (e: Throwable) {
        }
        if (snap!=null){
            rightText.setStableValue("")
            title.value="日志详情"
            eraseRight.value=true
        }
//        snap?.id

        createDate.value = TimeUtil.takeNowTime("yyyy-MM-dd",
                "yyyy-MM-dd HH:mm:ss", snap?.createOrigin ?: "") ?: ""
        startTime.value = TimeUtil.takeNowTime("HH:mm",
                "yyyy-MM-dd HH:mm:ss", snap?.startData ?: "") ?: ""
        endTime.value = TimeUtil.takeNowTime("HH:mm",
                "yyyy-MM-dd HH:mm:ss", snap?.endData ?: "") ?: ""

        (dateSelectList.value as ArrayList<DateSelectItem>)
                .add(DateSelectItem(R.drawable.note_start_time_icon, "日志日期", 0,
                        createDate.value))
        (dateSelectList.value as ArrayList<DateSelectItem>)
                .add(DateSelectItem(R.drawable.begin_date_icon, "开始时间", 1,
                        startTime.value,
                        R.drawable.right_arrow_icon))
        (dateSelectList.value as ArrayList<DateSelectItem>)
                .add(DateSelectItem(R.drawable.end_date_icon, "结束时间", 2,
                        endTime.value,
                        R.drawable.right_arrow_icon))

    }
}

data class DateSelectItem(val image: Int, val name: String, val type: Int, var content: String, val right: Int? = 0)