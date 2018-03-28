package com.superfactory.sunyatsen.Interface.BindingActivity.GenderActivity

import com.superfactory.library.Context.BaseToolBarActivity
import org.jetbrains.anko.intentFor

/**
 * Created by vicky on 2018.01.31.
 *
 * @Author vicky
 * @Date 2018年01月31日  20:03:11
 * @ClassName 这里输入你的类名(或用途)
 */
class GenderActivity : BaseToolBarActivity<GenderActivityViewModel, GenderActivity>() {
    override fun newViewModel() = GenderActivityViewModel().apply {
        this.ownerNotifier = { i, any ->
            setResult(101, intentFor<Int>(Pair("gender", any as Int)))
            finish()
        }
    }

    override fun newComponent(v: GenderActivityViewModel) = GenderActivityComponent(v)

}