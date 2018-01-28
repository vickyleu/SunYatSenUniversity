package com.superfactory.library.Graphics.HUD

import android.app.Dialog
import android.content.Context
import android.os.Handler
import android.support.annotation.ColorInt
import android.util.TypedValue
import android.view.View
import com.superfactory.library.Graphics.HUD.Base.AnkoHUD
import com.superfactory.library.Graphics.HUD.Base.LoadingHUDComponent
import com.superfactory.library.Graphics.HUD.Base.LoadingHudModel
import com.superfactory.library.Graphics.HUD.Base.OnFinishListener
import com.superfactory.library.Graphics.HUD.View.FinishDrawListener
import com.superfactory.library.Graphics.HUD.View.LoadingDialog
import com.superfactory.library.Graphics.HUD.View.WrongDiaView
import com.superfactory.library.Graphics.HUD.manager.StyleManager

/**
 * Created by vicky on 2018/1/28.
 */
class LoadingHUD(ctx: Context, private val config: LoadingHudModel?) : AnkoHUD<LoadingHudModel, LoadingHUD>(ctx), FinishDrawListener {
    enum class Speed {
        SPEED_ONE,
        SPEED_TWO
    }

    private var o: OnFinishListener? = null
    private var time: Long = 1000
    private val h = Handler(Handler.Callback {
        this@LoadingHUD.close()
        if (o != null) o!!.onFinish()
        false
    })

    override fun dispatchFinishEvent(v: View) {
        if (v is WrongDiaView) {
            h.sendEmptyMessageDelayed(2, viewModel?.time ?: 0)
        } else {
            h.sendEmptyMessageDelayed(1, viewModel?.time ?: 0)
        }
    }

    override fun initView(view: View) {

    }

    override fun onLoadedDialog(hud: Dialog) {}

    override fun newComponent(v: LoadingHudModel?) = LoadingHUDComponent(v)

    override fun newViewModel(interceptBack: Array<Boolean>) = config?.setInterceptIfNeeded(interceptBack)
            ?: LoadingHudModel().setInterceptIfNeeded(interceptBack)

    



    //----------------------------------对外提供的api------------------------------//

    /**
     * please invoke show() method at last,because it's
     * return value is void
     * 请在最后调用show，因此show返回值为void会使链式api断开
     */
    fun show() {
        viewModel?.visibility?.value=true
    }

    /**
     * set load style
     * 设置load的样式，目前支持转圈圈和菊花转圈圈
     *
     * @param style
     */
    fun setLoadStyle(style: Int): LoadingHUD {
        if (style >= 3) {
            throw IllegalArgumentException("Your style is wrong!")
        }
        viewModel?.loadStyle = style
        return this
    }

    /**
     * 让这个dialog消失，在拦截back事件的情况下一定要调用这个方法！
     * 在调用了该方法之后如需再次使用loadingDialog，请新创建一个
     * LoadingDialog对象。
     */
    override fun close() {
        h.removeCallbacksAndMessages(null)
        if (hud() != null) {
            viewModel?.Animating?.value=false
        }
        super.close()
    }

//    fun close() {
//        viewList!!.clear()
//        h.removeCallbacksAndMessages(null)
//        if (mLoadingDialog != null) {
//            mLoadingView!!.stopAnim()
//            mLoadingDialog!!.dismiss()
//            mLoadingDialog = null
//        }
//    }

    /**
     * 设置加载时的文字提示
     *
     * @param msg 文字
     * @return 这个对象
     */
    fun setLoadingText(msg: String?): LoadingHUD {
        if (msg != null && msg.length > 0)
           viewModel?.loadingText?.value = msg
        return this
    }

    /**
     * 设置加载成功的文字提示
     *
     * @param msg 文字
     * @return 这个对象
     */
    fun setSuccessText(msg: String?): LoadingHUD {
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
    fun setFailedText(msg: String?): LoadingHUD {
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
    fun closeSuccessAnim(): LoadingHUD {
        this.openSuccessAnim = false
        return this
    }

    /**
     * 关闭动态绘制
     */
    fun closeFailedAnim(): LoadingHUD {
        this.openFailedAnim = false
        return this
    }

    /**
     * 设置是否拦截back，默认会拦截
     *
     * @param interceptBack true拦截back，false不拦截
     * @return 这个对象
     */
    fun setInterceptBack(interceptBack: Boolean): LoadingHUD {
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
    fun setLoadSpeed(speed: LoadingHUD.Speed): LoadingHUD {
        if (speed == LoadingHUD.Speed.SPEED_ONE) {
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
    private fun setDrawColor(@ColorInt color: Int): LoadingHUD {
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
    fun setSize(size: Int): LoadingHUD {
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
    fun setRepeatCount(count: Int): LoadingHUD {
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
    fun setShowTime(time: Long): LoadingHUD {
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
    fun setTextSize(size: Float): LoadingHUD {
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

    companion object {
        val STYLE_RING = 0
        val STYLE_LINE = 1

        private var s: StyleManager? = StyleManager(true, 0, LoadingDialog.Speed.SPEED_TWO, -1, -1, 1000L,
                true, "加载中...", "加载成功", "加载失败")

        fun initStyle(style: StyleManager?) {
            if (style != null)
                s = style
        }
    }


}