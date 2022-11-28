package com.example.wnews

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wnews.databinding.FragmentNewsBinding
import com.example.wnews.model.News
import com.example.wnews.viewModel.NewsViewModel
import okhttp3.internal.notify


class NewsFragment : Fragment() {

    lateinit var binding: FragmentNewsBinding
    lateinit var mainViewModel: NewsViewModel
    lateinit var adapter: NewsAdapter2


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


        val CellClickListener = { pressedNews: News ->
            mainViewModel.getLikes(pressedNews.id)
            val pressedNewsId : Int? = mainViewModel.pressedNewsId.value
            if (pressedNewsId != null) {
                mainViewModel.pressedNewsId.value?.let { adapter.updateLikes(it) }
                mainViewModel.updateOk.observe(this.viewLifecycleOwner) {
                    if (it) {
                        adapter.updateView()
                    }
                }
            }

         //   val pressedNewsId =
            //    mainViewModel.apiNewsResult.value?.data?.find { new: News -> new == pressedNews }?.id
          //  println(pressedNewsId)
        }



        adapter = NewsAdapter2(data, cellClickListener = CellClickListener)



        mainViewModel.pressedNewsId.observe(this.viewLifecycleOwner){
            adapter.run{
                adapter.updateData(data)
                getItemCount()
                getItemViewType(itemCount)
                println("holaaaaaaaaaa")
            }
        }

        mainViewModel.userId.observe(this.viewLifecycleOwner) {
            adapter.updateUser(it)
        }

        mainViewModel.apiNewsResult.observe(this.viewLifecycleOwner) {
            if (it != null) {
                adapter.updateData(it.data)
                binding.listRecyclerview.adapter?.notifyDataSetChanged()
            }
        }



        binding.listRecyclerview.adapter = adapter


        val cm = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

        if (isConnected) {
            binding.swipeRefreshLayout.setOnRefreshListener {
                binding.swipeRefreshLayout.isRefreshing = true
                mainViewModel.getNews()
                binding.swipeRefreshLayout.isRefreshing = false
            }

        } else {
            Toast.makeText(requireContext(), getString(R.string.no_internet), Toast.LENGTH_SHORT)
                .show()
        }
    }
}



