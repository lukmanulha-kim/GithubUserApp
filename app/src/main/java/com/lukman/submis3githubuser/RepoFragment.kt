package com.lukman.submis3githubuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.lukman.submis3githubuser.adapter.RepoAdapter
import com.lukman.submis3githubuser.databinding.FragmentRepoBinding
import com.lukman.submis3githubuser.viewmodel.MyViewModelFactory
import com.lukman.submis3githubuser.viewmodel.RepoViewModel

class RepoFragment : Fragment() {

    private var _binding: FragmentRepoBinding ?= null
    private val binding get() = _binding as FragmentRepoBinding
    private lateinit var repoAdapter: RepoAdapter
    private lateinit var repoViewModel: RepoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        repoAdapter = RepoAdapter()
        repoViewModel = ViewModelProvider(
            this,
            MyViewModelFactory.getInstance(activity?.application)
        )[RepoViewModel::class.java]
        
        getDataRepo()
    }

    private fun getDataRepo() {
        binding.apply { 
            loading.visibility = View.VISIBLE
            rvRepo.visibility = View.GONE
            
            rvRepo.adapter = repoAdapter
            rvRepo.setHasFixedSize(true)
        }
        
        val username = arguments?.getString(DetailActivity.EXTRA_USER).toString()
        
        repoViewModel.apply { 
            setRepositpry(username)
            getRepository().observe(viewLifecycleOwner){
                if (it != null){
                    repoAdapter.repos = it
                    binding.apply { 
                        loading.visibility = View.GONE
                        rvRepo.visibility = View.VISIBLE
                    }
                }else{
                    Toast.makeText(activity, "Failed Load Data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}