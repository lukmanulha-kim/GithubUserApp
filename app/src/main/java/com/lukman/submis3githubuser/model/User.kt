package com.lukman.submis3githubuser.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int,
    @SerializedName("avatar_url")
    val avatar: String,
    @SerializedName("login")
    val username: String
): Parcelable
