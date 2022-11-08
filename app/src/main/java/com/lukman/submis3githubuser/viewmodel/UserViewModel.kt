package com.lukman.submis3githubuser.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lukman.submis3githubuser.repository.UserRepository
import com.lukman.submis3githubuser.model.SearchResponse
import com.lukman.submis3githubuser.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel(private val application: Application): ViewModel() {

    private val listUsers = MutableLiveData<MutableList<User>>()

    fun setUsers(query: String){
        query.let {
            UserRepository.getUsers(it).enqueue(object : Callback<SearchResponse>{
                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {
                    if (response.isSuccessful){
                        listUsers.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    Toast.makeText(application, "Error : "+t.message.toString(), Toast.LENGTH_SHORT).show()
                }

            })
        }
    }

    fun getUsers() : LiveData<MutableList<User>> =listUsers
}