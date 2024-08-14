package com.mindgeeks.sportsnews.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mindgeeks.sportsnews.Model.OtherModel.SearchHint
import com.mindgeeks.sportsnews.R

class SearchAdapter(val list: List<SearchHint>, val onItemClick: (String) -> Unit): RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    class SearchViewHolder(itemView: View): ViewHolder(itemView){
        val title = itemView.findViewById<TextView>(R.id.title)
        val desp = itemView.findViewById<TextView>(R.id.desp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.single_search_layout, parent,false)
        return SearchViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val data = list.get(position)
        holder.title.text = data.title
        holder.desp.text = data.description

        holder.itemView.setOnClickListener {
            onItemClick.invoke(data.description)
        }
    }
}