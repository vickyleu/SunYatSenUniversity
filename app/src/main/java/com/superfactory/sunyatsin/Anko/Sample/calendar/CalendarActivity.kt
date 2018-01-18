package com.superfactory.sunyatsin.Anko.Sample.calendar

import com.superfactory.library.Context.BaseActivity

class CalendarActivity : BaseActivity<CalendarActivityViewModel, CalendarActivity>() {

    override fun newViewModel() = CalendarActivityViewModel()

    override fun newComponent(v: CalendarActivityViewModel) = CalendarActivityComponent(v)
}