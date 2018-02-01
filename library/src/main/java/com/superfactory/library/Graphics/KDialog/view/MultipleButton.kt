package com.superfactory.library.Graphics.KDialog.view

import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout

import com.superfactory.library.Graphics.KDialog.params.ButtonParams
import com.superfactory.library.Graphics.KDialog.params.CircleParams
import com.superfactory.library.Graphics.KDialog.res.drawable.SelectorBtn
import com.superfactory.library.Graphics.KDialog.res.values.CircleColor


/**
 * 对话框确定按钮与取消的视图
 * Created by hupei on 2017/3/30.
 */
internal class MultipleButton(context: Context, params: CircleParams) : ScaleLinearLayout(context) {
    private var mNegativeParams: ButtonParams? = null
    private var mPositiveParams: ButtonParams? = null
    private var mNegativeButton: ScaleTextView? = null
    private var mPositiveButton: ScaleTextView? = null

    init {
        init(params)
    }

    private fun init(params: CircleParams) {
        orientation = LinearLayout.HORIZONTAL

        mNegativeParams = params.negativeParams
        mPositiveParams = params.positiveParams

        val radius = params.dialogParams?.radius?:0

        //取消按钮
        createNegative(radius)

        //添加二人按钮之间的分隔线
        val dividerView = DividerView(context)
        addView(dividerView)

        //确定按钮
        createPositive(radius)
    }

    private fun createNegative(radius: Int) {
        mNegativeButton = ScaleTextView(context)
        mNegativeButton!!.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup
                        .LayoutParams.WRAP_CONTENT, 1f)

        mNegativeButton!!.text = mNegativeParams!!.text
        mNegativeButton!!.textSize = mNegativeParams!!.textSize.toFloat()
        mNegativeButton!!.setTextColor(mNegativeParams!!.textColor)
        mNegativeButton!!.height = mNegativeParams!!.height

        //如果取消按钮没有背景色，则使用默认色
        val backgroundNegative = if (mNegativeParams!!.backgroundColor != 0)
            mNegativeParams!!
                    .backgroundColor
        else
            CircleColor.bgDialog
        //按钮左下方为圆角
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mNegativeButton!!.background = SelectorBtn(backgroundNegative, 0, 0, 0, radius)
        } else {
            mNegativeButton!!.setBackgroundDrawable(SelectorBtn(backgroundNegative, 0, 0, 0,
                    radius))
        }

        regNegativeListener()

        addView(mNegativeButton)
    }

    private fun createPositive(radius: Int) {

        mPositiveButton = ScaleTextView(context)
        mPositiveButton!!.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup
                        .LayoutParams.WRAP_CONTENT, 1f)

        mPositiveButton!!.text = mPositiveParams!!.text
        mPositiveButton!!.textSize = mPositiveParams!!.textSize.toFloat()
        mPositiveButton!!.setTextColor(mPositiveParams!!.textColor)
        mPositiveButton!!.height = mPositiveParams!!.height

        //如果取消按钮没有背景色，则使用默认色
        val backgroundPositive = if (mPositiveParams!!.backgroundColor != 0)
            mPositiveParams!!
                    .backgroundColor
        else
            CircleColor
                    .bgDialog

        //取消按钮右下方为圆角
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mPositiveButton!!.background = SelectorBtn(backgroundPositive, 0, 0, radius, 0)
        } else {
            mPositiveButton!!.setBackgroundDrawable(SelectorBtn(backgroundPositive, 0, 0,
                    radius, 0))
        }

        regPositiveListener()

        addView(mPositiveButton)
    }

    private fun regNegativeListener() {
        mNegativeButton!!.setOnClickListener { v ->
            mNegativeParams!!.dismiss()
            if (mNegativeParams!!.listener != null) mNegativeParams!!.listener?.onClick(v)
        }
    }

    private fun regPositiveListener() {
        mPositiveButton!!.setOnClickListener { v ->
            mPositiveParams!!.dismiss()
            if (mPositiveParams!!.listener != null) mPositiveParams!!.listener?.onClick(v)
        }
    }

    fun regOnInputClickListener(input: EditText) {
        mPositiveButton!!.setOnClickListener { v ->
            val text = input.text.toString()
            if (!TextUtils.isEmpty(text))
                mPositiveParams!!.dismiss()
            if (mPositiveParams!!.inputListener != null)
                mPositiveParams!!.inputListener?.onClick(text, v)
        }
    }
}
