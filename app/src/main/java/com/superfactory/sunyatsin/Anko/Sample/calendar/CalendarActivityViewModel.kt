package com.superfactory.sunyatsin.Anko.Sample.calendar

import com.superfactory.library.Bridge.Anko.BaseObservable
import com.superfactory.library.Bridge.Anko.observable
import java.util.*

class CalendarActivityViewModel : BaseObservable(){

    val currentTime = observable(Calendar.getInstance())

}