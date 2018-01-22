package com.superfactory.library.Context.Extensions

import android.graphics.drawable.Drawable
import android.view.View
import com.superfactory.library.Context.BaseToolBarActivity

/**
 * Created by vicky on 2018/1/22.
 */

fun BaseToolBarActivity<*, *>.setTitle(str: String?) {
    ToolbarExtensions.setTitle(str, viewModel)
}

fun BaseToolBarActivity<*, *>.setBackIcon(res: Int?) {
    ToolbarExtensions.setBackIcon(res, viewModel)
}

fun BaseToolBarActivity<*, *>.setBackTextSize(size: Int?) {
    ToolbarExtensions.setBackTextSize(size, this, viewModel)
}

fun BaseToolBarActivity<*, *>.setBackTextColor(color: Int?) {
    ToolbarExtensions.setBackTextColor(color, this, viewModel)
}

fun BaseToolBarActivity<*, *>.setBackIcon(res: String?) {
    ToolbarExtensions.setBackIcon(res, this, viewModel)
}

fun BaseToolBarActivity<*, *>.setBackIcon(drawable: Drawable?) {
    ToolbarExtensions.setBackIcon(drawable, viewModel)
}

fun BaseToolBarActivity<*, *>.setRightIcon(res: Int?) {
    ToolbarExtensions.setRightIcon(res, viewModel)
}

fun BaseToolBarActivity<*, *>.setRightTextSize(size: Int?) {
    ToolbarExtensions.setRightTextSize(size, this, viewModel)
}

fun BaseToolBarActivity<*, *>.setRightTextColor(color: Int?) {
    ToolbarExtensions.setRightTextColor(color, this, viewModel)
}

fun BaseToolBarActivity<*, *>.setRightIcon(res: String?) {
    ToolbarExtensions.setRightIcon(res, this, viewModel)
}

fun BaseToolBarActivity<*, *>.setRightIcon(drawable: Drawable?) {
    ToolbarExtensions.setRightIcon(drawable, viewModel)
}

fun BaseToolBarActivity<*, *>.setBackView(view: View?) {
    ToolbarExtensions.setBackView(view, viewModel)
}

fun BaseToolBarActivity<*, *>.setRightView(view: View?) {
    ToolbarExtensions.setRightView(view, viewModel)
}

fun BaseToolBarActivity<*, *>.setBackgroundColor(color: Int?) {
    ToolbarExtensions.setBackgroundColor(color, viewModel)
}

