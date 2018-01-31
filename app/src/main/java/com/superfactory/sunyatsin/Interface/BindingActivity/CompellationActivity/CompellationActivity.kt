package com.superfactory.sunyatsin.Interface.BindingActivity.CompellationActivity

import android.view.View
import com.superfactory.library.Bridge.Anko.Adapt.BaseToolBar
import com.superfactory.library.Context.BaseToolBarActivity

/**
 * Created by vicky on 2018.01.31.
 *
 * @Author vicky
 * @Date 2018年01月31日  20:01:48
 * @ClassName 这里输入你的类名(或用途)
 */
class CompellationActivity : BaseToolBarActivity<CompellationActivityViewModel, CompellationActivity>() {

    override fun newViewModel() = CompellationActivityViewModel()

    override fun newComponent(v: CompellationActivityViewModel) = CompellationActivityComponent(v)
    override fun onLoadedModel(viewModel: CompellationActivityViewModel) {

    }

    override fun performToolbarClickEvent(view: View, event: BaseToolBar.Companion.ToolbarEvent) {
        super.performToolbarClickEvent(view, event)
        when (event) {
            BaseToolBar.Companion.ToolbarEvent.RIGHT -> {

            }
        }
    }
}