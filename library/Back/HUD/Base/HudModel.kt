package com.superfactory.library.Graphics.HUD.Base

import android.app.Dialog
import com.superfactory.library.Bridge.Anko.BaseObservable
import com.superfactory.library.Bridge.Anko.observable

/**
 * Created by vicky on 2018/1/28.
 */
open class HudModel : BaseObservable() {
    val visibility = observable(false)
    private val width = observable(-3)
    private val height = observable(-3)
    var hud: Dialog? = null

    open var _width =width.value
    open fun sizeOfHud(width: Int, height: Int) {
        this.width.setStableValue(width)
        this.height.value = height
    }
}