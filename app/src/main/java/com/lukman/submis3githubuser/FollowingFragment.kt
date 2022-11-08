package com.lukman.submis3githubuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.lukman.submis3githubuser.adapter.UserAdapter
import com.lukman.submis3githubuser.databinding.FragmentFollowingBinding
import com.lukman.submis3githubuser.model.User
import com.lukman.submis3githubuser.viewmodel.FollowingViewModel
import com.lukman.submis3githubuser.viewmodel.MyViewModelFactory

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding ? = null
    private val binding get() = _binding as FragmentFollowingBinding
    private lateinit var userAdapter: UserAdapter
    private lateinit var followingViewModel: FollowingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        userAdapter = UserAdapter(::handle)
        followingViewModel = ViewModelProvider(
            this,
            MyViewModelFactory.getInstance(activity?.application)
        )[FollowingViewModel::class.java]

        getDataFollowing()
    }

    private fun getDataFollowing() {
        binding.apply {
            loading.visibility = View.VISIBLE
            rvFollowing.visibility = View.GONE

            rvFollowing.adapter = userAdapter
            rvFollowing.setHasFixedSize(true)
        }

        val username = arguments?.getString(DetailActivity.EXTRA_USER).toString()

        followingViewModel.apply {
            setFollowing(username, "following")
            getFollowing().observe(viewLifecycleOwner){
                if (it != null) {
                    userAdapter.users = it
                    binding.apply {
                        loading.visibility = View.GONE
                        rvFollowing.visibility = View.VISIBLE
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