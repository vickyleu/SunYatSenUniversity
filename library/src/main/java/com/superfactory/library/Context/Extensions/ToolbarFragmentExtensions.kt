package com.superfactory.library.Context.Extensions

import android.graphics.drawable.Drawable
import android.view.View
import com.superfactory.library.Context.BaseToolbarFragment
import org.jetbrains.anko.support.v4.ctx

/**
 * Created by vicky on 2018/1/22.
 */

fun BaseToolbarFragment<*, *>.setTitle(str: String?) {
    ToolbarExtensions.setTitle(str, viewModel)
}

fun BaseToolbarFragment<*, *>.setBackIcon(res: Int?) {
    ToolbarExtensions.setBackIcon(res, viewModel)
}

fun BaseToolbarFragment<*, *>.setBackTextSize(size: Int?) {
    ToolbarExtensions.setBackTextSize(size,this.ctx, viewModel)
}

fun BaseToolbarFragment<*, *>.setBackTextColor(color: Int?) {
    ToolbarExtensions.setBackTextColor(color, this.ctx,viewModel)
}

fun BaseToolbarFragment<*, *>.setBackIcon(res: String?) {
    ToolbarExtensions.setBackIcon(res, this.ctx,viewModel)
}

fun BaseToolbarFragment<*, *>.setBackIcon(drawable: Drawable?) {
    ToolbarExtensions.setBackIcon(drawable, viewModel)
}

fun BaseToolbarFragment<*, *>.setRightIcon(res: Int?) {
    ToolbarExtensions.setRightIcon(res, viewModel)
}

fun BaseToolbarFragment<*, *>.setRightTextSize(size: Int?) {
    ToolbarExtensions.setRightTextSize(size,this.ctx, viewModel)
}

fun BaseToolbarFragment<*, *>.setRightTextColor(color: Int?) {
    ToolbarExtensions.setRightTextColor(color, this.ctx,viewModel)
}

fun BaseToolbarFragment<*, *>.setRightIcon(res: String?) {
    ToolbarExtensions.setRightIcon(res, this.ctx,viewModel)
}

fun BaseToolbarFragment<*, *>.setRightIcon(drawable: Drawable?) {
    ToolbarExtensions.setRightIcon(drawable, viewModel)
}

fun BaseToolbarFragment<*, *>.setBackView(view: View?) {
    ToolbarExtensions.setBackView(view, viewModel)
}

fun BaseToolbarFragment<*, *>.setRightView(view: View?) {
    ToolbarExtensions.setRightView(view, viewModel)
}

fun BaseToolbarFragment<*, *>.setBackgroundColor(color: Int?) {
    ToolbarExtensions.setBackgroundColor(color, viewModel)
}

