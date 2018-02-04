package com.superfactory.library.Graphics

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView


/**
 * Created by vicky on 2018.01.22.
 *
 * @Author vicky
 * @Date 2018年01月22日  17:06:56
 * @ClassName 这里输入你的类名(或用途)
 */
class SimpleDividerItemDecoration() : RecyclerView.ItemDecoration() {
    private var mDivider: Drawable? = null

    constructor(context: Context, drawable: Drawable) : this() {
        mDivider = drawable
    }

    constructor(context: Context, drawableRes: Int) : this() {
        mDivider = ContextCompat.getDrawable(context, drawableRes)
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        if (mDivider == null) return
        for (i in 0 until childCount) {
            if (i == childCount-1)return
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin
            val bottom = top + mDivider!!.intrinsicHeight

            mDivider!!.setBounds(left, top, right, bottom)
            mDivider!!.draw(c)
        }
    }
}