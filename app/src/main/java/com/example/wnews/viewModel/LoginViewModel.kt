import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wnews.model.LoginData
import com.example.wnews.model.LoginModel
import com.example.wnews.viewModel.LoginRepository
import kotlinx.coroutines.launch


class LoginViewModel : ViewModel() {
    private val loginModel: MutableLiveData<LoginModel> = MutableLiveData()
    val errorFirstName: MutableLiveData<Boolean> = MutableLiveData(false)
    val errorSecondName: MutableLiveData<Boolean> = MutableLiveData(false)
    val valditionsResult: MutableLiveData<Boolean> = MutableLiveData(false)
    val apiResult: MutableLiveData<Boolean> = MutableLiveData(false)
    val repository: LoginRepository = LoginRepository()


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
        viewModelScope.launch {
            val result = repository.loginWithUser(email, password)
            if (result.isSuccessful) {
                apiResult.value = true
            }
        }
    }
}


