package com.example.wnews

import android.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = PageAdapter(supportFragmentManager)

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)

        tabLayout.getTabAt(0)!!.setIcon(R.mipmap.ic_news_list_on)

        tabLayout.getTabAt(1)!!.setIcon(R.mipmap.ic_profile_on)

    }
}




