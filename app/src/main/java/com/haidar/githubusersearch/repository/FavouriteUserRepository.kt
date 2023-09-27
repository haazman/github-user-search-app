package com.haidar.githubusersearch.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.haidar.githubusersearch.data.database.FavouriteUser
import com.haidar.githubusersearch.data.database.FavouriteUserDao
import com.haidar.githubusersearch.data.database.FavouriteUserDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavouriteUserRepository(application: Application) {
    private val mFavouriteUserDao: FavouriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavouriteUserDatabase.getDatabase(application)
        mFavouriteUserDao = db.favouriteUserDao()
    }

    fun getAllFavouriteUser(): LiveData<List<FavouriteUser>> =
        mFavouriteUserDao.getAllFavouriteUser()

    fun insert(favouriteUser: FavouriteUser) {
        executorService.execute { mFavouriteUserDao.insert(favouriteUser) }
    }

    fun delete(favouriteUser: FavouriteUser) {
        executorService.execute { mFavouriteUserDao.delete(favouriteUser) }
    }

    fun getFavouriteUserByUsername(username: String):LiveData<FavouriteUser> = mFavouriteUserDao.getFavouriteUserByUser(username)
}