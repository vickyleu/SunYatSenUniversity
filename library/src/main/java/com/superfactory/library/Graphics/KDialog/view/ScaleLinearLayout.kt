package com.superfactory.library.Graphics.KDialog.view

import android.content.Context
import android.widget.LinearLayout

import com.superfactory.library.Graphics.KDialog.scale.ScaleLayoutConfig


/**
 * Created by hupei on 2017/3/29.
 */
internal open class ScaleLinearLayout(context: Context) : LinearLayout(context) {

    init {
        ScaleLayoutConfig.init(context.applicationContext)
    }
}
