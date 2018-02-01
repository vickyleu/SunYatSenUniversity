package com.superfactory.library.Graphics.KDialog.view

import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.widget.EditText

import com.superfactory.library.Graphics.KDialog.params.ButtonParams
import com.superfactory.library.Graphics.KDialog.params.CircleParams
import com.superfactory.library.Graphics.KDialog.res.drawable.SelectorBtn
import com.superfactory.library.Graphics.KDialog.res.values.CircleColor

/**
 * 对话框单个按钮的视图
 * Created by hupei on 2017/3/30.
 */
internal class SingleButton(context: Context, params: CircleParams) : ScaleTextView(context) {
    private var mButtonParams: ButtonParams? = null

    init {
        init(params)
    }

    private fun init(params: CircleParams) {
        mButtonParams = if (params.negativeParams != null)
            params.negativeParams
        else
            params
                    .positiveParams

        text = mButtonParams!!.text
        textSize = mButtonParams!!.textSize.toFloat()
        setTextColor(mButtonParams!!.textColor)
        height = mButtonParams!!.height

        //如果取消按钮没有背景色，则使用默认色
        val backgroundColor = if (mButtonParams!!.backgroundColor != 0)
            mButtonParams!!.backgroundColor!!
        else
            CircleColor.bgDialog

        val radius = params.dialogParams?.radius
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            background = SelectorBtn(backgroundColor, 0, 0, radius?:0, radius?:0)
        } else {
            setBackgroundDrawable(SelectorBtn(backgroundColor, 0, 0, radius?:0, radius?:0))
        }

        regOnClickListener()
    }

    private fun regOnClickListener() {
        setOnClickListener { v ->
            mButtonParams!!.dismiss()
            if (mButtonParams!!.listener != null) mButtonParams!!.listener?.onClick(v)
        }
    }

    fun regOnInputClickListener(input: EditText) {
        setOnClickListener { v ->
            val text = input.text.toString()
            if (!TextUtils.isEmpty(text))
                mButtonParams!!.dismiss()
            if (mButtonParams!!.inputListener != null)
                mButtonParams!!.inputListener?.onClick(text, v)
        }
    }
}
