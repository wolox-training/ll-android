package com.example.wnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import MainViewModel
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import com.example.wnews.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel : MainViewModel
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding= ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.apply {


            loginButton.setOnClickListener{
                println("user:"+ loadData())
                val firstNameInput = firstName.editableText.toString()
                val secondNameInput= secondName.editableText.toString()

                mainViewModel.setLogin(firstNameInput, secondNameInput)

                if(loginValidation(firstNameInput, secondNameInput)){
                    // fun save userData()
                    val intent = Intent(this@MainActivity, HomeActivity::class.java)

                    startActivity(intent)
                }
            }
            fun initHome(){
                loginButton.setOnClickListener { mainViewModel.setLogin(firstName =String(), secondName =String()) }
            }

        }

        setObserve()

    }

    fun loginValidation(firstNameInput: String, secondNameInput: String): Boolean{
        if(firstNameInput.isEmpty()) {
            binding.firstName.error = "This field cannot be empty."
            return false
        }

        if (firstNameInput.isNotEmpty() && !firstNameInput.contains("@")) {
            binding.firstName.error = "Please, enter a valid email."
            return false
        }

        if(secondNameInput.isEmpty()) {
            binding.secondName.error = "This field cannot be empty."
            return false
        }
        return true
    }



    fun setObserve(){
        mainViewModel.newLoginModel.observe(this@MainActivity) { model->
            println(model.firstName)
        }
    }


    private val SHARED_PREFS = "sharedPrefs"
    private val KEY_EMAIL = "keyEmail"
    private val PASSWORD = "password"


    fun saveData(firstName: String, secondName:String) {
        val sharedPreferences = this.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_EMAIL , firstName)
        editor.putString(PASSWORD , secondName)

        editor.apply()
    }

    fun loadData(): String? {
        val sharedPreferences =
            this.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
        return sharedPreferences.getString(KEY_EMAIL, "")
    }

    fun loadDataPassword(): String? {
        val sharedPreferences =
            this.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
        return sharedPreferences.getString(PASSWORD, "")
    }





}

