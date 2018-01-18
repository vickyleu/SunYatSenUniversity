package com.superfactory.sunyatsin.Anko.Sample.calendar

import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.bindings.toDatePicker
import com.superfactory.library.Bridge.Anko.bindings.toFieldFromDate
import com.superfactory.library.Bridge.Anko.bindings.twoWay
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.datePicker
import org.jetbrains.anko.textView
import org.jetbrains.anko.verticalLayout
import java.util.*

class CalendarActivityComponent(viewModel: CalendarActivityViewModel)
    : BindingComponent<CalendarActivity, CalendarActivityViewModel>(viewModel) {

    override fun createViewWithBindings(ui: AnkoContext<CalendarActivity>) = with(ui) {
        verticalLayout {
            textView {
                bindSelf { it.currentTime }.toView(this) { _, value ->
                    val date = value?.let {
                        "${value.get(Calendar.MONTH)}/${value.get(Calendar.DAY_OF_MONTH)}/${value.get(Calendar.YEAR)}"
                    }
                    text = "Current Date is $date"
                }
            }

            datePicker {
                bindSelf { it.currentTime }.toDatePicker(this)
                        .twoWay().toFieldFromDate()
            }
        }
    }
}