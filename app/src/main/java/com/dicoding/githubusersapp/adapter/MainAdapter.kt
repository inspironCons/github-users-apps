package com.dicoding.githubusersapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.githubusersapp.databinding.ItemListUserBinding
import com.dicoding.githubusersapp.model.Users


class MainAdapter:RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    private var users:List<Users> = listOf()
    private lateinit var onItemCallback: OnItemCallback

    inner class ViewHolder(view: ItemListUserBinding):RecyclerView.ViewHolder(view.root){
        val avatar = view.userProfile
        val username = view.tvUsername
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        //drawable to bitmap
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = users[position]

        holder.avatar.setImageResource(item.avatar)
        holder.username.text = item.username
        holder.itemView.setOnClickListener {
            onItemCallback.onItemClicked(item)
        }
    }

    override fun getItemCount(): Int = users.size

    fun setItem(items:List<Users>){
        this.users = items
        notifyDataSetChanged()
    }

    fun setOnItemCallback(onItemCallback: OnItemCallback){
        this.onItemCallback = onItemCallback
    }

    interface OnItemCallback{
        fun onItemClicked(user:Users)
    }
}