package com.haidar.githubusersearch.ui.adapters

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.haidar.githubusersearch.ui.fragments.FollowFragment
import com.haidar.githubusersearch.ui.fragments.FollowingFragment


class SectionsPagerAdapter(activity: AppCompatActivity, val username: String) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        fragment = FollowFragment()
        when (position) {
            0 -> fragment = FollowFragment()
            1 -> fragment = FollowingFragment()
        }
        val bundle = Bundle()
        bundle.putString("username", username)
        fragment?.arguments = bundle
        return fragment as Fragment
    }

}