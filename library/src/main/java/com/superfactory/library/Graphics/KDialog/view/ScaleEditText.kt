package com.superfactory.library.Graphics.KDialog.view

import android.content.Context
import android.util.TypedValue
import android.view.Gravity

import com.superfactory.library.Graphics.KDialog.scale.ScaleUtils


/**
 * Created by hupei on 2017/3/31.
 */
internal class ScaleEditText(context: Context) : android.support.v7.widget.AppCompatEditText(context) {
    init {
        config()
    }

    private fun config() {
        requestFocus()
        isFocusable = true
        isFocusableInTouchMode = true
        gravity = Gravity.TOP or Gravity.LEFT
    }

    override fun setHeight(pixels: Int) {
        val dimenHeight = ScaleUtils.scaleValue(pixels)
        super.setHeight(dimenHeight)
    }

    override fun setTextSize(size: Float) {
        val dimenTextSize = ScaleUtils.scaleValue(size.toInt())
        setTextSize(TypedValue.COMPLEX_UNIT_PX, dimenTextSize.toFloat())
    }
}
