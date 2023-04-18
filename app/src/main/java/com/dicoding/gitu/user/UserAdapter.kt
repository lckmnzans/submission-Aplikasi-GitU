package com.dicoding.gitu.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.gitu.R

class UserAdapter(private val listUser: ArrayList<User>): RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //menetapkan objek imgPhoto utk foto profil
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_user_photo)
        //menetapkan objek tvUsername utk username
        val tvUsername: TextView = itemView.findViewById(R.id.tv_user_username)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //mengambil data foto dan username dari list yg diterapkan nanti di MainActivity
        val (photo, username) = listUser[position]
        //load foto profil dari url
        Glide.with(holder.itemView.context).load(photo).into(holder.imgPhoto)
        //load username
        holder.tvUsername.text = username
    }

}