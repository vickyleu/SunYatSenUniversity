package com.superfactory.library.Graphics

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.support.design.widget.TextInputEditText
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.Selection
import android.text.TextUtils
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.superfactory.library.Bridge.Anko.DslView.OnTextCleanListener
import com.superfactory.library.R

/**
 * Created by vicky on 2018.01.29.
 *
 * @Author vicky
 * @Date 2018年01月29日  16:27:59
 * @ClassName 这里输入你的类名(或用途)
 */
class CleanUpEditText(context: Context) : TextInputEditText(context) {
    val UNDEFINED_RESOURCE = 0xABCD
    private val INDEX_LEFT = 0
    private val INDEX_TOP = 1
    private val INDEX_RIGHT = 2
    private val INDEX_BOTTOM = 3
    private var m: InputMethodManager? = null
    private var mOnClick: OnClickListener? = null
    var maxLength: Int = 0
    override fun setOnClickListener(listener: OnClickListener?) {
        this.mOnClick = listener
    }

    fun setRightClick(cleanUp: OnTextCleanListener?) {
        setOnTouchListener(object : OnTouchListener {
            var touch_flag = 0
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if (touch_flag < 3)
                    touch_flag++
                /**
                 * Called when a touch event is dispatched to a view. This allows listeners to
                 * get a chance to respond before the target view.
                 *
                 * @param v The view the touch event has been dispatched to.
                 * @param event The MotionEvent object containing full information about
                 * the event.
                 * @return True if the listener has consumed the event, false otherwise.
                 */
                m = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                if (isFocused) {
                    m?.showSoftInput(this@CleanUpEditText, InputMethodManager.SHOW_FORCED);
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
                        if (cleanUp!=null)
                        cleanUp.onClean()
                    }
                }
                if (event?.action==MotionEvent.ACTION_UP){
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

    init {
        addTextChangedListener(object : TextWatcher {
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
                        val originHeight = right.intrinsicWidth
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
                    }else{
                        var height = textSize.toInt()
                        var originWidth = right!!.intrinsicWidth
                        val originHeight = right.intrinsicWidth
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

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
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
                val num = "[1][345789]\\d{9}"//"[1]"代表第1位为数字1，"[345789]"代表第二位可以为3、4、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
                return if (TextUtils.isEmpty(text.toString())) {
                    false
                } else {
                    //matches():字符串是否在给定的正则表达式匹配
                    return text.toString().matches(num.toRegex())
                }
            }
        }
        return false
    }

}