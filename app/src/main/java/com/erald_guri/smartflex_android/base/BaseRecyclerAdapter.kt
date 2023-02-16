package com.erald_guri.smartflex_android.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.erald_guri.smartflex_android.interfaces.BaseRecyclerListener

abstract class BaseRecyclerAdapter<T,L: BaseRecyclerListener, VH: BaseViewHolder<T,L>>(
    context: Context,
    listener: L
): RecyclerView.Adapter<VH>() {

    private var items: ArrayList<T>? = null
    private var listener: L? = null
    var layoutInflater: LayoutInflater? = null

    init {
        layoutInflater = LayoutInflater.from(context)
        items = ArrayList()
        this.listener = listener
    }

//    constructor(context: Context, listener: L) : this(context) {
//        this.listener = listener
//        this.items = ArrayList()
//        this.layoutInflater = LayoutInflater.from(context)
//    }

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (items?.size!! <= position) {
            return
        }
        val item: T = items!![position]
        holder.onBind(item)
    }

    override fun getItemCount(): Int = if (items!!.isNotEmpty()) items!!.size else 0

    fun setItems(items: List<T>) = setItems(items, true)

    @Throws(IllegalArgumentException::class)
    fun setItems(recyclerItems: List<T>?, notifyChanges: Boolean) {
        if (recyclerItems!!.isEmpty()) {
            throw IllegalArgumentException("Cannot set `null` item to the Recycler adapter")
        }
        this.items!!.clear()
        this.items!!.addAll(recyclerItems)
        if (notifyChanges) {
            notifyDataSetChanged()
        }
    }

    fun updateItems(newItems: ArrayList<T>) = setItems(newItems)

    fun updateItems(newItems: ArrayList<T>, diffCallback: DiffUtil.Callback) {
        val result = DiffUtil.calculateDiff(diffCallback, false)
        setItems(newItems, false)
        result.dispatchUpdatesTo(this)
    }

    fun getItems(): ArrayList<T>? = items

    fun getItem(position: Int): T = items!![position]

    @Throws(IllegalArgumentException::class)
    fun add(item: T) {
        if (items!!.isEmpty()) {
            throw IllegalArgumentException("Cannot set `null` item to the Recycler adapter")
        }
        items!!.add(item)
        notifyItemInserted(items!!.size - 1)
    }

    @Throws(IllegalArgumentException::class)
    fun addToFirstPosition(item: T) {
        if (items!!.isEmpty()) {
            throw IllegalArgumentException("Cannot set `null` item to the Recycler adapter")
        }
        items!!.add(0, item)
        notifyItemInserted(0)
    }

    @Throws(IllegalArgumentException::class)
    fun addAll(items: ArrayList<T>?) {
        if (items!!.isEmpty()) {
            throw IllegalArgumentException("Cannot set `null` item to the Recycler adapter")
        }
        this.items!!.addAll(items)
        notifyItemRangeInserted(this.items!!.size - items.size, items.size)
    }

    fun clear() {
        items!!.clear()
        notifyDataSetChanged()
    }

    fun remove(item: T) {
        val position = items!!.indexOf(item)
        if (position > - 1) {
            items!!.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun isEmpty(): Boolean = itemCount == 0

    override fun setHasStableIds(hasStableIds: Boolean) {
        super.setHasStableIds(hasStableIds)
    }

    fun getListener(): L? = listener

    fun setListener(listener: L) {
        this.listener = listener
    }

    protected fun inflate(@LayoutRes layout: Int, parent: ViewGroup?, attachToRoot: Boolean): View {
        return layoutInflater!!.inflate(layout, parent, false)
    }

    protected open fun inflate(@LayoutRes layout: Int, parent: ViewGroup?): View {
        return inflate(layout, parent, false)
    }
}