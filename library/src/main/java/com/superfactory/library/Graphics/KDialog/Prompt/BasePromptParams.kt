package com.superfactory.library.Graphics.KDialog.Prompt

import com.superfactory.library.Bridge.Anko.BaseObservable
import com.superfactory.library.Bridge.Anko.ScreenSizeExtension
import com.superfactory.library.Bridge.Anko.observable

/**
 * Created by vicky on 2018/2/2.
 */
abstract class BasePromptParams : BaseObservable() {
    val cancelable = observable(true)
    val touchCancelable = observable(true)
    val width = observable(0)
    val height = observable(0)
    val backgroundColor = observable(0)
    lateinit var screenSize: ScreenSizeExtension

    abstract fun setPrompt(promptParams: BasePromptParams)

}
