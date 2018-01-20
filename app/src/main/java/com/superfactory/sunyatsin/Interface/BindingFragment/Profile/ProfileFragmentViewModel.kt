package com.superfactory.sunyatsin.Interface.BindingFragment.Profile

import android.graphics.Color
import com.superfactory.library.Bridge.Anko.BaseObservable
import com.superfactory.library.Bridge.Model.ToolbarBindingModel

/**
 * Created by vicky on 2018.01.19.
 *
 * @Author vicky
 * @Date 2018年01月19日  13:41:34
 * @ClassName 这里输入你的类名(或用途)
 */
class ProfileFragmentViewModel : ToolbarBindingModel(){
    override fun setToolbar(toolbarBindingModel: ToolbarBindingModel) {
        toolbarBindingModel.backgroundColor.value= Color.DKGRAY
        toolbarBindingModel.title.value="妈卖批哦2"
    }

}