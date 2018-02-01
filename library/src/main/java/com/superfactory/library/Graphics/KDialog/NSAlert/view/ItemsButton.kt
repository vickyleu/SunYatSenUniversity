package com.superfactory.library.Graphics.KDialog.view

import android.content.Context
import android.os.Build
import android.view.ViewGroup
import android.widget.LinearLayout

import com.superfactory.library.Graphics.KDialog.params.CircleParams
import com.superfactory.library.Graphics.KDialog.res.drawable.SelectorBtn
import com.superfactory.library.Graphics.KDialog.res.values.CircleColor
import com.superfactory.library.Graphics.KDialog.res.values.CircleDimen
import com.superfactory.library.Graphics.KDialog.scale.ScaleUtils


/**
 * 列表对话框的取消按钮视图
 * Created by hupei on 2017/3/30.
 */
internal class ItemsButton(context: Context, params: CircleParams) : ScaleTextView(context) {

    init {
        init(params)
    }

    private fun init(params: CircleParams) {
        val negativeParams = params.negativeParams
        val buttonParams = negativeParams ?: params
                .positiveParams
        //为列表显示时，设置列表与按钮之间的距离
        if (params.itemsParams != null) buttonParams?.topMargin = CircleDimen.BUTTON_ITEMS_MARGIN

        val layoutParams = LinearLayout.LayoutParams(ViewGroup
                .LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
        layoutParams.topMargin = ScaleUtils.scaleValue(buttonParams?.topMargin?:0)
        setLayoutParams(layoutParams)

        isClickable = true
        setOnClickListener { v ->
            buttonParams?.dismiss()
            if (buttonParams?.listener != null) buttonParams.listener!!.onClick(v)
        }
        text = buttonParams?.text
        textSize = buttonParams?.textSize?.toFloat()?:0f
        setTextColor(buttonParams?.textColor?:0)
        height = buttonParams?.height?:0

        //如果取消按钮没有背景色，则使用默认色
        val backgroundColor = if (buttonParams?.backgroundColor != 0)
            buttonParams?.backgroundColor!!
        else
            CircleColor.bgDialog
        val radius = params.dialogParams?.radius?:0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            background = SelectorBtn(backgroundColor, radius, radius, radius, radius)
        } else {
            setBackgroundDrawable(SelectorBtn(backgroundColor, radius, radius, radius, radius))
        }
    }
}
