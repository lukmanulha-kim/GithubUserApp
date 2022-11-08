package com.lukman.submis3githubuser.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteUserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favUser: FavoriteUser)

    @Query("SELECT * FROM FavoriteUser ORDER BY id ASC")
    fun getFavUser(): LiveData<List<FavoriteUser>>

    @Query("DELETE FROM FavoriteUser WHERE FavoriteUser.id = :id")
    fun deleteFavUser(id: Int): Int

    @Query("SELECT COUNT(*) FROM FavoriteUser WHERE FavoriteUser.id = :id")
    fun countFavUser(id: Int): Int

}