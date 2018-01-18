package com.superfactory.sunyatsin.Anko.Sample.calendar

import com.superfactory.library.Bridge.Anko.observable
import java.util.*

class CalendarActivityViewModel {

    val currentTime = observable(Calendar.getInstance())

}