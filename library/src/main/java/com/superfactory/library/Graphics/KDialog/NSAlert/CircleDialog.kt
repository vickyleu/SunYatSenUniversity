package com.superfactory.library.Graphics.KDialog.NSAlert

import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.annotation.FloatRange
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentActivity
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import com.superfactory.library.Graphics.KDialog.callback.*
import com.superfactory.library.Graphics.KDialog.params.*
import com.superfactory.library.Graphics.KDialog.view.listener.OnInputClickListener


/**
 * Created by hupei on 2017/3/29.
 */

class CircleDialog private constructor() {
    private var mDialog: AbsCircleDialog? = null
    private var params: CircleParams? = null
    private var mActivity: FragmentActivity? = null

    fun create(params: CircleParams, mActivity: FragmentActivity?): DialogFragment {
        if (mDialog == null)
            mDialog = AbsCircleDialog.newAbsCircleDialog(params)
        else {
            if (mDialog != null && mDialog!!.dialog != null && mDialog!!.dialog.isShowing) {
                mDialog!!.refreshView()
            }
        }
        this.params = params
        this.mActivity = mActivity
        return mDialog!!
    }

    fun dismiss() {
        if (params?.dialogFragment != null) {
            params?.dialogFragment?.dismiss()
            mActivity = null
            params?.dialogFragment = null
        }
    }

    fun show(activity: FragmentActivity?) {
        mDialog!!.show(activity!!.supportFragmentManager, "circleDialog")
    }

    class Builder(private var mActivity: FragmentActivity?) {
        private var mCircleDialog: CircleDialog? = null
        private val mCircleParams: CircleParams
        private var negativeInterrupter: Interrupter? = null
        private var positiveInterrupter: Interrupter? = null

        init {
            mCircleParams = CircleParams()
            mCircleParams.dialogParams = DialogParams()
        }

        /**
         * 设置对话框位置
         *
         * @param gravity 位置
         * @return builder
         */
        fun setGravity(gravity: Int): Builder {
            mCircleParams.dialogParams?.gravity = gravity
            return this
        }

        /**
         * 设置对话框点击外部关闭
         *
         * @param cancel true允许
         * @return Builder
         */
        fun setCanceledOnTouchOutside(cancel: Boolean): Builder {
            mCircleParams.dialogParams?.canceledOnTouchOutside = cancel
            return this
        }

        /**
         * 设置对话框返回键关闭
         *
         * @param cancel true允许
         * @return Builder
         */
        fun setCancelable(cancel: Boolean): Builder {
            mCircleParams.dialogParams?.cancelable = cancel
            return this
        }

        /**
         * 设置对话框宽度
         *
         * @param width 0.0 - 1.0
         * @return Builder
         */
        fun setWidth(@FloatRange(from = 0.0, to = 1.0) width: Float): Builder {
            mCircleParams.dialogParams?.width = width
            return this
        }

        /**
         * 设置对话框圆角
         *
         * @param radius 半径
         * @return Builder
         */
        fun setRadius(radius: Int): Builder {
            mCircleParams.dialogParams?.radius = radius
            return this
        }

        fun configDialog(configDialog: ConfigDialog): Builder {
            configDialog.onConfig(mCircleParams.dialogParams!!)
            return this
        }

        fun setTitle(text: String): Builder {
            newTitleParams()
            mCircleParams.titleParams?.text = text
            return this
        }

        fun setTitleColor(@ColorInt color: Int): Builder {
            newTitleParams()
            mCircleParams.titleParams?.textColor = color
            return this
        }

        fun configTitle(configTitle: ConfigTitle): Builder {
            newTitleParams()
            configTitle.onConfig(mCircleParams.titleParams!!)
            return this
        }

        private fun newTitleParams() {
            if (mCircleParams.titleParams == null)
                mCircleParams.titleParams = TitleParams()
        }

        fun setText(text: String): Builder {
            newTextParams()
            mCircleParams.textParams?.text = text
            return this
        }

        fun setTextColor(@ColorInt color: Int): Builder {
            newTextParams()
            mCircleParams.textParams?.textColor = color
            return this
        }

        fun configText(configText: ConfigText): Builder {
            newTextParams()
            configText.onConfig(mCircleParams.textParams!!)
            return this
        }

        private fun newTextParams() {
            //判断是否已经设置过
            if (mCircleParams.dialogParams?.gravity === Gravity.NO_GRAVITY)
                mCircleParams.dialogParams?.gravity = Gravity.CENTER
            if (mCircleParams.textParams == null)
                mCircleParams.textParams = TextParams()
        }

        fun setItems(items: Any, listener: AdapterView.OnItemClickListener): Builder {
            newItemsParams()
            val params = mCircleParams.itemsParams
            params?.items = items
            params?.listener = listener
            return this
        }


        fun configItems(configItems: ConfigItems): Builder {
            newItemsParams()
            configItems.onConfig(mCircleParams.itemsParams!!)
            return this
        }

        private fun newItemsParams() {
            //设置列表特殊的参数
            val dialogParams = mCircleParams.dialogParams
            //判断是否已经设置过
            if (dialogParams?.gravity === Gravity.NO_GRAVITY)
                dialogParams.gravity = Gravity.BOTTOM//默认底部显示
            //判断是否已经设置过
            if (dialogParams?.yOff === 0)
                dialogParams.yOff = 20//底部与屏幕的距离

            if (mCircleParams.itemsParams == null)
                mCircleParams.itemsParams = object : ItemsParams() {
                    override fun dismiss() {
                        onDismiss()
                    }
                }
        }

