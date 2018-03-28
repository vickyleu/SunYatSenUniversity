package com.superfactory.sunyatsen.Interface.BindingActivity.CompellationActivity

import com.superfactory.library.Context.BaseToolBarActivity
import org.jetbrains.anko.intentFor

/**
 * Created by vicky on 2018.01.31.
 *
 * @Author vicky
 * @Date 2018年01月31日  20:01:48
 * @ClassName 这里输入你的类名(或用途)
 */
class CompellationActivity : BaseToolBarActivity<CompellationActivityViewModel, CompellationActivity>() {

    override fun newViewModel() = CompellationActivityViewModel().apply {
        this.ownerNotifier={
            i,any->
            setResult(101, intentFor<String>(Pair("name",any as String)))
            finish()
        }
    }

    override fun newComponent(v: CompellationActivityViewModel) = CompellationActivityComponent(v)
    override fun onLoadedModel(viewModel: CompellationActivityViewModel) {

    }

}