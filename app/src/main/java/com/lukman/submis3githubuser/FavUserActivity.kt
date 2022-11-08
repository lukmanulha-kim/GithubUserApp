package com.lukman.submis3githubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.lukman.submis3githubuser.adapter.UserAdapter
import com.lukman.submis3githubuser.databinding.ActivityFavUserBinding
import com.lukman.submis3githubuser.local.FavoriteUser
import com.lukman.submis3githubuser.model.User
import com.lukman.submis3githubuser.viewmodel.FavUserViewModel
import com.lukman.submis3githubuser.viewmodel.MyViewModelFactory

class FavUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavUserBinding
    private lateinit var favUserViewModel: FavUserViewModel
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.appbar_favuser)

        favUserViewModel = ViewModelProvider(
            this,
            MyViewModelFactory.getInstance(this.application)
        )[FavUserViewModel::class.java]
        userAdapter = UserAdapter(::handleToDetail)

        binding.apply {
            rvFavuser.adapter = userAdapter
            rvFavuser.setHasFixedSize(true)
        }

        favUserViewModel.getFavUser().observe(this){
            if (!(it.isNullOrEmpty())){
                val list = mapList(it)
                userAdapter.users = list
                binding.apply {
                    imageview.visibility = View.GONE
                    tvNodata.visibility = View.GONE
                    rvFavuser.visibility = View.VISIBLE
                }
            }else{
                binding.apply {
                    imageview.visibility = View.VISIBLE
                    tvNodata.visibility = View.VISIBLE
                    rvFavuser.visibility = View.GONE
                }
            }
        }
    }

    private fun handleToDetail(user: User) {
        Intent(this, DetailActivity::class.java).also {
            it.putExtra(DetailActivity.EXTRA_USER, user)
            startActivity(it)
        }
    }

    private fun mapList(favusers: List<FavoriteUser>): MutableList<User> {
        val listUsers = mutableListOf<User>()

        for (i in favusers){
            val userMapped = User(
                i.id,
                i.avatar_url as String,
                i.username as String
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }
}