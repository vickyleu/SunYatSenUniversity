package com.superfactory.library.Bridge.Anko.BindingExtensions

import android.content.Context
import android.support.v4.app.FragmentManager
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Context.BaseActivity
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.dip

/**
 * Created by vicky on 2018.01.19.
 *
 * @Author vicky
 * @Date 2018年01月19日  19:35:35
 * @ClassName 这里输入你的类名(或用途)
 */
inline fun <V> BindingComponent<*, V>.getModel(): V = this.viewModel!! as V

inline fun BindingComponent<*, *>.dipValue(value: Int, ctx: Context): Int = ctx.dip(value)
inline fun BindingComponent<*, *>.supportFragmentManager(ui: AnkoContext<*>): FragmentManager = (ui.owner as BaseActivity<*, *>).getSupportFragmentManager()
inline fun BindingComponent<*, *>.fragmentManager(ui: AnkoContext<*>): android.app.FragmentManager = (ui.owner as BaseActivity<*, *>).getFragmentManager()