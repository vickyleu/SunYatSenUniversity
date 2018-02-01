package com.superfactory.library.Graphics.KDialog.view

import android.content.Context
import android.os.Build
import android.view.Gravity

import com.superfactory.library.Graphics.KDialog.params.CircleParams
import com.superfactory.library.Graphics.KDialog.res.drawable.CircleDrawable
import com.superfactory.library.Graphics.KDialog.res.values.CircleColor


/**
 * 对话框纯文本视图
 * Created by hupei on 2017/3/30.
 */
internal class BodyTextView(context: Context, private val mParams: CircleParams) : ScaleTextView(context) {

    init {
        init(mParams)
    }

    private fun init(params: CircleParams) {
        val dialogParams = params.dialogParams
        val titleParams = params.titleParams
        val textParams = params.textParams
        val negativeParams = params.negativeParams
        val positiveParams = params.positiveParams

        gravity = textParams?.gravity ?: Gravity.NO_GRAVITY

        //如果标题没有背景色，则使用默认色
        val backgroundColor = if (textParams?.backgroundColor != 0)
            textParams?.backgroundColor!!
        else
            CircleColor.bgDialog

        //有标题没按钮则底部圆角
        if (titleParams != null && negativeParams == null && positiveParams == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                background = CircleDrawable(backgroundColor!!, 0, 0, dialogParams?.radius ?: 0,
                        dialogParams?.radius ?: 0)
            } else {
                setBackgroundDrawable(CircleDrawable(backgroundColor!!, 0, 0, dialogParams?.radius
                        ?: 0,
                        dialogParams?.radius ?: 0))
            }
        } else if (titleParams == null && (negativeParams != null || positiveParams != null)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                background = CircleDrawable(backgroundColor!!, dialogParams?.radius
                        ?: 0, dialogParams
                        ?.radius ?: 0, 0, 0)
            } else {
                setBackgroundDrawable(CircleDrawable(backgroundColor!!, dialogParams?.radius ?: 0,
                        dialogParams?.radius ?: 0, 0, 0))
            }
        } else if (titleParams == null && negativeParams == null && positiveParams == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                background = CircleDrawable(backgroundColor!!, dialogParams?.radius ?: 0)
            } else {
                setBackgroundDrawable(CircleDrawable(backgroundColor!!, dialogParams?.radius ?: 0))
            }
        } else
            setBackgroundColor(backgroundColor!!)//有标题有按钮则不用考虑圆角
        //没标题没按钮则全部圆角
        //没标题有按钮则顶部圆角

        //        setHeight(textParams.height);
        minHeight = textParams?.height ?: 0
        setTextColor(textParams?.textColor ?: 0)
        textSize = textParams?.textSize?.toFloat() ?: 0f
        text = textParams?.text

        val padding = textParams?.padding
        if (padding != null) setAutoPadding(padding[0], padding[1], padding[2], padding[3])
    }

    fun refreshText() {
        if (mParams.textParams == null) return
        post { text = mParams.textParams!!.text }
    }
}
