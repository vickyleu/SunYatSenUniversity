package com.superfactory.library.Context

import android.content.Context
import android.content.pm.ActivityInfo
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import cn.nekocode.emojix.Emojix
import com.superfactory.library.Bridge.Anko.Adapt.BaseAnko
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.Adapt.BaseToolBar
import org.jetbrains.anko.AnkoContextImpl


/**
 * Created by vicky on 2018.01.17.
 *
 * @Author vicky
 * @Date 2018年01月17日  11:35:47
 * @ClassName 这里输入你的类名(或用途)
 */
abstract class BaseActivity<V, A : BaseActivity<V, A>> : AppCompatActivity(), BaseAnko<V, A> {
    protected var toolbar: BaseToolBar<A, V>? = null
    protected var toolbarAnko: View? = null
    private var layout: BindingComponent<A, V>? = null

    protected var showToolBar: Boolean = false

    var viewModel: V? = null

    override fun ankoToolBar(viewModel: V): BaseToolBar<V, A>? {
        return null
    }

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation=ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        viewModel = newViewModel().apply {
            if (showToolBar) {
                val tc = newToolBarComponent(this)
                if (tc != null) {
                    toolbar = tc.apply {
                        toolbarAnko = createView(AnkoContextImpl(this@BaseActivity, this@BaseActivity as A, false))
                        // 经测试在代码里直接声明透明状态栏更有效
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                            val localLayoutParams = window.attributes
                            localLayoutParams.flags = android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or localLayoutParams.flags
                        }
                        notifyChanges()
                    } as BaseToolBar<A, V>
                }
            }

            layout = newComponent(this).apply {
                if (toolbarAnko != null) {
                    createView(
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
                    createView(AnkoContextImpl(this@BaseActivity, this@BaseActivity as A, true))
                }
                // 经测试在代码里直接声明透明状态栏更有效
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    val localLayoutParams = window.attributes
                    localLayoutParams.flags = android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or localLayoutParams.flags
                }
                notifyChanges()
            }

        }
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
            if (app.injectEmojix()) {
                super.attachBaseContext(Emojix.wrap(newBase))
            } else {
                super.attachBaseContext(newBase)
            }
        } else {
            super.attachBaseContext(newBase)
        }
    }
}