package com.superfactory.library.Graphics.KDialog.view

import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.widget.LinearLayout
import android.widget.ProgressBar


import com.superfactory.library.Graphics.KDialog.params.CircleParams
import com.superfactory.library.Graphics.KDialog.params.ProgressParams
import com.superfactory.library.Graphics.KDialog.res.drawable.CircleDrawable
import com.superfactory.library.Graphics.KDialog.res.values.CircleColor
import com.superfactory.library.Graphics.KDialog.res.values.CircleDimen

import java.lang.reflect.Field
import java.lang.reflect.Modifier

/**
 * Created by hupei on 2017/3/31.
 */

internal class BodyProgressView(context: Context, params: CircleParams) : ScaleLinearLayout(context) {
    private var mProgressParams: ProgressParams? = null
    private var mProgressBar: ProgressBar? = null
    private var mViewUpdateHandler: Handler? = null

    init {
        init(context, params)
    }

    private fun init(context: Context, params: CircleParams) {
        orientation = LinearLayout.VERTICAL
        val dialogParams = params.dialogParams
        val titleParams = params.titleParams
        val negativeParams = params.negativeParams
        val positiveParams = params.positiveParams
        mProgressParams = params.progressParams

        //如果没有背景色，则使用默认色
        val backgroundColor = if (mProgressParams!!.backgroundColor != 0)
            mProgressParams!!
                    .backgroundColor!!
        else
            CircleColor
                    .bgDialog

        //有标题没按钮则底部圆角
        if (titleParams != null && negativeParams == null && positiveParams == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                background = CircleDrawable(backgroundColor, 0, 0, dialogParams?.radius ?: 0,
                        dialogParams?.radius ?: 0)
            } else {
                setBackgroundDrawable(CircleDrawable(backgroundColor, 0, 0, dialogParams?.radius
                        ?: 0,
                        dialogParams?.radius ?: 0))
            }
        } else if (titleParams == null && (negativeParams != null || positiveParams != null)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                background = CircleDrawable(backgroundColor, dialogParams?.radius ?: 0, dialogParams
                        ?.radius ?: 0, 0, 0)
            } else {
                setBackgroundDrawable(CircleDrawable(backgroundColor, dialogParams?.radius ?: 0,
                        dialogParams?.radius ?: 0, 0, 0))
            }
        } else if (titleParams == null && negativeParams == null && positiveParams == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                background = CircleDrawable(backgroundColor, dialogParams?.radius ?: 0)
            } else {
                setBackgroundDrawable(CircleDrawable(backgroundColor, dialogParams?.radius ?: 0))
            }
        } else
            setBackgroundColor(backgroundColor)//有标题有按钮则不用考虑圆角
        //没标题没按钮则全部圆角
        //没标题有按钮则顶部圆角

        //自定义样式
        val progressDrawableId = mProgressParams!!.progressDrawableId
        //水平样式
        if (mProgressParams!!.style == ProgressParams.STYLE_HORIZONTAL) {
            if (progressDrawableId != 0) {
                mProgressBar = ProgressBar(getContext())
                setFieldValue(mProgressBar!!, "mOnlyIndeterminate", false)
                mProgressBar!!.isIndeterminate = false
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    mProgressBar!!.setProgressDrawableTiled(context.getDrawable(progressDrawableId))
                else
                    mProgressBar!!.progressDrawable = context.resources.getDrawable(progressDrawableId)
            } else {
                mProgressBar = ProgressBar(getContext(), null, android.R.attr
                        .progressBarStyleHorizontal)
            }
            mProgressParams!!.progressHeight = CircleDimen.PROGRESS_HEIGHT_HORIZONTAL
        } else {
            if (progressDrawableId != 0) {
                mProgressBar = ProgressBar(getContext())
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    mProgressBar!!.setIndeterminateDrawableTiled(context.getDrawable(progressDrawableId))
                else
                    mProgressBar!!.indeterminateDrawable = context.resources.getDrawable(progressDrawableId)
            } else {
                mProgressBar = ProgressBar(getContext(), null, android.R.attr.progressBarStyle)
            }
            mProgressParams!!.progressHeight = CircleDimen.PROGRESS_HEIGHT_SPINNER
        }//旋转样式

        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, mProgressParams!!
                .progressHeight)
        val margins = mProgressParams!!.margins
        if (margins != null)
            layoutParams.setMargins(margins[0], margins[1], margins[2], margins[3])
        addView(mProgressBar, layoutParams)

        //构建文本
        val textView = ScaleTextView(getContext())
        textView.textSize = mProgressParams!!.textSize.toFloat()
        textView.setTextColor(mProgressParams!!.textColor)
        val padding = mProgressParams!!.padding
        if (padding != null)
            textView.setAutoPadding(padding[0], padding[1], padding[2], padding[3])
        addView(textView)

        if (mProgressParams!!.style == ProgressParams.STYLE_HORIZONTAL) {
            mViewUpdateHandler = object : Handler() {
                override fun handleMessage(msg: Message) {
                    super.handleMessage(msg)
                    val progress = mProgressBar!!.progress
                    val max = mProgressBar!!.max
                    val percent = (progress.toFloat() / max.toFloat() * 100).toInt()
                    val args = percent.toString() + "%"
                    if (!TextUtils.isEmpty(mProgressParams!!.text) && mProgressParams!!.text.contains("%s"))
                        textView.text = String.format(mProgressParams!!.text, args)
                    else
                        textView.text = mProgressParams!!.text + args
                }
            }
        } else {
            textView.text = mProgressParams!!.text
        }
    }


    fun refreshProgress() {
        mProgressBar!!.max = mProgressParams!!.max
        mProgressBar!!.progress = mProgressParams!!.progress
        mProgressBar!!.secondaryProgress = mProgressParams!!.progress + 10
        onProgressChanged()
    }

    private fun onProgressChanged() {
        if (mViewUpdateHandler != null && !mViewUpdateHandler!!.hasMessages(0))
            mViewUpdateHandler!!.sendEmptyMessage(0)
    }

    companion object {

        /**
         * 直接设置对象属性值,无视private/protected修饰符,不经过setter函数.
         */
        private fun setFieldValue(`object`: Any, fieldName: String, value: Any) {
            val field = getDeclaredField(`object`, fieldName)
                    ?: throw IllegalArgumentException("Could not find field [" + fieldName + "] on " +
                            "target [" + `object` + "]")
            makeAccessible(field)
            try {
                field.set(`object`, value)
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }

        }

        /**
         * 循环向上转型,获取对象的DeclaredField.
         */
        protected fun getDeclaredField(`object`: Any, fieldName: String): Field? {
            return getDeclaredField(`object`.javaClass, fieldName)
        }

        /**
         * 循环向上转型,获取类的DeclaredField.
         */
        protected fun getDeclaredField(clazz: Class<*>, fieldName: String): Field? {
            var superClass: Class<*> = clazz
            while (superClass != Any::class.java) {
                try {
                    return superClass.getDeclaredField(fieldName)
                } catch (e: NoSuchFieldException) {
                    e.printStackTrace()// Field不在当前类定义,继续向上转型
                }

                superClass = superClass
                        .superclass
            }
            return null
        }

        /**
         * 强制转换fileld可访问.
         */
        protected fun makeAccessible(field: Field) {
            if (!Modifier.isPublic(field.modifiers) || !Modifier.isPublic(field
                            .declaringClass.modifiers)) {
                field.isAccessible = true
            }
        }
    }
}
