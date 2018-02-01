package com.superfactory.library.Graphics.KDialog.NSAlert

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.annotation.FloatRange
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.util.DisplayMetrics
import android.view.*
import com.superfactory.library.Graphics.KDialog.res.drawable.CircleDrawable
import com.superfactory.library.Graphics.KDialog.res.values.CircleDimen
import com.superfactory.library.Graphics.KDialog.scale.ScaleUtils


/**
 * Created by hupei on 2017/3/29.
 */

abstract class BaseCircleDialog : DialogFragment() {

    private var mGravity = Gravity.CENTER//对话框的位置
    private var mCanceledOnTouchOutside = true//是否触摸外部关闭
    private var mCanceledBack = true//是否返回键关闭
    private var mWidth = 0.9f//对话框宽度，范围：0-1；1整屏宽
    private var mPadding: IntArray? = null//对话框与屏幕边缘距离
    private var mAnimStyle: Int = 0//显示动画
    private var isDimEnabled = true
    private var mBackgroundColor = Color.TRANSPARENT//对话框的背景色
    private var mRadius = CircleDimen.RADIUS//对话框的圆角半径
    private var mAlpha = 1f//对话框透明度，范围：0-1；1不透明
    private var mX: Int = 0
    private var mY: Int = 0

    abstract fun createView(context: Context, inflater: LayoutInflater?, container: ViewGroup?): View?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置 无标题 无边框
        setStyle(DialogFragment.STYLE_NO_TITLE, 0)
        if (savedInstanceState != null) {
            mGravity = savedInstanceState.getInt(SAVED_GRAVITY)
            mCanceledOnTouchOutside = savedInstanceState.getBoolean(SAVED_TOUCH_OUT)
            mCanceledBack = savedInstanceState.getBoolean(SAVED_CANCELED_BACK)
            mWidth = savedInstanceState.getFloat(SAVED_WIDTH)
            mPadding = savedInstanceState.getIntArray(SAVED_PADDING)
            mAnimStyle = savedInstanceState.getInt(SAVED_ANIM_STYLE)
            isDimEnabled = savedInstanceState.getBoolean(SAVED_DIM_ENABLED)
            mBackgroundColor = savedInstanceState.getInt(SAVED_BACKGROUND_COLOR)
            mRadius = savedInstanceState.getInt(SAVED_RADIUS)
            mAlpha = savedInstanceState.getFloat(SAVED_ALPHA)
            mX = savedInstanceState.getInt(SAVED_X)
            mY = savedInstanceState.getInt(SAVED_Y)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.putInt(SAVED_GRAVITY, mGravity)
        outState.putBoolean(SAVED_TOUCH_OUT, mCanceledOnTouchOutside)
        outState.putBoolean(SAVED_CANCELED_BACK, mCanceledBack)
        outState.putFloat(SAVED_WIDTH, mWidth)
        if (mPadding != null) outState.putIntArray(SAVED_PADDING, mPadding)
        outState.putInt(SAVED_ANIM_STYLE, mAnimStyle)
        outState.putBoolean(SAVED_DIM_ENABLED, isDimEnabled)
        outState.putInt(SAVED_BACKGROUND_COLOR, mBackgroundColor)
        outState.putInt(SAVED_RADIUS, mRadius)
        outState.putFloat(SAVED_ALPHA, mAlpha)
        outState.putInt(SAVED_X, mX)
        outState.putInt(SAVED_Y, mY)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = createView(context, inflater, container) ?: return null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.background = CircleDrawable(mBackgroundColor, mRadius)
        } else {
            view.setBackgroundDrawable(CircleDrawable(mBackgroundColor, mRadius))
        }
        view.alpha = mAlpha
        return view
    }


    override fun onStart() {
        val dialog = dialog
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(mCanceledOnTouchOutside)
            dialog.setCancelable(mCanceledBack)
            setDialogGravity(dialog)//设置对话框布局
        }
        super.onStart()
    }

    /**
     * 设置对话框底部显示
     *
     * @param dialog
     */
    private fun setDialogGravity(dialog: Dialog) {
        // 设置宽度为屏宽、靠近屏幕底部。
        val window = dialog.window
        window!!.setBackgroundDrawableResource(android.R.color.transparent)
        val wlp = window.attributes
        val dm = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(dm)//获取屏幕宽
        wlp.width = (dm.widthPixels * mWidth).toInt()//宽度按屏幕大小的百分比设置
        wlp.gravity = mGravity
        wlp.x = mX
        wlp.y = mY
        //边距
        if (mPadding != null) {
            val padding = mPadding
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT
            window.decorView.setPadding(ScaleUtils.scaleValue(padding!![0]), ScaleUtils
                    .scaleValue(padding[1]), ScaleUtils.scaleValue(padding[2]), ScaleUtils
                    .scaleValue(padding[3]))
        }
        //动画
        if (mAnimStyle != 0) window.setWindowAnimations(mAnimStyle)

        if (isDimEnabled)
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        else
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        window.attributes = wlp
    }

    override fun show(manager: FragmentManager, tag: String) {
        if (!isAdded) {
            val transaction = manager.beginTransaction()
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            transaction.add(this, tag)
            transaction.commitAllowingStateLoss()
        }
    }

    fun remove() {
        val ft = fragmentManager.beginTransaction()
        ft.remove(this)
        ft.addToBackStack(null)
    }

    /**
     * 设置对话框位置
     * [默认][Gravity.CENTER]
     *
     * @param gravity 位置
     */
    protected fun setGravity(gravity: Int) {
        mGravity = gravity
    }

    /**
     * 设置对话框点击外部关闭
     *
     * @param cancel true允许
     */
    protected fun setCanceledOnTouchOutside(cancel: Boolean) {
        mCanceledOnTouchOutside = cancel
    }

    /**
     * 设置对话框返回键关闭关闭
     *
     * @param cancel true允许
     */
    protected fun setCanceledBack(cancel: Boolean) {
        mCanceledBack = cancel
    }

    /**
     * 设置对话框宽度
     *
     * @param width 0.0 - 1.0
     */
    protected fun setWidth(@FloatRange(from = 0.0, to = 1.0) width: Float) {
        mWidth = width
    }

    /**
     * 设置边距
     *
     * @param left   px
     * @param top    px
     * @param right  px
     * @param bottom px
     */
    protected fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        mPadding = intArrayOf(left, top, right, bottom)
    }

    /**
     * 弹出对话框的动画
     *
     * @param animStyle StyleRes
     */
    protected fun setAnimations(animStyle: Int) {
        mAnimStyle = animStyle
    }


    /**
     * 设置背景是否昏暗，默认true
     *
     * @param dimEnabled true昏暗
     */
    protected fun setDimEnabled(dimEnabled: Boolean) {
        isDimEnabled = dimEnabled
    }

    /**
     * 设置对话框背景色
     *
     * @param color 颜色值
     */
    protected fun setBackgroundColor(@ColorInt color: Int) {
        mBackgroundColor = color
    }

    /**
     * 设置对话框圆角
     *
     * @param radius 半径
     */
    protected fun setRadius(radius: Int) {
        mRadius = radius
    }

    /**
     * 设置对话框透明度
     *
     * @param alpha 0.0 - 1.0
     */
    protected fun setAlpha(@FloatRange(from = 0.0, to = 1.0) alpha: Float) {
        mAlpha = alpha
    }

    protected fun setX(x: Int) {
        mX = x
    }

    protected fun setY(y: Int) {
        mY = y
    }

    abstract fun getInputText(): String?
    open fun getInputView(): View? {
        return null
    }

    companion object {

        private val SAVED_GRAVITY = "circle:baseGravity"
        private val SAVED_TOUCH_OUT = "circle:baseTouchOut"
        private val SAVED_CANCELED_BACK = "circle:baseCanceledBack"
        private val SAVED_WIDTH = "circle:baseWidth"
        private val SAVED_PADDING = "circle:basePadding"
        private val SAVED_ANIM_STYLE = "circle:baseAnimStyle"
        private val SAVED_DIM_ENABLED = "circle:baseDimEnabled"
        private val SAVED_BACKGROUND_COLOR = "circle:baseBackgroundColor"
        private val SAVED_RADIUS = "circle:baseRadius"
        private val SAVED_ALPHA = "circle:baseAlpha"
        private val SAVED_X = "circle:baseX"
        private val SAVED_Y = "circle:baseY"
    }
}
