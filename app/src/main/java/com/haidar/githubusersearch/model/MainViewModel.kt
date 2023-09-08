package com.haidar.githubusersearch.model

import android.content.ContentValues
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haidar.githubusersearch.data.response.DetailResponse
import com.haidar.githubusersearch.data.response.ItemsItem
import com.haidar.githubusersearch.data.response.ResponseGithub
import com.haidar.githubusersearch.data.retrofit.ApiConfig
import com.haidar.githubusersearch.ui.GithubAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel:ViewModel() {

    private var _listGithub = MutableLiveData<List<ItemsItem>>()
    val listGithub: LiveData<List<ItemsItem>> = _listGithub



    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading



    companion object{
        private const val TAG = "MainViewModel"
    }

    fun findGithub(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getGithub(query)
        client.enqueue(object : Callback<ResponseGithub> {
            override fun onResponse(
                call: Call<ResponseGithub>,
                response: Response<ResponseGithub>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listGithub.value = responseBody.items
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ResponseGithub>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }


}