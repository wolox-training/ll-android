package com.example.wnews.network

import com.example.wnews.model.LoginData
import com.example.wnews.model.LoginModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.net.CacheResponse

interface LoginApi {
    @POST("/auth/sign_in")
    suspend fun login (@Body loginModel: LoginModel) : Response <LoginData>

}

