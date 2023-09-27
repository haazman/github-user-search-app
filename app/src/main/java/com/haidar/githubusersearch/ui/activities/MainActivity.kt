package com.haidar.githubusersearch.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.haidar.githubusersearch.R
import com.haidar.githubusersearch.data.response.ItemsItem
import com.haidar.githubusersearch.databinding.ActivityMainBinding
import com.haidar.githubusersearch.preferences.SettingPreferences
import com.haidar.githubusersearch.preferences.dataStore
import com.haidar.githubusersearch.viewmodel.MainViewModel
import com.haidar.githubusersearch.ui.adapters.GithubAdapter
import com.haidar.githubusersearch.viewmodel.ViewModelFactory


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val layoutManager = LinearLayoutManager(this)

        showLoading(false)

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            rvGithub.layoutManager = layoutManager
        }

        val factory = ViewModelFactory.getInstance(this.application, pref)
        val mainViewModel = ViewModelProvider(this, factory).get(
            MainViewModel::class.java
        )

        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                binding.imgGithub.setImageResource(R.drawable.github_invert)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
            }
        }

        binding.switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            mainViewModel.saveThemeSetting(isChecked)
        }
        mainViewModel.listGithub.observe(this) { github ->
            setGithubData(github)
        }
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        binding.searchView.editText.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            mainViewModel.findGithub(binding.searchView.editText.text.toString())
            false
        })
        binding.btnFavourite.setOnClickListener {
            val intentFav = Intent(this, FavouriteUserActivity::class.java)
            startActivity(intentFav)
        }

    }


    private fun setGithubData(data: List<ItemsItem>) {
        val adapter = GithubAdapter()
        adapter.submitList(data)
        binding.rvGithub.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.rvGithub.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.rvGithub.visibility = View.VISIBLE
        }
    }
}







