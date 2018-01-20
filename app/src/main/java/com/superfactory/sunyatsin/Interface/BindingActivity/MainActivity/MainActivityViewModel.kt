package com.superfactory.sunyatsin.Interface.BindingActivity.MainActivity

import android.content.Intent
import android.graphics.Color
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.View
import com.superfactory.library.Bridge.Anko.Adapt.FragmentContainer
import com.superfactory.library.Bridge.Anko.BaseObservable
import com.superfactory.library.Bridge.Anko.ObservableFieldImpl
import com.superfactory.library.Bridge.Anko.observable
import com.superfactory.library.Bridge.Model.ToolbarBindingModel
import com.superfactory.library.Debuger
import com.superfactory.sunyatsin.Interface.BindingFragment.Note.NoteFragment
import com.superfactory.sunyatsin.Interface.BindingFragment.Profile.ProfileFragment
import com.superfactory.sunyatsin.R
import java.util.*

/**
 * Created by vicky on 2018.01.17.
 *
 * @Author vicky
 * @Date 2018年01月17日  14:43:59
 * @ClassName 这里输入你的类名(或用途)
 */
class MainActivityViewModel(intent: Intent?, manager: FragmentManager?) : ToolbarBindingModel() {
    override fun setToolbar(toolbarBindingModel: ToolbarBindingModel) {
        toolbarBindingModel.backgroundColor.value=Color.BLUE
        toolbarBindingModel.title.value="妈卖批"
    }

    private var selected = 0
    private val fragmentList = observable(ArrayList<Fragment>())

    var fragments = observable(FragmentContainer())

    val clickListener = observable(View.OnClickListener {
        when (it.id) {
            R.id.ctrl_button -> {
                if (selected == 1) return@OnClickListener
                selected = 1
                Debuger.printMsg(this, "点击了中间按钮")
            }
            R.id.ctrl_text_left -> {
                if (selected == 0) return@OnClickListener
                selected = 0
                Debuger.printMsg(this, "点击了左边按钮")
            }
            R.id.ctrl_text_right -> {
                if (selected == 2) return@OnClickListener
                selected = 2
                Debuger.printMsg(this, "点击了右边按钮")
            }
            else -> {
                return@OnClickListener
            }
        }
        if (fragmentList.value.size > 0 && fragmentList.value.size > selected) {
            selectFragment(fragments, fragmentList, selected)
            fragments.notifyChange(MainActivityViewModel::fragments)
        }
    })

    private fun selectFragment(fragments: ObservableFieldImpl<FragmentContainer>,
                               fragmentList: ObservableFieldImpl<ArrayList<Fragment>>, selected: Int) {
        val container = fragments.value
        container.fragment = fragmentList.value.get(selected)
        Debuger.printMsg(this, "切换了:" + if (container.fragment != null) container.fragment!!.javaClass.simpleName else "无")
    }

    init {
        if (intent != null && manager != null) {
            selected = 0
            val list = ArrayList<Fragment>()
            list.add(NoteFragment())
            list.add(ProfileFragment())
            fragmentList.value.clear()
            fragmentList.value.addAll(list)
            fragments.value.manager = manager
            selectFragment(fragments, fragmentList, selected)
        }
    }



    fun update() {
        val list = ArrayList<Fragment>()
        list.add(NoteFragment())
        list.add(ProfileFragment())
        fragmentList.value.clear()
        fragmentList.value.addAll(list)
        if (fragmentList.value.size > 0 && fragmentList.value.size > selected) {
            selectFragment(fragments, fragmentList, selected)
            fragments.notifyChange(MainActivityViewModel::fragments)
        }
    }
}