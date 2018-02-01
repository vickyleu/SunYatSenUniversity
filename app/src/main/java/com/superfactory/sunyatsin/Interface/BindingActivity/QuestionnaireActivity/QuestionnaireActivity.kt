package com.superfactory.sunyatsin.Interface.BindingActivity.QuestionnaireActivity

import com.superfactory.library.Bridge.Adapt.startActivityForResult
import com.superfactory.library.Context.BaseToolBarActivity
import com.superfactory.sunyatsin.Interface.BindingActivity.QuestionnaireDetailActivity.QuestionnaireDetailActivity

/**
 * Created by vicky on 2018.02.01.
 *
 * @Author vicky
 * @Date 2018年02月01日  13:50:52
 * @ClassName 这里输入你的类名(或用途)
 */
class QuestionnaireActivity : BaseToolBarActivity<QuestionnaireActivityViewModel, QuestionnaireActivity>() {
    override fun newViewModel() = QuestionnaireActivityViewModel(intent)

    override fun newComponent(v: QuestionnaireActivityViewModel) = QuestionnaireActivityComponent(v).apply {
        viewModelSafe.ownerNotifier = { i, any ->
            startActivityForResult<QuestionnaireDetailActivity>(0,{
                intent ->

            }, Pair("data",any))
        }
    }

    override fun onLoadedModel(viewModel: QuestionnaireActivityViewModel) {
//        viewModel.onItemClicked = { idx, model ->
//
//        }
    }
}