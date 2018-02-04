package com.superfactory.sunyatsin.Interface.BindingActivity.MainActivity


import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import com.superfactory.library.Bridge.Anko.Adapt.BaseToolBar
import com.superfactory.library.Context.BaseActivity
import com.superfactory.sunyatsin.Interface.BindingActivity.NoteDetailOrAddActivity.NoteDetailOrAddActivity
import org.jetbrains.anko.startActivityForResult

class MainActivity : BaseActivity<MainActivityViewModel, MainActivity>() {

    override fun ifNeedTopPadding(): Boolean {
        return false
    }

    override fun newViewModel() = MainActivityViewModel(intent, supportFragmentManager)


    override fun newComponent(v: MainActivityViewModel) = MainActivityComponent(v).apply {
        viewModel?.ownerNotifier={
            i,any->
            startActivityForResult<NoteDetailOrAddActivity>(1001,Pair("data", any))
        }
    }


    override fun setToolbarAttribution(toolbarBinder: BaseToolBar<MainActivity, MainActivityViewModel>, actionBar: ActionBar?, toolbarView: Toolbar) {
        super.setToolbarAttribution(toolbarBinder, actionBar, toolbarView)
//        toolbarView.setTitle("ToolbarDemo");
//        toolbarView.setSubtitle("the detail of toolbar");
// 显示导航按钮
//        toolbarView.setNavigationIcon(R.drawable.icon_back);
    }

}
