package com.superfactory.library.Graphics.KDialog.res.drawable

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.StateListDrawable

import com.superfactory.library.Graphics.KDialog.res.values.CircleColor


/**
 * 按钮的背景，有点击效果
 * Created by hupei on 2017/3/30.
 */

class SelectorBtn(backgroundColor: Int, leftTopRadius: Int, rightTopRadius: Int, rightBottomRadius: Int, leftBottomRadius: Int) : StateListDrawable() {

    init {
        //按下
        val drawablePress = ShapeDrawable(DrawableHelper.getRoundRectShape(leftTopRadius, rightTopRadius,
                rightBottomRadius, leftBottomRadius))
        drawablePress.paint.color = CircleColor.buttonPress
        //默认
        val defaultDrawable = ShapeDrawable(DrawableHelper.getRoundRectShape(leftTopRadius,
                rightTopRadius,
                rightBottomRadius, leftBottomRadius))
        defaultDrawable.paint.color = backgroundColor

        addState(intArrayOf(android.R.attr.state_pressed), drawablePress)
        addState(intArrayOf(-android.R.attr.state_pressed), defaultDrawable)
    }
}
