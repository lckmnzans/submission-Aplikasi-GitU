package com.dicoding.gitu.api

import com.dicoding.gitu.GithubResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUser(@Query("q") q: String): Call<GithubResponse>
}