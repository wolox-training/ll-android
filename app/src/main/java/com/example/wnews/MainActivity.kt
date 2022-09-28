package com.example.wnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import MainViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wnews.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel : MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding= ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.apply {

            val firstNameInput = firstName.editableText.toString()
            val secondNameInput= secondName.editableText.toString()

            loginButton.setOnClickListener{mainViewModel.setLogin(firstNameInput, secondNameInput)
                if(firstName.editableText.isEmpty()) {
                    firstName.setError("This field cannot be empty.")
                    if (firstNameInput.contains("@") == true) {

                    } else firstName.setError("Please, enter a valid email.")
                }

                if(secondName.editableText.isEmpty()) {
                    secondName.setError("This field cannot be empty.")
                }
            }
        }

        setObserve()

    }



    fun setObserve(){
        mainViewModel.newLoginModel.observe(this@MainActivity) { model->
            println(model.firstName)
        }
    }
}