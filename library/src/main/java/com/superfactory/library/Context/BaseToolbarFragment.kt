package com.superfactory.library.Context

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.text.Layout
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.superfactory.library.Bridge.Anko.Adapt.BaseToolBar
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.ObservableField
import com.superfactory.library.Bridge.Model.ToolbarBindingModel
import com.superfactory.library.Graphics.TextDrawable
import com.superfactory.library.R
import kotlin.reflect.KProperty

/**
 * Created by vicky on 2018.01.20.
 *
 * @Author vicky
 * @Date 2018年01月20日  15:13:15
 * @ClassName 这里输入你的类名(或用途)
 */
abstract class BaseToolbarFragment<V : ToolbarBindingModel, A : BaseToolbarFragment<V, A>> : BaseFragment<V, A>() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        showToolBar = true
        if (activity!=null&&activity is BaseToolBarActivity<*,*>){
            throw ExceptionInInitializerError("nested toolbar controller error")
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun setToolbarAttribution(toolbarBinder: BaseToolBar<A, V>, actionBar: ActionBar?, toolbarView: Toolbar) {
        if (actionBar != null) {
            if (toolbarBinder.viewModelSafe is ToolbarBindingModel) {
                toolbarBinder.setAttribution(actionBar, toolbarView)
            }
        }
    }


    override fun performToolbarClickEvent(view: View, event: BaseToolBar.Companion.ToolbarEvent) {
        when(view.id){
            R.id.toolbar_left->{
                activity?.finish()
            }
            R.id.toolbar_right->{

            }
        }
    }

    override fun newToolBarComponent(v: V): BindingComponent<A, V>? {
        return ankoToolBar(v)
    }

    override fun ankoToolBar(viewModel: V): BaseToolBar<V, A>? {
        return BaseToolBar(viewModel)
    }

}