package com.superfactory.library.Bridge.Anko.ViewExtensions

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.superfactory.library.Bridge.Anko.Adapt.FragmentContainer
import com.superfactory.library.Debuger


/**
 * Created by vicky on 2018.01.18.
 *
 * @Author vicky
 * @Date 2018年01月18日  15:53:43
 * @ClassName 这里输入你的类名(或用途)
 */

fun FrameLayout.setFragmentPosition(container: FragmentContainer?) {
    if (container == null) return
    val manager = container.manager
    val fragment = container.fragment
    if (manager == null || fragment == null) return
    if (this.id == View.NO_ID) return
    val transaction = manager.beginTransaction()
    if (fragment.isAdded) {
        transaction.replace(this.id, fragment)
//        transaction.addToBackStack()
    } else {
            if (fragment.motherFuckerTags == null) {
                fragment.arguments= Bundle()
                transaction.add(this.id, fragment,fragment.javaClass.simpleName + fragment.hashCode())
//                transaction.addToBackStack()
            }else{
                Debuger.printMsg(this, "瑟瑟发抖11>>>"+fragment.javaClass.simpleName)
            }
    }
    transaction.show(fragment)
    transaction.commit()
}




