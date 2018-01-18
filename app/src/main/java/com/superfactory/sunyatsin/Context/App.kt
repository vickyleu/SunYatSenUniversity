package com.superfactory.sunyatsin.Context

import android.content.Context
import com.scwang.smartrefresh.layout.api.RefreshFooter
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.superfactory.library.Context.BaseApp

/**
 * Created by vicky on 2018.01.17.
 *
 * @Author vicky
 * @Date 2018年01月17日  11:02:05
 * @ClassName 这里输入你的类名(或用途)
 */
class App : BaseApp() {
    override fun buglyID(): String {
        return "13686de2a3"
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun loadBaseFooter(context: Context, layout: RefreshLayout): RefreshFooter {
        return super.loadBaseFooter(context, layout)
    }

    override fun loadBaseHeader(context: Context, layout: RefreshLayout): RefreshHeader {
        return super.loadBaseHeader(context, layout)
    }

}