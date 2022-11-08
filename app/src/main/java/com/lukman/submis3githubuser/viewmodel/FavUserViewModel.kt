package com.lukman.submis3githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lukman.submis3githubuser.local.FavoriteUser
import com.lukman.submis3githubuser.repository.FavUserRepository

class FavUserViewModel(application: Application): ViewModel() {

    private val  favUserRepository = FavUserRepository(application)

    fun getFavUser(): LiveData<List<FavoriteUser>> =
        favUserRepository.getFavUser()
}