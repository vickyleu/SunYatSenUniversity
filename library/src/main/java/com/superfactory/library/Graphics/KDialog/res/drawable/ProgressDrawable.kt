package com.superfactory.library.Graphics.KDialog.res.drawable

import android.graphics.drawable.ClipDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.view.Gravity

/**
 * 进度条背景，不提供内置水平样式
 * Created by hupei on 2017/3/29.
 */
@Deprecated("")
class ProgressDrawable {
    val layerDrawable: LayerDrawable

    init {
        val background = createBackground()
        val secondaryProgress = createSecondaryProgress()
        val progress = createProgress()
        val layers = arrayOf(background, secondaryProgress, progress)
        layerDrawable = LayerDrawable(layers)
        layerDrawable.setId(0, android.R.id.background)
        layerDrawable.setId(1, android.R.id.secondaryProgress)
        layerDrawable.setId(2, android.R.id.progress)
    }

    private fun createBackground(): GradientDrawable {
        /*     <item android:id="@android:id/background">
            <shape>
                <corners android:radius="5dip" />

                <gradient
                android:angle="270"
                android:centerY="0.75"
                android:endColor="#F5F5F5"
                android:startColor="#BEBEBE" />
            </shape>
        </item>*/

        val drawable = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colorsBg)
        drawable.cornerRadius = 5f
        drawable.setGradientCenter(0.5f, 0.75f)
        return drawable
    }

    private fun createSecondaryProgress(): ClipDrawable {
        /*      <item android:id="@android:id/secondaryProgress">
            <clip>
                <shape>
                    <corners android:radius="0dip" />

                    <gradient
                    android:angle="270"
                    android:centerY="0.75"
                    android:endColor="#165CBC"
                    android:startColor="#85B0E9" />
                    </shape>
            </clip>
        </item>*/
        val drawable = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                colorsSecondaryProgress)
        drawable.setGradientCenter(0.5f, 0.75f)
        return ClipDrawable(drawable, Gravity.LEFT, ClipDrawable.HORIZONTAL)
    }

    private fun createProgress(): ClipDrawable {
        /*      <item android:id="@android:id/progress">
            <clip>
                <shape>
                    <corners android:radius="5dip" />

                    <gradient
                    android:angle="270"
                    android:centerY="0.75"
                    android:endColor="#165CBC"
                    android:startColor="#85B0E9" />
                </shape>
            </clip>
        </item>*/

        //android:angle 默认是Orientation.TOP_BOTTOM，见源码 GradientDrawableGradient_angle 处
        //见源码 updateGradientDrawableGradient
        val drawable = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colorsProgress)
        drawable.cornerRadius = 5f//android:radius
        drawable.setGradientCenter(0.5f, 0.75f)//x默认是0.5，android:centerY
        return ClipDrawable(drawable, Gravity.LEFT, ClipDrawable.HORIZONTAL)
    }

    companion object {
        private val colorsBg = intArrayOf(-0x414142, -0xa0a0b)
        private val colorsSecondaryProgress = intArrayOf(-0x7a4f17, -0xe9a344)
        private val colorsProgress = intArrayOf(-0x7a4f17, -0xe9a344)
    }
}
