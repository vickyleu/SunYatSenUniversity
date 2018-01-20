package com.superfactory.library.Bridge.Model

import com.superfactory.library.Bridge.Anko.BaseObservable
import com.superfactory.library.Bridge.Anko.observable

/**
 * Created by vicky on 2018/1/19.
 */
class ToolbarBindingModel : BaseObservable() {
    val title = observable("")
    var navigationIcon = observable(Any())
    val backgroundColor = observable(0)
    val titleColor = observable(0)
    val titleSize = observable(0)
    val navigationText = observable("")
    val navigationTextSize = observable(0)
    val navigationTextColor = observable(0)
    val leftPadding = observable(0)
    val rightPadding = observable(0)

}