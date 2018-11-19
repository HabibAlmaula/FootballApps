package com.example.mediacenterfkam.footballappssubs_2.FragmentMain


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mediacenterfkam.footballappssubs_2.Adapter.ViewPagerAdapter

import com.example.mediacenterfkam.footballappssubs_2.R
import kotlinx.android.synthetic.main.fragment_main_match.*


class MainMatch : Fragment() {

    private lateinit var adapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ViewPagerAdapter(childFragmentManager)
        viewpagerMatch.adapter = adapter
        tabs.setupWithViewPager(viewpagerMatch)
    }




}
