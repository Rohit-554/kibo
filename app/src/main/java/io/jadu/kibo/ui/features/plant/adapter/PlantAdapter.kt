package io.jadu.kibo.ui.features.plant.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.jadu.kibo.R
import io.jadu.kibo.data.remote.models.plantModels.PlantDto

class PlantAdapter:RecyclerView.Adapter<PlantAdapter.PlantViewHolder>() {
    var plantList:PlantDto? = null
    inner class PlantViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val plantName = itemView.findViewById<TextView>(R.id.plantName)
        val plantImage = itemView.findViewById<ImageView>(R.id.plantImage)
        val plantDescription = itemView.findViewById<TextView>(R.id.plantDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_plant_layout,parent,false)
        return PlantViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d("totalplant", "getItemCount: ${plantList?.data?.size}")
        return plantList?.data?.size ?: 0
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val currentItem = plantList?.data?.get(position)
        Log.d("currentplant", "onBindViewHolder: ${currentItem?.common_name}")
        holder.plantName.text = currentItem?.common_name
        holder.plantDescription.text = currentItem?.scientific_name.toString()
        Glide.with(holder.itemView.context).load(currentItem?.default_image?.original_url).into(holder.plantImage)
    }
}