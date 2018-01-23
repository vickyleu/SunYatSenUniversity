package com.superfactory.library.Context

import android.content.Context
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.superfactory.library.Bridge.Anko.Adapt.BaseAnko
import com.superfactory.library.Bridge.Anko.Adapt.BaseToolBar
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.observable
import com.superfactory.library.Debuger
import com.superfactory.library.R
import org.jetbrains.anko.AnkoContextImpl


/**
 * Created by vicky on 2018.01.18.
 *
 * @Author vicky
 * @Date 2018年01月18日  11:11:39
 * @ClassName 这里输入你的类名(或用途)
 */
abstract class BaseFragment<V, A : BaseFragment<V, A>> : Fragment(), BaseAnko<V, A> {
    protected var toolbar: BaseToolBar<A, V>? = null
    protected var toolbarAnko: View? = null

    private var layout: BindingComponent<A, V>? = null
    var viewModel: V? = null

    protected var showToolBar: Boolean = false


    private val toolbarClickEvent= observable(View.OnClickListener {
        if (it!=null){
            performToolbarClickEvent(it)
        }
    })

    protected open fun performToolbarClickEvent(view: View){
        when(view.id){
            R.id.toolbar_left->{

            }
            R.id.toolbar_right->{

            }
        }

    }


    override fun onLoadedModel(viewModel: V) {

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View? = null
        viewModel = newViewModel().apply {
            if (showToolBar) {
                val tc = newToolBarComponent(this)
                if (tc != null) {
                    toolbar = tc.apply {
                        toolbarAnko = createView(AnkoContextImpl(this@BaseFragment.context, this@BaseFragment as A, false))
                        notifyChanges()
                    } as BaseToolBar<A, V>
                    toolbar!!.eventDelegate=toolbarClickEvent
                }
            }
            layout = newComponent(this).apply {
                if (toolbarAnko != null) {
                    Debuger.printMsg(this, "妈卖批")
                    view = createView(
                            AnkoContextImpl(this@BaseFragment.context, this@BaseFragment as A, false),
                            toolbarAnko,
                            this@BaseFragment.context,
                            this@BaseFragment
                    )
                    if (toolbarAnko is Toolbar && toolbar != null) {
                        toolbar!!.initToolbar(this@BaseFragment, toolbarAnko!! as Toolbar)
                        setToolbarAttribution(
                                toolbar!!,
                                toolbar!!.getActionBar(this@BaseFragment),
                                toolbarAnko!! as Toolbar)
                    }
                } else {
                    view = createView(AnkoContextImpl(this@BaseFragment.context, this@BaseFragment as A, false))
                }
                notifyChanges()
            }
        }

        if (viewModel!=null){
            onLoadedModel(viewModel!!)
        }
        if (view == null) {
            throw RuntimeException(javaClass.simpleName + "创建view为空")
        }
        return view;
    }

    protected open fun setToolbarAttribution(toolbarBinder: BaseToolBar<A, V>, actionBar: ActionBar?, toolbarView: Toolbar) {

    }

    open fun newToolBarComponent(v: V): BindingComponent<A, V>? {
        return null
    }

    override fun ankoToolBar(viewModel: V): BaseToolBar<V, A>? {
        return null
    }

    abstract fun newViewModel(): V

    abstract fun newComponent(v: V): BindingComponent<A, V>

    override fun onDestroy() {
        super.onDestroy()
        layout?.destroyView()
        layout = null
    }

    var behavior: CoordinatorLayout.Behavior<*>? = null


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (behavior != null)
            return
        val layout = this.view
        if (layout == null||!(layout is ViewGroup)) return
        if (layout.layoutParams != null && layout.layoutParams is CoordinatorLayout.LayoutParams) {
            val params = layout.layoutParams as CoordinatorLayout.LayoutParams
            behavior = params.behavior
            params.behavior = null
        }
    }
    /* override fun onAttach(activity: Activity?) {
         super.onAttach(activity)
         if (behavior != null)
             return
         val layout = this.view
         if (layout==null)return
         val params = layout.layoutParams as CoordinatorLayout.LayoutParams
         behavior = params.behavior
         params.behavior = null
     }*/

    override fun onDetach() {
        super.onDetach()
//        if (showToolBar){
//            (activity as AppCompatActivity).setSupportActionBar(null)
//        }
        if (behavior == null)
            return
        val layout = this.view
        if (layout == null||!(layout is ViewGroup)) return
        if (layout.layoutParams != null && layout.layoutParams is CoordinatorLayout.LayoutParams) {
            val params = layout.layoutParams as CoordinatorLayout.LayoutParams
            params.behavior = behavior
            layout.layoutParams = params
            behavior = null
//            layout.removeView()
        }
    }

}