package com.lukman.submis3githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.lukman.submis3githubuser.adapter.SectionPagerAdapter
import com.lukman.submis3githubuser.databinding.ActivityDetailBinding
import com.lukman.submis3githubuser.model.User
import com.lukman.submis3githubuser.util.loadImage
import com.lukman.submis3githubuser.viewmodel.DetUserViewModel
import com.lukman.submis3githubuser.viewmodel.MyViewModelFactory
import kotlinx.coroutines.*
import java.lang.StringBuilder
import java.util.concurrent.Executors

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailUserViewModel: DetUserViewModel
    private lateinit var sectionPagerAdapter: SectionPagerAdapter
    private var bundle: Bundle ?= null
    private lateinit var user: User
    private val dispatchers = Executors.newCachedThreadPool().asCoroutineDispatcher()
    private val scope = CoroutineScope(dispatchers)
    private var isChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bundle = Bundle()

        detailUserViewModel = ViewModelProvider(
            this,
            MyViewModelFactory.getInstance(this.application)
        )[DetUserViewModel::class.java]

        sectionPagerAdapter = SectionPagerAdapter(this, bundle)

        intent?.let{
            user = it.getParcelableExtra<User>(EXTRA_USER) as User
            val usename = user.username
            supportActionBar?.title = usename

            detailUserViewModel.setDetailUser(usename)
            getDetailUser()

        }

        setPages()
        sendBundle()
        checkFavUser()
        clickFavUser()
    }

    private fun clickFavUser() {
        binding.toggleFav.setOnClickListener {
            isChecked = !isChecked

            scope.launch {
                if (isChecked){
                    detailUserViewModel.insertFavUser(user.id, user.avatar, user.username)
                }else{
                    detailUserViewModel.deleteFavUser(user.id)
                }
                withContext(Dispatchers.Main){
                    binding.toggleFav.isChecked = isChecked
                }
            }
        }
    }

    private fun checkFavUser() {
        scope.launch {
            val count = detailUserViewModel.checkedUser(user)

            withContext(Dispatchers.Main){
                if (count>0){
                    binding.toggleFav.isChecked = true
                    isChecked = true
                }else{
                    binding.toggleFav.isChecked = false
                    isChecked = false
                }
            }
        }
    }

    private fun sendBundle() {
        user = intent?.getParcelableExtra<User>(EXTRA_USER) as User
        bundle?.putString(EXTRA_USER, user.username)
    }

    private fun setPages() {
        binding.apply {
            vpDetail.adapter = sectionPagerAdapter

            TabLayoutMediator(tabLayout, vpDetail){tab, position->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
        supportActionBar?.elevation = 0f
    }

    private fun getDetailUser() {
        showLoading()
        detailUserViewModel.getDetailUser().observe(this){
            if (it != null){
                binding.apply {
                    civAvatar.loadImage(it.avatar_url)
                    tvUsername.text = StringBuilder("@").append(it.login)
                    tvName.text = it.name
                    tvRepo.text =  it.public_repos.toString()+"\nRepositories"
                    tvFollowers.text = it.followers.toString()+"\nFollowers"
                    tvFollowing.text = it.following.toString()+"\nFollowing"
                    hideLoading()
                }
            }
        }
    }

    private fun showLoading() {
        binding.loading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.loading.visibility = View.GONE
    }

    companion object {
        const val EXTRA_USER = "extra_user"
        private val TAB_TITLES = intArrayOf(R.string.tab1, R.string.tab2, R.string.tab3)
    }
}