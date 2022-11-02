package com.example.wnews.network

import com.example.wnews.model.NewsData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap

interface NewsApi {
    @GET("/comments/")
    suspend fun news(
        @HeaderMap token: Map<String, String>,
        @HeaderMap Uid: Map<String, String>,
        @HeaderMap Client: Map<String, String>,
    ): Response<NewsData>
}