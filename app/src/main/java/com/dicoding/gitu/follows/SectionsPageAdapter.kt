package com.dicoding.gitu.follows

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPageAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowsFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowsFragment.ARG_SECTION_NUMBER, position + 1)
        }
        return fragment
    }
}