package com.superfactory.sunyatsin.Interface.BindingActivity.NoteByDayActivity

import android.content.Intent
import android.graphics.Color
import android.support.v4.content.ContextCompat
import com.superfactory.library.Bridge.Anko.observableNullable
import com.superfactory.library.Bridge.Model.ToolbarBindingModel
import com.superfactory.sunyatsin.Interface.BindingFragment.Note.NoteItemDataViewModel
import com.superfactory.sunyatsin.Interface.BindingFragment.Note.NoteSnapShot
import com.superfactory.sunyatsin.R

/**
 * Created by vicky on 2018/2/4.
 */
class NoteByDayActivityViewModel(val intent: Intent) : ToolbarBindingModel() {
    override fun setToolbar(toolbarBindingModel: ToolbarBindingModel) {
        toolbarBindingModel.backgroundColor.value = Color.parseColor("#1688ff")
        val ctx = getStaticsContextRef()
        toolbarBindingModel.rightIcon.value = ContextCompat.getDrawable(ctx, R.drawable.alarm_icon)
        toolbarBindingModel.leftIcon.value = ContextCompat.getDrawable(ctx, R.drawable.back_stack_icon)
    }

    val itemList = observableNullable<ArrayList<NoteSnapShot>>(arrayListOf<NoteSnapShot>())

    init {
        val struct = intent.extras.getParcelable<NoteItemDataViewModel>("data")
        title.value = "${struct.titleBody.startData ?: ""}工作日志"
        val snap = struct.noteSnapShot
        itemList.value = (snap as ArrayList<NoteSnapShot>)
    }
}