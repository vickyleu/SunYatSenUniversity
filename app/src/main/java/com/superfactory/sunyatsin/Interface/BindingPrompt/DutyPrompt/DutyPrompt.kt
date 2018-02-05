package com.superfactory.sunyatsin.Interface.BindingPrompt.DutyPrompt

import android.content.Context
import com.superfactory.library.Context.BasePrompt
import com.superfactory.library.Context.PromptTransfer
import com.superfactory.sunyatsin.R

/**
 * Created by vicky on 2018/2/5.
 */
class DutyPrompt(ctx: Context, private val transfer: PromptTransfer) :
        BasePrompt<DutyPromptViewModel, DutyPrompt>(ctx, R.style.Dialog_FS) {
    override fun newViewModel() = DutyPromptViewModel()

    override fun newComponent(viewModel: DutyPromptViewModel) = DutyPromptComponent(viewModel)

    override fun onLoadedModel(viewModel: DutyPromptViewModel) {
        viewModel.onItemClickListener = { i, v ->
            this.dismiss()
            transfer.invoke(i, v)
        }
    }

    override fun show() {
        if (viewModel?.errorInterrupt != true) {
            super.show()
        } else {

        }
    }
}