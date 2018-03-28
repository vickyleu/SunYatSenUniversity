package com.superfactory.sunyatsen.Interface.BindingPrompt.QuestionnairePrompt

import android.content.Context
import com.superfactory.library.Context.BasePrompt
import com.superfactory.library.Context.PromptTransfer
import com.superfactory.sunyatsen.R

/**
 * Created by vicky on 2018/2/1.
 */
class QuestionnairePrompt(ctx: Context, val transfer: PromptTransfer) : BasePrompt<QuestionPromptViewModel, QuestionnairePrompt>(ctx, R.style.prompt_style) {

    override fun newViewModel() = QuestionPromptViewModel().apply {
        transfer?.invoke(0, null)
    }

    override fun newComponent(viewModel: QuestionPromptViewModel) = QuestionPromptComponent(viewModel)


}