package com.superfactory.library.Context


import android.content.Context
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.provider.Settings
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.superfactory.library.Bridge.Anko.Adapt.BaseAnko
import com.superfactory.library.Bridge.Anko.Adapt.BaseToolBar
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.observable
import com.superfactory.library.R
import com.superfactory.library.Utils.ResourceIdUtil
import com.superfactory.library.Utils.StatusBarUtil
import org.jetbrains.anko.AnkoContextImpl
import org.jetbrains.anko.find
import org.jetbrains.anko.topPadding


/**
 * Created by vicky on 2018.01.17.
 *
 * @Author vicky
 * @Date 2018年01月17日  11:35:47
 * @ClassName 这里输入你的类名(或用途)
 */
abstract class BaseActivity<V : Parcelable, A : BaseActivity<V, A>> : AppCompatActivity(), BaseAnko<V, A> {
    private val TAG = javaClass.simpleName
    protected var toolbar: BaseToolBar<A, V>? = null
    protected var toolbarAnko: View? = null
    private var layout: BindingComponent<A, V>? = null

//    private val mNavigationStatusObserver = object : ContentObserver(Handler()) {
//        override fun onChange(selfChange: Boolean) {
//            dealWithHuaWei()
//            //            int navigationBarIsMin = Settings.System.getInt(getContentResolver(), "navigationbar_is_min", 0);
//            //            if (navigationBarIsMin == 1) {
//            //                Log.d(TAG, "onChange: ------------------导航键隐藏了");
//            //            } else {
//            //                Log.d(TAG, "onChange: ------------------导航键显示了");
//            //            }
//        }
//    }

//    /**
//     * 判断是否是华为手机并且是否有虚拟导航键
//     */
//    if (DeviceUtil.isHUAWEI() && DeviceUtil.checkDeviceHasNavigationBar(this.getApplicationContext()))
//    {
//        contentResolver.registerContentObserver(Settings.System.getUriFor("navigationbar_is_min"), true, mNavigationStatusObserver)
//    }

    private val toolbarClickEvent = observable(View.OnClickListener {
        if (it != null) {
            var event: BaseToolBar.Companion.ToolbarEvent? = null
            when (it.id) {
                R.id.toolbar_left -> {
                    event = BaseToolBar.Companion.ToolbarEvent.LEFT
                }
                R.id.toolbar_right -> {
                    event = BaseToolBar.Companion.ToolbarEvent.RIGHT
                }

                else -> {
                    event = BaseToolBar.Companion.ToolbarEvent.NONE
                }
            }
            performToolbarClickEvent(it, event)
        }
    })

    protected open fun performToolbarClickEvent(view: View, event: BaseToolBar.Companion.ToolbarEvent) {

    }

    fun getVerName(): String? {
        try {
            return (application as BaseApp).getVerName()
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return null
    }

    fun getVerCode(): Int {
        try {
            return (application as BaseApp).getVerCode()
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return -1
    }


    protected open var binder: BindingComponent<*, *>? = layout

    protected var showToolBar: Boolean = false

    var viewModel: V? = null

    override fun ankoToolBar(viewModel: V): BaseToolBar<V, A>? {
        return null
    }

    override fun onLoadedModel(viewModel: V) {

    }

    private lateinit var root: View

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

//        if (savedInstanceState!=null){
//            viewModel=savedInstanceState.getParcelable(TAG)
//        }else{
        if (viewModel == null) {
            viewModel = newViewModel()
        }
        viewModel!!.apply {
            if (showToolBar) {
                val tc = newToolBarComponent(this)
                if (tc != null) {

                    toolbar = tc.apply {
                        toolbarAnko = createView(AnkoContextImpl(this@BaseActivity, this@BaseActivity as A, false))
                        //状态栏透明和间距处理
                        StatusBarUtil.immersive(this@BaseActivity) // 经测试在代码里直接声明透明状态栏更有效
//                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
//                            val localLayoutParams = window.attributes
//                            localLayoutParams.flags = android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or localLayoutParams.flags
//                        }
                        notifyChanges()
                    } as BaseToolBar<A, V>
                    toolbar!!.eventDelegate = toolbarClickEvent
                }
            }

            layout = newComponent(this).apply {
                if (toolbarAnko != null) {
                    root = createView(
                            AnkoContextImpl(this@BaseActivity, this@BaseActivity as A, true),
                            toolbarAnko,
                            this@BaseActivity,
                            this@BaseActivity
                    )
                    if (toolbarAnko is Toolbar && toolbar != null) {
                        toolbar!!.initToolbar(this@BaseActivity, toolbarAnko!! as Toolbar)
                        setToolbarAttribution(
                                toolbar!!,
                                toolbar!!.getActionBar(this@BaseActivity),
                                toolbarAnko!! as Toolbar)
                    }
                } else {
                    root = createView(AnkoContextImpl(this@BaseActivity, this@BaseActivity as A, true))
                    if (ifNeedTopPadding()) {
                        openTopPadding()
                    }
                }
                // 经测试在代码里直接声明透明状态栏更有效
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    val localLayoutParams = window.attributes
                    localLayoutParams.flags = android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or localLayoutParams.flags
                }
                notifyChanges()
            }

        }
        if (viewModel != null) {
            onLoadedModel(viewModel!!)
        }
    }

    protected open fun ifNeedTopPadding(): Boolean {
        return true
    }

    private fun openTopPadding() {
        root.topPadding = StatusBarUtil.getStatusBarHeight(root.context)
    }


    protected open fun setToolbarAttribution(toolbarBinder: BaseToolBar<A, V>, actionBar: ActionBar?, toolbarView: Toolbar) {

    }

    abstract fun newViewModel(): V

    abstract fun newComponent(v: V): BindingComponent<A, V>

    override fun onDestroy() {
        super.onDestroy()
        layout?.destroyView()
        layout = null
    }


    open fun newToolBarComponent(v: V): BindingComponent<A, V>? {
        return null
    }

    override fun attachBaseContext(newBase: Context) {
        if (newBase.applicationContext != null) {
            val app = newBase.applicationContext as BaseApp
            super.attachBaseContext(app.wrapEmoji(newBase))
        } else {
            super.attachBaseContext(newBase)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putParcelable(TAG, viewModel)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        viewModel = savedInstanceState?.getParcelable(TAG)
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onStop() {
        hideInputMethod()
        super.onStop()
    }

    private fun hideInputMethod() {
        try {
            val id = ResourceIdUtil.findFocus(this)
            if (!TextUtils.isEmpty(id)) {
                var imm: InputMethodManager? = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm!!.hideSoftInputFromWindow(find<View>(ResourceIdUtil.getId(this, id!!)).windowToken, 0)
                imm = null
            }
        } catch (ignored: Exception) {
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        binder?.onRequestPermissionsResult(this, requestCode, permissions, grantResults)
    }


}