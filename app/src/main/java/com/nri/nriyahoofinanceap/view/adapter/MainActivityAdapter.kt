package com.nri.nriyahoofinanceap.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nri.nriyahoofinanceap.R
import com.nri.nriyahoofinanceap.model.suggestion.StockSymbol
import com.nri.nriyahoofinanceap.util.Constants
import com.nri.nriyahoofinanceap.view.adapter.MainActivityAdapter.*

class MainActivityAdapter(private val context: Context, private var items: List<StockSymbol>) :
    RecyclerView.Adapter<ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val labelOne = itemView.findViewById<TextView>(R.id.tvlabelOne)
        val labelTwo = itemView.findViewById<TextView>(R.id.tvLabelTwo)
        val labelThree = itemView.findViewById<TextView>(R.id.tvLabelThree)
        val labelFour = itemView.findViewById<TextView>(R.id.tvLabelFour)
        val labelFive = itemView.findViewById<TextView>(R.id.tvLabelFive)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.labelOne.text = items[position].exchDisp
        holder.labelTwo.text = items[position].type
        holder.labelThree.text = items[position].exch
        holder.labelFour.text = items[position].name
        holder.labelFive.text = "Type:"+items[position].typeDisp
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.row_stockitems, parent, false)
        )
    }

    fun updateList(items: List<StockSymbol>) {
        this.items = items
        notifyDataSetChanged()
    }
}