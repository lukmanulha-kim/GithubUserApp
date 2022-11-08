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

class FollowersViewModel(private val application: Application): ViewModel() {

    private val dataFollowers = MutableLiveData<MutableList<User>>()

    fun setFollowers(username:String, tipe: String){
        UserRepository.getDetails(username, tipe).enqueue(object: Callback<MutableList<User>>{
            override fun onResponse(
                call: Call<MutableList<User>>,
                response: Response<MutableList<User>>
            ) {
                if (response.isSuccessful){
                    dataFollowers.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<MutableList<User>>, t: Throwable) {
                Toast.makeText(application, "Error : "+t.message.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun getFollowers(): LiveData<MutableList<User>> = dataFollowers
}