package com.superfactory.library.Bridge.Adapt

import android.animation.ValueAnimator
import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View


/**
 * Created by vicky on 2018.02.02.
 *
 * @Author vicky
 * @Date 2018年02月02日  16:58:40
 * @ClassName 这里输入你的类名(或用途)
 */
class AppBarLayoutOverScrollViewBehavior : AppBarLayout.Behavior {
    companion object {
        val overScroll = "overScroll"
    }

    private val TARGET_HEIGHT = 500f
    private var mTargetView: View? = null
    private var mParentHeight: Int = 0
    private var mTargetViewHeight: Int = 0
    private var mTotalDy: Float = 0.toFloat()
    private var mLastScale: Float = 0.toFloat()
    private var mLastBottom: Int = 0
    private var isAnimate: Boolean = false

    constructor() {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}


    override fun onLayoutChild(parent: CoordinatorLayout?, abl: AppBarLayout, layoutDirection: Int): Boolean {
        val handled = super.onLayoutChild(parent, abl, layoutDirection)
        // 需要在调用过super.onLayoutChild()方法之后获取
        if (mTargetView == null) {
            mTargetView = parent!!.findViewWithTag(overScroll)
            if (mTargetView != null) {
                initial(abl)
            }
        }
        return handled
    }

    override fun onStartNestedScroll(parent: CoordinatorLayout, child: AppBarLayout, directTargetChild: View, target: View, nestedScrollAxes: Int, type: Int): Boolean {
        isAnimate = true
        return super.onStartNestedScroll(parent, child, directTargetChild, target, nestedScrollAxes, type)
    }


    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: AppBarLayout, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        if (mTargetView != null && (dy < 0 && child.bottom >= mParentHeight || dy > 0 && child.bottom > mParentHeight)) {
            scale(child, target, dy)
        } else {
            super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        }
    }

    override fun onNestedPreFling(coordinatorLayout: CoordinatorLayout, child: AppBarLayout, target: View, velocityX: Float, velocityY: Float): Boolean {
        if (velocityY > 100) {
            isAnimate = false
        }
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY)
    }

    override fun onStopNestedScroll(coordinatorLayout: CoordinatorLayout, abl: AppBarLayout, target: View, type: Int) {
        recovery(abl)
        super.onStopNestedScroll(coordinatorLayout, abl, target, type)
    }

    private fun initial(abl: AppBarLayout) {
        abl.clipChildren = false
        mParentHeight = abl.height
        mTargetViewHeight = mTargetView!!.height
    }

    private fun scale(abl: AppBarLayout, target: View, dy: Int) {
        mTotalDy += (-dy).toFloat()
        mTotalDy = Math.min(mTotalDy, TARGET_HEIGHT)
        mLastScale = Math.max(1f, 1f + mTotalDy / TARGET_HEIGHT)
        ViewCompat.setScaleX(mTargetView!!, mLastScale)
        ViewCompat.setScaleY(mTargetView!!, mLastScale)
        mLastBottom = mParentHeight + (mTargetViewHeight / 2 * (mLastScale - 1)).toInt()
        abl.bottom = mLastBottom
        target.scrollY = 0
    }

    private fun recovery(abl: AppBarLayout) {
        if (mTotalDy > 0) {
            mTotalDy = 0f
            if (isAnimate) {
                val anim = ValueAnimator.ofFloat(mLastScale, 1f).setDuration(200)
                anim.addUpdateListener { animation ->
                    val value = animation.animatedValue as Float
                    ViewCompat.setScaleX(mTargetView!!, value)
                    ViewCompat.setScaleY(mTargetView!!, value)
                    abl.bottom = (mLastBottom - (mLastBottom - mParentHeight) * animation.animatedFraction).toInt()
                }
                anim.start()
            } else {
                ViewCompat.setScaleX(mTargetView!!, 1f)
                ViewCompat.setScaleY(mTargetView!!, 1f)
                abl.bottom = mParentHeight
            }
        }
    }
}