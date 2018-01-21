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
                toolbarBinder.setAttribution(actionBar, toolbarView)
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

    fun setBackIcon(@DrawableRes res: Int?) {
        if (res == null) return
        this.setToolbarProperty(ToolbarBindingModel::leftIcon, res)
        this.setToolbarProperty(ToolbarBindingModel::leftText, "")
    }

    fun setBackTextSize(size: Int?) {
        if (size == null) return
        this.setToolbarProperty(ToolbarBindingModel::leftTextSize, size)
        if (viewModel!=null&&!TextUtils.isEmpty(viewModel!!.leftText.value)){
            setBackIcon(viewModel!!.leftText.value)
        }
    }

    fun setBackTextColor(@ColorInt color: Int?) {
        if (color == null) return
        this.setToolbarProperty(ToolbarBindingModel::leftTextColor, color)
        if (viewModel!=null&&!TextUtils.isEmpty(viewModel!!.leftText.value)){
            setBackIcon(viewModel!!.leftText.value)
        }
    }

    /**
     * 设置文字会转化成图形,所以必须在转换之前设置好文字大小和颜色,否则将使用默认颜色和字体大小
     */
    fun setBackIcon(res: String?) {
        if (TextUtils.isEmpty(res)) return
        this.setToolbarProperty(ToolbarBindingModel::leftText, res!!)
        val td = TextDrawable(context)
        td.text = res
        val vm = (viewModel as ToolbarBindingModel)
        val color = vm.leftTextColor.value
        if (color > 0)
            td.setTextColor(color)
        val size = vm.leftTextSize.value.toFloat()
        if (size > 0)
            td.textSize = size
        td.textAlign = Layout.Alignment.ALIGN_CENTER
        this.setToolbarProperty(ToolbarBindingModel::leftIcon, td)
    }

    fun setBackIcon(drawable: Drawable?) {
        if (drawable == null) return
        this.setToolbarProperty(ToolbarBindingModel::leftIcon, drawable)
        this.setToolbarProperty(ToolbarBindingModel::leftText, "")
    }




    fun setRightIcon(@DrawableRes res: Int?) {
        if (res == null) return
        this.setToolbarProperty(ToolbarBindingModel::rightIcon, res)
        this.setToolbarProperty(ToolbarBindingModel::rightText, "")
    }

    fun setRightTextSize(size: Int?) {
        if (size == null) return
        this.setToolbarProperty(ToolbarBindingModel::rightTextSize, size)
        if (viewModel!=null&&!TextUtils.isEmpty(viewModel!!.rightText.value)){
            setBackIcon(viewModel!!.rightText.value)
        }
    }

    fun setRightTextColor(@ColorInt color: Int?) {
        if (color == null) return
        this.setToolbarProperty(ToolbarBindingModel::rightTextColor, color)
        if (viewModel!=null&&!TextUtils.isEmpty(viewModel!!.rightText.value)){
            setBackIcon(viewModel!!.rightText.value)
        }
    }

    /**
     * 设置文字会转化成图形,所以必须在转换之前设置好文字大小和颜色,否则将使用默认颜色和字体大小
     */
    fun setRightIcon(res: String?) {
        if (TextUtils.isEmpty(res)) return
        this.setToolbarProperty(ToolbarBindingModel::rightText, res!!)
        val td = TextDrawable(context)
        td.text = res
        val vm = (viewModel as ToolbarBindingModel)
        val color = vm.rightTextColor.value
        if (color > 0)
            td.setTextColor(color)
        val size = vm.rightTextSize.value.toFloat()
        if (size > 0)
            td.textSize = size
        td.textAlign = Layout.Alignment.ALIGN_CENTER
        this.setToolbarProperty(ToolbarBindingModel::rightIcon, td)
    }

    fun setRightIcon(drawable: Drawable?) {
        if (drawable == null) return
        this.setToolbarProperty(ToolbarBindingModel::rightIcon, drawable)
        this.setToolbarProperty(ToolbarBindingModel::rightText, "")
    }



    fun setBackgroundColor(@ColorInt color: Int?) {
        if (color == null) return
        this.setToolbarProperty(ToolbarBindingModel::backgroundColor, color)
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