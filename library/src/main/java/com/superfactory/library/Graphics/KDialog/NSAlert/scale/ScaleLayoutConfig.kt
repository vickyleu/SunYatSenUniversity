package com.superfactory.library.Graphics.KDialog.scale

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager


/**
 * Created by hupei on 2016/3/8 17:32.
 */
class ScaleLayoutConfig private constructor() {

    private var mScreenWidth: Int = 0
    private var mScreenHeight: Int = 0

    private var mDesignWidth = DESIGN_WIDTH
    private var mDesignHeight = DESIGN_HEIGHT

    var scale: Float = 0.toFloat()
        private set

    private fun initInternal(context: Context, scaleAdapter: ScaleAdapter?) {
        getMetaData(context)
        checkParams()
        val size = ScaleUtils.getRealScreenSize(context)
        mScreenWidth = size[0]
        mScreenHeight = size[1]

        if (mScreenWidth > mScreenHeight) {//横屏状态下，宽高互换，按竖屏模式计算scale
            mScreenWidth = mScreenWidth + mScreenHeight
            mScreenHeight = mScreenWidth - mScreenHeight
            mScreenWidth = mScreenWidth - mScreenHeight
        }

        val deviceScale = mScreenHeight.toFloat() / mScreenWidth
        val designScale = mDesignHeight.toFloat() / mDesignWidth
        if (deviceScale <= designScale) {//高宽比小于等于标准比（较标准屏宽一些），以高为基准计算scale（以短边计算），否则以宽为基准计算scale
            scale = mScreenHeight.toFloat() / mDesignHeight
        } else {
            scale = mScreenWidth.toFloat() / mDesignWidth
        }

        if (scaleAdapter != null) {
            scale = scaleAdapter.adapt(scale, mScreenWidth, mScreenHeight)
        }
    }

    private fun getMetaData(context: Context) {
        val packageManager = context.packageManager
        val applicationInfo: ApplicationInfo?
        try {
            applicationInfo = packageManager.getApplicationInfo(context
                    .packageName, PackageManager.GET_META_DATA)
            if (applicationInfo != null && applicationInfo.metaData != null
                    && applicationInfo.metaData.containsKey(KEY_DESIGN_WIDTH)
                    && applicationInfo.metaData.containsKey(KEY_DESIGN_HEIGHT)) {
                mDesignWidth = applicationInfo.metaData.get(KEY_DESIGN_WIDTH) as Int
                mDesignHeight = applicationInfo.metaData.get(KEY_DESIGN_HEIGHT) as Int
            }
        } catch (e: PackageManager.NameNotFoundException) {

        }

    }

    private fun checkParams() {
        if (mDesignHeight <= 0 || mDesignWidth <= 0) {
            throw RuntimeException(
                    "you must set ${KEY_DESIGN_WIDTH} and ${KEY_DESIGN_HEIGHT} > 0")
        }
    }

    companion object {
        private var sInstance: ScaleLayoutConfig? = null

        private val KEY_DESIGN_WIDTH = "circle_dialog_design_width"
        private val KEY_DESIGN_HEIGHT = "circle_dialog_design_height"
        private val DESIGN_WIDTH = 1080
        private val DESIGN_HEIGHT = 1920

        @JvmOverloads
        fun init(appContext: Context, designWidth: Int = DESIGN_WIDTH, designHeight: Int = DESIGN_HEIGHT) {
            if (sInstance == null) {
                sInstance = ScaleLayoutConfig()
                sInstance!!.mDesignWidth = designWidth
                sInstance!!.mDesignHeight = designHeight
                sInstance!!.initInternal(appContext, ScaleAdapter(appContext))
            }
        }

        var instance: ScaleLayoutConfig?=null
            get() {
                if (sInstance == null) {
                    throw IllegalStateException("Must init before using.")
                }
                return sInstance
            }
    }
}
