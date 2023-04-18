package com.dicoding.gitu.follows

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dicoding.gitu.R

class FollowsFragment : Fragment() {
    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
    }
    override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tvLabel: TextView = view.findViewById(R.id.section_label)
        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)

        tvLabel.text = getString(R.string.content_tab, index)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_follows, container, false)
    }
}