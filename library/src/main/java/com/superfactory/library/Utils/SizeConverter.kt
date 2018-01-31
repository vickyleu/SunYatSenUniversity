package com.superfactory.library.Utils

import android.text.TextUtils

import java.util.Locale

/***
 * 存储大小(单位)转换器.
 */
enum class SizeConverter {
    /**
     * 转换任意单位的大小, 返回结果会包含两位小数但不包含单位.
     */
    Arbitrary {
        override fun convert(size: Float): String {
            var size = size
            while (size > 1024) {
                size /= 1024f
            }
            return String.format(Locale.CHINA, FORMAT_F, size)
        }
    },

    // -----------------------------------------------------------------------
    // 有单位

    /**
     * 转换单位为B的大小, 返回结果会包含两位小数以及单位. 如: 1024B->1KB, (1024*1024)B->1MB
     */
    B {
        override fun convert(B: Float): String {
            return converter(0, B)
        }
    },

    /**
     * 转换单位为KB的大小, 返回结果会包含两位小数以及单位.
     */
    KB {
        override fun convert(KB: Float): String {
            return converter(1, KB)
        }
    },

    /**
     * 转换单位为MB的大小, 返回结果会包含两位小数以及单位.
     */
    MB {
        override fun convert(MB: Float): String {
            return converter(2, MB)
        }
    },

    /**
     * 转换单位为GB的大小, 返回结果会包含两位小数以及单位.
     */
    GB {
        override fun convert(GB: Float): String {
            return converter(3, GB)
        }
    },

    /**
     * 转换单位为TB的大小, 返回结果会包含两位小数以及单位.
     */
    TB {
        override fun convert(TB: Float): String {
            return converter(4, TB)
        }
    },

    // -----------------------------------------------------------------------
    // trim没单位
    /**
     * 转换任意单位的大小, 返回结果小数部分为0时将去除两位小数, 不包含单位.
     */
    ArbitraryTrim {
        override fun convert(size: Float): String {
            var size = size
            while (size > 1024) {
                size /= 1024f
            }

            val sizeInt = size.toInt()
            val isfloat = size - sizeInt > 0.0f
            return if (isfloat) {
                String.format(Locale.CHINA, FORMAT_F, size)
            } else String.format(Locale.CHINA, FORMAT_D, sizeInt)
        }
    },

    // -----------------------------------------------------------------------
    // trim有单位
    /**
     * 转换单位为B的大小, 返回结果小数部分为0时将去除两位小数, 会包含单位.
     */
    BTrim {
        override fun convert(B: Float): String {
            return trimConverter(0, B)
        }

    },
    /**
     * 转换单位为KB的大小, 返回结果小数部分为0时将去除两位小数, 会包含单位.
     */
    KBTrim {
        override fun convert(KB: Float): String {
            return trimConverter(1, KB)
        }
    },
    /**
     * 转换单位为MB的大小, 返回结果小数部分为0时将去除两位小数, 会包含单位.
     */
    MBTrim {
        override fun convert(MB: Float): String {
            return trimConverter(2, MB)
        }
    },
    /**
     * 转换单位为GB的大小, 返回结果小数部分为0时将去除两位小数, 会包含单位.
     */
    GBTrim {
        override fun convert(GB: Float): String {
            return trimConverter(3, GB)
        }
    },
    /**
     * 转换单位为TB的大小, 返回结果小数部分为0时将去除两位小数, 会包含单位.
     */
    TBTrim {
        override fun convert(TB: Float): String {
            return trimConverter(4, TB)
        }
    };

    /***
     *
     *  将指定的大小转换到1024范围内的大小. 注意该方法的最大单位为PB, 最小单位为B,
     * 任何超出该范围的单位最终会显示为**.
     *
     * @param size 要转换的大小, 注意是浮点数, 不要以整形的方式传入, 容易造成溢出.
     * (如: 1024*1024*1024*1024*1024会溢出, 使结果为0, 因为它先将结果以int相乘后再转换为float;
     * 而1024.0F*1024.0F*1024.0F*1024.0F*1024.0F就不会溢出)
     * @return
     */
    abstract fun convert(size: Float): String

