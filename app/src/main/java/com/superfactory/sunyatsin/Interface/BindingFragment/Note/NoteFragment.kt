package com.superfactory.sunyatsin.Interface.BindingFragment.Note

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import cn.qqtheme.framework.picker.DatePicker
import cn.qqtheme.framework.util.ConvertUtils
import com.superfactory.library.Bridge.Anko.Adapt.BaseToolBar
import com.superfactory.library.Context.BaseToolbarFragment
import com.superfactory.library.Debuger
import com.superfactory.library.Utils.TimeUtil
import com.superfactory.sunyatsin.Interface.BindingActivity.MessageActivity.MessageActivity
import com.superfactory.sunyatsin.Interface.BindingActivity.QuestionnaireActivity.QuestionnaireActivity
import com.superfactory.sunyatsin.R
import com.superfactory.sunyatsin.Struct.Note.NoteStruct
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.startActivityForResult

/**
 * Created by vicky on 2018.01.18.
 *
 * @Author vicky
 * @Date 2018年01月18日  11:54:32
 * @ClassName 这里输入你的类名(或用途)
 */
class NoteFragment : BaseToolbarFragment<NoteFragmentViewModel, NoteFragment>() {

    override fun newViewModel() = NoteFragmentViewModel()

    override fun newComponent(v: NoteFragmentViewModel) = NoteFragmentComponent(v).apply {
        viewModelSafe.ownerNotifier = { option, any ->
            if (any == null) {
                Debuger.printMsg(this, "数据不能为空啊")
            } else {
                if (option == 101) {
                    viewModelSafe.updateItemList(any as NoteStruct)
                } else if (option == 102) {
                    startActivity<MessageActivity>(Pair("data",any))
                } else
                    startActivityForResult<QuestionnaireActivity>(1001, Pair("data", any))
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel?.currDate?.value = TimeUtil.takeNowTime("yyyy年MM月dd日")
    }


    override fun performToolbarClickEvent(view: View, event: BaseToolBar.Companion.ToolbarEvent) {
        when (event) {
            BaseToolBar.Companion.ToolbarEvent.RIGHT -> {
            }
            BaseToolBar.Companion.ToolbarEvent.LEFT -> {
                datePicker(view)
            }
            else -> {
            }
        }

    }

    private fun datePicker(view: View) {
        val picker = DatePicker(activity)
        picker.setCanceledOnTouchOutside(false)
        picker.setUseWeight(false)
        //                picker.setLabel("","","")
        picker.setTitleText("日期筛选")
        picker.setTopPadding(ConvertUtils.toPx(context, 10f))
        picker.setRangeEnd(2111, 1, 11)
        picker.setRangeStart(2016, 8, 29)
        picker.setSelectedItem(2050, 10, 14)
        picker.setResetWhileWheel(false)
        picker.setCancelTextColor(Color.parseColor("#222222"))
        picker.setAnimationStyle(R.style.Animation_CustomPopup)

        picker.setOnDatePickListener(DatePicker.OnYearMonthDayPickListener { year, month, day ->
            Snackbar.make(view, "$year-$month-$day", 1).show()
        })
        picker.setOnWheelListener(object : DatePicker.OnWheelListener {
            override fun onYearWheeled(index: Int, year: String) {
                //                        picker.setTitleText(year + "-" + picker.selectedMonth + "-" + picker.selectedDay)
            }

            override fun onMonthWheeled(index: Int, month: String) {
                //                        picker.setTitleText(picker.selectedYear + "-" + month + "-" + picker.selectedDay)
            }

            override fun onDayWheeled(index: Int, day: String) {
                //                        picker.setTitleText(picker.selectedYear + "-" + picker.selectedMonth + "-" + day)
            }
        })
        picker.show()
    }

    override fun onLoadedModel(viewModel: NoteFragmentViewModel) {
        viewModel.onItemClicked = { i, model ->
            when (i) {
                -1 -> {
                    Debuger.printMsg(this, "立即填写")
                }
                else -> {

                }
            }
        }
    }

}