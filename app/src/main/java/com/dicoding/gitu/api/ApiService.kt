package com.dicoding.gitu.api

import com.dicoding.gitu.UserDetailResponse
import com.dicoding.gitu.GithubResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getUser(@Query("q") q: String): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<UserDetailResponse>
}