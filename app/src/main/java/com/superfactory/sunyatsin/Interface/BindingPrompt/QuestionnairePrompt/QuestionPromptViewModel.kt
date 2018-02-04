package com.superfactory.sunyatsin.Interface.BindingPrompt.QuestionnairePrompt

import com.superfactory.library.Graphics.KDialog.Prompt.BasePromptParams
import com.superfactory.sunyatsin.R

/**
 * Created by vicky on 2018/2/1.
 */
class QuestionPromptViewModel : BasePromptParams() {

    override fun setPrompt(promptParams: BasePromptParams) {
        promptParams.width.value= (promptParams.screenSize.width*0.16f).toInt()
        promptParams.cancelable.value=false
        promptParams.touchCancelable.value=false
    }

    val headerImage=R.drawable.header_tips_icon
    val centreImage= R.drawable.upload_success_icon

}