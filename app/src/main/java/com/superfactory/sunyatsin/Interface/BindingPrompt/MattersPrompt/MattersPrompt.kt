package com.superfactory.sunyatsin.Interface.BindingPrompt.MattersPrompt

import android.content.Context
import com.superfactory.library.Context.BasePrompt
import com.superfactory.sunyatsin.R
import com.superfactory.library.Context.PromptTransfer
/**
 * Created by vicky on 2018.02.05.
 *
 * @Author vicky
 * @Date 2018年02月05日  15:15:25
 * @ClassName 这里输入你的类名(或用途)
 */
class MattersPrompt(ctx: Context,private val id:String,private val transfer: PromptTransfer) :
        BasePrompt<MattersPromptViewModel, MattersPrompt>(ctx, R.style.Dialog_FS) {
    override fun newViewModel() = MattersPromptViewModel(id)

    override fun newComponent(viewModel: MattersPromptViewModel) = MattersPromptComponent(viewModel)

    override fun onLoadedModel(viewModel: MattersPromptViewModel) {
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