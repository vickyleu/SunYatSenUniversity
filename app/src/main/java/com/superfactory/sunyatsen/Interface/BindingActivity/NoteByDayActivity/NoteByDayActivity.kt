package com.superfactory.sunyatsen.Interface.BindingActivity.NoteByDayActivity

import com.superfactory.library.Context.BaseToolBarActivity
import com.superfactory.sunyatsen.Interface.BindingActivity.NoteDetailOrAddActivity.NoteDetailOrAddActivity
import org.jetbrains.anko.startActivityForResult

/**
 * Created by vicky on 2018/2/4.
 */
class NoteByDayActivity : BaseToolBarActivity<NoteByDayActivityViewModel, NoteByDayActivity>() {
    override fun newViewModel() = NoteByDayActivityViewModel(intent)

    override fun newComponent(v: NoteByDayActivityViewModel) = NoteByDayActivityComponent(v).apply {
        viewModelSafe.ownerNotifier = { i, any ->
            if (any != null)
                startActivityForResult<NoteDetailOrAddActivity>(1001,Pair("data", any))
        }
    }
}