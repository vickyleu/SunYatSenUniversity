package com.superfactory.sunyatsin.Interface.BindingPrompt

import android.content.Context
import com.superfactory.library.Context.BasePrompt

/**
 * Created by vicky on 2018/2/1.
 */
class QuestionPrompt(ctx: Context) : BasePrompt<QuestionPromptViewModel, QuestionPrompt>(ctx) {

    override fun newViewModel() = QuestionPromptViewModel()

    override fun newComponent(viewModel: QuestionPromptViewModel) = QuestionPromptComponent(viewModel)


}