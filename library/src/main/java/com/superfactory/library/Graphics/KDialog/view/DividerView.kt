package com.superfactory.library.Graphics.KDialog.view

import android.content.Context
import android.view.View
import android.widget.LinearLayout

import com.superfactory.library.Graphics.KDialog.res.values.CircleColor


/**
 * 分隔线，默认垂直
 * Created by hupei on 2017/3/30.
 */
internal class DividerView(context: Context) : View(context) {
    init {
        init()
    }

    private fun init() {
        layoutParams = LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT)
        setBackgroundColor(CircleColor.divider)
    }

    /**
     * 将分隔线设置为水平分隔
     */
    fun setVertical() {
        layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1)
    }
}
