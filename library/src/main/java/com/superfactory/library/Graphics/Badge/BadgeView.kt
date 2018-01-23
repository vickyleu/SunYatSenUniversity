package com.superfactory.library.Graphics.Badge

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Parcelable
import android.text.TextPaint
import android.text.TextUtils
import android.util.AttributeSet
import android.util.SparseArray
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import com.superfactory.library.Bridge.Anko.PropertyExtensions.dp2px
import com.superfactory.library.Bridge.Anko.PropertyExtensions.px2dp
import java.util.*

/**
 * @author chqiu
 * Email:qstumn@163.com
 */

class BadgeView private constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr), Badge {
    protected var mColorBackground: Int = 0
    protected var mColorBackgroundBorder: Int = 0
    protected var mColorBadgeText: Int = 0
    protected var mDrawableBackground: Drawable? = null
    protected var mBitmapClip: Bitmap? = null
    protected var mDrawableBackgroundClip: Boolean = false
    protected var mBackgroundBorderWidth: Float = 0.toFloat()
    protected var mBadgeTextSize: Float = 0.toFloat()
    protected var mBadgePadding: Float = 0.toFloat()
    protected var mBadgeNumber: Int = 0
    protected var mBadgeText: String? = null
    protected var mDraggable: Boolean = false
    protected var mDragging: Boolean = false
    protected var mExact: Boolean = false
    protected var mShowShadow: Boolean = false
    protected var mBadgeGravity: Int = 0
    protected var mGravityOffsetX: Float = 0.toFloat()
    protected var mGravityOffsetY: Float = 0.toFloat()

    protected var mDefalutRadius: Float = 0.toFloat()
    protected var mFinalDragDistance: Float = 0.toFloat()
    protected var mDragQuadrant: Int = 0
    protected var mDragOutOfRange: Boolean = false

    protected var mBadgeTextRect: RectF? = null
    protected var mBadgeBackgroundRect: RectF? = null
    protected var mDragPath: Path? = null

    protected var mBadgeTextFontMetrics: Paint.FontMetrics? = null

    protected var mBadgeCenter: PointF? = null
    protected var mDragCenter: PointF? = null
    protected var mRowBadgeCenter: PointF? = null
    protected var mControlPoint: PointF? = null

    protected var mInnertangentPoints: MutableList<PointF>? = null

    protected var mTargetView: View? = null

    protected var mWidth: Int = 0
    protected var mHeight: Int = 0

    protected var mBadgeTextPaint: TextPaint? = null
    protected var mBadgeBackgroundPaint: Paint? = null
    protected var mBadgeBackgroundBorderPaint: Paint? = null

    protected var mAnimator: BadgeAnimator? = null

    protected var mDragStateChangedListener: ((dragState: Int, badge: Badge, targetView: View?) -> Unit)? = null

//    protected var mDragStateChangedListener: Badge.OnDragStateChangedListener? = null

    protected var mActivityRoot: ViewGroup? = null

    private val badgeCircleRadius: Float
        get() {
            if (mBadgeText!!.isEmpty()) {
                return mBadgePadding
            } else return if (mBadgeText!!.length == 1) {
                if (mBadgeTextRect!!.height() > mBadgeTextRect!!.width())
                    mBadgeTextRect!!.height() / 2f + mBadgePadding * 0.5f
                else
                    mBadgeTextRect!!.width() / 2f + mBadgePadding * 0.5f
            } else {
                mBadgeBackgroundRect!!.height() / 2f
            }
        }

    constructor(context: Context) : this(context, null) {}

    init {
        init()
    }

