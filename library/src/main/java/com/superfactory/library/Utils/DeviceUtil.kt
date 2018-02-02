package com.superfactory.library.Utils

import android.annotation.SuppressLint
import android.content.Context

/**
 * Created by vicky on 2018.02.02.
 *
 * @Author vicky
 * @Date 2018年02月02日  20:03:03
 * @ClassName 这里输入你的类名(或用途)
 */

object DeviceUtil {

    /**
     * 是否是华为
     */
    val isHUAWEI: Boolean
        get() = android.os.Build.MANUFACTURER == "HUAWEI"

    @SuppressLint("PrivateApi")
//获取是否存在NavigationBar
    fun checkDeviceHasNavigationBar(context: Context): Boolean {
        var hasNavigationBar = false
        try {
            val rs = context.resources
            val id = rs.getIdentifier("config_showNavigationBar", "bool", "android")
            if (id > 0) {
                hasNavigationBar = rs.getBoolean(id)
            }
            val systemPropertiesClass = Class.forName("android.os.SystemProperties")
            val m = systemPropertiesClass.getMethod("get", String::class.java)
            val navBarOverride = m.invoke(systemPropertiesClass, "qemu.hw.mainkeys") as String
            if ("1" == navBarOverride) {
                hasNavigationBar = false
            } else if ("0" == navBarOverride) {
                hasNavigationBar = true
            }
        } catch (e: Exception) {

        }

        return hasNavigationBar
    }
}
