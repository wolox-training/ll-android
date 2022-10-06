package com.example.wnews.model

import com.google.gson.annotations.SerializedName

data class LoginData(
    @SerializedName("data")
    val data: Data
)

data class Data(
    @SerializedName("id")
    val id: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("provider")
    val provider: String,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("allow_password_change")
    val allow_password_change: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("image")
    val image: String,
    )



