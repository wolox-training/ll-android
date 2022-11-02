package com.example.wnews.viewModel

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.wnews.databinding.NewsItemBinding
import com.example.wnews.model.NewsData
import kotlinx.coroutines.launch

class NewsViewModel(private val app: Application) : AndroidViewModel(app) {
    lateinit var sharedPreferences: SharedPreferences
        val apiNewsResult: MutableLiveData<NewsData> = MutableLiveData()
        var isPressedFav : MutableLiveData<Boolean> = MutableLiveData(false)
        val userId : MutableLiveData<Int> = MutableLiveData()
        val response = NewsRepository()

        fun getNews() {
            sharedPreferences =
                app.getSharedPreferences("sharedPrefs", AppCompatActivity.MODE_PRIVATE)
            val token = sharedPreferences.getString("Access-Token", "").toString()
            val uid = sharedPreferences.getString("Uid", "").toString()
            val client = sharedPreferences.getString("Client", "").toString()
            val id = sharedPreferences.getInt("id", 0)
            userId.value = id
            viewModelScope.launch {
                val apiResult = response.getNews(accessToken = token, uid = uid, client = client)

                if (apiResult.code() == 200) {
                    val noNewsToView = null
                    val data = apiResult.body()
                    apiNewsResult.value = data!!
                } else if (apiResult.code() == 401) {
                    println("Unauthorized")

                } else println("No news to view")
            }
        }
}



