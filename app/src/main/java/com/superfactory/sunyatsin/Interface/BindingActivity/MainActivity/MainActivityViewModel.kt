package com.superfactory.sunyatsin.Interface.BindingActivity.MainActivity

import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.View
import com.superfactory.library.Bridge.Anko.Adapt.FragmentContainer
import com.superfactory.library.Bridge.Anko.BaseObservable
import com.superfactory.library.Bridge.Anko.ObservableFieldImpl
import com.superfactory.library.Bridge.Anko.observable
import com.superfactory.sunyatsin.Interface.BindingFragment.Note.NoteFragment
import com.superfactory.sunyatsin.R
import java.util.*

/**
 * Created by vicky on 2018.01.17.
 *
 * @Author vicky
 * @Date 2018年01月17日  14:43:59
 * @ClassName 这里输入你的类名(或用途)
 */
class MainActivityViewModel(intent: Intent?, manager: FragmentManager?) : BaseObservable() {

    private var selected = 0
    private val fragmentList = observable(ArrayList<Fragment>())
    var fragments = observable(FragmentContainer())

    val clickListener: ObservableFieldImpl<View.OnClickListener> = observable(View.OnClickListener {
        when (it.id) {
            R.id.ctrl_button -> {
                selected = 1
            }
            R.id.ctrl_text_left -> {
                selected = 0
            }
            R.id.ctrl_text_right -> {
                selected = 2
            }
            else -> {
                return@OnClickListener
            }
        }
        if (fragmentList.value.size > 0 && fragmentList.value.size > selected) {
            selectFragment(fragments, fragmentList, selected)
            notifyChange(this::fragments)
        }
    })

    private fun selectFragment(fragments: ObservableFieldImpl<FragmentContainer>,
                               fragmentList: ObservableFieldImpl<ArrayList<Fragment>>, selected: Int) {
        val container = fragments.value
        container.fragment = fragmentList.value.get(selected)
    }

    init {
        if (intent != null && manager != null) {
            selected = 0
            val list = ArrayList<Fragment>()
            list.add(NoteFragment())
            list.add(NoteFragment())
            fragmentList.value.clear()
            fragmentList.value.addAll(list)
            fragments.value.manager = manager
            selectFragment(fragments, fragmentList, selected)
        }
    }

    fun update() {
        val list = ArrayList<Fragment>()
        list.add(NoteFragment())
        list.add(NoteFragment())
        fragmentList.value.clear()
        fragmentList.value.addAll(list)
        if (fragmentList.value.size > 0 && fragmentList.value.size > selected) {
            selectFragment(fragments, fragmentList, selected)
            notifyChange(this::fragments)
        }
    }
}