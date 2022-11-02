package com.example.wnews.viewModel

import com.example.wnews.model.LoginData
import com.example.wnews.model.LoginModel
import com.example.wnews.network.ApiBuilder
import com.example.wnews.network.LoginApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class LoginRepository {
    val service: LoginApi =
        ApiBuilder("https://w-android-traning-backup2.herokuapp.com").create(LoginApi::class.java)

    suspend fun loginWithUser(email: String, password: String): Response<LoginData> =
        withContext(Dispatchers.IO) {
            service.login(LoginModel(email, password))
        }
}