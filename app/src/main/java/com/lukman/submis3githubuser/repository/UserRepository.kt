package com.lukman.submis3githubuser.repository

import com.lukman.submis3githubuser.api.RetroClient
import com.lukman.submis3githubuser.model.DetUserResponse
import com.lukman.submis3githubuser.model.RepoResponse
import com.lukman.submis3githubuser.model.SearchResponse
import com.lukman.submis3githubuser.model.User
import retrofit2.Call

object UserRepository {

    fun getUsers(query: String) : Call<SearchResponse> = RetroClient.getInstance.getUsers(query)

    fun detailUser(username: String): Call<DetUserResponse> = RetroClient.getInstance.detailUsers(username)

    fun getDetails(username: String, tipe: String): Call<MutableList<User>> = RetroClient.getInstance.getDetail(username, tipe)

    fun getRepo(username: String): Call<MutableList<RepoResponse>> = RetroClient.getInstance.getRepo(username)
}