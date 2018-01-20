package com.superfactory.library.Context

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.annotation.DrawableRes
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.DslView.BaseToolBar
import com.superfactory.library.Bridge.Anko.ObservableField
import com.superfactory.library.Bridge.Anko.ObservableFieldImpl
import com.superfactory.library.Bridge.Model.ToolbarBindingModel
import kotlin.reflect.KProperty

/**
 * Created by vicky on 2018.01.19.
 *
 * @Author vicky
 * @Date 2018年01月19日  16:53:52
 * @ClassName 这里输入你的类名(或用途)
 */
@Suppress("UNREACHABLE_CODE")
abstract class BaseToolBarActivity<V, A : BaseToolBarActivity<V, A>> : BaseActivity<V, A>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        showToolBar = true
        super.onCreate(savedInstanceState)
    }

    override fun newToolBarComponent(v: V): BindingComponent<A, V>? {
        return ankoToolBar(v)
    }

    override fun ankoToolBar(viewModel: V): BaseToolBar<V, A>? {
        return BaseToolBar(viewModel)
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
        val v = toModel(any, input, viewModel as ToolbarBindingModel);
        this.setToolbarPropertyBase(v, any)
    }

    private fun <Value, Input> toModel(any: Value, input: Input, model: ToolbarBindingModel): ObservableFieldImpl<Value>? {
        val field: ObservableFieldImpl<Value>
        when (input) {
            ToolbarBindingModel::title -> {
                field = model.title as ObservableFieldImpl<Value>
            }
            ToolbarBindingModel::navigationIcon -> {
                field = model.navigationIcon as ObservableFieldImpl<Value>
            }
            ToolbarBindingModel::backgroundColor -> {
                field = model.backgroundColor as ObservableFieldImpl<Value>
            }
            ToolbarBindingModel::titleColor -> {
                field = model.titleColor as ObservableFieldImpl<Value>
            }
            ToolbarBindingModel::titleSize -> {
                field = model.titleSize as ObservableFieldImpl<Value>
            }
            ToolbarBindingModel::navigationText -> {
                field = model.navigationText as ObservableFieldImpl<Value>
            }
            ToolbarBindingModel::navigationTextSize -> {
                field = model.navigationTextSize as ObservableFieldImpl<Value>
            }
            ToolbarBindingModel::navigationTextColor -> {
                field = model.navigationTextColor as ObservableFieldImpl<Value>
            }
            ToolbarBindingModel::leftPadding -> {
                field = model.leftPadding as ObservableFieldImpl<Value>
            }
            ToolbarBindingModel::rightPadding -> {
                field = model.rightPadding as ObservableFieldImpl<Value>
            }
            else -> {
                return null
            }
        }
        return field
    }

    fun <Data : ObservableField<Input>, Input> setToolbarPropertyBase(field: Data?, any: Input?) {
        if (viewModel != null && viewModel is ToolbarBindingModel && any != null) {
            if (field != null)
                field.value = any
        }
    }
}
