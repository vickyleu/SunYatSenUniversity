package com.superfactory.library.Graphics.KDialog.view

import android.content.Context
import android.util.TypedValue
import android.view.Gravity

import com.superfactory.library.Graphics.KDialog.scale.ScaleUtils


/**
 * Created by hupei on 2017/3/29.
 */

internal open class ScaleTextView(context: Context) : android.support.v7.widget.AppCompatTextView(context) {
    init {
        config()
    }

    private fun config() {
        gravity = Gravity.CENTER
    }

    override fun setHeight(pixels: Int) {
        val dimenHeight = ScaleUtils.scaleValue(pixels)
        super.setHeight(dimenHeight)
    }

    override fun setTextSize(size: Float) {
        val dimenTextSize = ScaleUtils.scaleValue(size.toInt())
        setTextSize(TypedValue.COMPLEX_UNIT_PX, dimenTextSize.toFloat())
    }

    fun setAutoPadding(left: Int, top: Int, right: Int, bottom: Int) {
        val dimenLeft = ScaleUtils.scaleValue(left)
        val dimenTop = ScaleUtils.scaleValue(top)
        val dimenRight = ScaleUtils.scaleValue(right)
        val dimenBottom = ScaleUtils.scaleValue(bottom)
        super.setPadding(dimenLeft, dimenTop, dimenRight, dimenBottom)
    }
}
