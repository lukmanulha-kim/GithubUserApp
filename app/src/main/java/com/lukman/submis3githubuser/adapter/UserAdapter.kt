package com.lukman.submis3githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lukman.submis3githubuser.util.DivUsersCallback
import com.lukman.submis3githubuser.adapter.UserAdapter.*
import com.lukman.submis3githubuser.databinding.UserItemBinding
import com.lukman.submis3githubuser.util.loadImage
import com.lukman.submis3githubuser.model.User

class UserAdapter(val handleToDetail: ((User))->Unit) : RecyclerView.Adapter<UserViewHolder>() {

    var users = mutableListOf<User>()
        set(value) {
            val diffCallback = DivUsersCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field.clear()
            field.addAll(value)
            diffResult.dispatchUpdatesTo(this)
        }

    inner class UserViewHolder(private val binding: UserItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User){
            binding.apply {
                civUser.loadImage(user.avatar)
                tvUsername.text = user.username
            }

            binding.toDetail.setOnClickListener {
                handleToDetail.invoke(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size
}