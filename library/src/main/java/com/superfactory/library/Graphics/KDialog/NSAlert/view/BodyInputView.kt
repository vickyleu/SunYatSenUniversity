package com.superfactory.library.Graphics.KDialog.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.widget.EditText
import android.widget.LinearLayout
import com.superfactory.library.Graphics.KDialog.params.CircleParams
import com.superfactory.library.Graphics.KDialog.res.drawable.CircleDrawable
import com.superfactory.library.Graphics.KDialog.res.drawable.InputDrawable
import com.superfactory.library.Graphics.KDialog.res.values.CircleColor


/**
 * Created by hupei on 2017/3/31.
 */

@SuppressLint("ViewConstructor")
internal class BodyInputView(context: Context, params: CircleParams) : ScaleLinearLayout(context) {
    private var mEditText: ScaleEditText? = null

    val input: EditText?
        get() = mEditText

    init {
        init(context, params)
    }

    private fun init(context: Context, params: CircleParams) {
        val dialogParams = params.dialogParams
        val titleParams = params.titleParams
        val inputParams = params.inputParams
        val negativeParams = params.negativeParams
        val positiveParams = params.positiveParams

        //如果标题没有背景色，则使用默认色
        val backgroundColor = if (inputParams?.backgroundColor != 0)
            inputParams?.backgroundColor!!
        else
            CircleColor.bgDialog

        //有标题没按钮则底部圆角
        if (titleParams != null && negativeParams == null && positiveParams == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                background = CircleDrawable(backgroundColor, 0, 0, dialogParams?.radius?:0,
                        dialogParams?.radius?:0)
            } else {
                setBackgroundDrawable(CircleDrawable(backgroundColor, 0, 0, dialogParams
                        ?.radius?:0, dialogParams?.radius?:0))
            }
        } else if (titleParams == null && (negativeParams != null || positiveParams != null)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                background = CircleDrawable(backgroundColor, dialogParams?.radius ?: 0, dialogParams
                        ?.radius ?: 0, 0, 0)
            } else {
                setBackgroundDrawable(CircleDrawable(backgroundColor, dialogParams?.radius?:0,
                        dialogParams?.radius?:0, 0, 0))
            }
        } else if (titleParams == null && negativeParams == null && positiveParams == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                background = CircleDrawable(backgroundColor, dialogParams?.radius?:0)
            } else {
                setBackgroundDrawable(CircleDrawable(backgroundColor, dialogParams?.radius?:0))
            }
        } else
            setBackgroundColor(backgroundColor)//有标题有按钮则不用考虑圆角
        //没标题没按钮则全部圆角
        //没标题有按钮则顶部圆角

        mEditText = ScaleEditText(context)
        mEditText!!.hint = inputParams?.hintText?:""
        mEditText!!.setHintTextColor(inputParams?.hintTextColor?:0)
        mEditText!!.textSize = inputParams?.textSize?.toFloat()?:0f
        mEditText!!.setTextColor(inputParams?.textColor?:0)
        mEditText!!.height = inputParams?.inputHeight?:0

        val backgroundResourceId = inputParams?.inputBackgroundResourceId?:0
        if (backgroundResourceId == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mEditText!!.background = InputDrawable(inputParams?.strokeWidth?:0, inputParams
                        ?.strokeColor?:0, inputParams?.inputBackgroundColor?:0)
            } else {
                mEditText!!.setBackgroundDrawable(InputDrawable(inputParams?.strokeWidth?:0,
                        inputParams?.strokeColor?:0, inputParams?.inputBackgroundColor?:0))
            }
        } else
            mEditText!!.setBackgroundResource(backgroundResourceId)

        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
        val margins = inputParams?.margins
        if (margins != null) {
            layoutParams.setMargins(margins[0], margins[1], margins[2], margins[3])
        }
        addView(mEditText, layoutParams)
    }
}
