package com.superfactory.library.Context

import android.app.Application
import android.content.ComponentCallbacks
import android.content.Context
import android.content.res.Configuration
import android.text.TextUtils
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshFooter
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.superfactory.library.Bridge.Anko.ScreenSizeExtension
import com.superfactory.library.BuildConfig
import com.superfactory.library.Communication.IRetrofit
import com.superfactory.library.Communication.RetrofitCenter
import com.superfactory.library.R
import com.tencent.bugly.crashreport.CrashReport


/**
 * Created by vicky on 2018.01.17.
 *
 * @Author vicky
 * @Date 2018年01月17日  10:50:42
 * @ClassName 这里输入你的类名(或用途)
 */

abstract class BaseApp : Application() {
    companion object {
        var appDelegate: BaseApp? = null
        //static 代码段可以防止内存泄露
        init {
            //设置全局的Header构建器
            SmartRefreshLayout.setDefaultRefreshHeaderCreater { context, layout ->
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white)//全局设置主题颜色
                if (appDelegate != null) {
                    return@setDefaultRefreshHeaderCreater appDelegate!!.loadBaseHeader(context, layout)
                } else {
                    return@setDefaultRefreshHeaderCreater ClassicsHeader(context);
                }
            }
            //设置全局的Footer构建器
            SmartRefreshLayout.setDefaultRefreshFooterCreater { context, layout ->
                if (appDelegate != null) {
                    return@setDefaultRefreshFooterCreater appDelegate!!.loadBaseFooter(context, layout)
                } else {
                    return@setDefaultRefreshFooterCreater ClassicsFooter(context).setDrawableSize(20f)
                }
            }

        }


    }

    val mScreenSizeExtension: ScreenSizeExtension = ScreenSizeExtension()

    fun getAppScreenSize(): ScreenSizeExtension {
        return mScreenSizeExtension;
    }

    open fun loadBaseFooter(context: Context, layout: RefreshLayout): RefreshFooter {
        //指定为经典Footer，默认是 BallPulseFooter
        return ClassicsFooter(context).setDrawableSize(20f)
    }

    open fun loadBaseHeader(context: Context, layout: RefreshLayout): RefreshHeader {
        return ClassicsHeader(context)//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
    }
    private  var retrofit: RetrofitCenter<*>?=null
    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects (excluding content providers) have been created.
     * Implementations should be as quick as possible (for example using
     * lazy initialization of state) since the time spent in this function
     * directly impacts the performance of starting the first activity,
     * service, or receiver in a process.
     * If you override this method, be sure to call super.onCreate().
     */
    override fun onCreate() {
        appDelegate = this
        super.onCreate()
        val service=loadRetrofitService()
        if (service!=null){
            retrofit= RetrofitCenter.getRetrofiter(service)
        }
        val dm = appDelegate!!.resources.displayMetrics
        mScreenSizeExtension.width=dm.widthPixels;
        mScreenSizeExtension.height=dm.heightPixels;
        mScreenSizeExtension.density= dm.density;
        mScreenSizeExtension.densityDpi=dm.densityDpi;

        val bugly = buglyID()
        if (!TextUtils.isEmpty(bugly))
            CrashReport.initCrashReport(applicationContext, bugly, BuildConfig.DEBUG);
    }

    abstract fun buglyID(): String

    protected abstract fun loadRetrofitService():Class<in IRetrofit>?
    /**
     * This method is for use in emulated process environments.  It will
     * never be called on a production Android device, where processes are
     * removed by simply killing them; no user code (including this callback)
     * is executed when doing so.
     */
    override fun onTerminate() {
        super.onTerminate()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
    }

    override fun registerComponentCallbacks(callback: ComponentCallbacks) {
        super.registerComponentCallbacks(callback)
    }

    override fun unregisterComponentCallbacks(callback: ComponentCallbacks) {
        super.unregisterComponentCallbacks(callback)
    }

    override fun registerActivityLifecycleCallbacks(callback: Application.ActivityLifecycleCallbacks) {
        super.registerActivityLifecycleCallbacks(callback)
    }

    override fun unregisterActivityLifecycleCallbacks(callback: Application.ActivityLifecycleCallbacks) {
        super.unregisterActivityLifecycleCallbacks(callback)
    }

    override fun registerOnProvideAssistDataListener(callback: Application.OnProvideAssistDataListener) {
        super.registerOnProvideAssistDataListener(callback)
    }

    override fun unregisterOnProvideAssistDataListener(callback: Application.OnProvideAssistDataListener) {
        super.unregisterOnProvideAssistDataListener(callback)
    }

    fun injectEmojix(): Boolean {
        return true
    }


}
