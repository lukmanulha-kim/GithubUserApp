package com.lukman.submis3githubuser.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lukman.submis3githubuser.repository.UserRepository
import com.lukman.submis3githubuser.model.RepoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepoViewModel(private val application: Application): ViewModel() {

    private val dataRepos = MutableLiveData<MutableList<RepoResponse>>()

    fun setRepositpry(username: String){
        UserRepository.getRepo(username).enqueue(object : Callback<MutableList<RepoResponse>>{
            override fun onResponse(
                call: Call<MutableList<RepoResponse>>,
                response: Response<MutableList<RepoResponse>>
            ) {
                if (response.isSuccessful){
                    dataRepos.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<MutableList<RepoResponse>>, t: Throwable) {
                Toast.makeText(application, "Error : "+t.message.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun getRepository(): LiveData<MutableList<RepoResponse>> = dataRepos
}