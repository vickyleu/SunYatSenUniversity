package com.superfactory.library.Context

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.Adapt.BaseToolBar
import com.superfactory.library.Bridge.Anko.ObservableField
import com.superfactory.library.Bridge.Model.ToolbarBindingModel
import kotlin.reflect.KProperty

/**
 * Created by vicky on 2018.01.20.
 *
 * @Author vicky
 * @Date 2018年01月20日  15:13:15
 * @ClassName 这里输入你的类名(或用途)
 */
abstract class BaseToolbarFragment<V : ToolbarBindingModel, A : BaseToolbarFragment<V, A>> : BaseFragment<V, A>() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        showToolBar = true
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun setToolbarAttribution(toolbarBinder: BaseToolBar<A, V>, actionBar: ActionBar?, toolbarView: Toolbar) {
        if (actionBar != null) {
            if (toolbarBinder.viewModelSafe is ToolbarBindingModel) {
                val displayNavigator = (toolbarBinder.viewModelSafe as ToolbarBindingModel).displayNavigator
                // 显示应用的Logo
                actionBar.setDisplayShowHomeEnabled(displayNavigator);
                actionBar.setDisplayUseLogoEnabled(displayNavigator);
//            actionBar.setLogo(R.mipmap.ic_launcher);
                // 显示标题和子标题
                actionBar.setDisplayShowTitleEnabled(displayNavigator);
            }
        }
    }

    override fun newToolBarComponent(v: V): BindingComponent<A, V>? {
        return ankoToolBar(v)
    }

    override fun ankoToolBar(viewModel: V): BaseToolBar<V, A>? {
        return BaseToolBar(viewModel)
    }

    fun setTitle(str: String?) {
        if (str == null) return
        this.setToolbarProperty(ToolbarBindingModel::title, str)
    }

    fun setBackIcon(@DrawableRes res: Int) {
        if (res == 0) return
        this.setToolbarProperty(ToolbarBindingModel::navigationIcon, res)
    }

    fun setBackIcon(drawable: Drawable?) {
        if (drawable == null) return
        this.setToolbarProperty(ToolbarBindingModel::navigationIcon, drawable)
    }


    fun <Input : KProperty<Value>, Value : Any> setToolbarProperty(input: Input, any: Value) {
        val v = ToolbarBindingModel.toModel(any, input, viewModel as ToolbarBindingModel);
        this.setToolbarPropertyBase(v, any)
    }


    fun <Data : ObservableField<Input>, Input> setToolbarPropertyBase(field: Data?, any: Input?) {
        if (viewModel != null && viewModel is ToolbarBindingModel && any != null) {
            if (field != null)
                field.value = any
        }
    }

}