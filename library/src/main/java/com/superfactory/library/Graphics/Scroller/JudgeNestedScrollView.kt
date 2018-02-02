package com.superfactory.library.Graphics.Scroller

import android.content.Context
import android.support.v4.widget.NestedScrollView
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ViewConfiguration

/**
 * Created by vicky on 2018.02.02.
 *
 * @Author vicky
 * @Date 2018年02月02日  19:53:02
 * @ClassName 这里输入你的类名(或用途)
 */

/**
 * @Created SiberiaDante
 * @Describe：
 * @CreateTime: 2017/12/17
 * @UpDateTime:
 * @Email: 2654828081@qq.com
 * @GitHub: https://github.com/SiberiaDante
 */

class JudgeNestedScrollView : NestedScrollView {
    private var isNeedScroll = true
    private var xDistance: Float = 0.toFloat()
    private var yDistance: Float = 0.toFloat()
    private var xLast: Float = 0.toFloat()
    private var yLast: Float = 0.toFloat()
    private var scaledTouchSlop: Int = 0

    constructor(context: Context) : super(context, null) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, 0) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        scaledTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                yDistance = 0f
                xDistance = yDistance
                xLast = ev.x
                yLast = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
                val curX = ev.x
                val curY = ev.y

                xDistance += Math.abs(curX - xLast)
                yDistance += Math.abs(curY - yLast)
                xLast = curX
                yLast = curY
                Log.e("SiberiaDante", "xDistance ：$xDistance---yDistance:$yDistance")
                return !(xDistance > yDistance || yDistance < scaledTouchSlop) && isNeedScroll
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    /*
    改方法用来处理NestedScrollView是否拦截滑动事件
     */
    fun setNeedScroll(isNeedScroll: Boolean) {
        this.isNeedScroll = isNeedScroll
    }
}
