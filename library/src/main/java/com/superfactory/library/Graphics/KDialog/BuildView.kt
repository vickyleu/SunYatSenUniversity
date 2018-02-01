package com.superfactory.library.Graphics.Circledialog

/**
 * Created by vicky on 2018.02.01.
 *
 * @Author vicky
 * @Date 2018年02月01日  11:20:08
 * @ClassName 这里输入你的类名(或用途)
 */

import android.view.View

/**
 * Created by hupei on 2017/3/29.
 */

interface BuildView {

    /**
     * 取出根布局
     *
     * @return 对话框视图
     */
//    var view: View?

    /**
     * 生成根布局
     */
    fun buildRoot()

    /**
     * 生成标题布局
     */
    fun buildTitle()

    /**
     * 生成文本布局
     */
    fun buildText()

    /**
     * 刷新文本内容
     */
    fun refreshText()

    /**
     * 生成列表布局
     */
    fun buildItems()

    /**
     * 生成列表按钮
     */
    fun buildItemsButton()

    /**
     * 刷新列表内容
     */
    fun refreshItems()

    /**
     * 生成进度条布局
     */
    fun buildProgress()

    /**
     * 刷新进度条
     */
    fun refreshProgress()

    /**
     * 生成输入布局
     */
    fun buildInput()

    /**
     * 生成多按钮布局
     */
    fun buildMultipleButton()

    /**
     * 生成单个按钮布局
     */
    fun buildSingleButton()

    /**
     * 注册输入框确定事件
     */
    fun regInputListener()

    fun getInputText() :String?

    fun getInputView(): View?

    fun getView():View?
}
