package com.superfactory.library.Graphics.KDialog.res.drawable

import android.graphics.drawable.shapes.RoundRectShape

/**
 * Created by hupei on 2017/3/30.
 */

internal object DrawableHelper {

    fun getRoundRectShape(leftTop: Int, rightTop: Int, rightBottom: Int, leftBottom: Int): RoundRectShape {
        val outerRadii = FloatArray(8)
        if (leftTop > 0) {
            outerRadii[0] = leftTop.toFloat()
            outerRadii[1] = leftTop.toFloat()
        }
        if (rightTop > 0) {
            outerRadii[2] = rightTop.toFloat()
            outerRadii[3] = rightTop.toFloat()
        }
        if (rightBottom > 0) {
            outerRadii[4] = rightBottom.toFloat()
            outerRadii[5] = rightBottom.toFloat()
        }
        if (leftBottom > 0) {
            outerRadii[6] = leftBottom.toFloat()
            outerRadii[7] = leftBottom.toFloat()
        }
        return RoundRectShape(outerRadii, null, null)
    }
}
