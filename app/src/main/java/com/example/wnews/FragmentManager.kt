package com.example.wnews

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PageAdapter(fm:FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 2;
    }

    override fun getItem(position: Int): Fragment {
        when(position) {
            0 -> {
                return NewsFragment()
            }
            1 -> {
                return ProfileFragment()
            }
            else -> {
                return NewsFragment()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> {
                return "News"
            }
            1 -> {
                return "Profile"
            }
        }
        return super.getPageTitle(position)
    }
}
