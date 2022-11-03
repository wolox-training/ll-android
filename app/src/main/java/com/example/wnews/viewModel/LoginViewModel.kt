import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.wnews.model.LoginModel
import com.example.wnews.viewModel.LoginRepository
import kotlinx.coroutines.launch


class LoginViewModel (private val app:Application ): AndroidViewModel(app) {
    private val loginModel: MutableLiveData<LoginModel> = MutableLiveData()
    val errorFirstName: MutableLiveData<Boolean> = MutableLiveData(false)
    val errorSecondName: MutableLiveData<Boolean> = MutableLiveData(false)
    val valditionsResult: MutableLiveData<Boolean> = MutableLiveData(false)
    val apiResult: MutableLiveData<Boolean> = MutableLiveData()
    val headersList : MutableLiveData<List<String>> = MutableLiveData()
    val userId : MutableLiveData<Int> = MutableLiveData()
    val apiLoading: MutableLiveData<Boolean> = MutableLiveData()
    val repository: LoginRepository = LoginRepository()
    lateinit var sharedPreferences : SharedPreferences
    val userIsAuth : MutableLiveData<Boolean> = MutableLiveData(null)


    fun userLogged (){
        sharedPreferences =  app.getSharedPreferences(SHARED_PREFS, AppCompatActivity.MODE_PRIVATE)
        val userEmail =sharedPreferences.getString(KEY_EMAIL, "")
        val userPassword =sharedPreferences.getString(PASSWORD, "")
        if(userEmail!!.isNotEmpty() && userPassword!!.isNotEmpty()) {
            userIsAuth.value = true
        }
    }


    fun setLogin(firstName: String, secondName: String) {
        loginModel.value = LoginModel(firstName, secondName)
    }

        fun validateFirstName(firstName: String) {
            if (firstName.isEmpty()) {
                errorFirstName.value = true
            }

            if (!firstName.contains("@")) {
                errorFirstName.value = true
            }
        }

        fun validateSecondName(firstName: String) {
            if (firstName.isEmpty()) {
                errorSecondName.value = true
            }

        }

        fun successValidations() {
            if (errorFirstName.value == false && errorSecondName.value == false) {
                valditionsResult.value = true
            }
        }

    fun apiLogin(email: String, password: String) {
        apiLoading.value = true
        viewModelScope.launch {
            val result = repository.loginWithUser(email, password)
            apiResult.value = result.isSuccessful
            apiLoading.value = false


            val accessToken = result.headers().get(name = ACCESS_TOKEN).toString()
            val uid = result.headers().get(name = UID).toString()
            val client = result.headers().get(name = CLIENT).toString()
            val id = result.body()!!.data.id
            userId.value = id

            headersList.value = listOf(accessToken, uid, client)

        }
    }
    companion object {
        private val SHARED_PREFS = "sharedPrefs"
        private val KEY_EMAIL = "keyEmail"
        private val PASSWORD = "password"
        private val ACCESS_TOKEN = "Access-Token"
        private val UID = "Uid"
        private val CLIENT = "Client"
    }
}



