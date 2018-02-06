package com.superfactory.sunyatsin.Interface.BindingPrompt.QuestionnairePrompt

import android.content.Context
import com.superfactory.library.Context.BasePrompt
import com.superfactory.sunyatsin.R

/**
 * Created by vicky on 2018/2/1.
 */
class QuestionnairePrompt(ctx: Context) : BasePrompt<QuestionPromptViewModel, QuestionnairePrompt>(ctx, R.style.prompt_style) {

    override fun newViewModel() = QuestionPromptViewModel()

    override fun newComponent(viewModel: QuestionPromptViewModel) = QuestionPromptComponent(viewModel)


}