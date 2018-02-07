package com.superfactory.sunyatsin.Interface.BindingActivity.QuestionnaireDetailActivity

import com.superfactory.library.Context.BaseToolBarActivity
import com.superfactory.sunyatsin.Interface.BindingPrompt.QuestionnairePrompt.QuestionnairePrompt

/**
 * Created by vicky on 2018.02.01.
 *
 * @Author vicky
 * @Date 2018年02月01日  16:27:29
 * @ClassName 这里输入你的类名(或用途)
 */
class QuestionnaireDetailActivity : BaseToolBarActivity<QuestionnaireDetailActivityViewModel, QuestionnaireDetailActivity>() {
    override fun newViewModel() = QuestionnaireDetailActivityViewModel(intent)
    override fun newComponent(v: QuestionnaireDetailActivityViewModel) = QuestionnaireDetailActivityComponent(v).apply {
        viewModel?.ownerNotifier = { i, any ->
            QuestionnairePrompt(this@QuestionnaireDetailActivity) { _, _ ->
                finish()
            }.show()

        }
    }
}