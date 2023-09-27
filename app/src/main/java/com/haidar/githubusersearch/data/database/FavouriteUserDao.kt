package com.haidar.githubusersearch.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavouriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: FavouriteUser)

    @Update
    fun update(note: FavouriteUser)

    @Delete
    fun delete(note: FavouriteUser)

    @Query("SELECT * from favouriteuser ORDER BY id ASC")
    fun getAllFavouriteUser(): LiveData<List<FavouriteUser>>

    @Query("SELECT * from favouriteuser WHERE username = :username")
    fun getFavouriteUserByUser(username: String): LiveData<FavouriteUser>
}