package com.lukman.submis3githubuser.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lukman.submis3githubuser.local.FavoriteUser
import com.lukman.submis3githubuser.repository.UserRepository
import com.lukman.submis3githubuser.model.DetUserResponse
import com.lukman.submis3githubuser.model.User
import com.lukman.submis3githubuser.repository.FavUserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetUserViewModel(private var application: Application): ViewModel() {

    private val favUserRepository = FavUserRepository(application)
    private val detailUser = MutableLiveData<DetUserResponse>()

    fun setDetailUser(username: String){
        UserRepository.detailUser(username).enqueue(object : Callback<DetUserResponse>{
            override fun onResponse(
                call: Call<DetUserResponse>,
                response: Response<DetUserResponse>
            ) {
                if (response.isSuccessful){
                    detailUser.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<DetUserResponse>, t: Throwable) {
                Toast.makeText(application, "Error : "+t.message.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun getDetailUser(): LiveData<DetUserResponse> = detailUser

    fun insertFavUser(id: Int, avatar_url: String, username: String){
        val favUser = FavoriteUser(id, avatar_url, username)
        favUserRepository.insertFavUser(favUser)
    }

    fun deleteFavUser(user: Int){
        CoroutineScope(Dispatchers.IO).launch {
            favUserRepository.deleteFavUser(user)
        }
    }

    suspend fun checkedUser(user: User): Int{
        return favUserRepository.checkedUser(user)
    }
}