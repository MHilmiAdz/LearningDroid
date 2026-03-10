package com.example.learningdroid.tablayout

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.learningdroid.bottomnav.FragmentHome
import com.example.learningdroid.bottomnav.FragmentProfile

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FragmentHome()
            1 -> fragment = FragmentProfile()
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}