package com.superfactory.library.Graphics

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import com.superfactory.library.Bridge.Anko.DslView.OnTextCleanListener
import com.superfactory.library.R


/**
 * Created by vicky on 2018.01.29.
 *
 * @Author vicky
 * @Date 2018年01月29日  10:56:31
 * @ClassName 这里输入你的类名(或用途)
 */
class AutoCompleteCleanUpTextView(context: Context) : AutoCompleteTextView(context) {


    val UNDEFINED_RESOURCE = 0xABCD
    private val INDEX_LEFT = 0
    private val INDEX_TOP = 1
    private val INDEX_RIGHT = 2
    private val INDEX_BOTTOM = 3
    var maxLength: Int = 0
//    /**
//     * @see android.support.v7.widget.DrawableUtils.VECTOR_DRAWABLE_CLAZZ_NAME
//     */
//    private val VECTOR_DRAWABLE_CLAZZ_NAME = "android.graphics.drawable.VectorDrawable"
//    /**
//     * @see android.support.v7.widget.ThemeUtils.CHECKED_STATE_SET
//     */
//    private val CHECKED_STATE_SET = intArrayOf(android.R.attr.state_checked)
//    /**
//     * @see android.support.v7.widget.ThemeUtils.EMPTY_STATE_SET
//     */
//    private val EMPTY_STATE_SET = IntArray(0)
//    private var iconWidth = UNDEFINED_RESOURCE
//    private var iconHeight = UNDEFINED_RESOURCE
//    private var iconColor = UNDEFINED_RESOURCE
//    private val drawables = arrayOfNulls<Drawable>(4)
//    private val drawableResIds = IntArray(4) // cache drawable resource ids


    private var m: InputMethodManager? = null

