package com.lukman.submis3githubuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.lukman.submis3githubuser.adapter.UserAdapter
import com.lukman.submis3githubuser.databinding.FragmentFollowersBinding
import com.lukman.submis3githubuser.model.User
import com.lukman.submis3githubuser.viewmodel.FollowersViewModel
import com.lukman.submis3githubuser.viewmodel.MyViewModelFactory

class FollowersFragment : Fragment() {

    private var _binding: FragmentFollowersBinding ?= null
    private val binding get() = _binding as FragmentFollowersBinding
    private lateinit var userAdapter: UserAdapter
    private lateinit var followersViewModel: FollowersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      userAdapter = UserAdapter(::handle)
      followersViewModel = ViewModelProvider(
          this,
          MyViewModelFactory.getInstance(activity?.application)
      )[FollowersViewModel::class.java]

      getDataFollowers()
    }

    private fun getDataFollowers() {
        binding.apply {
            loading.visibility = View.VISIBLE
            rvFollowers.visibility = View.GONE

            rvFollowers.adapter = userAdapter
            rvFollowers.setHasFixedSize(true)
        }

        val username = arguments?.getString(DetailActivity.EXTRA_USER).toString()

        followersViewModel.apply {
            setFollowers(username, "followers")
            getFollowers().observe(viewLifecycleOwner){
                if (it != null){
                    userAdapter.users = it
                    binding.apply {
                        loading.visibility = View.GONE
                        rvFollowers.visibility = View.VISIBLE
                    }
                }else{
                    Toast.makeText(activity, "Failed Load Data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun handle(user: User): Boolean {
        return false
    }
}