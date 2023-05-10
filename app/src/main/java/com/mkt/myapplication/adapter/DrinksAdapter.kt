package com.mkt.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mkt.myapplication.R
import com.mkt.myapplication.dto.Drink

class DrinksAdapter : RecyclerView.Adapter<DrinksAdapter.MyViewHolder> {
    var drinkList: List<Drink>? = null
    private var listener: AdapterView.OnItemClickListener? = null
    var context: Context? = null

    constructor(drinkList: List<Drink>?, context: Context) : super() {
        this.drinkList = drinkList
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.cocktail_item_row, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val menu = drinkList!![position]
        val imageUrl = menu.strDrinkThumb
        holder.tvTitle.text = menu.strDrink
        holder.tvPrice.text = "Kes ${menu.idDrink} /="
        Glide.with(holder.itemView)
            .load(imageUrl)
            .placeholder(R.drawable.ic_cocktail_placeholder)
            .into(holder.ivCocktail)

    }

    fun setOnClickListener(listener: AdapterView.OnItemClickListener?) {
        this.listener = listener
    }

    override fun getItemCount(): Int {
        return drinkList!!.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView = itemView.findViewById(R.id.tvName)
        var tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        var cvCart: CardView = itemView.findViewById(R.id.cvCart)
        var ivCocktail: ImageView = itemView.findViewById(R.id.ivCocktail)
    }

    interface OnItemClickListener {
        fun onItemSelected(Drink: Drink?, position: Int)
    }
}