package io.jadu.kibo.ui.features.profile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.jadu.kibo.R
import io.jadu.kibo.data.remote.firestoreUpload.UserData
import org.w3c.dom.Text
import java.util.Date
import java.util.concurrent.TimeUnit

class UserProfileAdapter: RecyclerView.Adapter<UserProfileAdapter.UserProfileViewHolder>() {
    var dataList:List<UserData> = emptyList()
    class UserProfileViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
//        val email:TextView = itemView.findViewById(R.id.userId)
        val treeName:TextView = itemView.findViewById(R.id.tree_title)
        val points:TextView = itemView.findViewById(R.id.grow_points)
        val treeImage:ImageView = itemView.findViewById(R.id.profile_tree_image)
        val lastDatUpdate:TextView = itemView.findViewById(R.id.lastUpdateDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserProfileViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_profile_trees,parent,false)
        return UserProfileViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: UserProfileViewHolder, position: Int) {
        val currentItem = dataList[position]
//        holder.email.text = currentItem.email
        holder.treeName.text = currentItem.selectedTree
        holder.points.text = currentItem.points.toString()
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.treeImage)
        holder.lastDatUpdate.text = calculateDaysDifference(currentItem.lastUpdateDate.toLong(), System.currentTimeMillis()).toString() + "Days"


    }


    fun calculateDaysDifference(serverTimestampMillis: Long, currentTimestampMillis: Long): Long {
        val differenceMillis = currentTimestampMillis - serverTimestampMillis
        val daysDifference = TimeUnit.MILLISECONDS.toDays(differenceMillis)
        return daysDifference
    }
}