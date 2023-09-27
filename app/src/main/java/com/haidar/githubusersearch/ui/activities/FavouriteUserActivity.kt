package com.haidar.githubusersearch.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.haidar.githubusersearch.R
import com.haidar.githubusersearch.data.database.FavouriteUser
import com.haidar.githubusersearch.data.response.ItemsItem
import com.haidar.githubusersearch.databinding.ActivityFavouriteUserBinding
import com.haidar.githubusersearch.preferences.SettingPreferences
import com.haidar.githubusersearch.preferences.dataStore
import com.haidar.githubusersearch.ui.adapters.FavListAdapter
import com.haidar.githubusersearch.ui.adapters.FollowListAdapter
import com.haidar.githubusersearch.ui.adapters.GithubAdapter
import com.haidar.githubusersearch.viewmodel.FavouriteViewModel
import com.haidar.githubusersearch.viewmodel.ViewModelFactory

class FavouriteUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavouriteUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pref = SettingPreferences.getInstance(application.dataStore)
        val layoutManager = LinearLayoutManager(this)
        binding.rvFav.layoutManager = layoutManager
        val factory = ViewModelFactory.getInstance(this.application, pref)
        val favUserViewModel = ViewModelProvider(this, factory).get(
            FavouriteViewModel::class.java
        )
        favUserViewModel.getAllNotes().observe(this) {
            setFavData(it)
        }

    }

    private fun setFavData(data: List<FavouriteUser>) {
        val adapter = FavListAdapter()
        adapter.submitList(data)
        binding.rvFav.adapter = adapter
    }
}