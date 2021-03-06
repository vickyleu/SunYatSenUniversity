package com.superfactory.sunyatsen.Interface.BindingActivity.MainActivity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.View
import com.superfactory.library.Bridge.Anko.Adapt.FragmentContainer
import com.superfactory.library.Bridge.Anko.ObservableFieldImpl
import com.superfactory.library.Bridge.Anko.observable
import com.superfactory.library.Bridge.Model.ToolbarBindingModel
import com.superfactory.library.Debuger
import com.superfactory.sunyatsen.Interface.BindingFragment.Note.NoteFragment
import com.superfactory.sunyatsen.Interface.BindingFragment.Profile.ProfileFragment
import com.superfactory.sunyatsen.R
import com.superfactory.sunyatsen.Struct.Login.LoginAfterStruct
import java.util.*

/**
 * Created by vicky on 2018.01.17.
 *
 * @Author vicky
 * @Date 2018年01月17日  14:43:59
 * @ClassName 这里输入你的类名(或用途)
 */
class MainActivityViewModel(val intent: Intent?, manager: FragmentManager?) : ToolbarBindingModel() {
    override fun setToolbar(toolbarBindingModel: ToolbarBindingModel) {
        toolbarBindingModel.backgroundColor.value = Color.BLUE
        toolbarBindingModel.title.value = "主标题"
    }

    val selected = observable(0)
    private val fragmentList = observable(ArrayList<Fragment>())

    var fragments = observable(FragmentContainer())

    val clickListener = observable(View.OnClickListener {
        when (it.id) {
            R.id.ctrl_button -> {
                ownerNotifier?.invoke(101,null)
                return@OnClickListener
            }
            R.id.ctrl_text_left -> {
                if (selected.value == 0) return@OnClickListener
                selected.value = 0
            }
            R.id.ctrl_text_right -> {
                if (selected.value == 1) return@OnClickListener
                selected.value = 1
            }
            else -> {
                return@OnClickListener
            }
        }
        if (fragmentList.value.size > 0 && fragmentList.value.size > selected.value) {
            selectFragment(fragments, fragmentList, selected.value)
            fragments.notifyChange(MainActivityViewModel::fragments)
        }
    })

    private fun selectFragment(fragments: ObservableFieldImpl<FragmentContainer>,
                               fragmentList: ObservableFieldImpl<ArrayList<Fragment>>, selected: Int) {
        val container = fragments.value
        val loginStruct = intent?.extras?.getParcelable<LoginAfterStruct>("data")
        val b = Bundle()
        b.putParcelable("data", loginStruct)
        container.extras = b//intent?.extras
        container.fragment = fragmentList.value.get(selected)
        Debuger.printMsg(this, "切换了:" + if (container.fragment != null) container.fragment!!.javaClass.simpleName + " arg=" + container.fragment?.arguments else "无")
    }

    init {
        if (intent != null && manager != null) {
            selected.value = 0
            val list = ArrayList<Fragment>()
            list.add(NoteFragment())
            list.add(ProfileFragment())
            fragmentList.value.clear()
            fragmentList.value.addAll(list)
            fragments.value.manager = manager
            selectFragment(fragments, fragmentList, selected.value)
        }
    }


    fun update() {
        val list = ArrayList<Fragment>()
        list.add(NoteFragment())
        list.add(ProfileFragment())
        fragmentList.value.clear()
        fragmentList.value.addAll(list)
        if (fragmentList.value.size > 0 && fragmentList.value.size > selected.value) {
            selectFragment(fragments, fragmentList, selected.value)
            fragments.notifyChange(MainActivityViewModel::fragments)
        }
    }
}