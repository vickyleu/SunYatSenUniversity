package com.superfactory.library.Bridge.Anko.Adapt

/**
 * Created by vicky on 2018.01.19.
 *
 * @Author vicky
 * @Date 2018年01月19日  16:00:13
 * @ClassName 这里输入你的类名(或用途)
 */
interface BaseAnko<V, A> {

    fun ankoToolBar(viewModel: V): BaseToolBar<V, A>?

    fun onLoadedModel(viewModel: V)

}