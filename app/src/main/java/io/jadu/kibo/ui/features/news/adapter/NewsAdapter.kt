package io.jadu.kibo.ui.features.news.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.jadu.kibo.R
import io.jadu.kibo.data.remote.models.NewsDto


class NewsAdapter():RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    var newsList:NewsDto?=null
    inner class NewsViewHolder(itemview: View):RecyclerView.ViewHolder(itemview) {
        val newsTitle = itemview.findViewById<TextView>(R.id.heading)
        val newsDescription = itemview.findViewById<TextView>(R.id.newsDescription)
        val newsImage = itemview.findViewById<ImageView>(R.id.newsImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news_layout,parent,false)
        return NewsViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d("totalc", "getItemCount: ${newsList?.totalResults}")
        return newsList?.totalResults ?: 0
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = newsList?.articles?.get(position)
        Log.d("current", "onBindViewHolder: ${currentItem?.title}")
        holder.newsTitle.text = currentItem?.title
        holder.newsDescription.text = currentItem?.description
        Glide.with(holder.itemView.context).load(currentItem?.urlToImage).into(holder.newsImage)
    }
}