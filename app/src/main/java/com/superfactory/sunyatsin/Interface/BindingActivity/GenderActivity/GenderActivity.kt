package com.superfactory.sunyatsin.Interface.BindingActivity.GenderActivity

import android.view.View
import com.superfactory.library.Bridge.Anko.Adapt.BaseToolBar
import com.superfactory.library.Context.BaseToolBarActivity

/**
 * Created by vicky on 2018.01.31.
 *
 * @Author vicky
 * @Date 2018年01月31日  20:03:11
 * @ClassName 这里输入你的类名(或用途)
 */
class GenderActivity : BaseToolBarActivity<GenderActivityViewModel, GenderActivity>() {
    override fun newViewModel() = GenderActivityViewModel()

    override fun newComponent(v: GenderActivityViewModel) = GenderActivityComponent(v)

    override fun performToolbarClickEvent(view: View, event: BaseToolBar.Companion.ToolbarEvent) {
        super.performToolbarClickEvent(view, event)
        when(event){
            BaseToolBar.Companion.ToolbarEvent.RIGHT->{

            }
        }
    }
}