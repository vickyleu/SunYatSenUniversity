package com.superfactory.sunyatsin.Interface.BindingActivity.NoteDetailOrAddActivity

import android.graphics.Color
import android.text.TextUtils
import cn.qqtheme.framework.picker.DatePicker
import cn.qqtheme.framework.picker.DateTimePicker.HOUR_24
import cn.qqtheme.framework.picker.TimePicker
import cn.qqtheme.framework.util.ConvertUtils
import com.superfactory.library.Context.BaseToolBarActivity
import com.superfactory.sunyatsin.Interface.BindingPrompt.DutyPrompt.DutyPrompt
import com.superfactory.sunyatsin.Interface.BindingPrompt.MattersPrompt.MattersPrompt
import com.superfactory.sunyatsin.R
import com.superfactory.sunyatsin.Struct.Duty.BzDutyInfo

/**
 * Created by vicky on 2018/2/4.
 */
class NoteDetailOrAddActivity : BaseToolBarActivity<NoteDetailOrAddViewModel, NoteDetailOrAddActivity>() {
    override fun newViewModel() = NoteDetailOrAddViewModel(intent)

    override fun newComponent(v: NoteDetailOrAddViewModel) = NoteDetailOrAddComponent(v).apply {
        viewModel?.ownerNotifier = { i, any ->

        }
    }

    override fun onLoadedModel(viewModel: NoteDetailOrAddViewModel) {
        viewModel.onItemClickListener = { i, any ->
            when (i) {
                0 -> {
                    DutyPrompt(this, { pos, v ->
                        if (v != null && v is BzDutyInfo) {
                            viewModel.dutyText.value = v.dutyName
                            viewModel.dutyID.value = v.id
                            viewModel.canCommitData.value = !commitCondition(viewModel)
                        }else{
                            viewModel.dutyID.value=""
                        }
                    }).show()
                }
                1 -> {
                    if (!TextUtils.isEmpty(viewModel.dutyID.value)) {
                        MattersPrompt(this, viewModel.dutyID.value, { pos, v ->
                            if (v != null && v is BzDutyInfo) {
                                viewModel.mattersText.value = v.dutyName
                                viewModel.canCommitData.value = !commitCondition(viewModel)
                            }
                        }).show()
                    } else {
                        viewModel.tips.value = "请选择类型与事项"
                    }
                }
                2 -> {
                    if (!TextUtils.isEmpty(viewModel.dutyID.value) && !TextUtils.isEmpty(viewModel.mattersText.value)) {
                        val picker = DatePicker(this)
                        picker.setCanceledOnTouchOutside(false)
                        picker.setUseWeight(false)
                        picker.setTitleText("日期选择")
                        picker.setTopPadding(ConvertUtils.toPx(this, 10f))
                        picker.setRangeEnd(2111, 1, 11)
                        picker.setRangeStart(2016, 8, 29)
                        picker.setSelectedItem(2050, 10, 14)
                        picker.setResetWhileWheel(false)
                        picker.setCancelTextColor(Color.parseColor("#222222"))
                        picker.setAnimationStyle(R.style.Animation_CustomPopup)
                        picker.setOnDatePickListener(DatePicker.OnYearMonthDayPickListener { year, month, day ->
                            viewModel.createDate.value = "$year-$month-$day"
                            viewModel.canCommitData.value = !commitCondition(viewModel)
                        })
                        picker.show()
                    } else {
                        viewModel.tips.value = "请选择类型与事项"
                    }
                }
                3 -> {
                    if (!TextUtils.isEmpty(viewModel.dutyText.value) && !TextUtils.isEmpty(viewModel.mattersText.value)) {
                        val picker = TimePicker(this, HOUR_24)
                        picker.setCanceledOnTouchOutside(false)
                        picker.setUseWeight(false)
                        picker.setTitleText("开始时间")
                        picker.setTopPadding(ConvertUtils.toPx(this, 10f))
                        picker.setResetWhileWheel(false)
                        picker.setCancelTextColor(Color.parseColor("#222222"))
                        picker.setAnimationStyle(R.style.Animation_CustomPopup)
                        picker.setOnTimePickListener({ minute, second ->
                            viewModel.startTime.value = "${minute}:${second}"
                            viewModel.canCommitData.value = !commitCondition(viewModel)
                        })
                        picker.show()
                    } else {
                        viewModel.tips.value = "请选择类型与事项"
                    }

                }
                4 -> {

                    if (!TextUtils.isEmpty(viewModel.dutyText.value) && !TextUtils.isEmpty(viewModel.mattersText.value)) {
                        val picker = TimePicker(this, HOUR_24)
                        picker.setCanceledOnTouchOutside(false)
                        picker.setUseWeight(false)
                        picker.setTitleText("结束时间")
                        picker.setTopPadding(ConvertUtils.toPx(this, 10f))
                        picker.setResetWhileWheel(false)
                        picker.setCancelTextColor(Color.parseColor("#222222"))
                        picker.setAnimationStyle(R.style.Animation_CustomPopup)
                        picker.setOnTimePickListener({ minute, second ->
                            viewModel.endTime.value = "$minute:$second"
                            viewModel.canCommitData.value = !commitCondition(viewModel)
                        })
                        picker.show()
                    } else {
                        viewModel.tips.value = "请选择类型与事项"
                    }

                }

            }
        }
    }

    private fun commitCondition(viewModel: NoteDetailOrAddViewModel): Boolean {
        return TextUtils.isEmpty(viewModel.createDate.value) ||
                TextUtils.isEmpty(viewModel.endTime.value) ||
                TextUtils.isEmpty(viewModel.startTime.value) ||
                TextUtils.isEmpty(viewModel.dutyText.value) ||
                TextUtils.isEmpty(viewModel.mattersText.value)
    }

}
