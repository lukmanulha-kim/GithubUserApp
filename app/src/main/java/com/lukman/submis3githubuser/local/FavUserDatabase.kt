package com.lukman.submis3githubuser.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteUser::class], version = 1)
abstract class FavUserDatabase : RoomDatabase() {

    abstract fun favUserDao(): FavoriteUserDao

    companion object{
        @Volatile
        private var INSTANCE: FavUserDatabase ?= null

        fun getDatabase(context: Context): FavUserDatabase{
            if (INSTANCE == null){
                synchronized(FavUserDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavUserDatabase::class.java,
                        "fav_user_database"
                    ).build()
                }
            }
            return INSTANCE as FavUserDatabase
        }
    }
}