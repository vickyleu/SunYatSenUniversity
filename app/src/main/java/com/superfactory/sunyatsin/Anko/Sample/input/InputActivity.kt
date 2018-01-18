package com.superfactory.sunyatsin.Anko.Sample.input

import com.superfactory.library.Context.BaseActivity

class InputActivity : BaseActivity<InputActivityViewModel, InputActivity>() {

    override fun newViewModel() = InputActivityViewModel()

    override fun newComponent(v: InputActivityViewModel) = InputActivityComponent(v)

}
