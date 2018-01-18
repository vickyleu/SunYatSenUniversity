package com.superfactory.library.Bridge.Adapt

import android.os.Build
import android.support.v4.view.GestureDetectorCompat
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.HapticFeedbackConstants
import android.view.MotionEvent
import android.view.View


/**
 * Created by AllenCoder on 2016/8/03.
 *
 *
 * This can be useful for applications that wish to implement various forms of click and longclick and childView click
 * manipulation of item views within the RecyclerView. SimpleClickListener may intercept
 * a touch interaction already in progress even if the SimpleClickListener is already handling that
 * gesture stream itself for the purposes of scrolling.
 *
 * @see RecyclerView.OnItemTouchListener
 */
abstract class SimpleClickListener : RecyclerView.OnItemTouchListener {
    private var mGestureDetector: GestureDetectorCompat? = null
    private var recyclerView: RecyclerView? = null
    private var childClickViewIds: Set<Int>? = null
    private var longClickViewIds: Set<Int>? = null
    protected var baseQuickAdapter: BaseAdapter<*, *>? = null
    private var mIsPrepressed = false
    private var mIsShowPress = false
    private var mPressedView: View? = null

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        if (recyclerView == null) {
            this.recyclerView = rv
            this.baseQuickAdapter = recyclerView!!.adapter as BaseAdapter<*, *>
            mGestureDetector = GestureDetectorCompat(recyclerView!!.context, ItemTouchHelperGestureListener(recyclerView!!))
        }
        if (!mGestureDetector!!.onTouchEvent(e) && e.actionMasked == MotionEvent.ACTION_UP && mIsShowPress) {
            if (mPressedView != null) {
                val vh = recyclerView!!.getChildViewHolder(mPressedView!!) as BaseViewHolder
                if (vh == null) {
                    mPressedView!!.isPressed = false
                }
            }
            mIsShowPress = false
            mIsPrepressed = false
        }
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        mGestureDetector!!.onTouchEvent(e)
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

    private inner class ItemTouchHelperGestureListener(private val recyclerView: RecyclerView) : GestureDetector.SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent): Boolean {
            mIsPrepressed = true
            mPressedView = recyclerView.findChildViewUnder(e.x, e.y)

            super.onDown(e)
            return false
        }

        override fun onShowPress(e: MotionEvent) {
            if (mIsPrepressed && mPressedView != null) {
                //                mPressedView.setPressed(true);
                mIsShowPress = true
            }
            super.onShowPress(e)
        }

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            if (mIsPrepressed && mPressedView != null) {
                if (recyclerView.scrollState != RecyclerView.SCROLL_STATE_IDLE) {
                    return false
                }
                val pressedView = mPressedView
                val vh = recyclerView.getChildViewHolder(pressedView!!) as BaseViewHolder
                childClickViewIds = vh.childClickViewIds
                if (childClickViewIds != null && childClickViewIds!!.size > 0) {
                    for (childClickViewId in childClickViewIds!!) {
                        val childView = pressedView.findViewById<View>(childClickViewId)
                        if (inRangeOfView(childView, e) && childView.isEnabled) {
                            setPressViewHotSpot(e, childView)
                            childView.isPressed = true
                            if (baseQuickAdapter != null) {
                                onItemChildClick(baseQuickAdapter!!, childView, vh, vh.layoutPosition)
                            }
                            resetPressedView(childView)
                            return true
                        } else {
                            childView.isPressed = false
                        }
                    }
                    setPressViewHotSpot(e, pressedView)
                    mPressedView!!.isPressed = true
                    for (childClickViewId in childClickViewIds!!) {
                        val childView = pressedView.findViewById<View>(childClickViewId)
                        childView.isPressed = false
                    }
                    if (baseQuickAdapter != null) {
                        onItemClick(baseQuickAdapter!!, pressedView, vh.layoutPosition)
                    }

                } else {
                    setPressViewHotSpot(e, pressedView)
                    mPressedView!!.isPressed = true
                    for (childClickViewId in childClickViewIds!!) {
                        val childView = pressedView.findViewById<View>(childClickViewId)
                        childView.isPressed = false
                    }
                    if (baseQuickAdapter != null) {
                        onItemClick(baseQuickAdapter!!, pressedView, vh.layoutPosition)
                    }
                }
                resetPressedView(pressedView)

            }
            return true
        }

        private fun resetPressedView(pressedView: View?) {
            pressedView?.postDelayed({
                if (pressedView != null) {
                    pressedView.isPressed = false
                }
            }, 100)

            mIsPrepressed = false
            mPressedView = null
        }

        override fun onLongPress(e: MotionEvent) {
            var isChildLongClick = false
            if (recyclerView.scrollState != RecyclerView.SCROLL_STATE_IDLE) {
                return
            }
            if (mIsPrepressed && mPressedView != null) {
                mPressedView!!.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
                val vh = recyclerView.getChildViewHolder(mPressedView!!) as BaseViewHolder
                longClickViewIds = vh.itemChildLongClickViewIds
                if (longClickViewIds != null && longClickViewIds!!.size > 0) {
                    for (longClickViewId in longClickViewIds!!) {
                        val childView = mPressedView!!.findViewById<View>(longClickViewId)
                        if (inRangeOfView(childView, e) && childView.isEnabled) {
                            setPressViewHotSpot(e, childView)
                            if (baseQuickAdapter != null) {
                                onItemClick(baseQuickAdapter!!, childView, vh.layoutPosition)
                            }
                            childView.isPressed = true
                            mIsShowPress = true
                            isChildLongClick = true
                            break
                        }
                    }
                    if (!isChildLongClick) {
                        if (baseQuickAdapter != null) {
                            onItemClick(baseQuickAdapter!!, mPressedView!!, vh.layoutPosition)
                        }
                        setPressViewHotSpot(e, mPressedView)
                        mPressedView!!.isPressed = true
                        for (longClickViewId in longClickViewIds!!) {
                            val childView = mPressedView!!.findViewById<View>(longClickViewId)
                            childView.isPressed = false
                        }
                        mIsShowPress = true
                    }

                }

            }
        }


    }

    private fun setPressViewHotSpot(e: MotionEvent, mPressedView: View?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            /**
             * when   click   Outside the region  ,mPressedView is null
             */
            if (mPressedView != null && mPressedView.background != null) {
                mPressedView.background.setHotspot(e.rawX, e.y - mPressedView.y)
            }
        }
    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     *
     * @param view     The view within the AdapterView that was clicked (this
     * will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     */
    abstract fun onItemClick(adapter: BaseAdapter<*, *>, view: View, position: Int)

    /**
     * callback method to be invoked when an item in this view has been
     * click and held
     *
     * @param view     The view whihin the AbsListView that was clicked
     * @param position The position of the view int the adapter
     * @return true if the callback consumed the long click ,false otherwise
     */
    abstract fun onItemLongClick(adapter: BaseAdapter<*, *>, view: View, position: Int)

    abstract fun onItemChildClick(adapter: BaseAdapter<*, *>, view: View, holder: BaseViewHolder, position: Int)

    abstract fun onItemChildLongClick(adapter: BaseAdapter<*, *>, view: View, position: Int)

    fun inRangeOfView(view: View, ev: MotionEvent): Boolean {
        val location = IntArray(2)
        if (view.visibility != View.VISIBLE) {
            return false
        }
        view.getLocationOnScreen(location)
        val x = location[0]
        val y = location[1]
        return !(ev.rawX < x
                || ev.rawX > x + view.width
                || ev.rawY < y
                || ev.rawY > y + view.height)
    }

    companion object {
        var TAG = "SimpleClickListener"
    }

}


