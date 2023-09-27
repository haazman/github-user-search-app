package com.haidar.githubusersearch.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.haidar.githubusersearch.data.database.FavouriteUser
import com.haidar.githubusersearch.repository.FavouriteUserRepository

class FavouriteViewModel(application: Application) : ViewModel() {
    private val mFavUserRepository: FavouriteUserRepository = FavouriteUserRepository(application)
    fun getAllNotes(): LiveData<List<FavouriteUser>> = mFavUserRepository.getAllFavouriteUser()

}