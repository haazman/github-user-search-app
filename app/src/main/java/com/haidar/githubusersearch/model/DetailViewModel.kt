package com.haidar.githubusersearch.model

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haidar.githubusersearch.data.response.DetailResponse
import com.haidar.githubusersearch.data.response.ItemsItem
import com.haidar.githubusersearch.data.response.ResponseFollow
import com.haidar.githubusersearch.data.response.ResponseGithub
import com.haidar.githubusersearch.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _detail = MutableLiveData<DetailResponse?>()
    val detail: LiveData<DetailResponse?> = _detail

    private var _follower = MutableLiveData<List<ResponseFollow>?>()
    val follower: LiveData<List<ResponseFollow>?> = _follower

    private var _following = MutableLiveData<List<ResponseFollow>?>()
    val following: LiveData<List<ResponseFollow>?> = _following

    fun findGithubDetail(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getGithubDetail(username)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _detail.value = responseBody
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun findFollower(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollower(query)
        client.enqueue(object : Callback<List<ResponseFollow>> {
            override fun onResponse(
                call: Call<List<ResponseFollow>>,
                response: Response<List<ResponseFollow>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _follower.value = responseBody
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<ResponseFollow>>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun findFollowing(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(query)
        client.enqueue(object : Callback<List<ResponseFollow>> {
            override fun onResponse(
                call: Call<List<ResponseFollow>>,
                response: Response<List<ResponseFollow>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _following.value = responseBody
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<ResponseFollow>>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }

}