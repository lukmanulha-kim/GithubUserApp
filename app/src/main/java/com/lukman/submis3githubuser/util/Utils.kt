package com.lukman.submis3githubuser.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadImage(url: String?) {
    Glide.with(this.context)
        .load(url)
        .apply(RequestOptions().override(500, 500))
        .centerCrop()
        .into(this)
}