package com.superfactory.library.Graphics.HUD.manager


import com.superfactory.library.Graphics.HUD.View.LoadingDialog

/**
 * Created by Luo_xiasuhuei321@163.com on 2016/11/12.
 * desc:用于设置全局的loading样式
 */

class StyleManager {

    /**
     * 是否开启绘制
     */
    var isOpenAnim = true
        private set

    /**
     * 重绘次数
     */
    var repeatTime: Int = 0
        private set

    var speed: LoadingDialog.Speed = LoadingDialog.Speed.SPEED_TWO
        private set

    /**
     * 反馈的尺寸，单位px
     */
    var contentSize = -1
        private set

    /**
     * 文字的尺寸，单位px
     */
    var textSize = -1
        private set

    /**
     * loading的反馈展示的时间，单位ms
     */
    var showTime: Long = -1
        private set

    var isInterceptBack = true
        private set

    var loadText = "加载中..."
        private set

    var successText = "加载成功"
        private set

    var failedText = "加载失败"
        private set

    internal var loadStyle = LoadingDialog.STYLE_RING

    constructor() {}

    constructor(open: Boolean, repeatTime: Int, speed: LoadingDialog.Speed,
                contentSize: Int, textSize: Int, showTime: Long, interceptBack: Boolean,
                loadText: String, successText: String, failedText: String) {
        this.isOpenAnim = open
        this.repeatTime = repeatTime
        this.speed = speed
        this.contentSize = contentSize
        this.textSize = textSize
        this.showTime = showTime
        this.isInterceptBack = interceptBack
        this.loadText = loadText
        this.successText = successText
        this.failedText = failedText

    }

    constructor(open: Boolean, repeatTime: Int, speed: LoadingDialog.Speed,
                contentSize: Int, textSize: Int, showTime: Long, interceptBack: Boolean,
                loadText: String, successText: String, failedText: String, loadStyle: Int) {
        this.isOpenAnim = open
        this.repeatTime = repeatTime
        this.speed = speed
        this.contentSize = contentSize
        this.textSize = textSize
        this.showTime = showTime
        this.isInterceptBack = interceptBack
        this.loadText = loadText
        this.successText = successText
        this.failedText = failedText
        this.loadStyle = loadStyle
    }

    fun setLoadStyle(loadStyle: Int): StyleManager {
        this.loadStyle = loadStyle
        return this
    }

    fun getLoadStyle(): Int {
        return loadStyle
    }

    /**
     * 是否开启动态绘制
     *
     * @param openAnim true开启，false关闭
     * @return this
     */
    fun Anim(openAnim: Boolean): StyleManager {
        this.isOpenAnim = openAnim
        return this
    }

    /**
     * 重复次数
     *
     * @param times 次数
     * @return this
     */
    fun repeatTime(times: Int): StyleManager {
        this.repeatTime = times
        return this
    }

    fun speed(s: LoadingDialog.Speed): StyleManager {
        this.speed = s
        return this
    }

    /**
     * 设置loading的大小
     *
     * @param size 尺寸，单位px
     * @return this
     */
    fun contentSize(size: Int): StyleManager {
        this.contentSize = size
        return this
    }

    /**
     * 设置loading 文字的大小
     *
     * @param size 尺寸，单位px
     * @return this
     */
    fun textSize(size: Int): StyleManager {
        this.textSize = size
        return this
    }

    /**
     * 设置展示的事件，如果开启绘制则从绘制完毕开始计算
     *
     * @param showTime 事件
     * @return this
     */
    fun showTime(showTime: Long): StyleManager {
        this.showTime = showTime
        return this
    }

    /**
     * 设置是否拦截back，默认拦截
     *
     * @param interceptBack true拦截，false不拦截
     * @return this
     */
    fun intercept(interceptBack: Boolean): StyleManager {
        this.isInterceptBack = interceptBack
        return this
    }

    /**
     * 设置loading时的文字
     *
     * @param text 文字
     * @return this
     */
    fun loadText(text: String): StyleManager {
        this.loadText = text
        return this
    }

    /**
     * 设置success时的文字
     *
     * @param text 文字
     * @return this
     */
    fun successText(text: String): StyleManager {
        this.successText = text
        return this
    }

    /**
     * 设置failed时的文字
     *
     * @param text 文字
     * @return this
     */
    fun failedText(text: String): StyleManager {
        this.failedText = text
        return this
    }

}
