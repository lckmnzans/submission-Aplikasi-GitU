package com.dicoding.gitu.follows

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.gitu.R
import com.dicoding.gitu.user.User

class UserFollowsAdapter(private val listFollows: ArrayList<User>) : RecyclerView.Adapter<UserFollowsAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_user_photo)
        val tvUsername: TextView = itemView.findViewById(R.id.tv_user_username)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listFollows.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (photo, username) = listFollows[position]
        Glide.with(holder.itemView.context).load(photo).into(holder.imgPhoto)
        holder.tvUsername.text = username
    }
}
