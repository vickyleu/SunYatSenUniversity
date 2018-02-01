package com.superfactory.library.Graphics.KDialog.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.*


import com.superfactory.library.Graphics.KDialog.params.CircleParams
import com.superfactory.library.Graphics.KDialog.params.ItemsParams
import com.superfactory.library.Graphics.KDialog.params.TitleParams
import com.superfactory.library.Graphics.KDialog.res.drawable.SelectorBtn
import com.superfactory.library.Graphics.KDialog.res.values.CircleColor

import java.util.Arrays

/**
 * Created by hupei on 2017/3/30.
 */

@SuppressLint("ViewConstructor")
internal class BodyItemsView<D>(context: Context, params: CircleParams) : ListView(context) {
    private var mAdapter: BaseAdapter? = null

    init {
        init(context, params)
    }

    private fun init(context: Context, params: CircleParams) {
        val itemsParams = params.itemsParams

        val layoutParams = LinearLayout.LayoutParams(AbsListView.LayoutParams
                .MATCH_PARENT, AbsListView.LayoutParams
                .MATCH_PARENT)
        setLayoutParams(layoutParams)

        selector = ColorDrawable(Color.TRANSPARENT)
        divider = ColorDrawable(CircleColor.divider)
        dividerHeight = 1

        onItemClickListener = OnItemClickListener { parent, view, position, id ->
            itemsParams?.dismiss()
            if (itemsParams?.listener != null)
                itemsParams.listener?.onItemClick(parent, view, position, id)
        }

        mAdapter = ItemsAdapter<D>(mContext = context, params = params)
        adapter = mAdapter
    }

    fun refreshItems() {
        post { mAdapter!!.notifyDataSetChanged() }
    }

    internal class ItemsAdapter<T>(private val mContext: Context, params: CircleParams) : BaseAdapter() {
        private var mItems: List<T>? = null
        private val mRadius: Int
        private val mBackgroundColor: Int
        private val mItemsParams: ItemsParams
        private val mTitleParams: TitleParams?

        internal inner class ViewHolder<T> {
            var item: TextView? = null
        }

        init {
            this.mTitleParams = params.titleParams
            this.mItemsParams = params.itemsParams!!
            this.mRadius = params.dialogParams?.radius?:0
            //如果没有背景色，则使用默认色
            this.mBackgroundColor = if (mItemsParams.backgroundColor != 0)
                mItemsParams
                        .backgroundColor
            else
                CircleColor
                        .bgDialog
            val entity = this.mItemsParams.items
            if (entity != null && entity is Iterable<*>) {
                this.mItems = entity as List<T>?
            } else if (entity != null && entity.javaClass.isArray) {
                this.mItems = Arrays.asList(*(entity as Array<T>?)!!)
            } else {
                throw IllegalArgumentException("entity must be an Array or an Iterable.")
            }
        }

        override fun getCount(): Int {
            return if (mItems != null) mItems!!.size else 0
        }

        override fun getItem(position: Int): T? {
            return if (mItems != null) mItems!![position] else null
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var convertView_ = convertView
            val viewHolder: ViewHolder<T>
            if (convertView_ == null) {
                viewHolder = ViewHolder()
                val textView = ScaleTextView(mContext)
                textView.textSize = mItemsParams.textSize.toFloat()
                textView.setTextColor(mItemsParams.textColor)
                textView.height = mItemsParams.itemHeight
                viewHolder.item = textView
                convertView_ = textView
                convertView_.tag = viewHolder
            } else {
                viewHolder = convertView_.tag as ViewHolder<T>
            }

            //top
            if (position == 0 && mTitleParams == null) {
                if (count == 1) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        viewHolder.item!!.background = SelectorBtn(mBackgroundColor, mRadius,
                                mRadius, mRadius, mRadius)
                    } else {
                        viewHolder.item!!.setBackgroundDrawable(SelectorBtn(mBackgroundColor,
                                mRadius, mRadius, mRadius, mRadius))
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        viewHolder.item!!.background = SelectorBtn(mBackgroundColor,
                                mRadius, mRadius, 0, 0)
                    } else {
                        viewHolder.item!!.setBackgroundDrawable(SelectorBtn(mBackgroundColor,
                                mRadius, mRadius, 0, 0))
                    }
                }
            } else if (position == count - 1) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    viewHolder.item!!.background = SelectorBtn(mBackgroundColor, 0, 0,
                            mRadius, mRadius)
                } else {
                    viewHolder.item!!.setBackgroundDrawable(SelectorBtn(mBackgroundColor, 0, 0,
                            mRadius, mRadius))
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    viewHolder.item!!.background = SelectorBtn(mBackgroundColor, 0, 0, 0, 0)
                } else {
                    viewHolder.item!!.setBackgroundDrawable(SelectorBtn(mBackgroundColor, 0, 0,
                            0, 0))
                }
            }//middle
            //bottom
            viewHolder.item!!.text = getItem(position)!!.toString()
            return convertView_
        }
    }
}
