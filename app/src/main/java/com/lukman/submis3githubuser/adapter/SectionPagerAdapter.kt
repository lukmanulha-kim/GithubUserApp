package com.lukman.submis3githubuser.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lukman.submis3githubuser.FollowersFragment
import com.lukman.submis3githubuser.FollowingFragment
import com.lukman.submis3githubuser.RepoFragment

class SectionPagerAdapter(activity: AppCompatActivity, private val fragmentBundle: Bundle?):
    FragmentStateAdapter(activity){
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = RepoFragment()
            1 -> fragment = FollowersFragment()
            2 -> fragment = FollowingFragment()
        }

        fragment?.arguments = fragmentBundle

        return fragment as Fragment
    }
}