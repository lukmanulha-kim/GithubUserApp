package com.lukman.submis3githubuser.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lukman.submis3githubuser.repository.UserRepository
import com.lukman.submis3githubuser.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel(private val application: Application): ViewModel() {

    private val dataFollowing = MutableLiveData<MutableList<User>>()

    fun setFollowing(username: String, tipe: String){
        UserRepository.getDetails(username, tipe).enqueue(object : Callback<MutableList<User>>{
            override fun onResponse(
                call: Call<MutableList<User>>,
                response: Response<MutableList<User>>
            ) {
                if (response.isSuccessful){
                    dataFollowing.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<MutableList<User>>, t: Throwable) {
                Toast.makeText(application, "Error : "+t.message.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun getFollowing() : LiveData<MutableList<User>> = dataFollowing
}