    private fun init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        mBadgeTextRect = RectF()
        mBadgeBackgroundRect = RectF()
        mDragPath = Path()
        mBadgeCenter = PointF()
        mDragCenter = PointF()
        mRowBadgeCenter = PointF()
        mControlPoint = PointF()
        mInnertangentPoints = ArrayList()
        mBadgeTextPaint = TextPaint()
        mBadgeTextPaint!!.isAntiAlias = true
        mBadgeTextPaint!!.isSubpixelText = true
        mBadgeTextPaint!!.isFakeBoldText = true
        mBadgeTextPaint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        mBadgeBackgroundPaint = Paint()
        mBadgeBackgroundPaint!!.isAntiAlias = true
        mBadgeBackgroundPaint!!.style = Paint.Style.FILL
        mBadgeBackgroundBorderPaint = Paint()
        mBadgeBackgroundBorderPaint!!.isAntiAlias = true
        mBadgeBackgroundBorderPaint!!.style = Paint.Style.STROKE
        mColorBackground = -0x17b1c0
        mColorBadgeText = -0x1
        mBadgeTextSize = dp2px(context, 11f).toFloat()
        mBadgePadding = dp2px(context, 5f).toFloat()
        mBadgeNumber = 0
        mBadgeGravity = Gravity.END or Gravity.TOP
        mGravityOffsetX = dp2px(context, 1f).toFloat()
        mGravityOffsetY = dp2px(context, 1f).toFloat()
        mFinalDragDistance = dp2px(context, 90f).toFloat()
        mShowShadow = true
        mDrawableBackgroundClip = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            translationZ = 1000f
        }
    }

    override fun bindTarget(view: View?): Badge {
        if (view == null) {
            throw IllegalStateException("targetView can not be null")
        }
        if (parent != null) {
            (parent as ViewGroup).removeView(this)
        }
        val targetParent = view!!.parent
        if (targetParent != null && targetParent is ViewGroup) {
            mTargetView = view
            if (targetParent is BadgeContainer) {
                (targetParent as BadgeContainer).addView(this)
            } else {
                val targetContainer = targetParent as ViewGroup
                val index = targetContainer!!.indexOfChild(view)
                val targetParams = view!!.layoutParams
                targetContainer!!.removeView(view)
                val badgeContainer = BadgeContainer(context)
                if (targetContainer is RelativeLayout) {
                    badgeContainer.id = view!!.id
                }
                targetContainer!!.addView(badgeContainer, index, targetParams)
                badgeContainer.addView(view)
                badgeContainer.addView(this)
            }
        } else {
            throw IllegalStateException("targetView must have a parent")
        }
        return this
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (mActivityRoot == null) findViewRoot(mTargetView!!)
    }

    private fun findViewRoot(view: View) {
        mActivityRoot = view.rootView as ViewGroup
        if (mActivityRoot == null) {
            findActivityRoot(view)
        }
    }

    private fun findActivityRoot(view: View) {
        if (view.parent != null && view.parent is View) {
            findActivityRoot(view.parent as View)
        } else if (view is ViewGroup) {
            mActivityRoot = view
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                val x = event.x
                val y = event.y
                if (mDraggable && event.getPointerId(event.actionIndex) == 0
                        && x > mBadgeBackgroundRect!!.left && x < mBadgeBackgroundRect!!.right &&
                        y > mBadgeBackgroundRect!!.top && y < mBadgeBackgroundRect!!.bottom
                        && mBadgeText != null) {
                    initRowBadgeCenter()
                    mDragging = true
                    updataListener(Badge.STATE_START)
                    mDefalutRadius = dp2px(context, 7f).toFloat()
                    parent.requestDisallowInterceptTouchEvent(true)
                    screenFromWindow(true)
                    mDragCenter!!.x = event.rawX
                    mDragCenter!!.y = event.rawY
                }
            }
            MotionEvent.ACTION_MOVE -> if (mDragging) {
                mDragCenter!!.x = event.rawX
                mDragCenter!!.y = event.rawY
                invalidate()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP, MotionEvent.ACTION_CANCEL -> if (event.getPointerId(event.actionIndex) == 0 && mDragging) {
                mDragging = false
                onPointerUp()
            }
        }
        return mDragging || super.onTouchEvent(event)
    }

    private fun onPointerUp() {
        if (mDragOutOfRange) {
            animateHide(mDragCenter!!)
            updataListener(Badge.STATE_SUCCEED)
        } else {
            reset()
            updataListener(Badge.STATE_CANCELED)
        }
    }

    protected fun createBadgeBitmap(): Bitmap {
        val bitmap = Bitmap.createBitmap(mBadgeBackgroundRect!!.width().toInt() + dp2px(context, 3f),
                mBadgeBackgroundRect!!.height().toInt() + dp2px(context, 3f), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawBadge(canvas, PointF(canvas.width / 2f, canvas.height / 2f), badgeCircleRadius)
        return bitmap
    }

    protected fun screenFromWindow(screen: Boolean) {
        if (parent != null) {
            (parent as ViewGroup).removeView(this)
        }
        if (screen) {
            mActivityRoot!!.addView(this, FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT))
        } else {
            bindTarget(mTargetView)
        }
    }

    private fun showShadowImpl(showShadow: Boolean) {
        var x = dp2px(context, 1f)
        var y = dp2px(context, 1.5f)
        when (mDragQuadrant) {
            1 -> {
                x = dp2px(context, 1f)
                y = dp2px(context, -1.5f)
            }
            2 -> {
                x = dp2px(context, -1f)
                y = dp2px(context, -1.5f)
            }
            3 -> {
                x = dp2px(context, -1f)
                y = dp2px(context, 1.5f)
            }
            4 -> {
                x = dp2px(context, 1f)
                y = dp2px(context, 1.5f)
            }
        }
        mBadgeBackgroundPaint!!.setShadowLayer((if (showShadow)
            dp2px(context, 2f)
        else
            0).toFloat(), x.toFloat(), y.toFloat(), 0x33000000)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
    }

    override fun onDraw(canvas: Canvas) {
        if (mAnimator != null && mAnimator!!.isRunning) {
            mAnimator!!.draw(canvas)
            return
        }
        if (mBadgeText != null) {
            initPaints()
            val badgeRadius = badgeCircleRadius
            val startCircleRadius = mDefalutRadius * (1 - MathUtil.getPointDistance(mRowBadgeCenter!!, mDragCenter!!) / mFinalDragDistance)
            if (mDraggable && mDragging) {
                mDragQuadrant = MathUtil.getQuadrant(mDragCenter!!, mRowBadgeCenter!!)
                showShadowImpl(mShowShadow)
                mDragOutOfRange = (startCircleRadius < dp2px(context, 1.5f).toFloat())
                if (mDragOutOfRange) {
                    updataListener(Badge.STATE_DRAGGING_OUT_OF_RANGE)
                    drawBadge(canvas, mDragCenter!!, badgeRadius)
                } else {
                    updataListener(Badge.STATE_DRAGGING)
                    drawDragging(canvas, startCircleRadius, badgeRadius)
                    drawBadge(canvas, mDragCenter!!, badgeRadius)
                }
            } else {
                findBadgeCenter()
                drawBadge(canvas, mBadgeCenter!!, badgeRadius)
            }
        }
    }

    private fun initPaints() {
        showShadowImpl(mShowShadow)
        mBadgeBackgroundPaint!!.color = mColorBackground
        mBadgeBackgroundBorderPaint!!.color = mColorBackgroundBorder
        mBadgeBackgroundBorderPaint!!.strokeWidth = mBackgroundBorderWidth
        mBadgeTextPaint!!.color = mColorBadgeText
        mBadgeTextPaint!!.textAlign = Paint.Align.CENTER
    }

    private fun drawDragging(canvas: Canvas, startRadius: Float, badgeRadius: Float) {
        val dy = mDragCenter!!.y - mRowBadgeCenter!!.y
        val dx = mDragCenter!!.x - mRowBadgeCenter!!.x
        mInnertangentPoints!!.clear()
        if (dx != 0f) {
            val k1 = (dy / dx).toDouble()
            val k2 = -1 / k1
            MathUtil.getInnertangentPoints(mDragCenter!!, badgeRadius, k2, mInnertangentPoints!!)
            MathUtil.getInnertangentPoints(mRowBadgeCenter!!, startRadius, k2, mInnertangentPoints!!)
        } else {
            MathUtil.getInnertangentPoints(mDragCenter!!, badgeRadius, 0.0, mInnertangentPoints!!)
            MathUtil.getInnertangentPoints(mRowBadgeCenter!!, startRadius, 0.0, mInnertangentPoints!!)
        }
        mDragPath!!.reset()
        mDragPath!!.addCircle(mRowBadgeCenter!!.x, mRowBadgeCenter!!.y, startRadius,
                if (mDragQuadrant == 1 || mDragQuadrant == 2) Path.Direction.CCW else Path.Direction.CW)
        mControlPoint!!.x = (mRowBadgeCenter!!.x + mDragCenter!!.x) / 2.0f
        mControlPoint!!.y = (mRowBadgeCenter!!.y + mDragCenter!!.y) / 2.0f
        mDragPath!!.moveTo(mInnertangentPoints!![2].x, mInnertangentPoints!![2].y)
        mDragPath!!.quadTo(mControlPoint!!.x, mControlPoint!!.y, mInnertangentPoints!![0].x, mInnertangentPoints!![0].y)
        mDragPath!!.lineTo(mInnertangentPoints!![1].x, mInnertangentPoints!![1].y)
        mDragPath!!.quadTo(mControlPoint!!.x, mControlPoint!!.y, mInnertangentPoints!![3].x, mInnertangentPoints!![3].y)
        mDragPath!!.lineTo(mInnertangentPoints!![2].x, mInnertangentPoints!![2].y)
        mDragPath!!.close()
        canvas.drawPath(mDragPath, mBadgeBackgroundPaint)

        //draw dragging border
        if (mColorBackgroundBorder != 0 && mBackgroundBorderWidth > 0) {
            mDragPath!!.reset()
            mDragPath!!.moveTo(mInnertangentPoints!![2].x, mInnertangentPoints!![2].y)
            mDragPath!!.quadTo(mControlPoint!!.x, mControlPoint!!.y, mInnertangentPoints!![0].x, mInnertangentPoints!![0].y)
            mDragPath!!.moveTo(mInnertangentPoints!![1].x, mInnertangentPoints!![1].y)
            mDragPath!!.quadTo(mControlPoint!!.x, mControlPoint!!.y, mInnertangentPoints!![3].x, mInnertangentPoints!![3].y)
            val startY: Float
            val startX: Float
            if (mDragQuadrant == 1 || mDragQuadrant == 2) {
                startX = mInnertangentPoints!![2].x - mRowBadgeCenter!!.x
                startY = mRowBadgeCenter!!.y - mInnertangentPoints!![2].y
            } else {
                startX = mInnertangentPoints!![3].x - mRowBadgeCenter!!.x
                startY = mRowBadgeCenter!!.y - mInnertangentPoints!![3].y
            }
            val startAngle = 360 - MathUtil.radianToAngle(MathUtil.getTanRadian(Math.atan((startY / startX).toDouble()),
                    if (mDragQuadrant - 1 == 0) 4 else mDragQuadrant - 1)).toFloat()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mDragPath!!.addArc(mRowBadgeCenter!!.x - startRadius, mRowBadgeCenter!!.y - startRadius,
                        mRowBadgeCenter!!.x + startRadius, mRowBadgeCenter!!.y + startRadius, startAngle,
                        180f)
            } else {
                mDragPath!!.addArc(RectF(mRowBadgeCenter!!.x - startRadius, mRowBadgeCenter!!.y - startRadius,
                        mRowBadgeCenter!!.x + startRadius, mRowBadgeCenter!!.y + startRadius), startAngle, 180f)
            }
            canvas.drawPath(mDragPath, mBadgeBackgroundBorderPaint)
        }
    }

    private fun drawBadge(canvas: Canvas, center: PointF, radius: Float) {
        var radius = radius
        if (center.x == -1000f && center.y == -1000f) {
            return
        }
        if (mBadgeText!!.isEmpty() || mBadgeText!!.length == 1) {
            mBadgeBackgroundRect!!.left = center.x - radius.toInt()
            mBadgeBackgroundRect!!.top = center.y - radius.toInt()
            mBadgeBackgroundRect!!.right = center.x + radius.toInt()
            mBadgeBackgroundRect!!.bottom = center.y + radius.toInt()
            if (mDrawableBackground != null) {
                drawBadgeBackground(canvas)
            } else {
                canvas.drawCircle(center.x, center.y, radius, mBadgeBackgroundPaint)
                if (mColorBackgroundBorder != 0 && mBackgroundBorderWidth > 0) {
                    canvas.drawCircle(center.x, center.y, radius, mBadgeBackgroundBorderPaint)
                }
            }
        } else {
            mBadgeBackgroundRect!!.left = center.x - (mBadgeTextRect!!.width() / 2f + mBadgePadding)
            mBadgeBackgroundRect!!.top = center.y - (mBadgeTextRect!!.height() / 2f + mBadgePadding * 0.5f)
            mBadgeBackgroundRect!!.right = center.x + (mBadgeTextRect!!.width() / 2f + mBadgePadding)
            mBadgeBackgroundRect!!.bottom = center.y + (mBadgeTextRect!!.height() / 2f + mBadgePadding * 0.5f)
            radius = mBadgeBackgroundRect!!.height() / 2f
            if (mDrawableBackground != null) {
                drawBadgeBackground(canvas)
            } else {
                canvas.drawRoundRect(mBadgeBackgroundRect, radius, radius, mBadgeBackgroundPaint)
                if (mColorBackgroundBorder != 0 && mBackgroundBorderWidth > 0) {
                    canvas.drawRoundRect(mBadgeBackgroundRect, radius, radius, mBadgeBackgroundBorderPaint)
                }
            }
        }
        if (!mBadgeText!!.isEmpty()) {
            canvas.drawText(mBadgeText!!, center.x,
                    (mBadgeBackgroundRect!!.bottom + mBadgeBackgroundRect!!.top
                            - mBadgeTextFontMetrics!!.bottom - mBadgeTextFontMetrics!!.top) / 2f,
                    mBadgeTextPaint)
        }
    }

    private fun drawBadgeBackground(canvas: Canvas) {
        mBadgeBackgroundPaint!!.setShadowLayer(0f, 0f, 0f, 0)
        val left = mBadgeBackgroundRect!!.left.toInt()
        val top = mBadgeBackgroundRect!!.top.toInt()
        var right = mBadgeBackgroundRect!!.right.toInt()
        var bottom = mBadgeBackgroundRect!!.bottom.toInt()
        if (mDrawableBackgroundClip) {
            right = left + mBitmapClip!!.width
            bottom = top + mBitmapClip!!.height
            canvas.saveLayer(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), null, Canvas.ALL_SAVE_FLAG)
        }
        mDrawableBackground!!.setBounds(left, top, right, bottom)
        mDrawableBackground!!.draw(canvas)
        if (mDrawableBackgroundClip) {
            mBadgeBackgroundPaint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
            canvas.drawBitmap(mBitmapClip!!, left.toFloat(), top.toFloat(), mBadgeBackgroundPaint)
            canvas.restore()
            mBadgeBackgroundPaint!!.xfermode = null
            if (mBadgeText!!.isEmpty() || mBadgeText!!.length == 1) {
                canvas.drawCircle(mBadgeBackgroundRect!!.centerX(), mBadgeBackgroundRect!!.centerY(),
                        mBadgeBackgroundRect!!.width() / 2f, mBadgeBackgroundBorderPaint)
            } else {
                canvas.drawRoundRect(mBadgeBackgroundRect,
                        mBadgeBackgroundRect!!.height() / 2, mBadgeBackgroundRect!!.height() / 2,
                        mBadgeBackgroundBorderPaint)
            }
        } else {
            canvas.drawRect(mBadgeBackgroundRect, mBadgeBackgroundBorderPaint)
        }
    }

    private fun createClipLayer() {
        if (mBadgeText == null) {
            return
        }
        if (!mDrawableBackgroundClip) {
            return
        }
        if (mBitmapClip != null && !mBitmapClip!!.isRecycled) {
            mBitmapClip!!.recycle()
        }
        val radius = badgeCircleRadius
        if (mBadgeText!!.isEmpty() || mBadgeText!!.length == 1) {
            mBitmapClip = Bitmap.createBitmap(radius.toInt() * 2, radius.toInt() * 2,
                    Bitmap.Config.ARGB_4444)
            val srcCanvas = Canvas(mBitmapClip!!)
            srcCanvas.drawCircle(srcCanvas.width / 2f, srcCanvas.height / 2f,
                    srcCanvas.width / 2f, mBadgeBackgroundPaint)
        } else {
            mBitmapClip = Bitmap.createBitmap((mBadgeTextRect!!.width() + mBadgePadding * 2).toInt(),
                    (mBadgeTextRect!!.height() + mBadgePadding).toInt(), Bitmap.Config.ARGB_4444)
            val srcCanvas = Canvas(mBitmapClip!!)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                srcCanvas.drawRoundRect(0f, 0f, srcCanvas.width.toFloat(), srcCanvas.height.toFloat(), srcCanvas.height / 2f,
                        srcCanvas.height / 2f, mBadgeBackgroundPaint)
            } else {
                srcCanvas.drawRoundRect(RectF(0f, 0f, srcCanvas.width.toFloat(), srcCanvas.height.toFloat()),
                        srcCanvas.height / 2f, srcCanvas.height / 2f, mBadgeBackgroundPaint)
            }
        }
    }

    private fun findBadgeCenter() {
        val rectWidth = if (mBadgeTextRect!!.height() > mBadgeTextRect!!.width())
            mBadgeTextRect!!.height()
        else
            mBadgeTextRect!!.width()
        when (mBadgeGravity) {
            Gravity.START or Gravity.TOP -> {
                mBadgeCenter!!.x = mGravityOffsetX + mBadgePadding + rectWidth / 2f
                mBadgeCenter!!.y = mGravityOffsetY + mBadgePadding + mBadgeTextRect!!.height() / 2f
            }
            Gravity.START or Gravity.BOTTOM -> {
                mBadgeCenter!!.x = mGravityOffsetX + mBadgePadding + rectWidth / 2f
                mBadgeCenter!!.y = mHeight - (mGravityOffsetY + mBadgePadding + mBadgeTextRect!!.height() / 2f)
            }
            Gravity.END or Gravity.TOP -> {
                mBadgeCenter!!.x = mWidth - (mGravityOffsetX + mBadgePadding + rectWidth / 2f)
                mBadgeCenter!!.y = mGravityOffsetY + mBadgePadding + mBadgeTextRect!!.height() / 2f
            }
            Gravity.END or Gravity.BOTTOM -> {
                mBadgeCenter!!.x = mWidth - (mGravityOffsetX + mBadgePadding + rectWidth / 2f)
                mBadgeCenter!!.y = mHeight - (mGravityOffsetY + mBadgePadding + mBadgeTextRect!!.height() / 2f)
            }
            Gravity.CENTER -> {
                mBadgeCenter!!.x = mWidth / 2f
                mBadgeCenter!!.y = mHeight / 2f
            }
            Gravity.CENTER or Gravity.TOP -> {
                mBadgeCenter!!.x = mWidth / 2f
                mBadgeCenter!!.y = mGravityOffsetY + mBadgePadding + mBadgeTextRect!!.height() / 2f
            }
            Gravity.CENTER or Gravity.BOTTOM -> {
                mBadgeCenter!!.x = mWidth / 2f
                mBadgeCenter!!.y = mHeight - (mGravityOffsetY + mBadgePadding + mBadgeTextRect!!.height() / 2f)
            }
            Gravity.CENTER or Gravity.START -> {
                mBadgeCenter!!.x = mGravityOffsetX + mBadgePadding + rectWidth / 2f
                mBadgeCenter!!.y = mHeight / 2f
            }
            Gravity.CENTER or Gravity.END -> {
                mBadgeCenter!!.x = mWidth - (mGravityOffsetX + mBadgePadding + rectWidth / 2f)
                mBadgeCenter!!.y = mHeight / 2f
            }
        }
        initRowBadgeCenter()
    }

    private fun measureText() {
        mBadgeTextRect!!.left = 0f
        mBadgeTextRect!!.top = 0f
        if (TextUtils.isEmpty(mBadgeText)) {
            mBadgeTextRect!!.right = 0f
            mBadgeTextRect!!.bottom = 0f
        } else {
            mBadgeTextPaint!!.textSize = mBadgeTextSize
            mBadgeTextRect!!.right = mBadgeTextPaint!!.measureText(mBadgeText)
            mBadgeTextFontMetrics = mBadgeTextPaint!!.fontMetrics
            mBadgeTextRect!!.bottom = mBadgeTextFontMetrics!!.descent - mBadgeTextFontMetrics!!.ascent
        }
        createClipLayer()
    }

    private fun initRowBadgeCenter() {
        val screenPoint = IntArray(2)
        getLocationOnScreen(screenPoint)
        mRowBadgeCenter!!.x = mBadgeCenter!!.x + screenPoint[0]
        mRowBadgeCenter!!.y = mBadgeCenter!!.y + screenPoint[1]
    }

    protected fun animateHide(center: PointF) {
        if (mBadgeText == null) {
            return
        }
        if (mAnimator == null || !mAnimator!!.isRunning) {
            screenFromWindow(true)
            mAnimator = BadgeAnimator(createBadgeBitmap(), center, this)
            mAnimator!!.start()
            badgeNumber = 0
        }
    }

    fun reset() {
        mDragCenter!!.x = -1000f
        mDragCenter!!.y = -1000f
        mDragQuadrant = 4
        screenFromWindow(false)
        parent.requestDisallowInterceptTouchEvent(false)
        invalidate()
    }

    override fun hide(animate: Boolean) {
        if (animate && mActivityRoot != null) {
            initRowBadgeCenter()
            animateHide(mRowBadgeCenter!!)
        } else {
            badgeNumber = 0
        }
    }

    /**
     * @param badgeNumber equal to zero badge will be hidden, less than zero show dot
     */
    override fun setBadgeNumber(badgeNumber: Int): Badge {
        mBadgeNumber = badgeNumber
        if (mBadgeNumber < 0) {
            mBadgeText = ""
        } else if (mBadgeNumber > 99) {
            mBadgeText = if (mExact) (mBadgeNumber).toString() else "99+"
        } else if (mBadgeNumber > 0 && mBadgeNumber <= 99) {
            mBadgeText = (mBadgeNumber).toString()
        } else if (mBadgeNumber == 0) {
            mBadgeText = null
        }
        measureText()
        invalidate()
        return this
    }

    override var badgeNumber: Int = 0
        get() = mBadgeNumber

    override var targetView: View? = null
        get() = mTargetView


    override fun setBadgeText(badgeText: String): Badge {
        mBadgeText = badgeText
        mBadgeNumber = 1
        measureText()
        invalidate()
        return this
    }

    override var badgeText: String? = null
        get() = mBadgeText

    override var isExactMode: Boolean = false
        get() = mExact

    override fun setExactMode(isExact: Boolean): Badge {
        mExact = isExact
        if (mBadgeNumber > 99) {
            badgeNumber = mBadgeNumber
        }
        return this
    }


    override var isShowShadow: Boolean = false
        get() = mShowShadow

    override fun setShowShadow(showShadow: Boolean): Badge {
        mShowShadow = showShadow
        invalidate()
        return this
    }

    override fun setBadgeBackgroundColor(color: Int): Badge {
        mColorBackground = color
        if (mColorBackground == Color.TRANSPARENT) {
            mBadgeTextPaint!!.xfermode = null
        } else {
            mBadgeTextPaint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        }
        invalidate()
        return this
    }

    override fun stroke(color: Int, width: Float, isDpValue: Boolean): Badge {
        mColorBackgroundBorder = color
        mBackgroundBorderWidth = if (isDpValue) dp2px(context, width).toFloat() else width
        invalidate()
        return this
    }


    override var badgeBackgroundColor: Int = 0
        get() = mColorBackground


    override fun setBadgeBackground(drawable: Drawable): Badge {
        return setBadgeBackground(drawable, false)
    }


    override var badgeBackground: Drawable? = null
        get() = mDrawableBackground

    override fun setBadgeBackground(drawable: Drawable, clip: Boolean): Badge {
        mDrawableBackgroundClip = clip
        mDrawableBackground = drawable
        createClipLayer()
        invalidate()
        return this
    }


    override var badgeTextColor: Int = 0
        get() = mColorBadgeText

    override fun setBadgeTextColor(color: Int): Badge {
        mColorBadgeText = color
        invalidate()
        return this
    }

    override fun setBadgeTextSize(size: Float, isSpValue: Boolean): Badge {
        mBadgeTextSize = if (isSpValue) dp2px(context, size).toFloat() else size
        measureText()
        invalidate()
        return this
    }

    override fun getBadgeTextSize(isSpValue: Boolean): Float {
        return if (isSpValue) px2dp(context, mBadgeTextSize).toFloat() else mBadgeTextSize
    }

    override fun setBadgePadding(padding: Float, isDpValue: Boolean): Badge {
        mBadgePadding = if (isDpValue) dp2px(context, padding).toFloat() else padding
        createClipLayer()
        invalidate()
        return this
    }

    override fun getBadgePadding(isDpValue: Boolean): Float {
        return if (isDpValue) px2dp(context, mBadgePadding).toFloat() else mBadgePadding
    }

    override var isDraggable: Boolean = false
        get() = mDraggable

    /**
     * @param gravity only support Gravity.START | Gravity.TOP , Gravity.END | Gravity.TOP ,
     * Gravity.START | Gravity.BOTTOM , Gravity.END | Gravity.BOTTOM ,
     * Gravity.CENTER , Gravity.CENTER | Gravity.TOP , Gravity.CENTER | Gravity.BOTTOM ,
     * Gravity.CENTER | Gravity.START , Gravity.CENTER | Gravity.END
     */
    override fun setBadgeGravity(gravity: Int): Badge {
        if ((gravity == (Gravity.START or Gravity.TOP) ||
                        gravity == (Gravity.END or Gravity.TOP) ||
                        gravity == (Gravity.START or Gravity.BOTTOM) ||
                        gravity == (Gravity.END or Gravity.BOTTOM) ||
                        gravity == (Gravity.CENTER) ||
                        gravity == (Gravity.CENTER or Gravity.TOP) ||
                        gravity == (Gravity.CENTER or Gravity.BOTTOM) ||
                        gravity == (Gravity.CENTER or Gravity.START) ||
                        gravity == (Gravity.CENTER or Gravity.END))) {
            mBadgeGravity = gravity
            invalidate()
        } else {
            throw IllegalStateException(("only support Gravity.START | Gravity.TOP , Gravity.END | Gravity.TOP , " +
                    "Gravity.START | Gravity.BOTTOM , Gravity.END | Gravity.BOTTOM , Gravity.CENTER" +
                    " , Gravity.CENTER | Gravity.TOP , Gravity.CENTER | Gravity.BOTTOM ," +
                    "Gravity.CENTER | Gravity.START , Gravity.CENTER | Gravity.END"))
        }
        return this
    }

    override var badgeGravity: Int = 0
        get() = mBadgeGravity

    override fun setGravityOffset(offset: Float, isDpValue: Boolean): Badge {
        return setGravityOffset(offset, offset, isDpValue)
    }

    override fun setGravityOffset(offsetX: Float, offsetY: Float, isDpValue: Boolean): Badge {
        mGravityOffsetX = if (isDpValue) dp2px(context, offsetX).toFloat() else offsetX
        mGravityOffsetY = if (isDpValue) dp2px(context, offsetY).toFloat() else offsetY
        invalidate()
        return this
    }

    override fun getGravityOffsetX(isDpValue: Boolean): Float {
        return if (isDpValue) px2dp(context, mGravityOffsetX).toFloat() else mGravityOffsetX
    }

    override fun getGravityOffsetY(isDpValue: Boolean): Float {
        return if (isDpValue) px2dp(context, mGravityOffsetY).toFloat() else mGravityOffsetY
    }


    private fun updataListener(state: Int) {
        if (mDragStateChangedListener != null) {
            mDragStateChangedListener?.invoke(state, this, mTargetView)
        }
    }

    override fun setOnDragStateChangedListener(l: ((dragState: Int, badge: Badge, targetView: View?) -> Unit)?): Badge {
        mDraggable = l != null
        mDragStateChangedListener = l
        return this
    }

    override var dragCenter: PointF? = null
        get() = if (mDraggable && mDragging) mDragCenter else null


    private inner class BadgeContainer(context: Context) : ViewGroup(context) {

        override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>) {
            if (parent !is RelativeLayout) {
                super.dispatchRestoreInstanceState(container)
            }
        }

        override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                child.layout(0, 0, child.measuredWidth, child.measuredHeight)
            }
        }

        override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
            var targetView: View? = null
            var badgeView: View? = null
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                if (child !is BadgeView) {
                    targetView = child
                } else {
                    badgeView = child
                }
            }
            if (targetView == null) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            } else {
                targetView!!.measure(widthMeasureSpec, heightMeasureSpec)
                if (badgeView != null) {
                    badgeView!!.measure(View.MeasureSpec.makeMeasureSpec(targetView!!.measuredWidth, View.MeasureSpec.EXACTLY),
                            View.MeasureSpec.makeMeasureSpec(targetView!!.measuredHeight, View.MeasureSpec.EXACTLY))
                }
                setMeasuredDimension(targetView!!.measuredWidth, targetView!!.measuredHeight)
            }
        }
    }
}
