package com.superfactory.sunyatsin.Interface.BindingFragment.Note

import android.graphics.Color
import com.superfactory.library.Bridge.Anko.BaseObservable
import com.superfactory.library.Bridge.Model.ToolbarBindingModel

/**
 * Created by vicky on 2018.01.18.
 *
 * @Author vicky
 * @Date 2018年01月18日  11:55:35
 * @ClassName 这里输入你的类名(或用途)
 */
class NoteFragmentViewModel : ToolbarBindingModel(){
    override fun setToolbar(toolbarBindingModel: ToolbarBindingModel) {
        toolbarBindingModel.backgroundColor.value= Color.BLUE
        toolbarBindingModel.title.value="妈卖批哦1"
    }

}