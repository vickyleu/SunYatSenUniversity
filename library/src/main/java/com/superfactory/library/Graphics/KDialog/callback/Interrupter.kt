package com.superfactory.library.Graphics.KDialog.callback

import android.view.View
import com.superfactory.library.Graphics.KDialog.CircleDialog

/**
 * Created by vicky on 2018.02.01.
 *
 * @Author vicky
 * @Date 2018年02月01日  12:47:45
 * @ClassName 这里输入你的类名(或用途)
 */
interface Interrupter {
    fun dismissMission(text: String?, dialog: CircleDialog,inputView:View?)
}
