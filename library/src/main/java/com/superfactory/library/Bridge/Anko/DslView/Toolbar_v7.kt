@file:JvmName("ToolbarLayout-v7-ViewsKt")

package com.superfactory.library.Bridge.Anko.DslView

import android.app.ActionBar
import android.content.Context
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

/**
 * Created by vicky on 2018.01.20.
 *
 * @Author vicky
 * @Date 2018年01月20日  14:05:51
 * @ClassName 这里输入你的类名(或用途)
 */
open class Toolbar_v7(ctx: Context) : Toolbar(ctx) {

    inline fun <T : View> T.lparams(
            c: Context?,
            attrs: AttributeSet?,
            init: Toolbar.LayoutParams.() -> Unit
    ): T {
        val layoutParams = Toolbar.LayoutParams(c!!, attrs!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
            c: Context?,
            attrs: AttributeSet?
    ): T {
        val layoutParams = Toolbar.LayoutParams(c!!, attrs!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
            width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
            height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
            init: Toolbar.LayoutParams.() -> Unit
    ): T {
        val layoutParams = Toolbar.LayoutParams(width, height)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
            width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
            height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT
    ): T {
        val layoutParams = Toolbar.LayoutParams(width, height)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
            width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
            height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
            gravity: Int,
            init: Toolbar.LayoutParams.() -> Unit
    ): T {
        val layoutParams = Toolbar.LayoutParams(width, height, gravity)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
            width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
            height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
            gravity: Int
    ): T {
        val layoutParams = Toolbar.LayoutParams(width, height, gravity)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
            gravity: Int,
            init: Toolbar.LayoutParams.() -> Unit
    ): T {
        val layoutParams = Toolbar.LayoutParams(gravity)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
            gravity: Int
    ): T {
        val layoutParams = Toolbar.LayoutParams(gravity)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
            source: Toolbar.LayoutParams?,
            init: Toolbar.LayoutParams.() -> Unit
    ): T {
        val layoutParams = Toolbar.LayoutParams(source!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
            source: Toolbar.LayoutParams?
    ): T {
        val layoutParams = Toolbar.LayoutParams(source!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
            source: ActionBar.LayoutParams?,
            init: Toolbar.LayoutParams.() -> Unit
    ): T {
        val layoutParams = Toolbar.LayoutParams(source!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
            source: ActionBar.LayoutParams?
    ): T {
        val layoutParams = Toolbar.LayoutParams(source!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
            source: ViewGroup.MarginLayoutParams?,
            init: Toolbar.LayoutParams.() -> Unit
    ): T {
        val layoutParams = Toolbar.LayoutParams(source!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
            source: ViewGroup.MarginLayoutParams?
    ): T {
        val layoutParams = Toolbar.LayoutParams(source!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
            source: ViewGroup.LayoutParams?,
            init: Toolbar.LayoutParams.() -> Unit
    ): T {
        val layoutParams = Toolbar.LayoutParams(source!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
            source: ViewGroup.LayoutParams?
    ): T {
        val layoutParams = Toolbar.LayoutParams(source!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

}