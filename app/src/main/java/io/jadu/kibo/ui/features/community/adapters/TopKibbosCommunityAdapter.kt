package io.jadu.kibo.ui.features.community.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import io.jadu.kibo.R
import io.jadu.kibo.data.remote.models.topkiboscommunity.TopKibbosCommunity

class TopKibbosCommunityAdapter:Adapter<TopKibbosCommunityAdapter.TopKibbosCommunityViewHolder>() {
    var topKibosList:List<TopKibbosCommunity> = listOf()
    class TopKibbosCommunityViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.topKibosuserName)
        val desc = itemView.findViewById<TextView>(R.id.desc)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TopKibbosCommunityViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.item_top_kibos,parent,false)
        return TopKibbosCommunityViewHolder(view)
    }

    override fun getItemCount(): Int {
        return topKibosList.size
    }

    override fun onBindViewHolder(holder: TopKibbosCommunityViewHolder, position: Int) {
        val topKibos = topKibosList[position]
        holder.name.text = topKibos.name
        holder.desc.text = topKibos.desc

    }
}