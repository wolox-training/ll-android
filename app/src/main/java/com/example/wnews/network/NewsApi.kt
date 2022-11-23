
package com.example.wnews.network
import com.example.wnews.model.ApiMessage
import com.example.wnews.model.LikesData
import com.example.wnews.model.NewsData
import retrofit2.Response
import retrofit2.http.*


interface NewsApi {
    @GET("/comments/")
    suspend fun news(
        @HeaderMap token: Map<String, String>,
        @HeaderMap Uid: Map<String, String>,
        @HeaderMap Client: Map<String, String>,
    ): Response<NewsData>

    @PUT("/comments/like/{id}")
    suspend fun likes(
        @HeaderMap token: Map<String, String>,
        @HeaderMap Uid: Map<String, String>,
        @HeaderMap Client: Map<String, String>,
        @Path("id") NewsId : Int
    ): Response<ApiMessage>

    @GET("/comments/")
    suspend fun getLikes(
        @HeaderMap token: Map<String, String>,
        @HeaderMap Uid: Map<String, String>,
        @HeaderMap Client: Map<String, String>,
    ): Response<LikesData>


}