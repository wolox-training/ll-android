package com.example.wnews

import LoginViewModel
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.wnews.databinding.ActivityMainBinding
import com.example.wnews.viewModel.SignUpActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var mainViewModel: LoginViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mainViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

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
        binding.signupButton.setOnClickListener {
            with(Intent(this, SignUpActivity::class.java)) {
                startActivity(this)
            }
        }

        binding.footer.setOnClickListener {
            openWebPage(getString(R.string.wolox_page))
        }
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
                mainViewModel.apiLogin(
                    binding.firstName.editableText.toString(),
                    binding.secondName.editableText.toString()
                )
            }
        }

        mainViewModel.userLogged()

        mainViewModel.userIsAuth.observe(this){
            if(it == true){
                with(Intent(this, HomeActivity::class.java)) {
                    startActivity(this)
                }
            }
        }

        mainViewModel.apiResult.observe(this) {
            when (it) {
                true -> {
                    with(Intent(this, HomeActivity::class.java)) {
                        startActivity(this)
                    }
                }

                 false -> { Toast.makeText(this, getString(R.string.failed_login), Toast.LENGTH_SHORT).show() }

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

    fun openWebPage(url: String) {
        val webpage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    companion object {
        private val SHARED_PREFS = "sharedPrefs"
        private val KEY_EMAIL = "keyEmail"
        private val PASSWORD = "password"
    }


}