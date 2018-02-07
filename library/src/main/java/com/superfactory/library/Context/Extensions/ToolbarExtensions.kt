package com.superfactory.library.Context.Extensions

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.text.Layout
import android.text.TextUtils
import android.view.View
import com.superfactory.library.Bridge.Anko.Adapt.BaseToolBar
import com.superfactory.library.Bridge.Anko.ObservableField
import com.superfactory.library.Bridge.Model.ToolbarBindingModel
import com.superfactory.library.Graphics.TextDrawable
import kotlin.reflect.KProperty

/**
 * Created by vicky on 2018/1/22.
 */
class ToolbarExtensions {

    companion object {

        fun <V> setTitle(str: String?, viewModel: V) {
            if (str == null) return
            setToolbarProperty(ToolbarBindingModel::title, str, viewModel)
        }

        fun <V> setBackIcon(@DrawableRes res: Int?, viewModel: V) {
            if (res == null) return
            setToolbarProperty(ToolbarBindingModel::leftIcon, res, viewModel)
            setToolbarProperty(ToolbarBindingModel::leftText, "", viewModel)
        }

        fun <V> setBackTextSize(size: Int?, context: Context, viewModel: V) {
            if (size == null) return
            setToolbarProperty(ToolbarBindingModel::leftTextSize, size, viewModel)
            if (viewModel != null && !TextUtils.isEmpty((viewModel as ToolbarBindingModel).leftText.value)) {
                setBackIcon((viewModel as ToolbarBindingModel).leftText.value, context, viewModel)
            }
        }

        fun <V> setBackTextColor(@ColorInt color: Int?, context: Context, viewModel: V) {
            if (color == null) return
            setToolbarProperty(ToolbarBindingModel::leftTextColor, color, viewModel)
            if (viewModel != null && !TextUtils.isEmpty((viewModel as ToolbarBindingModel).leftText.value)) {
                setBackIcon((viewModel as ToolbarBindingModel).leftText.value, context, viewModel)
            }
        }

        /**
         * 设置文字会转化成图形,所以必须在转换之前设置好文字大小和颜色,否则将使用默认颜色和字体大小
         */

        fun <V> setBackIcon(res: String?, context: Context, viewModel: V) {
            if (TextUtils.isEmpty(res)) return
            setToolbarProperty(ToolbarBindingModel::leftText, res!!, viewModel)
            val td = TextDrawable(context)
            td.text = res
            val vm = (viewModel as ToolbarBindingModel)
            val color = vm.leftTextColor.value
                td.setTextColor(color)
            val size = vm.leftTextSize.value
            if (size > 0)
                td.textSize = size.toFloat()
            td.textAlign = Layout.Alignment.ALIGN_CENTER
            setToolbarProperty(ToolbarBindingModel::leftIcon, td, viewModel)
        }

        fun <V> setBackIcon(drawable: Drawable?, viewModel: V) {
            if (drawable == null) return
            setToolbarProperty(ToolbarBindingModel::leftIcon, drawable, viewModel)
            setToolbarProperty(ToolbarBindingModel::leftText, "", viewModel)
        }


        fun <V> setRightIcon(@DrawableRes res: Int?, viewModel: V) {
            if (res == null) return
            setToolbarProperty(ToolbarBindingModel::rightIcon, res, viewModel)
            setToolbarProperty(ToolbarBindingModel::rightText, "", viewModel)
        }

        fun <V> setRightTextSize(size: Int?, context: Context, viewModel: V) {
            if (size == null) return
            setToolbarProperty(ToolbarBindingModel::rightTextSize, size, viewModel)
            if (viewModel != null && !TextUtils.isEmpty((viewModel as ToolbarBindingModel).rightText.value)) {
                setRightIcon((viewModel as ToolbarBindingModel).rightText.value, context, viewModel)
            }
        }

        fun <V> setRightTextColor(@ColorInt color: Int?, context: Context, viewModel: V) {
            if (color == null) return
            setToolbarProperty(ToolbarBindingModel::rightTextColor, color, viewModel)
            if (viewModel != null && !TextUtils.isEmpty((viewModel as ToolbarBindingModel).rightText.value)) {
                setRightIcon((viewModel as ToolbarBindingModel).rightText.value, context, viewModel)
            }
        }

        /**
         * 设置文字会转化成图形,所以必须在转换之前设置好文字大小和颜色,否则将使用默认颜色和字体大小
         */
        fun <V> setRightIcon(res: String?, context: Context, viewModel: V) {
            if (TextUtils.isEmpty(res)) return
            setToolbarProperty(ToolbarBindingModel::rightText, res!!, viewModel)
            val td = TextDrawable(context)
            td.text = res
            val vm = (viewModel as ToolbarBindingModel)
            val color = vm.rightTextColor.value
                td.setTextColor(color)
            val size = vm.rightTextSize.value
            if (size > 0)
                td.textSize = size.toFloat()
            td.textAlign = Layout.Alignment.ALIGN_CENTER
            setToolbarProperty(ToolbarBindingModel::rightIcon, td, viewModel)
        }


        fun <V> BaseToolBar<*, *>.setBackIcon(res: String?, context: Context, viewModel: V) {
            if (TextUtils.isEmpty(res)) return
            setToolbarProperty(ToolbarBindingModel::leftText, res!!, viewModel)
            val td = TextDrawable(context)
            td.text = res
            val vm = (viewModel as ToolbarBindingModel)
            val color = vm.leftTextColor.value
                td.setTextColor(color)
            val size = vm.leftTextSize.value
            if (size > 0)
                td.textSize = size.toFloat()
            td.textAlign = Layout.Alignment.ALIGN_CENTER
            setToolbarProperty(ToolbarBindingModel::leftIcon, td, viewModel)
        }


        fun <V> BaseToolBar<*, *>.setRightTextColor(@ColorInt color: Int?, context: Context, viewModel: V) {
            if (color == null) return
            setToolbarProperty(ToolbarBindingModel::rightTextColor, color, viewModel)
            if (viewModel != null && !TextUtils.isEmpty((viewModel as ToolbarBindingModel).rightText.value)) {
                setRightIcon((viewModel as ToolbarBindingModel).rightText.value, context, viewModel)
            }
        }


        /**
         * 设置文字会转化成图形,所以必须在转换之前设置好文字大小和颜色,否则将使用默认颜色和字体大小
         */
        fun <V> BaseToolBar<*, *>.setRightIcon(res: String?, context: Context, viewModel: V) {
            if (TextUtils.isEmpty(res)) return
            setToolbarProperty(ToolbarBindingModel::rightText, res!!, viewModel)
            val td = TextDrawable(context)
            td.text = res
            val vm = (viewModel as ToolbarBindingModel)
            val color = vm.rightTextColor.value
                td.setTextColor(color)
            val size = vm.rightTextSize.value
            if (size > 0)
                td.textSize = size.toFloat()
            td.textAlign = Layout.Alignment.ALIGN_CENTER
            setToolbarProperty(ToolbarBindingModel::rightIcon, td, viewModel)
        }

        fun <V> setRightIcon(drawable: Drawable?, viewModel: V) {
            if (drawable == null) return
            setToolbarProperty(ToolbarBindingModel::rightIcon, drawable, viewModel)
            setToolbarProperty(ToolbarBindingModel::rightText, "", viewModel)
        }


        fun <V> setBackView(view: View?, viewModel: V) {
            if (view == null) return
            setToolbarProperty(ToolbarBindingModel::leftView, view, viewModel)
        }

        fun <V> setRightView(view: View?, viewModel: V) {
            if (view == null) return
            setToolbarProperty(ToolbarBindingModel::rightView, view, viewModel)
        }


        fun <V> setBackgroundColor(@ColorInt color: Int?, viewModel: V) {
            if (color == null) return
            setToolbarProperty(ToolbarBindingModel::backgroundColor, color, viewModel)
        }


        fun <Input : KProperty<Value>, Value : Any, V> setToolbarProperty(input: Input, any: Value, viewModel: V) {
            val v = ToolbarBindingModel.toModel(any, input, viewModel as ToolbarBindingModel);
            setToolbarPropertyBase(v, any, viewModel)
        }


        fun <Data : ObservableField<Input>, Input, V> setToolbarPropertyBase(field: Data?, any: Input?, viewModel: V) {
            if (viewModel != null && viewModel is ToolbarBindingModel && any != null) {
                if (field != null)
                    field.value = any
            }
        }
    }
}

