package com.example.wnews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wnews.databinding.FragmentNewsBinding
import com.example.wnews.model.News
import com.example.wnews.viewModel.NewsViewModel


class NewsFragment : Fragment()  {

    lateinit var binding: FragmentNewsBinding
    lateinit var mainViewModel: NewsViewModel
    lateinit var adapter : NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainViewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel
        binding.listRecyclerview
        binding.listRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        mainViewModel.getNews()

        val data = ArrayList<News>()
        println(data)

        val onCellClickListener = { pressed: News ->
            mainViewModel.isPressedFav.value = !mainViewModel.isPressedFav.value!!
           val newTest =mainViewModel.apiNewsResult.value?.data?.find { new: News -> new == pressed }?.commenter
            Unit
        }

        adapter = NewsAdapter(data, UserId = 0, cellClickListener = onCellClickListener )

        mainViewModel.userId.observe(viewLifecycleOwner) {
            adapter.updateUser(it)
        }

        mainViewModel.apiNewsResult.observe(this.viewLifecycleOwner) {
            if (it != null) {
                adapter.updateData(it.data as ArrayList<News>)
                binding.listRecyclerview.adapter?.notifyDataSetChanged()
            }
        }
        binding.listRecyclerview.adapter = adapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = true
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }
}
