package com.example.androidproject.network

import com.example.androidproject.model.GitHubUser
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApiService {

    @GET("users")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<GitHubUser>


    @GET("users/{login}")
    suspend fun getUserDetails(@Path("login") login: String): GitHubUser


    companion object {
        const val BASE_URL = "https://api.github.com/"
    }
}
