package com.superfactory.library.Communication.Responder

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.superfactory.library.Debuger


/**
 * Created by vicky on 2018.01.24.
 *
 * @Author vicky
 * @Date 2018年01月24日  14:25:58
 * @ClassName 这里输入你的类名(或用途)
 */
// 在 kotlin中扩展 Java 类的 Gson.fromJson 方法
// 不在传入 T 的class，inline 的作用就是将函数插入到被调用处，配合 reified 就可以获取到 T 真正的类型
//inline fun <reified T : Any> Gson.fromJson(json: String): T {
//    val t = object : TypeToken<T>() {}.type
//    return Gson().fromJson(json, t)
//}

//@Throws(Exception::class)
inline fun <reified T> Gson.fromJson(json: String): T? = try {
    Debuger.printMsg(this, json)
    val model = this.fromJson<T>(json, object : TypeToken<T>() {}.type)
    Debuger.printMsg(this, model ?: "null")
    model
} catch (e: Exception) {
    e.printStackTrace()
    null
}
//@Throws(Exception::class)
inline fun <reified T : Any> Gson.fromJsonList(json: String): List<T>? = try {
    Debuger.printMsg(this, json)
    val model = this.fromJson<List<T>>(json, object : TypeToken<List<T>>() {}.type)
    Debuger.printMsg(this, model ?: "null")
    model
} catch (e: Exception) {
    e.printStackTrace()
    null
}


