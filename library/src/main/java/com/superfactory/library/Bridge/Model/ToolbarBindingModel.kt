package com.superfactory.library.Bridge.Model

import com.superfactory.library.Bridge.Anko.BaseObservable
import com.superfactory.library.Bridge.Anko.ObservableFieldImpl
import com.superfactory.library.Bridge.Anko.observable
import kotlin.reflect.full.memberProperties

/**
 * Created by vicky on 2018/1/19.
 */
abstract class ToolbarBindingModel : BaseObservable() {
    var displayNavigator = false
    val title = observable("")
    var navigationIcon = observable(Any())
    var backgroundColor = observable(1)
    val titleColor = observable(0)
    val titleSize = observable(0)
    val navigationText = observable("")
    val navigationTextSize = observable(0)
    val navigationTextColor = observable(0)
    val leftPadding = observable(0)
    val rightPadding = observable(0)

    init {
        apply {
            setToolbar(this@ToolbarBindingModel)
        }
    }

    abstract fun setToolbar(toolbarBindingModel: ToolbarBindingModel)

    companion object {
        fun <Value, Input> toModel(any: Value, input: Input, model: ToolbarBindingModel): ObservableFieldImpl<Value>? {
            var field: ObservableFieldImpl<Value>? = null

//            val memberProperties = ToolbarBindingModel::class.memberProperties
//            memberProperties.forEach {
//                if ( it.isFinal&&it.){
//                   val p= it as ObservableFieldImpl<Value>
//                    if (p.){
//                        field=it as ObservableFieldImpl<Value>
//                    }
//                }
//            }

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
    }

}