package com.haidar.githubusersearch.ui

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.haidar.githubusersearch.R
import com.haidar.githubusersearch.data.response.DetailResponse
import com.haidar.githubusersearch.databinding.ActivityDetailBinding
import com.haidar.githubusersearch.model.DetailViewModel

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
        setContentView(binding.root)
        val username = intent.getStringExtra("username")
        val detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DetailViewModel::class.java)
        detailViewModel.findGithubDetail(username!!)
        detailViewModel.detail.observe(this) {
            updateUser(it)
            binding.webBtn.setOnClickListener(){view ->
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setData(Uri.parse(it?.url))
                startActivity(intent)
            }
            binding.shareBtn.setOnClickListener{
                view ->
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, it?.url)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
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

    private fun updateUser(detail:DetailResponse?){
        binding.textUsername.text = detail?.login
        binding.textUsername2.text = detail?.login
        Glide.with(binding.root.context).load(detail?.avatarUrl).into(binding.imageProfile)
        binding.textName.text = detail?.name
        binding.textCountFollowers.text = detail?.followers.toString()
        binding.textCountFollowing.text = detail?.following.toString()
        binding.textCountRepos.text = detail?.publicRepos.toString()
    }


}