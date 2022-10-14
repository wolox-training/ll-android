package com.example.wnews

import LoginViewModel
import com.example.wnews.viewModel.LoginRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.BeforeEach


class ExampleUnitTest {
    lateinit var loginRepository : LoginRepository

    @Before
    fun setUp() {
        loginRepository = LoginRepository()
    }
    @Test
    fun test1(){
        runBlocking { val test = loginRepository.loginWithUser("test", "")
        val result = !test.isSuccessful
            assert(result)
        }

    }

}