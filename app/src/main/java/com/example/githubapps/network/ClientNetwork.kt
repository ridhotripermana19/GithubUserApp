package com.example.githubapps.network

import com.example.githubapps.model.user.User
import com.example.githubapps.model.user.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ClientNetwork {

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }

    @GET("search/users")
    fun searchUsers(@Query("q") query: String) : Call<User>

    @GET("users/{username}")
    fun getDetail(@Path("username") username: String ): Call<UserResponse>

    @GET("users/{username}/followers")
    fun getFollower(@Path("username") follower: String): Call<List<UserResponse>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") following: String): Call<List<UserResponse>>

}