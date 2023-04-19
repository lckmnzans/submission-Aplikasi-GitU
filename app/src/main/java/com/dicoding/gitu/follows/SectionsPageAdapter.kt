package com.dicoding.gitu.follows

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.gitu.DetailActivity

class SectionsPageAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        //menentukan berapa tab yang akan dibuat
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowsFragment()
        fragment.arguments = Bundle().apply {
            //mengirim data integer ke tujuan ARG_SECTION_NUMBER di FollowsFragment
            putInt(FollowsFragment.ARG_POSITION, position + 1)
            //mengirim data string ke tujuan EXTRA_USERNAME di FollowsFragment
            putString(FollowsFragment.ARG_USERNAME, DetailActivity.EXTRA_USERNAME)
        }
        return fragment
    }
}