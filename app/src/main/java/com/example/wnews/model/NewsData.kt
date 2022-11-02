package com.example.wnews.model


import com.google.gson.annotations.SerializedName

data class NewsData(
    @SerializedName("page")
    val data: List<News>
)

data class News(
    val id: Int,
    val commenter: String,
    val comment: String,
    val date: String,
    val avatar: String,
    val likes: List<Int>,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
)