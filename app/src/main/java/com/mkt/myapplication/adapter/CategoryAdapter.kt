package com.mkt.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mkt.myapplication.R
import com.mkt.myapplication.dto.Category

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    var categoryList: List<Category>? = null
    private var listener: AdapterView.OnItemClickListener? = null
    var context: Context? = null

    constructor(categoryList: List<Category>?, context: Context) : super() {
        this.categoryList = categoryList
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_item_row, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val menu = categoryList!![position]
        holder.tvTitle.text = menu.strCategory
    }

    fun setOnClickListener(listener: AdapterView.OnItemClickListener?) {
        this.listener = listener
    }

    override fun getItemCount(): Int {
        return categoryList!!.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView = itemView.findViewById(R.id.tvCategoryTitle)
    }

    interface OnItemClickListener : AdapterView.OnItemClickListener {
        fun onItemSelected(Category: Category?, position: Int)
    }
}