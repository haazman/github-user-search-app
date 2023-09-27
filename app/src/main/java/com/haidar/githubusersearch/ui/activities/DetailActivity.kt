package com.haidar.githubusersearch.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.haidar.githubusersearch.R
import com.haidar.githubusersearch.data.database.FavouriteUser
import com.haidar.githubusersearch.data.response.DetailResponse
import com.haidar.githubusersearch.databinding.ActivityDetailBinding
import com.haidar.githubusersearch.preferences.SettingPreferences
import com.haidar.githubusersearch.preferences.dataStore
import com.haidar.githubusersearch.repository.FavouriteUserRepository
import com.haidar.githubusersearch.viewmodel.DetailViewModel
import com.haidar.githubusersearch.ui.adapters.SectionsPagerAdapter
import com.haidar.githubusersearch.viewmodel.FavouriteViewModel
import com.haidar.githubusersearch.viewmodel.ViewModelFactory

class DetailActivity : AppCompatActivity() {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val pref = SettingPreferences.getInstance(application.dataStore)
        setContentView(binding.root)
        var isFavourite = false
        val username = intent.getStringExtra("username")
        val favUser = FavouriteUser()
        val factory = ViewModelFactory.getInstance(this.application, pref)
        val detailViewModel = ViewModelProvider(this, factory).get(
            DetailViewModel::class.java
        )
        detailViewModel.getFavouriteUserByUsername(username!!).observe(this) {
            if (it != null) {
                binding.favouriteBtn.setImageResource(R.drawable.ic_love)
                isFavourite = true
            }
        }
        detailViewModel.findGithubDetail(username!!)
        detailViewModel.detail.observe(this) {
            updateUser(it)
            binding.webBtn.setOnClickListener { view ->
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(it?.htmlUrl)
                startActivity(intent)
            }
            binding.shareBtn.setOnClickListener { view ->
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, it?.htmlUrl)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)

            }
            binding.favouriteBtn.setOnClickListener { view ->
                when (isFavourite) {
                    true -> {
                        binding.favouriteBtn.setImageResource(R.drawable.ic_love_outline)
                        favUser.id = it!!.id
                        favUser.username = it.login
                        favUser.avatar_url = it.avatarUrl
                        detailViewModel.delete(favUser)
                        isFavourite = false
                    }

                    false -> {
                        binding.favouriteBtn.setImageResource(R.drawable.ic_love)
                        favUser.id = it!!.id
                        favUser.username = it.login
                        favUser.avatar_url = it.avatarUrl
                        detailViewModel.insert(favUser)
                        isFavourite = true
                    }
                }
            }
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, username)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f


    }

    private fun updateUser(detail: DetailResponse?) {
        binding.textUsername.text = detail?.login
        binding.textUsername2.text = detail?.login
        Glide.with(binding.root.context).load(detail?.avatarUrl).into(binding.imageProfile)
        binding.textName.text = detail?.name
        binding.textCountFollowers.text = detail?.followers.toString()
        binding.textCountFollowing.text = detail?.following.toString()
        binding.textCountRepos.text = detail?.publicRepos.toString()
    }


}