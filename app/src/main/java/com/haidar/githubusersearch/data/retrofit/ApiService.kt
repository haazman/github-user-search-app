package com.haidar.githubusersearch.data.retrofit

import com.haidar.githubusersearch.data.response.DetailResponse
import com.haidar.githubusersearch.data.response.ResponseFollow
import com.haidar.githubusersearch.data.response.ResponseGithub
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getGithub(
        @Query("q") query: String
    ): Call<ResponseGithub>

    @GET("users/{username}")
    fun getGithubDetail(
        @Path("username") username:String
    ): Call<DetailResponse>

    @GET("users/{username}/followers")
    fun getFollower(
        @Path("username") username:String
    ): Call<List<ResponseFollow>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username:String
    ): Call<List<ResponseFollow>>

}



class ApiConfig {
    companion object{
        fun getApiService(): ApiService {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}

