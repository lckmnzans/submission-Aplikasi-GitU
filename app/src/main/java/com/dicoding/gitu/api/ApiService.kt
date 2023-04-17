package com.dicoding.gitu.api

import com.dicoding.gitu.GithubResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users?q={login}")
    fun getUser(
        @Path("login") login: String
    ): Call<GithubResponse>
}