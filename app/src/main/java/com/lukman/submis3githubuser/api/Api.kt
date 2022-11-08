package com.lukman.submis3githubuser.api

import com.lukman.submis3githubuser.BuildConfig.API_KEY
import com.lukman.submis3githubuser.model.DetUserResponse
import com.lukman.submis3githubuser.model.RepoResponse
import com.lukman.submis3githubuser.model.SearchResponse
import com.lukman.submis3githubuser.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("search/users")
    @Headers("Authorization: token $API_KEY")
    fun getUsers(@Query("q") query: String): Call<SearchResponse>

    @GET("users/{username}")
    @Headers("Authorization: token $API_KEY")
    fun detailUsers(@Path("username") username: String): Call<DetUserResponse>

    @GET("users/{username}/{tipe}")
    @Headers("Authorization: token $API_KEY")
    fun getDetail(
        @Path("username") username: String,
        @Path("tipe") tipe : String
    ): Call<MutableList<User>>

    @GET("users/{username}/repos")
    @Headers("Authorization: token $API_KEY")
    fun getRepo(@Path("username") username: String): Call<MutableList<RepoResponse>>

}