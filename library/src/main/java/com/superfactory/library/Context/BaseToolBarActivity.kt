package com.superfactory.library.Context

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.View
import com.superfactory.library.Bridge.Anko.Adapt.BaseToolBar
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.ObservableField
import com.superfactory.library.Bridge.Model.ToolbarBindingModel
import com.superfactory.library.Bridge.Model.ToolbarBindingModel.Companion.toModel
import com.superfactory.library.R
import kotlin.reflect.KProperty

/**
 * Created by vicky on 2018.01.19.
 *
 * @Author vicky
 * @Date 2018年01月19日  16:53:52
 * @ClassName 这里输入你的类名(或用途)
 */
abstract class BaseToolBarActivity<V : ToolbarBindingModel, A : BaseToolBarActivity<V, A>> : BaseActivity<V, A>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        showToolBar = true
        super.onCreate(savedInstanceState)
    }

    override fun setToolbarAttribution(toolbarBinder: BaseToolBar<A, V>, actionBar: ActionBar?, toolbarView: Toolbar) {
        if (actionBar != null) {
            if (toolbarBinder.viewModelSafe is ToolbarBindingModel) {
                toolbarBinder.setAttribution(actionBar, toolbarView)
            }
        }
    }

    override fun performToolbarClickEvent(view: View, event: BaseToolBar.Companion.ToolbarEvent) {
        when (view.id) {
            R.id.toolbar_left -> {
                finish()
            }
            R.id.toolbar_right -> {

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
        this.setToolbarProperty(ToolbarBindingModel::leftIcon, res)
    }

    fun setBackIcon(drawable: Drawable?) {
        if (drawable == null) return
        this.setToolbarProperty(ToolbarBindingModel::leftIcon, drawable)
    }

    fun <Input : KProperty<Value>, Value : Any> setToolbarProperty(input: Input, any: Value) {
        val v = toModel(any, input, viewModel as ToolbarBindingModel);
        this.setToolbarPropertyBase(v, any)
    }


    fun <Data : ObservableField<Input>, Input> setToolbarPropertyBase(field: Data?, any: Input?) {
        if (viewModel != null && viewModel is ToolbarBindingModel && any != null) {
            if (field != null)
                field.value = any
        }
    }
}
