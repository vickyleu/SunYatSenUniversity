package com.superfactory.library.Bridge.Anko.ViewExtensions

import android.content.Context
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.support.v7.widget.RecyclerView
import com.superfactory.library.Graphics.SimpleDividerItemDecoration
import org.jetbrains.anko.matchParent

/**
 * Created by vicky on 2018.01.22.
 *
 * @Author vicky
 * @Date 2018年01月22日  17:19:49
 * @ClassName 这里输入你的类名(或用途)
 */
fun RecyclerView.getLineDividerItemDecoration(height: Int, color: Int): SimpleDividerItemDecoration {
    return SimpleDividerItemDecoration(context, getLineDivider(context, height, color))
}

private fun getLineDivider(context: Context, height: Int, color: Int): Drawable {
    val shape = ShapeDrawable(RectShape())
    shape.paint.color = color
    shape.paint.style = Paint.Style.FILL
    shape.paint.isAntiAlias = true
    shape.intrinsicHeight = height
    shape.intrinsicWidth = matchParent
    return shape
}
