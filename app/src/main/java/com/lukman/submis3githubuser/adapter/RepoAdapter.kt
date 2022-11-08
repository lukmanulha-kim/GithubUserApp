package com.lukman.submis3githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukman.submis3githubuser.databinding.RepoItemBinding
import com.lukman.submis3githubuser.model.RepoResponse

class RepoAdapter : RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {

    var repos = mutableListOf<RepoResponse>()

    inner class RepoViewHolder(private val binding: RepoItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(repo: RepoResponse){
            binding.apply {
                tvReponame.text = repo.name
                tvRepourl.text = repo.full_name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val binding = RepoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(repos[position])
    }

    override fun getItemCount(): Int = repos.size
}