    init {

//        if (attrs!=null){
//            var typedArray: TypedArray? = null
//            try {
//                typedArray = context.obtainStyledAttributes(attrs, R.styleable.AutoCompleteCleanUpTextView, defStyleAttr, 0)
//                drawableResIds[INDEX_LEFT] = typedArray!!.getResourceId(R.styleable.AutoCompleteCleanUpTextView_cit_drawableLeft, UNDEFINED_RESOURCE)
//                drawableResIds[INDEX_TOP] = typedArray.getResourceId(R.styleable.AutoCompleteCleanUpTextView_cit_drawableTop, UNDEFINED_RESOURCE)
//                drawableResIds[INDEX_RIGHT] = typedArray.getResourceId(R.styleable.AutoCompleteCleanUpTextView_cit_drawableRight, UNDEFINED_RESOURCE)
//                drawableResIds[INDEX_BOTTOM] = typedArray.getResourceId(R.styleable.AutoCompleteCleanUpTextView_cit_drawableBottom, UNDEFINED_RESOURCE)
//                if (typedArray.hasValue(R.styleable.AutoCompleteCleanUpTextView_cit_drawableStart)) {
//                    drawableResIds[if (isRtl()) INDEX_RIGHT else INDEX_LEFT] = typedArray.getResourceId(R.styleable.AutoCompleteCleanUpTextView_cit_drawableStart, UNDEFINED_RESOURCE)
//                }
//                if (typedArray.hasValue(R.styleable.AutoCompleteCleanUpTextView_cit_drawableEnd)) {
//                    drawableResIds[if (isRtl()) INDEX_LEFT else INDEX_RIGHT] = typedArray.getResourceId(R.styleable.AutoCompleteCleanUpTextView_cit_drawableEnd, UNDEFINED_RESOURCE)
//                }
//                iconWidth = typedArray.getDimensionPixelSize(R.styleable.AutoCompleteCleanUpTextView_cit_iconWidth, UNDEFINED_RESOURCE)
//                iconHeight = typedArray.getDimensionPixelSize(R.styleable.AutoCompleteCleanUpTextView_cit_iconHeight, UNDEFINED_RESOURCE)
//                iconColor = typedArray.getColor(R.styleable.AutoCompleteCleanUpTextView_cit_iconColor, UNDEFINED_RESOURCE)
//            } finally {
//                if (typedArray != null) {
//                    typedArray.recycle()
//                }
//            }
//        }else{
//            drawableResIds[INDEX_LEFT] =UNDEFINED_RESOURCE
//            drawableResIds[INDEX_TOP] = UNDEFINED_RESOURCE
//            drawableResIds[INDEX_RIGHT] =UNDEFINED_RESOURCE
//            drawableResIds[INDEX_BOTTOM] =UNDEFINED_RESOURCE
//            iconWidth =UNDEFINED_RESOURCE
//            iconHeight =UNDEFINED_RESOURCE
//            iconColor =UNDEFINED_RESOURCE
//        }

        addTextChangedListener(object : TextWatcher {
            /**
             * This method is called to notify you that, within `s`,
             * the `count` characters beginning at `start`
             * have just replaced old text that had length `before`.
             * It is an error to attempt to make changes to `s` from
             * this callback.
             */
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (maxLength > 0) {
                    var editable = text
                    val length = editable.length//原字符串长度
                    if (length > maxLength) {//如果原字符串长度大于最大长度
                        var selectEndIndex = Selection.getSelectionEnd(editable);//getSelectionEnd：获取光标结束的索引值
                        val str = editable.toString()//旧字符串
                        val newStr = str.substring(0, maxLength);//截取新字符串
                        setText(newStr)
                        editable = text
                        val newLength = editable.length//新字符串长度
                        if (selectEndIndex > newLength) {//如果光标结束的索引值超过新字符串长度
                            selectEndIndex = editable.length
                        }
                        Selection.setSelection(editable, selectEndIndex);//设置新光标所在的位置
                    }
                }

                if (s?.length == 0) {
                    setCompoundDrawablesWithIntrinsicBounds(compoundDrawables[0], null,
                            null, null) //注意刚开始使用的是setCompoundDrawables方法就没有用，不知道为什么
                } else {
                    var right = ContextCompat.getDrawable(context, R.drawable.auto_clean_up_icon)
                    if (compoundDrawables[0] != null) {
                        var height = compoundDrawables[0].intrinsicHeight
                        var originWidth = right!!.intrinsicWidth
                        val originHeight = right!!.intrinsicWidth
                        val scale = height.toFloat() / originHeight.toFloat()
                        originWidth =(originWidth.toFloat()* scale).toInt()
                        // Read your drawable from somewhere
                        val bitmap = (right as? BitmapDrawable)?.bitmap
                        if (bitmap != null) {
                            if (originWidth<=0)originWidth=1
                            if (height<=0)height=1
                            // Scale it
                            right = BitmapDrawable(resources, Bitmap.createScaledBitmap(bitmap, originWidth, height, true))
                        }
                    }
                    setCompoundDrawablesWithIntrinsicBounds(compoundDrawables[0], null, right, null)
                }
            }

            /**
             * This method is called to notify you that, within `s`,
             * the `count` characters beginning at `start`
             * are about to be replaced by new text with length `after`.
             * It is an error to attempt to make changes to `s` from
             * this callback.
             */
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            /**
             * This method is called to notify you that, somewhere within
             * `s`, the text has been changed.
             * It is legitimate to make further changes to `s` from
             * this callback, but be careful not to get yourself into an infinite
             * loop, because any changes you make will cause this method to be
             * called again recursively.
             * (You are not told where the change took place because other
             * afterTextChanged() methods may already have made other changes
             * and invalidated the offsets.  But if you need to know here,
             * you can use [Spannable.setSpan] in [.onTextChanged]
             * to mark your place and then look up from here where the span
             * ended up.
             */
            override fun afterTextChanged(s: Editable?) {
            }
        })
        refresh()


    }

    private fun refresh() {
//        if (drawableResIds[INDEX_LEFT] === UNDEFINED_RESOURCE && drawableResIds[INDEX_TOP] === UNDEFINED_RESOURCE
//                && drawableResIds[INDEX_RIGHT] === UNDEFINED_RESOURCE && drawableResIds[INDEX_BOTTOM] === UNDEFINED_RESOURCE) {
//        } else {
//            checkHasIconSize()
//            makeDrawableIcons()
//            updateIcons()
//        }
    }

    private var mOnClick: OnClickListener? = null

    override fun setOnClickListener(listener: OnClickListener?) {
        this.mOnClick = listener
    }


    fun setRightClick(cleanUp: OnTextCleanListener) {
        setOnTouchListener(object : OnTouchListener {
            var touch_flag = 0
            /**
             * Called when a touch event is dispatched to a view. This allows listeners to
             * get a chance to respond before the target view.
             *
             * @param v The view the touch event has been dispatched to.
             * @param event The MotionEvent object containing full information about
             * the event.
             * @return True if the listener has consumed the event, false otherwise.
             */
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if (touch_flag < 3)
                    touch_flag++
                m = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                if (isFocused) {
                    m?.showSoftInput(this@AutoCompleteCleanUpTextView, InputMethodManager.SHOW_FORCED);
                }
                //可以获得上下左右四个drawable，右侧排第二。图标没有设置则为空。
//            if (drawableResIds[INDEX_RIGHT] === UNDEFINED_RESOURCE) {
//                return@setOnTouchListener false
//            }//todo
                val rightIcon = compoundDrawables[INDEX_RIGHT]
                @Suppress("IMPLICIT_BOXING_IN_IDENTITY_EQUALS")
                if (rightIcon != null && event?.action === MotionEvent.ACTION_UP) {
                    //检查点击的位置是否是右侧的删除图标
                    //注意，使用getRwwX()是获取相对屏幕的位置，getX()可能获取相对父组件的位置
                    val leftEdgeOfRightDrawable = (right - paddingRight - rightIcon.bounds.width())

                    if (event.getRawX() >= leftEdgeOfRightDrawable) {
                        error = null
                        setText("")
                        cleanUp.onClean()
                    }
                }
                if (event?.action == MotionEvent.ACTION_UP) {
                    touch_flag = 0
                }
                if (touch_flag == 2) {
                    //自己业务
                    if (mOnClick != null) {
                        mOnClick?.onClick(v)
                    }
                }
                return false
            }
        })
    }

    fun isValidSize(size: Int): Boolean {
        if (text != null && text.isNotEmpty()) {
            if (text.length >= size) {
                return true
            }
        }
        return false
    }

    /**
     * 验证手机格式
     */
    fun isValidPhoneNumber(): Boolean {
        /*
    移动：134、135、136、137、138、139、150、151、152、157(TD)、158、159、178(新)、182、184、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、170、173、177、180、181、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        if (text != null && text.isNotEmpty()) {
            if (text.length == 11) {
                return text.toString().matches("[1][345789]\\d{9}".toRegex())
                //"[1]"代表第1位为数字1，"[345789]"代表第二位可以为3、4、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
            } else {
                return false
            }
        }
        return false
    }


//    /**
//     * @param resourceId Set the [AutoCompleteCleanUpTextView.UNDEFINED_RESOURCE] if you want clear icon.
//     */
//    fun setVectorDrawableLeft(@DrawableRes resourceId: Int) {
//        setVectorDrawable(INDEX_LEFT, resourceId)
//    }
//
//    /**
//     * @param resourceId Set the [AutoCompleteCleanUpTextView.UNDEFINED_RESOURCE] if you want clear icon.
//     */
//    fun setVectorDrawableTop(@DrawableRes resourceId: Int) {
//        setVectorDrawable(INDEX_TOP, resourceId)
//    }
//
//    /**
//     * @param resourceId Set the [AutoCompleteCleanUpTextView.UNDEFINED_RESOURCE] if you want clear icon.
//     */
//    fun setVectorDrawableRight(@DrawableRes resourceId: Int) {
//        setVectorDrawable(INDEX_RIGHT, resourceId)
//    }
//
//    /**
//     * @param resourceId Set the [AutoCompleteCleanUpTextView.UNDEFINED_RESOURCE] if you want clear icon.
//     */
//    fun setVectorDrawableBottom(@DrawableRes resourceId: Int) {
//        setVectorDrawable(INDEX_BOTTOM, resourceId)
//    }
//
//    /**
//     * @param resourceId Set the [AutoCompleteCleanUpTextView.UNDEFINED_RESOURCE] if you want clear icon.
//     */
//    fun setVectorDrawableStart(@DrawableRes resourceId: Int) {
//        setVectorDrawable(if (isRtl()) INDEX_RIGHT else INDEX_LEFT, resourceId)
//    }
//
//    /**
//     * @param resourceId Set the [AutoCompleteCleanUpTextView.UNDEFINED_RESOURCE] if you want clear icon.
//     */
//    fun setVectorDrawableEnd(@DrawableRes resourceId: Int) {
//        setVectorDrawable(if (isRtl()) INDEX_LEFT else INDEX_RIGHT, resourceId)
//    }
//
//    /**
//     * Change drawable icon color
//     *
//     * @param resId Set color resource id
//     */
//    fun setIconColorResource(@ColorRes resId: Int) {
//        setIconColor(ContextCompat.getColor(context, resId))
//    }
//
//    /**
//     * Change drawable icon color
//     *
//     * @param color Set color integer
//     */
//    fun setIconColor(@ColorInt color: Int) {
//        for (i in 0 until drawables.size) {
//            setColorFilter(i, color)
//        }
//        iconColor = color
//        updateIcons()
//    }
//
//    /**
//     * Change drawable icon size
//     *
//     * @param widthRes  Set width resource id
//     * @param heightRes Set height resource id
//     */
//    fun setIconSizeResource(@DimenRes widthRes: Int, @DimenRes heightRes: Int) {
//        setIconSize(context.resources.getDimensionPixelSize(widthRes),
//                context.resources.getDimensionPixelSize(heightRes))
//    }
//
//    /**
//     * Change drawable icon size
//     *
//     * @param width  Set width size
//     * @param height Set height size
//     */
//    fun setIconSize(@Dimension width: Int, @Dimension height: Int) {
//        iconWidth = width
//        iconHeight = height
//        makeDrawableIcons()
//        updateIcons()
//    }
//
//    private fun checkHasIconSize() {
//        if (iconWidth === UNDEFINED_RESOURCE || iconHeight === UNDEFINED_RESOURCE) {
//            throw IllegalStateException("You must set the 'iconSize'")
//        }
//    }
//
//    private fun makeDrawableIcons() {
//        for (i in 0 until drawables.size) {
//            if (drawableResIds[i] !== UNDEFINED_RESOURCE) {
//                setDrawable(i, drawableResIds[i])
//            }
//        }
//    }
//
//    private fun setColorFilter(index: Int, @ColorInt color: Int) {
//        if (drawables[index] != null) {
//            drawables[index]?.setColorFilter(color, PorterDuff.Mode.SRC_IN)
//        }
//    }
//
//    private fun setVectorDrawable(index: Int, @DrawableRes resourceId: Int) {
//        if (resourceId == UNDEFINED_RESOURCE) {
//            drawables[index] = null
//            drawableResIds[index] = UNDEFINED_RESOURCE
//            updateIcons()
//        } else {
//            checkHasIconSize()
//            setDrawable(index, resourceId)
//            drawableResIds[index] = resourceId
//            updateIcons()
//        }
//    }
//
//    private fun updateIcons() {
//        setCompoundDrawablesWithIntrinsicBounds(drawables[INDEX_LEFT], drawables[INDEX_TOP],
//                drawables[INDEX_RIGHT], drawables[INDEX_BOTTOM])
//    }
//
//    private fun setDrawable(index: Int, @DrawableRes resourceId: Int) {
//        drawables[index] = resource2VectorDrawable(resourceId, iconColor, iconWidth, iconHeight)
//    }
//
//    /**
//     * @param resourceId drawable resourceId
//     * @param iconColor  color integer
//     * @param iconWidth  pixel size
//     * @param iconHeight pixel size
//     * @return Resized bitmap
//     */
//    private fun resource2VectorDrawable(@DrawableRes resourceId: Int, @ColorInt iconColor: Int,
//                                        iconWidth: Int, iconHeight: Int): Drawable {
//        val context = context
//        val drawable = AppCompatResources.getDrawable(context, resourceId)
//                ?: throw Resources.NotFoundException("Resource not found : %s." + resourceId)
//
//// See if we need to 'fix' the drawableLeft
//        fixDrawable(drawable)
//        // Set color
//        if (iconColor != UNDEFINED_RESOURCE) {
//            DrawableCompat.setTint(drawable, iconColor)
//            DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN)
//        }
//        // Resize Bitmap
//        return BitmapDrawable(context.resources,
//                Bitmap.createScaledBitmap(drawable2Bitmap(drawable, iconWidth, iconHeight), iconWidth, iconHeight, true))
//    }
//
//    /**
//     * Convert to bitmap from drawable
//     */
//    private fun drawable2Bitmap(drawable: Drawable, iconWidth: Int, iconHeight: Int): Bitmap {
//        val bitmap = Bitmap.createBitmap(iconWidth, iconHeight, Bitmap.Config.ARGB_8888)
//        val canvas = Canvas(bitmap)
//        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
//        drawable.draw(canvas)
//        return bitmap
//    }
//
//    /**
//     * @see android.support.v7.widget.DrawableUtils.fixDrawable
//     */
//    private fun fixDrawable(drawable: Drawable) {
//        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP && VECTOR_DRAWABLE_CLAZZ_NAME.equals(drawable.javaClass.name)) {
//            fixVectorDrawableTinting(drawable)
//        }
//    }
//
//    /**
//     * @see android.support.v7.widget.DrawableUtils.fixVectorDrawableTinting
//     */
//    private fun fixVectorDrawableTinting(drawable: Drawable) {
//        val originalState = drawable.state
//        if (originalState.size == 0) {
//            // The drawable doesn't have a state, so set it to be checked
//            drawable.state = CHECKED_STATE_SET
//        } else {
//            // Else the drawable does have a state, so clear it
//            drawable.state = View.EMPTY_STATE_SET
//        }
//        // Now set the original state
//        drawable.state = originalState
//    }
//
//    private fun isRtl(): Boolean {
//        val resources = context.resources
//        val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
//            resources.configuration.locales.getFirstMatch(resources.assets.locales)
//        else
//            resources.configuration.locale
//        return TextUtilsCompat.getLayoutDirectionFromLocale(locale) == ViewCompat.LAYOUT_DIRECTION_RTL
//    }


}