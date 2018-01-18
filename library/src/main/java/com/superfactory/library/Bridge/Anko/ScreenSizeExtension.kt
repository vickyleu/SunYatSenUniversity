package com.superfactory.library.Bridge.Anko

/**
 * Created by vicky on 2018.01.17.
 *
 * @Author vicky
 * @Date 2018年01月17日  16:50:04
 * @ClassName 这里输入你的类名(或用途)
 */

class ScreenSizeExtension {
    var width: Int = 0
    var height: Int = 0
    var density: Float = 0f
    var densityDpi: Int = 0

    constructor() {}

    constructor(width: Int, height: Int) {
        this.width = width
        this.height = height
    }

    constructor(width: Int, height: Int, density: Float, densityDpi: Int) {
        this.width = width
        this.height = height
        this.density = density
        this.densityDpi = densityDpi
    }
}
