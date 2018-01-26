package com.superfactory.library.Bridge.Anko.widget

import android.view.ViewGroup

class AutoBindAdapter<Data>(private val createExp: (ViewGroup, Int) -> BaseViewHolder<Data>)
    : BaseRecyclerViewAdapter<Data, BaseViewHolder<Data>>() {
    private var assignment: ((BaseViewHolder<Data>, Data, Int) -> Unit)? = null

    fun assignment(assignment: (holder: BaseViewHolder<Data>, item: Data, position: Int) -> Unit): AutoBindAdapter<Data> {
        this.assignment = assignment
        return this
    }

    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int) = createExp.invoke(parent, viewType)


    override fun onBindViewHolder(holder: BaseViewHolder<Data>,
                                  item: Data, position: Int) {
        holder.bind(item)
        holder.component.assignmentHolder?.invoke(holder, item, position)
        assignment?.invoke(holder, item, position)
        holder.component.bindAll()
    }
}