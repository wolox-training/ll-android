import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wnews.model.LoginModel


class LoginViewModel : ViewModel() {
    private val loginModel: MutableLiveData<LoginModel> = MutableLiveData()
    val errorFirstName: MutableLiveData<Boolean> = MutableLiveData(false)
    val errorSecondName: MutableLiveData<Boolean> = MutableLiveData(false)
    val valditionsResult: MutableLiveData<Boolean> = MutableLiveData(false)

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
}


