package com.example.mediacenterfkam.footballappssubs_2.Adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.mediacenterfkam.footballappssubs_2.FragmentMatch.CurrentMatch
import com.example.mediacenterfkam.footballappssubs_2.FragmentMatch.NextMatch


class ViewPagerAdapter(fragmentManager: FragmentManager?) : FragmentPagerAdapter(fragmentManager){
    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> {
                CurrentMatch()
            }
            else -> NextMatch()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 ->"Current Match"
            else -> "Next Match"

        }

    }
}