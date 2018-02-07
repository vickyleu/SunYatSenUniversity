package com.superfactory.library.Graphics.Alert.InputAlert

import android.graphics.Color
import android.view.View
import com.superfactory.library.Bridge.Anko.observable
import com.superfactory.library.Bridge.Anko.observableNullable
import com.superfactory.library.Context.BasePrompt
import com.superfactory.library.Graphics.KDialog.Prompt.BasePromptParams
import org.jetbrains.anko.wrapContent

/**
 * Created by vicky on 2018.02.06.
 *
 * @Author vicky
 * @Date 2018年02月06日  17:18:24
 * @ClassName 这里输入你的类名(或用途)
 */
open class InputAlertViewModel : BasePromptParams() {
    override fun setPrompt(promptParams: BasePromptParams) {
        promptParams.width.value = (promptParams.screenSize.width * 0.82f).toInt()
        promptParams.height.value = wrapContent
        promptParams.cancelable.value = false
        promptParams.touchCancelable.value = true
    }

    val error = observable("")
    val title = observable("标题")
    val titleColor = observable(Color.parseColor("#222222"))
    val titleSize = observable(16f)
    val msg = observable("消息")
    val msgColor = observable(Color.parseColor("#222222"))
    val msgSize = observable(12f)
    val hint = observable("提示")
    val inputColor = observable(Color.parseColor("#222222"))
    val inputSize = observable(12f)
    val output = observable("")
    val positive = observable("确认")
    val positiveColor = observable(Color.parseColor("#1688ff"))
    val positiveSize = observable(14f)
    val negative = observable("取消")
    val negativeColor = observable(Color.parseColor("#1688ff"))
    val negativeSize = observable(14f)
    open val positiveClick = observableNullable<(View, InputAlertViewModel, BasePrompt<*, *>) -> Unit>(null)
    open val negativeClick = observableNullable<(View, InputAlertViewModel, BasePrompt<*, *>) -> Unit>(null)
}