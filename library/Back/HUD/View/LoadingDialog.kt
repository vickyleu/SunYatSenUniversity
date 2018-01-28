package com.superfactory.library.Graphics.HUD.View

import android.app.Dialog
import android.content.Context
import android.os.Handler
import android.os.Parcelable
import android.support.annotation.ColorInt
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.superfactory.library.Bridge.Anko.BindingComponent


import com.superfactory.library.Graphics.HUD.manager.StyleManager
import com.superfactory.library.R
import org.jetbrains.anko.AnkoContextImpl

import java.util.ArrayList


/**
 * Created by Luo on 2016/9/23.
 * desc:加载等待的Dialog
 */
abstract class LoadingDialog(context: Context): FinishDrawListener {
    val TAG = "LoadingDialog"


    private var mContext: Context? = null

    private var mLoadingView: LVCircularRing? = null
    private var mLoadingDialog: Dialog? = null
    private var layout: LinearLayout? = null
    private var loadingText: TextView? = null
    private var mSuccessView: RightDiaView? = null
    private var mFailedView: WrongDiaView? = null

    private var loadSuccessStr: String? = null
    private var loadFailedStr: String? = null
    private var viewList: MutableList<View>? = null

    private var interceptBack = true
    private var openSuccessAnim = true
    private var openFailedAnim = true
    /**
     * 返回当前绘制的速度
     *
     * @return 速度
     */
    var speed = 1
        private set
    private var time: Long = 1000
    private var loadStyle = STYLE_RING
    private var mCircleLoadView: LoadCircleView? = null

    private var o: OnFinshListener? = null

    private val h = Handler(Handler.Callback {
        this@LoadingDialog.close()
        if (o != null) o!!.onFinish()
        false
    })

    enum class Speed {
        SPEED_ONE,
        SPEED_TWO
    }

