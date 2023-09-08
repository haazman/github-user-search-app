package com.haidar.githubusersearch.ui
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.haidar.githubusersearch.R
import com.haidar.githubusersearch.data.response.DetailResponse
import com.haidar.githubusersearch.data.response.ItemsItem
import com.haidar.githubusersearch.databinding.ActivityMainBinding
import com.haidar.githubusersearch.model.MainViewModel
import java.security.AccessController.getContext


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val layoutManager = LinearLayoutManager(this)
        showLoading(false)
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            rvGithub.layoutManager = layoutManager
        }
        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)
        mainViewModel.listGithub.observe(this) { github ->
            setGithubData(github)
        }
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        binding.searchView.editText.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            mainViewModel.findGithub(binding.searchView.editText.text.toString()    )
            false
        })

        }

    private fun setGithubData(response: List<ItemsItem>) {
        val adapter = GithubAdapter()
        adapter.submitList(response)
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







