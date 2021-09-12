package com.dicoding.githubusersapp.network.users

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IUsers {
    @GET("search/users")
    suspend fun searchUser(
        @Query("q") search:String
    ):UsersSearchResponse

    @GET("users/{username}")
    suspend fun getDetailUsername(
        @Path("username") username:String
    ):UserDetailResponse

    @GET("users/{username}/followers")
    suspend fun getFolloweUser(
        @Path("username") username:String
    ):List<UsersFollowersFollowingResponse>

    @GET("users/{username}/following")
    suspend fun getFollowingUser(
        @Path("username") username:String
    ):List<UsersFollowersFollowingResponse>

}