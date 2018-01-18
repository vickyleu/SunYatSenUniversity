package com.superfactory.sunyatsin.Anko.Sample.home

import android.os.Bundle
import com.superfactory.library.Context.BaseActivity
import com.superfactory.sunyatsin.Anko.Sample.calendar.CalendarActivity
import com.superfactory.sunyatsin.Anko.Sample.input.InputActivity
import com.superfactory.sunyatsin.Interface.BindingActivity.MainActivity.MainActivity
import org.jetbrains.anko.startActivity

/**
 * Description:
 */
class HomeActivity : BaseActivity<HomeActivityViewModel, HomeActivity>() {

    override fun newViewModel() = HomeActivityViewModel()

    override fun newComponent(v: HomeActivityViewModel) = HomeActivityLayout(v)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel?.onItemClicked = { _, (name) ->
            when (name) {
                "RecyclerView" -> startActivity<MainActivity>()
                "Input Mirroring" -> startActivity<InputActivity>()
                "Calendar" -> startActivity<CalendarActivity>()
            }
        }
    }

}