    init {
        mContext = context
        // 首先得到整个View
        val view = LayoutInflater.from(context).inflate(
                R.layout.loading_dialog_view, null)
        initView(view)
        // 创建自定义样式的Dialog
        mLoadingDialog = object : Dialog(context, R.style.loading_dialog) {
            override fun onBackPressed() {
                if (interceptBack) {
                    return
                }
                this@LoadingDialog.close()
            }
        }
        // 设置返回键无效
        mLoadingDialog!!.setCancelable(!interceptBack)
        mLoadingDialog!!.setContentView(layout!!, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT))
        mLoadingDialog!!.setOnDismissListener { mContext = null }
        initStyle()
    }

    protected  open fun newComponent(v: V?): BindingComponent<A, V>{

    }

    abstract fun newViewModel(): V

    private fun initView(view: View) {
        layout = view.findViewById<View>(R.id.dialog_view) as LinearLayout
        mLoadingView = view.findViewById<View>(R.id.lv_circularring) as LVCircularRing
        loadingText = view.findViewById<View>(R.id.loading_text) as TextView
        mSuccessView = view.findViewById<View>(R.id.rdv_right) as RightDiaView
        mFailedView = view.findViewById<View>(R.id.wv_wrong) as WrongDiaView
        mCircleLoadView = view.findViewById<View>(R.id.lcv_circleload) as LoadCircleView
        initData()
    }

    private fun initData() {
        viewList = ArrayList()
        viewList?.add(mLoadingView!!)
        viewList?.add(mSuccessView!!)
        viewList?.add(mFailedView!!)
        viewList?.add(mCircleLoadView!!)

        mSuccessView?.setOnDrawFinishListener(this)
        mFailedView!!.setOnDrawFinishListener(this)
    }

    override fun dispatchFinishEvent(v: View) {
        if (v is WrongDiaView) {
            h.sendEmptyMessageDelayed(2, time)
        } else {
            h.sendEmptyMessageDelayed(1, time)
        }
    }

    private fun hideAll() {
        for (v in viewList!!) {
            if (v.visibility != View.GONE) {
                v.visibility = View.GONE
            }
        }
    }

    private fun setParams(size: Int) {
        if (size < 0) return
        val successParams = mSuccessView!!.layoutParams
        successParams.height = size
        successParams.width = size
        mSuccessView!!.layoutParams = successParams

        val failedParams = mFailedView!!.layoutParams
        failedParams.height = size
        failedParams.width = size
        mFailedView!!.layoutParams = failedParams

        val loadingParams = mLoadingView!!.layoutParams
        loadingParams.height = size
        loadingParams.width = size
    }


    private fun initStyle() {
        if (s != null) {
            setInterceptBack(s!!.isInterceptBack)
            setRepeatCount(s!!.repeatTime)
            setParams(s!!.contentSize)
            setTextSize(s!!.textSize.toFloat())
            setShowTime(s!!.showTime)
            if (!s!!.isOpenAnim) {
                closeFailedAnim()
                closeSuccessAnim()
            }
            setLoadingText(s!!.loadText)
            setSuccessText(s!!.successText)
            setFailedText(s!!.failedText)
            setLoadStyle(s!!.loadStyle)
        }
    }

    //----------------------------------对外提供的api------------------------------//

    /**
     * please invoke show() method at last,because it's
     * return value is void
     * 请在最后调用show，因此show返回值为void会使链式api断开
     */
    fun show() {
        hideAll()
        if (loadStyle == STYLE_RING) {
            mLoadingView!!.visibility = View.VISIBLE
            mCircleLoadView!!.visibility = View.GONE
            mLoadingDialog!!.show()
            mLoadingView!!.startAnim()
            Log.e("show", "style_ring")
        } else if (loadStyle == STYLE_LINE) {
            mCircleLoadView!!.visibility = View.VISIBLE
            mLoadingView!!.visibility = View.GONE
            mLoadingDialog!!.show()
            Log.e("show", "style_line")
        }
    }

    /**
     * set load style
     * 设置load的样式，目前支持转圈圈和菊花转圈圈
     *
     * @param style
     */
    fun setLoadStyle(style: Int): LoadingDialog {
        if (style >= 3) {
            throw IllegalArgumentException("Your style is wrong!")
        }
        this.loadStyle = style
        return this
    }

    /**
     * 让这个dialog消失，在拦截back事件的情况下一定要调用这个方法！
     * 在调用了该方法之后如需再次使用loadingDialog，请新创建一个
     * LoadingDialog对象。
     */
    fun close() {
        viewList!!.clear()
        h.removeCallbacksAndMessages(null)
        if (mLoadingDialog != null) {
            mLoadingView!!.stopAnim()
            mLoadingDialog!!.dismiss()
            mLoadingDialog = null
        }
    }

    /**
     * 设置加载时的文字提示
     *
     * @param msg 文字
     * @return 这个对象
     */
    fun setLoadingText(msg: String?): LoadingDialog {
        if (msg != null && msg.length > 0)
            loadingText!!.text = msg
        return this
    }

    /**
     * 设置加载成功的文字提示
     *
     * @param msg 文字
     * @return 这个对象
     */
    fun setSuccessText(msg: String?): LoadingDialog {
        if (msg != null && msg.length > 0)
            loadSuccessStr = msg
        return this
    }

    /**
     * 设置加载失败的文字提示
     *
     * @param msg 文字
     * @return 这个对象
     */
    fun setFailedText(msg: String?): LoadingDialog {
        if (msg != null && msg.length > 0) loadFailedStr = msg
        return this
    }

    /**
     * when you need a successful feedback,please invoke
     * this method in success's callback
     * 当你需要一个成功的反馈的时候，在加载成功的回调中调用此方法
     */
    fun loadSuccess() {
        mLoadingView!!.stopAnim()
        hideAll()
        mSuccessView!!.setDrawDynamic(openSuccessAnim)
        mSuccessView!!.visibility = View.VISIBLE
        loadingText!!.text = loadSuccessStr
    }

    /**
     * when you need a fail feedback,please invoke this
     * method in failed callback
     * 当你需要一个失败的反馈的时候，在加载失败的回调中调用此方法
     */
    fun loadFailed() {
        mLoadingView!!.stopAnim()
        hideAll()
        mFailedView!!.setDrawDynamic(openFailedAnim)
        mFailedView!!.visibility = View.VISIBLE
        loadingText!!.text = loadFailedStr
    }

    /**
     * 关闭动态绘制
     */
    fun closeSuccessAnim(): LoadingDialog {
        this.openSuccessAnim = false
        return this
    }

    /**
     * 关闭动态绘制
     */
    fun closeFailedAnim(): LoadingDialog {
        this.openFailedAnim = false
        return this
    }

    /**
     * 设置是否拦截back，默认会拦截
     *
     * @param interceptBack true拦截back，false不拦截
     * @return 这个对象
     */
    fun setInterceptBack(interceptBack: Boolean): LoadingDialog {
        this.interceptBack = interceptBack
        mLoadingDialog!!.setCancelable(!interceptBack)
        return this
    }

    /**
     * 当前dialog是否拦截back事件
     *
     * @return 如果拦截返回true，反之false
     */
    fun getInterceptBack(): Boolean {
        return interceptBack
    }

    /**
     * 使用该方法改变成功和失败绘制的速度
     *
     * @param speed 绘制速度
     * @return 这个对象
     */
    fun setLoadSpeed(speed: Speed): LoadingDialog {
        if (speed == Speed.SPEED_ONE) {
            this.speed = 1
            mSuccessView!!.setSpeed(1)
            mFailedView!!.setSpeed(1)
        } else {
            this.speed = 2
            mSuccessView!!.setSpeed(2)
            mFailedView!!.setSpeed(2)
        }
        return this
    }

    /**
     * 此方法改变成功失败绘制的颜色，此方法增加了处理的复杂性，暂时不公开此方法。
     * 而且暂时没有做到方便调用，真的要用的话十分的麻烦，暂时隐藏，后续不确定是否公开。
     */
    private fun setDrawColor(@ColorInt color: Int): LoadingDialog {
        mFailedView!!.setDrawColor(color)
        mSuccessView!!.setDrawColor(color)
        loadingText!!.setTextColor(color)
        mLoadingView!!.setColor(color)
        return this
    }

    /**
     * 设置中间弹框的尺寸
     *
     * @param size 尺寸，单位px
     * @return 这个对象
     */
    fun setSize(size: Int): LoadingDialog {
        //        int dip = SizeUtils.px2dip(mContext, size);
        if (size <= 50) return this
        setParams(size)
        return this
    }

    /**
     * 设置重新绘制的次数，默认只绘制一次，如果你设置这个
     * 数值为1，那么在绘制一次过后，还会再次绘制一次。
     *
     * @param count 绘制次数
     * @return 这个对象
     */
    fun setRepeatCount(count: Int): LoadingDialog {
        mFailedView!!.setRepeatTime(count)
        mSuccessView!!.setRepeatTime(count)
        return this
    }

    /**
     * 设置反馈展示时间
     *
     * @param time 时间
     * @return 这个对象
     */
    fun setShowTime(time: Long): LoadingDialog {
        if (time < 0) return this
        this.time = time
        return this
    }

    /**
     * set the size of load text size
     * 设置加载字体大小
     *
     * @param size 尺寸，单位sp，来将sp转换为对应的px值
     * @return 这个对象
     */
    fun setTextSize(size: Float): LoadingDialog {
        if (size < 0) return this
        loadingText!!.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
        return this
    }

    /**
     * dispatch the event when draw finish
     * 传递绘制完成的事件
     *
     * @param o 回调接口
     */
    fun setOnFinishListener(o: OnFinshListener) {
        this.o = o
    }

//    /**
//     * 监听器
//     */
//    interface OnFinishListener {
//        fun onFinish()
//    }

    companion object {
        val STYLE_RING = 0
        val STYLE_LINE = 1

        private var s: StyleManager? = StyleManager(true, 0, Speed.SPEED_TWO, -1, -1, 1000L,
                true, "加载中...", "加载成功", "加载失败")

        fun initStyle(style: StyleManager?) {
            if (style != null)
                s = style
        }
    }


}