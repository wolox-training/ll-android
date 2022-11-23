package com.example.wnews.viewModel

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.wnews.model.NewsData
import kotlinx.coroutines.launch

class NewsViewModel(private val app: Application) : AndroidViewModel(app) {
    lateinit var sharedPreferences: SharedPreferences
        val apiNewsResult: MutableLiveData<NewsData> = MutableLiveData()
        var isPressedFav : MutableLiveData<Boolean> = MutableLiveData(false)
        val pressedNewsId : MutableLiveData<Int> = MutableLiveData()
        val userId : MutableLiveData<Int> = MutableLiveData()
        val response = NewsRepository()

        fun getNews() {
            sharedPreferences =
                app.getSharedPreferences(SHARED_PREFS, AppCompatActivity.MODE_PRIVATE)
            val token = sharedPreferences.getString(ACCESS_TOKEN, "").toString()
            val uid = sharedPreferences.getString(UID, "").toString()
            val client = sharedPreferences.getString(CLIENT, "").toString()
            val id = sharedPreferences.getInt(USER_ID, 0)
            userId.value = id
            viewModelScope.launch {
                val apiResult = response.getNews(accessToken = token, uid = uid, client = client)

                if (apiResult.code() == 200) {
                    val noNewsToView = null
                    val data = apiResult.body()
                    apiNewsResult.value = data!!
                } else if (apiResult.code() == 401) {
                    println(UNAUTHORIZED)

                } else println(ERROR)
            }
        }

        fun getLikes(newsId: Int) {
            sharedPreferences =
                app.getSharedPreferences(SHARED_PREFS, AppCompatActivity.MODE_PRIVATE)
            val token = sharedPreferences.getString(ACCESS_TOKEN, "").toString()
            val uid = sharedPreferences.getString(UID, "").toString()
            val client = sharedPreferences.getString(CLIENT, "").toString()
            var newsId = newsId
            pressedNewsId.value = newsId

            viewModelScope.launch {
                val apiResult = response.getLikes(accessToken = token, uid = uid, client = client, newsId = newsId)
            }
        }

    companion object {
        private val SHARED_PREFS = "sharedPrefs"
        private val ACCESS_TOKEN = "Access-Token"
        private val UID = "Uid"
        private val CLIENT = "Client"
        private val USER_ID = "id"
        private val UNAUTHORIZED = "Unauthorized"
        private val ERROR = "No news to view"
    }
    }




