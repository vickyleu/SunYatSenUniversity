package com.superfactory.library.Bridge.Adapt

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView

/**
 * Created by vicky on 2018.01.17.
 *
 * @Author vicky
 * @Date 2018年01月17日  11:23:30
 * @ClassName 这里输入你的类名(或用途)
 */

abstract class BaseAdapter<T, K : BaseViewHolder>(
        private val mList: List<T>?,
        private val mLayoutRes: Int,
        mContext: Context)
//        this.mContext = mContext;
    : RecyclerView.Adapter<K>() {
    private var mContext: Context? = null
    private var mLayoutInflater: LayoutInflater? = null
    /**
     * Called when RecyclerView needs a new [ViewHolder] of the given type to represent
     * an item.
     *
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     *
     *
     * The new ViewHolder will be used to display items of the adapter using
     * [.onBindViewHolder]. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary [View.findViewById] calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     * an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see .getItemViewType
     * @see .onBindViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): K {
        this.mContext = parent.context
        this.mLayoutInflater = LayoutInflater.from(mContext)
        val itemView = getItemView(mLayoutRes, parent)
        if (itemView!=null)
        return BaseViewHolder(itemView) as K
        else return BaseViewHolder(View(mContext)) as K
    }


    /**
     * @param layoutResId ID for an XML layout resource to load
     * @param parent      Optional view to be the parent of the generated hierarchy or else simply an object that
     * provides a set of LayoutParams values for root of the returned
     * hierarchy
     * @return view will be return
     */
    protected fun getItemView(layoutResId: Int, parent: ViewGroup): View? {
        return if (mLayoutInflater == null) null else mLayoutInflater!!.inflate(layoutResId, parent, false)
    }

    /**
     * if addHeaderView will be return 1, if not will be return 0
     */


    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the [ViewHolder.itemView] to reflect the item at the given
     * position.
     *
     *
     * Note that unlike [ListView], RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the `position` parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use [ViewHolder.getAdapterPosition] which will
     * have the updated adapter position.
     *
     *
     * Override [.onBindViewHolder] instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     * item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: K, position: Int) {
        val viewType = holder.itemViewType
        displayItem(position, holder, getItem(position), viewType)
    }

    /**
     * @param position
     * @param holder
     * @param item
     * @param viewType
     */
    protected abstract fun displayItem(position: Int, holder: K, item: T?, viewType: Int)

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int {
        return mList?.size ?: 0
    }

    fun getItem(position: Int): T? {
        return if (mList != null && mList.size > position) {
            mList[position]
        } else null
    }
}
