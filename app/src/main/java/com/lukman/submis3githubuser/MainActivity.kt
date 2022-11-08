package com.lukman.submis3githubuser

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.lukman.submis3githubuser.adapter.UserAdapter
import com.lukman.submis3githubuser.databinding.ActivityMainBinding
import com.lukman.submis3githubuser.model.User
import com.lukman.submis3githubuser.viewmodel.MyViewModelFactory
import com.lukman.submis3githubuser.viewmodel.SettingViewModel
import com.lukman.submis3githubuser.viewmodel.UserViewModel
import com.lukman.submis3githubuser.viewmodel.ViewModelFactory
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: UserAdapter
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userAdapter = UserAdapter(::handleToDetail)
        userViewModel = ViewModelProvider(
            this,
            MyViewModelFactory.getInstance(this.application)
        )[UserViewModel::class.java]

        binding.etSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
            var jobSearch: Job? = null

            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                showLoading()
                binding.textView.visibility = View.GONE
                binding.imageView.visibility = View.GONE
                jobSearch?.cancel()
                if (p0 != null && p0.isNotEmpty()){
                    jobSearch = coroutineScope.launch{
                        delay(200)
                        userViewModel.setUsers(p0.toString().trim())
                        getDataUsers()
                        binding.rvUser.visibility = View.VISIBLE
                        binding.textView.visibility = View.GONE
                        binding.imageView.visibility = View.GONE
                        hideLoading()
                    }
                }else{
                    binding.rvUser.visibility = View.GONE
                    binding.textView.visibility = View.VISIBLE
                    binding.imageView.visibility = View.VISIBLE
                    hideLoading()
                }
                return false
            }

        })

        getThemeSettings()
    }

    private fun getThemeSettings() {

        val pref = SettingPreferences.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(this, ViewModelFactory(pref))[SettingViewModel::class.java]

        settingViewModel.getThemeSetting().observe(this){
            if (it){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun handleToDetail(user: User) {
        Intent(this, DetailActivity::class.java).also {
            it.putExtra(DetailActivity.EXTRA_USER, user)
            startActivity(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu1 -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu2 -> {
                val intent = Intent(this, FavUserActivity::class.java)
                startActivity(intent)
                true
            }
            else -> true
        }
    }

    private fun getDataUsers() {
        showLoading()

        userViewModel.getUsers().observe(this@MainActivity) {

            when {
                it.size == 0 -> {
                    hideLoading()
                }
                it != null -> {
                    userAdapter.users = it
                    binding.rvUser.adapter = userAdapter
                    hideLoading()
                }
                else -> {
                    hideLoading()
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.emptyData),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun showLoading(){
        binding.apply {
            loading.visibility = View.VISIBLE
        }
    }

    private fun hideLoading(){
        binding.apply {
            loading.visibility = View.GONE
        }
    }
}