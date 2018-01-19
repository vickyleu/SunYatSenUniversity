package com.superfactory.library.Context

import android.os.Bundle
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.DslView.BaseToolBar

/**
 * Created by vicky on 2018.01.19.
 *
 * @Author vicky
 * @Date 2018年01月19日  16:53:52
 * @ClassName 这里输入你的类名(或用途)
 */
abstract class BaseToolBarActivity<V, A : BaseToolBarActivity<V, A>> : BaseActivity<V, A>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        showToolBar = true
        super.onCreate(savedInstanceState)
    }

    override fun newToolBarComponent(v: V): BindingComponent<A, V>? {
        return ankoToolBar(v)
    }

    override fun ankoToolBar(viewModel: V): BaseToolBar<V, A>? {
        return BaseToolBar(viewModel)
    }

}
