package com.example.wnews

import MainViewModel
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.wnews.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.apply {


            loginButton.setOnClickListener {
                val firstNameInput = firstName.editableText.toString()
                val secondNameInput = secondName.editableText.toString()

                mainViewModel.setLogin(firstNameInput, secondNameInput)

                mainViewModel.validateFirstName(firstNameInput)

                mainViewModel.validateSecondName(secondNameInput)

                mainViewModel.successValidations()


            }
        }

        setObserve()

    }

    fun setObserve() {
        mainViewModel.errorFirstName.observe(this) {
            if (it) {
                binding.firstName.error = getString(R.string.empty_validation)
            }
        }

        mainViewModel.errorSecondName.observe(this) {
            if (it) {
                binding.secondName.error = getString(R.string.empty_validation)
            }
        }

        mainViewModel.valditionsResult.observe(this) {
            if (it) {
                saveData(
                    binding.firstName.editableText.toString(),
                    binding.secondName.editableText.toString()
                )
                with(Intent(this, HomeActivity::class.java)) {
                    startActivity(this)

                }

                finish()
            }
        }

    }


    fun saveData(firstName: String, secondName: String) {
        val sharedPreferences = this.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_EMAIL, firstName)
        editor.putString(PASSWORD, secondName)

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

    companion object {
        private val SHARED_PREFS = "sharedPrefs"
        private val KEY_EMAIL = "keyEmail"
        private val PASSWORD = "password"
    }


}

