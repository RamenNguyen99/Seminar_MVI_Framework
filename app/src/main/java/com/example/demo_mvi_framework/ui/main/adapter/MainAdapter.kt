package com.example.demo_mvi_framework.ui.main.adapter;

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demo_mvi_framework.data.model.User
import com.example.demo_mvi_framework.databinding.ItemLayoutBinding

/**
 * Created by tuong.nguyen2 on 22/03/2023.
 */
class MainAdapter(
    private val users: ArrayList<User>,
    private val onItemClicked: (user: User) -> Unit
) : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        ) {
            onItemClicked(it)
        }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(users[position])

    override fun getItemCount(): Int = users.size

    class DataViewHolder(
        private val binding: ItemLayoutBinding,
        private val onItemClicked: (user: User) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            // bind data into item view
            binding.run {
                tvUserName.text = user.name
                tvEmail.text = user.email
                Glide.with(imgAvatar.context).load(user.avatar).into(imgAvatar)
                itemView.setOnClickListener {
                    Log.i("TAG", "bind: $user")
                    onItemClicked(user)
                }
            }
        }
    }

    fun addData(users: List<User>) {
        this.users.addAll(users)
    }
}
