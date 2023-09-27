package com.haidar.githubusersearch.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.haidar.githubusersearch.data.response.ItemsItem
import com.haidar.githubusersearch.data.response.ResponseGithub
import com.haidar.githubusersearch.data.retrofit.ApiConfig
import com.haidar.githubusersearch.preferences.SettingPreferences
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.coroutines.flow.Flow

class MainViewModel(private val pref: SettingPreferences) : ViewModel() {

    private var _listGithub = MutableLiveData<List<ItemsItem>>()
    val listGithub: LiveData<List<ItemsItem>> = _listGithub


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    companion object {
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

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
}