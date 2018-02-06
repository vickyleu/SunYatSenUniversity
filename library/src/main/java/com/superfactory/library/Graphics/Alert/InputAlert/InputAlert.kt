package com.superfactory.library.Graphics.Alert.InputAlert

import android.content.Context
import com.superfactory.library.Context.BasePrompt
import com.superfactory.library.R

/**
 * Created by vicky on 2018.02.06.
 *
 * @Author vicky
 * @Date 2018年02月06日  17:17:29
 * @ClassName 这里输入你的类名(或用途)
 */
class InputAlert(private val fun0: ((InputAlertViewModel) -> Unit), ctx: Context) :
        BasePrompt<InputAlertViewModel, InputAlert>(ctx, R.style.prompt_style) {

    override fun newViewModel() = InputAlertViewModel().apply {
        fun0.invoke(this)
    }

    override fun newComponent(viewModel: InputAlertViewModel) = InputAlertComponent(viewModel)

}
