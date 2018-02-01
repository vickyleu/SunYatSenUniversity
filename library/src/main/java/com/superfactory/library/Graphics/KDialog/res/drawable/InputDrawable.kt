package com.superfactory.library.Graphics.KDialog.res.drawable

import android.graphics.drawable.GradientDrawable

/**
 * 输入框背景
 * Created by hupei on 2017/3/31.
 */

class InputDrawable(strokeWidth: Int, strokeColor: Int, backgroundColor: Int) : GradientDrawable() {
    init {
        setColor(backgroundColor)//内部填充颜色
        setStroke(strokeWidth, strokeColor)//边框宽度,边框颜色
    }
}
