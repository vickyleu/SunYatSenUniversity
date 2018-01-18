package com.superfactory.library.Bridge.Adapt

import android.support.annotation.StringRes
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.SparseArray
import android.view.View
import android.widget.TextView

import com.superfactory.library.Bridge.OnItemChildClickListener
import com.superfactory.library.Debuger

import java.util.HashSet
import java.util.LinkedHashSet

/**
 * Created by vicky on 2018.01.17.
 *
 * @Author vicky
 * @Date 2018年01月17日  11:42:05
 * @ClassName 这里输入你的类名(或用途)
 */

class BaseViewHolder(var convertView: View) : RecyclerView.ViewHolder(convertView) {
    /**
     * Views indexed with their IDs
     */
    internal val views: SparseArray<View>
    internal val childClickViewIds: LinkedHashSet<Int>
    internal val itemChildLongClickViewIds: LinkedHashSet<Int>
    /**
     * Package private field to retain the associated user object and detect a change
     */
    /**
     * Retrieves the last converted object on this view.
     */
    var associatedObject: Any? = null
        internal set
    private val itemChildClickListener: OnItemChildClickListener? = null

    init {
        this.views = SparseArray()
        this.childClickViewIds = LinkedHashSet()
        this.itemChildLongClickViewIds = LinkedHashSet()
    }


    fun getItemChildLongClickViewIds(): HashSet<Int> {
        return itemChildLongClickViewIds
    }

    fun getChildClickViewIds(): HashSet<Int> {
        return childClickViewIds
    }

    protected fun insert(origin: String): String {
        return if (TextUtils.isEmpty(origin)) "无" else if (TextUtils.isEmpty(origin.trim { it <= ' ' })) "无" else origin
    }

    protected fun insert(origin: CharSequence?): CharSequence {
        return if (TextUtils.isEmpty(origin)) "无" else if (TextUtils.isEmpty(origin!!.toString().trim { it <= ' ' })) "无" else origin
    }

    protected fun insert(origin: Int): String {
        return insert("" + origin)
    }

    protected fun insert(origin: Float): String {
        return insert("" + origin)
    }

    protected fun insert(origin: Double): String {
        return insert("" + origin)
    }

    /**
     * Will set the text of a TextView.
     *
     * @param viewId The view id.
     * @param value  The text to put in the text view.
     * @return The BaseViewHolder for chaining.
     */
    fun setText(viewId: Int, value: CharSequence): BaseViewHolder {
        try {
            val view = getView<TextView>(viewId)
            view!!.text = insert(value)
        } catch (e: Exception) {
            printStack(e)
        }

        return this
    }

    fun setText(viewId: Int, value: CharSequence?, placeHolder: Boolean): BaseViewHolder {
        try {
            val view = getView<TextView>(viewId)
            if (placeHolder)
                view!!.text = insert(value)
            else {
                if (value == null) {
                    view!!.text = ""
                } else
                    view!!.text = value
            }
        } catch (e: Exception) {
            printStack(e)
        }

        return this
    }


    fun setText(viewId: Int, @StringRes strId: Int): BaseViewHolder {
        try {
            val view = getView<TextView>(viewId)
            view!!.setText(strId)
        } catch (e: Exception) {
            printStack(e)
        }

        return this
    }

    fun <T : View> getView(viewId: Int): T? {
        try {
            var view: View? = views.get(viewId)
            if (view == null) {
                view = convertView.findViewById(viewId)
                views.put(viewId, view)
            }
            return view as T?
        } catch (e: Exception) {
            printStack(e)
        }

        return null
    }

    fun <T : View> getView(clazz: Class<T>, viewId: Int): T? {
        try {
            var view: View? = views.get(viewId)
            if (view == null) {
                view = convertView.findViewById(viewId)
                views.put(viewId, view)
            }
            return view as T?
        } catch (e: Exception) {
            printStack(e)
        }

        return null
    }

    private fun printStack(e: Exception) {
        Debuger.printMsg("","")
        Debuger.printMsg("适配器错误>>>>>>>", e.cause)
        if (Debuger.LOG) {
            e.printStackTrace()
        }
    }
}
