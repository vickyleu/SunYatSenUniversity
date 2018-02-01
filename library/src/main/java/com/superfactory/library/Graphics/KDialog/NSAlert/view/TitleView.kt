package com.superfactory.library.Graphics.KDialog.view

import android.content.Context
import android.os.Build
import android.view.Gravity

import com.superfactory.library.Graphics.KDialog.params.CircleParams
import com.superfactory.library.Graphics.KDialog.res.drawable.CircleDrawable
import com.superfactory.library.Graphics.KDialog.res.values.CircleColor


/**
 * 对话框标题
 * Created by hupei on 2017/3/29.
 */
internal class TitleView(context: Context, params: CircleParams) : ScaleTextView(context) {

    init {
        init(params)
    }

    private fun init(params: CircleParams) {
        val dialogParams = params.dialogParams
        val titleParams = params.titleParams

        gravity = titleParams?.gravity ?: Gravity.NO_GRAVITY

        //如果标题没有背景色，则使用默认色
        val backgroundColor = if (titleParams?.backgroundColor != 0)
            titleParams?.backgroundColor!!
        else
            CircleColor.bgDialog

        //有内容则顶部圆角
        if (params.textParams != null || params.itemsParams != null || params.progressParams != null
                || params.inputParams != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                background = CircleDrawable(backgroundColor, dialogParams?.radius ?: 0, dialogParams
                        ?.radius ?: 0, 0, 0)
            } else {
                setBackgroundDrawable(CircleDrawable(backgroundColor, dialogParams?.radius ?: 0,
                        dialogParams?.radius ?: 0, 0, 0))
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                background = CircleDrawable(backgroundColor, dialogParams?.radius ?: 0)
            } else {
                setBackgroundDrawable(CircleDrawable(backgroundColor, dialogParams?.radius ?: 0))
            }
        }//无内容则全部圆角

        height = titleParams.height
        setTextColor(titleParams.textColor)
        textSize = titleParams.textSize.toFloat()
        text = titleParams.text
    }
}
