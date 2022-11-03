package com.example.wnews

import LoginViewModel
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Bundle
import android.view.View
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
                val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
                val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

                if (isConnected) {
                    mainViewModel.apiLogin(
                        binding.firstName.editableText.toString(),
                        binding.secondName.editableText.toString()
                    )


                } else {
                    Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        mainViewModel.userLogged()

        mainViewModel.userIsAuth.observe(this) {
            if (it == true) {
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
                false -> {
                    Toast.makeText(this, getString(R.string.failed_login), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        mainViewModel.apiLoading.observe(this) {
            when (it) {
                true -> {
                    binding.loadingSpinner.visibility = View.VISIBLE
                }
                false -> {
                    binding.loadingSpinner.visibility = View.GONE
                }
            }
        }

        mainViewModel.headersList.observe(this) {
            val token = it.get(0)
            val uid = it.get(1)
            val client = it.get(2)
            saveHeaders(token, uid, client)
        }

        mainViewModel.userId.observe(this) {
                saveId(it)
        }
    }

    fun saveHeaders(token: String, uid: String, client: String) {
        val sharedPreferences = this.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.putString(ACCESS_TOKEN, token)
        editor.putString(UID, uid)
        editor.putString(CLIENT, client)
        editor.apply()
    }

    fun saveData(firstName: String, secondName: String) {
        val sharedPreferences = this.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_EMAIL, firstName)
        editor.putString(PASSWORD, secondName)
        editor.apply()
    }

    fun saveId( id : Int) {
        val sharedPreferences = this.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(USER_ID, id)

        editor.apply()
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
        private val ACCESS_TOKEN = "Access-Token"
        private val UID = "Uid"
        private val CLIENT = "Client"
        private val USER_ID = "id"
    }
}