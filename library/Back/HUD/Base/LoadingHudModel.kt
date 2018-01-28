package com.superfactory.library.Graphics.HUD.Base

import android.view.View
import com.superfactory.library.Bridge.Anko.observable
import com.superfactory.library.Graphics.HUD.View.LoadingDialog

/**
 * Created by vicky on 2018/1/28.
 */
open class LoadingHudModel : HudModel() {

    val Animating=observable(false)

    var loadSuccessStr: String? = null
    var loadFailedStr: String? = null
    var viewList: MutableList<View>? = null

    var interceptBack = true
    var openSuccessAnim = true
    var openFailedAnim = true
    /**
     * 返回当前绘制的速度
     *
     * @return 速度
     */
    var speed = 1
        private set
    var time: Long = 1000

    var loadStyle = LoadingDialog.STYLE_RING

    open fun setInterceptIfNeeded(interceptBack: Array<Boolean>): LoadingHudModel {
        interceptBack[0] = false
        return this
    }
}