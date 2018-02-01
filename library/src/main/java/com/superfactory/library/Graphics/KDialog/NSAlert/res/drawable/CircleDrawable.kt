package com.superfactory.library.Graphics.KDialog.res.drawable

import android.graphics.drawable.ShapeDrawable

/**
 * 圆角
 * Created by hupei on 2017/3/29.
 */
class CircleDrawable(backgroundColor: Int, leftTopRadius: Int, rightTopRadius: Int, rightBottomRadius: Int, leftBottomRadius: Int) : ShapeDrawable() {

    constructor(backgroundColor: Int, radius: Int) : this(backgroundColor, radius, radius, radius, radius) {}

    init {
        paint.color = backgroundColor//内部填充颜色
        //圆角半径
        shape = DrawableHelper.getRoundRectShape(leftTopRadius, rightTopRadius, rightBottomRadius, leftBottomRadius)
    }

}
