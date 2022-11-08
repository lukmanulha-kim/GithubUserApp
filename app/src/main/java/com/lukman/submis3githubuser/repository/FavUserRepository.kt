package com.lukman.submis3githubuser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.lukman.submis3githubuser.local.FavUserDatabase
import com.lukman.submis3githubuser.local.FavoriteUser
import com.lukman.submis3githubuser.local.FavoriteUserDao
import com.lukman.submis3githubuser.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

class FavUserRepository(application: Application) {

    private val favoriteUserDao: FavoriteUserDao
    private val dispatcher = Executors.newCachedThreadPool().asCoroutineDispatcher()
    private val scope = CoroutineScope(dispatcher)

    init {
        val database = FavUserDatabase.getDatabase(application)
        favoriteUserDao = database.favUserDao()
    }

    fun getFavUser(): LiveData<List<FavoriteUser>> = favoriteUserDao.getFavUser()

    fun insertFavUser(favoriteUser: FavoriteUser){
        scope.launch {
            favoriteUserDao.insert(favoriteUser)
        }
    }

    fun deleteFavUser(user: Int) {
        favoriteUserDao.deleteFavUser(user)
    }

    suspend fun checkedUser(user: User): Int{
        return withContext(scope.coroutineContext){
            favoriteUserDao.countFavUser(user.id)
        }
    }

}