        /**
         * 设置进度条文本
         *
         * @param text 进度条文本，style = 水平样式时，支持String.format() 例如：已经下载%s
         * @return
         */
        fun setProgressText(text: String): Builder {
            newProgressParams()
            mCircleParams.progressParams?.text = text
            return this
        }

        /**
         * 设置进度条样式
         *
         * @param style [水平样式][ProgressParams.STYLE_HORIZONTAL] or
         * [ProgressParams.STYLE_SPINNER]
         * @return
         */
        fun setProgressStyle(style: Int): Builder {
            newProgressParams()
            mCircleParams.progressParams?.style = style
            return this
        }

        fun setProgress(max: Int, progress: Int): Builder {
            newProgressParams()
            val progressParams = mCircleParams.progressParams
            progressParams?.max = max
            progressParams?.progress = progress
            return this
        }

        fun setProgressDrawable(@DrawableRes progressDrawableId: Int): Builder {
            newProgressParams()
            mCircleParams.progressParams?.progressDrawableId = progressDrawableId
            return this
        }

        fun setProgressHeight(height: Int): Builder {
            newProgressParams()
            mCircleParams.progressParams?.progressHeight = height
            return this
        }

        fun configProgress(configProgress: ConfigProgress): Builder {
            newProgressParams()
            configProgress.onConfig(mCircleParams.progressParams!!)
            return this
        }

        private fun newProgressParams() {
            //判断是否已经设置过
            if (mCircleParams.dialogParams?.gravity === Gravity.NO_GRAVITY)
                mCircleParams.dialogParams?.gravity = Gravity.CENTER
            if (mCircleParams.progressParams == null)
                mCircleParams.progressParams = ProgressParams()
        }

        fun setInputHint(text: String): Builder {
            newInputParams()
            mCircleParams.inputParams?.hintText = text
            return this
        }

        fun setInputHeight(height: Int): Builder {
            newInputParams()
            mCircleParams.inputParams?.inputHeight = height
            return this
        }

        fun configInput(configInput: ConfigInput): Builder {
            newInputParams()
            configInput.onConfig(mCircleParams.inputParams!!)
            return this
        }

        private fun newInputParams() {
            //判断是否已经设置过
            if (mCircleParams.dialogParams?.gravity === Gravity.NO_GRAVITY)
                mCircleParams.dialogParams?.gravity = Gravity.CENTER
            if (mCircleParams.inputParams == null)
                mCircleParams.inputParams = InputParams()
        }

        fun setPositive(text: String, listener: View.OnClickListener?): Builder {
            return setPositive(text, listener, null)
        }

        fun setPositive(text: String, listener: View.OnClickListener?, interrupter: Interrupter?): Builder {
            newPositiveParams()
            val params = mCircleParams.positiveParams
            params?.text = text
            params?.listener = listener
            this.positiveInterrupter = interrupter
            return this
        }

        fun setPositiveInput(text: String, listener: OnInputClickListener?): Builder {
            newPositiveParams()
            val params = mCircleParams.positiveParams
            params?.text = text
            params?.inputListener = listener
            return this
        }

        fun configPositive(configButton: ConfigButton): Builder {
            newPositiveParams()
            configButton.onConfig(mCircleParams.positiveParams!!)
            return this
        }

        private fun newPositiveParams() {
            if (mCircleParams.positiveParams == null)
                mCircleParams.positiveParams = object : ButtonParams() {
                    override fun dismiss() {
                        if (positiveInterrupter != null) {
                            positiveInterrupter?.dismissMission(mCircleDialog?.mDialog?.getInputText(), mCircleDialog!!, mCircleDialog?.mDialog?.getInputView())
                            return
                        }
                        onDismiss()
                    }
                }
        }

        fun setNegative(text: String, listener: View.OnClickListener?): Builder {
            return setNegative(text, listener, null)
        }

        fun setNegative(text: String, listener: View.OnClickListener?, interrupter: Interrupter?): Builder {
            newNegativeParams()
            val params = mCircleParams.negativeParams
            params?.text = text
            params?.listener = listener
            this.negativeInterrupter = interrupter
            return this
        }


        fun configNegative(configButton: ConfigButton): Builder {
            newNegativeParams()
            configButton.onConfig(mCircleParams.negativeParams!!)
            return this
        }

        private fun newNegativeParams() {
            if (mCircleParams.negativeParams == null)
                mCircleParams.negativeParams = object : ButtonParams() {
                    override fun dismiss() {
                        if (negativeInterrupter != null) {
                            negativeInterrupter?.dismissMission(mCircleDialog?.mDialog?.getInputText(), mCircleDialog!!, mCircleDialog?.mDialog?.getInputView())
                            return
                        }
                        onDismiss()
                    }
                }
        }

        private fun onDismiss() {
            if (mCircleParams.dialogFragment != null) {
                mCircleParams.dialogFragment?.dismiss()
                mActivity = null
                mCircleParams.dialogFragment = null
            }
            if (mCircleDialog != null) {
                mCircleDialog?.mActivity = null
                mCircleDialog?.params = null
            }
        }

        fun create(): DialogFragment {
            if (mCircleDialog == null)
                mCircleDialog = CircleDialog()
            return mCircleDialog!!.create(mCircleParams, mActivity)
        }

        fun show(): DialogFragment {
            val dialogFragment = create()
            mCircleDialog!!.show(mActivity)
            return dialogFragment
        }
    }
}
