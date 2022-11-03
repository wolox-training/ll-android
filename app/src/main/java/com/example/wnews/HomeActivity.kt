package com.example.wnews

import LoginViewModel
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.wnews.databinding.ActivityHomeBinding
import com.example.wnews.databinding.ActivityMainBinding
import com.example.wnews.model.News
import com.example.wnews.viewModel.NewsViewModel
import com.google.android.material.tabs.TabLayout


class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    lateinit var mainViewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        mainViewModel = ViewModelProvider(this)[NewsViewModel::class.java]
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