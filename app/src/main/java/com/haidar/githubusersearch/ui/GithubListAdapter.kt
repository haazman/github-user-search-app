package com.haidar.githubusersearch.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.common.io.Resources
import com.haidar.githubusersearch.R
import com.haidar.githubusersearch.data.response.ItemsItem
import com.haidar.githubusersearch.databinding.ItemGithubBinding

class GithubAdapter : ListAdapter<ItemsItem, GithubAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemGithubBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, parent.context)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
    }
    class MyViewHolder(val binding: ItemGithubBinding, val context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun bind(responseGithub: ItemsItem){

            val color = when{
                adapterPosition % 3 == 0 -> R.color.card_background_3
                adapterPosition % 2 == 0 -> R.color.card_background_2
                adapterPosition % 1 == 0 -> R.color.card_background_1
                else -> R.color.card_background_3
            }

            binding.card.setCardBackgroundColor(ContextCompat.getColor(context, color))
            binding.textUsername.text = responseGithub.login
            binding.textId.text = "ID : ${responseGithub.id}"
            binding.textScore.text = "Score: ${responseGithub.score}"
            Glide.with(context).load(responseGithub.avatar_url).into(binding.imageProfile)
            binding.card.setOnClickListener{
                val intentDetail = Intent(binding.root.context, DetailActivity::class.java)
                intentDetail.putExtra("username", responseGithub.login)
                binding.root.context.startActivity(intentDetail)
            }
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem:ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}