    companion object {

        // -----------------------------------------------------------------------
        // 单位转换

        private val UNITS = arrayOf("B", "KB", "MB", "GB", "TB", "PB", "**")

        private val LAST_IDX = UNITS.size - 1

        private val FORMAT_F = "%1$-1.2f"
        private val FORMAT_F_UNIT = "%1$-1.2f%2\$s"

        private val FORMAT_D = "%1$-1d"
        private val FORMAT_D_UNIT = "%1$-1d%2\$s"

        // -----------------------------------------------------------------------
        private fun converter(unit: Int, size: Float): String {
            var size = size
            var unitIdx = unit
            while (size > 1024) {
                unitIdx++
                size /= 1024f
            }
            val idx = if (unitIdx < LAST_IDX) unitIdx else LAST_IDX
            return String.format(Locale.CHINA, FORMAT_F_UNIT, size, UNITS[idx])
        }

        private fun trimConverter(unit: Int, size: Float): String {
            var size = size
            var unitIdx = unit
            while (size > 1024) {
                unitIdx++
                size /= 1024f
            }

            val sizeInt = size.toInt()
            val isfloat = size - sizeInt > 0.0f
            val idx = if (unitIdx < LAST_IDX) unitIdx else LAST_IDX
            return if (isfloat) {
                String.format(Locale.CHINA, FORMAT_F_UNIT, size, UNITS[idx])
            } else String.format(Locale.CHINA, FORMAT_D_UNIT, sizeInt, UNITS[idx])
        }

        fun convert2MB(B: Float): Double {
            return trimConverter2Mb(0, B)
        }

        private fun trimConverter2Mb(unit: Int, size: Float): Double {
            var size = size
            var unitIdx = unit
            while (size > 1024 && unitIdx != 2) {
                unitIdx++
                size /= 1024f
            }

            val sizeInt = size.toInt()
            val isfloat = size - sizeInt > 0.0f
            var str: String? = null
            if (isfloat) {
                str = String.format(Locale.CHINA, FORMAT_F, size)
            } else
                str = String.format(Locale.CHINA, FORMAT_D_UNIT, sizeInt, "")
            return if (!TextUtils.isEmpty(str)) {
                java.lang.Double.valueOf(str)!!
            } else 0.0
        }


        // -----------------------------------------------------------------------
        fun convertBytes(B: Float, trim: Boolean): String {
            return if (trim) trimConvert(0, B, true) else convert(0, B, true)
        }

        fun convertKB(KB: Float, trim: Boolean): String {
            return if (trim) trimConvert(1, KB, true) else convert(1, KB, true)
        }

        fun convertMB(MB: Float, trim: Boolean): String {
            return if (trim) trimConvert(2, MB, true) else convert(2, MB, true)
        }

        /***
         *
         *  存储大小单位间的转换. 注意该方法的最大单位为PB, 最小单位为B,
         * 任何超出该范围的单位最终会显示为**.
         *
         * @param unit     从哪个单位开始
         * @param size     存储大小, 注意是float, 不要以整形的形式传入, 否则会溢出(如:1024*1024这种,
         * 它是先将1024*1024作为int相乘再转换为float的, 如果值过大的话就会溢出了,
         * 所以这么写1024.0F*1024.0F)
         * @param withUnit 返回的结果字符串是否带有对应的单位
         * @return
         */
        private fun convert(unit: Int, size: Float, withUnit: Boolean): String {
            var size = size
            var unitIdx = unit
            while (size > 1024) {
                unitIdx++
                size /= 1024f
            }
            if (withUnit) {
                val idx = if (unitIdx < LAST_IDX) unitIdx else LAST_IDX
                return String.format(Locale.CHINA, FORMAT_F_UNIT, size, UNITS[idx])
            }
            return String.format(Locale.CHINA, FORMAT_F, size)
        }

        /***
         *
         *  存储大小单位间的转换, 如果转换后小数部分为0, 则去除小数部分.
         * 注意该方法的最大单位为PB, 最小单位为B, 任何超出该范围的单位最终会显示为**.
         *
         * @param unit     从哪个单位开始
         * @param size     存储大小, 注意是float, 不要以整形的形式传入, 否则会溢出(如:1024*1024这种,
         * 它是先将1024*1024作为int相乘再转换为float的, 如果值过大的话就会溢出了,
         * 所以这么写1024.0F*1024.0F)
         * @param withUnit 返回的结果字符串是否带有对应的单位
         * @return
         */
        private fun trimConvert(unit: Int, size: Float, withUnit: Boolean): String {
            var size = size
            var unitIdx = unit
            while (size > 1024) {
                unitIdx++
                size /= 1024f
            }

            val sizeInt = size.toInt()
            val isfloat = size - sizeInt > 0.0f
            if (withUnit) {
                val idx = if (unitIdx < LAST_IDX) unitIdx else LAST_IDX
                return if (isfloat) {
                    String.format(Locale.CHINA, FORMAT_F_UNIT, size, UNITS[idx])
                } else String.format(Locale.CHINA, FORMAT_D_UNIT, sizeInt, UNITS[idx])
            }

            return if (isfloat) {
                String.format(Locale.CHINA, FORMAT_F, size)
            } else String.format(Locale.CHINA, FORMAT_D, sizeInt)
        }
    }
}