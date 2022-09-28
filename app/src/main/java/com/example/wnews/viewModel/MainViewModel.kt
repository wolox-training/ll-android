
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wnews.model.LoginModel


class MainViewModel : ViewModel() {
    private val loginModel: MutableLiveData<LoginModel> = MutableLiveData()
    val newLoginModel: LiveData<LoginModel> = loginModel
    fun setLogin (firstName: String, secondName: String) {
        loginModel.value= LoginModel(firstName, secondName)
        }
    }



