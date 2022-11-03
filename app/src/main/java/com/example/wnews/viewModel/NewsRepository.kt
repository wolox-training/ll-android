package com.example.wnews.viewModel

import com.example.wnews.model.NewsData
import com.example.wnews.network.ApiBuilder
import com.example.wnews.network.NewsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class NewsRepository {
    val service: NewsApi =
        ApiBuilder("https://w-android-traning-backup2.herokuapp.com").create(NewsApi::class.java)

    suspend fun getNews(accessToken : String, uid:String, client:String ): Response<NewsData> =
        withContext(Dispatchers.IO) {
            service.news(mapOf("Access-Token" to accessToken), mapOf("Uid" to uid), mapOf("Client" to client))
        }
}

