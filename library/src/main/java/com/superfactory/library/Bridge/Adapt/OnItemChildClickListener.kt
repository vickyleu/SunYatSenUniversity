package com.superfactory.library.Bridge

import android.view.View

import com.superfactory.library.Bridge.Adapt.BaseAdapter
import com.superfactory.library.Bridge.Adapt.BaseViewHolder
import com.superfactory.library.Bridge.Adapt.SimpleClickListener


/**
 * Created by AllenCoder on 2016/8/03.
 * A convenience class to extend when you only want to OnItemChildClickListener for a subset
 * of all the SimpleClickListener. This implements all methods in the
 * [SimpleClickListener]
 */

abstract class OnItemChildClickListener : SimpleClickListener() {

    override fun onItemClick(adapter: BaseAdapter<*, *>, view: View, position: Int) {

    }

    override fun onItemLongClick(adapter: BaseAdapter<*, *>, view: View, position: Int) {

    }

    override fun onItemChildClick(adapter: BaseAdapter<*, *>, view: View, holder: BaseViewHolder, position: Int) {
        try {
            onSimpleItemChildClick(adapter, view, holder, position)
        } catch (ignored: Exception) {
        }

    }

    override fun onItemChildLongClick(adapter: BaseAdapter<*, *>, view: View, position: Int) {


    }

    abstract fun onSimpleItemChildClick(quickAdapter: BaseAdapter<*, *>, view: View, holder: BaseViewHolder, position: Int)

}
