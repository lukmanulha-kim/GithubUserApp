package com.lukman.submis3githubuser.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RepoResponse(
    val id: Int,
    val name: String,
    val full_name: String
): Parcelable
