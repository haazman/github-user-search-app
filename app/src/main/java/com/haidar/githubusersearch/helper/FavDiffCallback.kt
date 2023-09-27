package com.haidar.githubusersearch.helper

import androidx.recyclerview.widget.DiffUtil
import com.haidar.githubusersearch.data.database.FavouriteUser

class FavDiffCallback(
    private val oldFavList: List<FavouriteUser>,
    private val newFavList: List<FavouriteUser>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldFavList.size
    override fun getNewListSize(): Int = newFavList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavList[oldItemPosition].id == newFavList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFav = oldFavList[oldItemPosition]
        val newFav = newFavList[newItemPosition]
        return oldFav == newFav
    }
}