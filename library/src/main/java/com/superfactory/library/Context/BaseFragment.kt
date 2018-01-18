package com.superfactory.library.Context

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.superfactory.library.Bridge.Anko.BindingComponent
import org.jetbrains.anko.AnkoContextImpl

/**
 * Created by vicky on 2018.01.18.
 *
 * @Author vicky
 * @Date 2018年01月18日  11:11:39
 * @ClassName 这里输入你的类名(或用途)
 */
abstract class BaseFragment<V, A : BaseFragment<V, A>> : Fragment() {
    private var layout: BindingComponent<A, V>? = null
    var viewModel: V? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View? = null
        viewModel = newViewModel().apply {
            layout = newComponent(this).apply {
                view = createView(AnkoContextImpl(this@BaseFragment.context, this@BaseFragment as A, false))
                notifyChanges()
            }
        }
        if (view == null) {
            throw RuntimeException(javaClass.simpleName + "创建view为空")
        }
        return view;
    }

    abstract fun newViewModel(): V

    abstract fun newComponent(v: V): BindingComponent<A, V>

    override fun onDestroy() {
        super.onDestroy()
        layout?.destroyView()
        layout = null
    }

}