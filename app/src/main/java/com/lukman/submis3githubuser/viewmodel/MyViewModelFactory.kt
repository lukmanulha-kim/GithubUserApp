package com.lukman.submis3githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyViewModelFactory private constructor(private val application: Application) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(UserViewModel::class.java) -> {
                UserViewModel(application) as T
            }

            modelClass.isAssignableFrom(DetUserViewModel::class.java) -> {
                DetUserViewModel(application) as T
            }

            modelClass.isAssignableFrom(FollowersViewModel::class.java) -> {
                FollowersViewModel(application) as T
            }

            modelClass.isAssignableFrom(FollowingViewModel::class.java) -> {
                FollowingViewModel(application) as T
            }

            modelClass.isAssignableFrom(RepoViewModel::class.java) -> {
                RepoViewModel(application) as T
            }

            modelClass.isAssignableFrom(FavUserViewModel::class.java) -> {
                FavUserViewModel(application) as T
            }

            else -> {
                throw IllegalArgumentException("Unknown Modell Class ${modelClass.name}")
            }
        }
    }

    companion object{
        @Volatile
        private var INSTANCE : MyViewModelFactory? = null

        fun getInstance(application: Application?): MyViewModelFactory =
            INSTANCE ?: synchronized(this) {
                val instance = MyViewModelFactory(application as Application)
                INSTANCE = instance
                instance
            }
    }
}