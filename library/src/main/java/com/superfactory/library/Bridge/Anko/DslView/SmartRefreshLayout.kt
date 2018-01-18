package com.superfactory.library.Bridge.Anko.DslView

/**
 * Created by vicky on 2018.01.17.
 *
 * @Author vicky
 * @Date 2018年01月17日  14:54:32
 * @ClassName 这里输入你的类名(或用途)
 */
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.scwang.smartrefresh.layout.SmartRefreshLayout

open class _SmartRefreshLayout(ctx: Context) : SmartRefreshLayout(ctx) {
    inline fun <T : View> T.lparams(
            c: Context?,
            attrs: AttributeSet?,
            init: SmartRefreshLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = SmartRefreshLayout.LayoutParams(c!!, attrs!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
            c: Context?,
            attrs: AttributeSet?
    ): T {
        val layoutParams = SmartRefreshLayout.LayoutParams(c!!, attrs!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
            width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
            height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
            init: SmartRefreshLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = SmartRefreshLayout.LayoutParams(width, height)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }
//todo

    inline fun <T : View> T.lparams(
            width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
            height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT
    ): T {
        val layoutParams = SmartRefreshLayout.LayoutParams(width, height)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
            source: ViewGroup.MarginLayoutParams?,
            init: SmartRefreshLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = SmartRefreshLayout.LayoutParams(source!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
            source: ViewGroup.MarginLayoutParams?
    ): T {
        val layoutParams = SmartRefreshLayout.LayoutParams(source!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
            source: ViewGroup.LayoutParams?,
            init: SmartRefreshLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = SmartRefreshLayout.LayoutParams(source!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
            source: ViewGroup.LayoutParams?
    ): T {
        val layoutParams = SmartRefreshLayout.LayoutParams(source!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
            source: SmartRefreshLayout.LayoutParams?,
            init: SmartRefreshLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = SmartRefreshLayout.LayoutParams(source!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
            source: SmartRefreshLayout.LayoutParams?
    ): T {
        val layoutParams = SmartRefreshLayout.LayoutParams(source!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

}


