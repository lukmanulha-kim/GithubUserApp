package com.lukman.submis3githubuser.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetUserResponse(
    val login: String,
    val avatar_url: String,
    val name: String,
    val company: String,
    val location: String,
    val public_repos: Int,
    val followers: Int,
    val following: Int
): Parcelable
