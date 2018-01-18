package com.superfactory.library.Bridge.Anko.viewextensions

import android.view.View
import android.widget.FrameLayout
import com.superfactory.library.Bridge.Anko.Adapt.FragmentContainer
import com.superfactory.library.Bridge.Anko.ObservableFieldImpl

/**
 * Created by vicky on 2018.01.18.
 *
 * @Author vicky
 * @Date 2018年01月18日  15:53:43
 * @ClassName 这里输入你的类名(或用途)
 */

fun FrameLayout.setFragmentPosition(container: ObservableFieldImpl<FragmentContainer>?) {
    if(container==null)return
    val value = container.value
    val manager = value.manager
    val fragment = value.fragment
    if (manager==null||fragment==null)return
    if (this.id== View.NO_ID)return
    val transaction =manager.beginTransaction()
    if (fragment.isAdded){
        transaction.replace(this.id, fragment, null)
    }else{
        transaction.add(this.id, fragment, null)
    }
    transaction.commit()
}