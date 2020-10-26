package com.rmf.infiniteloadmore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.list_item.view.*

class MainAdapter(private val list : ArrayList<MainData>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MainAdapter.ViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(list.get(position).image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.itemView.imageview)

        holder.itemView.textview.text = list.get(position).name
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }
}