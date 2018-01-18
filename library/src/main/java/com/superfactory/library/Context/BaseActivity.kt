package com.superfactory.library.Context

import android.content.Context
import android.support.v7.app.AppCompatActivity
import cn.nekocode.emojix.Emojix
import com.superfactory.library.Bridge.Anko.BindingComponent
import org.jetbrains.anko.AnkoContextImpl


/**
 * Created by vicky on 2018.01.17.
 *
 * @Author vicky
 * @Date 2018年01月17日  11:35:47
 * @ClassName 这里输入你的类名(或用途)
 */
abstract class BaseActivity<V, A : BaseActivity<V, A>> : AppCompatActivity() {

    private var layout: BindingComponent<A, V>? = null

    var viewModel: V? = null

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = newViewModel().apply {
            layout = newComponent(this).apply {
                createView(AnkoContextImpl(this@BaseActivity, this@BaseActivity as A, true))
                notifyChanges()
            }
        }
    }

    abstract fun newViewModel(): V

    abstract fun newComponent(v: V): BindingComponent<A, V>

    override fun onDestroy() {
        super.onDestroy()
        layout?.destroyView()
        layout = null
    }

    override fun attachBaseContext(newBase: Context) {
        if (newBase.applicationContext!=null){
            val app = newBase.applicationContext as BaseApp
            if (app.injectEmojix()) {
                super.attachBaseContext(Emojix.wrap(newBase))
            } else {
                super.attachBaseContext(newBase)
            }
        }else{
            super.attachBaseContext(newBase)
        }
    }
}