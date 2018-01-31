package com.superfactory.library.Utils

import android.content.Context


/**
 * Created by vicky on 2018.01.31.
 *
 * @Author vicky
 * @Date 2018年01月31日  11:06:07
 * @ClassName 这里输入你的类名(或用途)
 */
open class ConfigXmlAccessor {
    companion object {
        fun storeValue(ctx: Context, path: String, key: String, value: Any?) {
            if (value == null) return
            val sharedPreferences = ctx.getSharedPreferences(path, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            when (value::class) {
                String::class -> {
                    editor.putString(key, value as String)
                }
                Int::class -> {
                    editor.putInt(key, value as Int)
                }
                Double::class -> {
                    editor.putFloat(key, (value as Double).toFloat())
                }
                Float::class -> {
                    editor.putFloat(key, value as Float)
                }
                Boolean::class -> {
                    editor.putBoolean(key, value as Boolean)
                }
                Long::class -> {
                    editor.putLong(key, value as Long)
                }
                Set::class -> {
                    if (value is Set<*> && value .firstOrNull() is String?) {
                        editor.putStringSet(key, value as Set<String>)
                    }
                }
                else -> {

                }
            }

            editor.apply()
        }

        @Suppress("UNCHECKED_CAST")
        fun <D : Any> restoreValue(ctx: Context, path: String, key: String, normalizer: D): D? {
            val sharedPreferences = ctx.getSharedPreferences(path, Context.MODE_PRIVATE)
            var d: D? = null
            when (normalizer::class) {
                String::class -> {
                    d = sharedPreferences.getString(key, normalizer as String) as D
                }
                Int::class -> {
                    d = sharedPreferences.getInt(key, normalizer as Int) as D
                }
                Double::class -> {
                    d = sharedPreferences.getFloat(key, (normalizer as Double).toFloat()).toDouble() as D
                }
                Float::class -> {
                    d = sharedPreferences.getFloat(key, normalizer as Float) as D
                }
                Boolean::class -> {
                    d = sharedPreferences.getBoolean(key, normalizer as Boolean) as D
                }
                Long::class -> {
                    d = sharedPreferences.getLong(key, normalizer as Long) as D
                }
                Set::class -> {
                    if (normalizer is Set<*> &&normalizer .firstOrNull() is String?) {
                        d = sharedPreferences.getStringSet(key, normalizer as Set<String>) as D
                    }
                }
                else -> {

                }
            }
            return d
        }
    }
}