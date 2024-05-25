package com.grl.clientapptfg.ui.screens.create_account

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grl.clientapptfg.core.UserSession
import com.grl.clientapptfg.data.models.UserModel
import com.grl.clientapptfg.data.repositories.UserRepository
import com.grl.clientapptfg.utils.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateAccountViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _createAccountFailed = MutableLiveData<Boolean>()
    val createAccountFailed: LiveData<Boolean> = _createAccountFailed

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _phone = MutableLiveData<String>()
    val phone: LiveData<String> = _phone

    private val _address = MutableLiveData<String>()
    val addres: LiveData<String> = _address

    private val _isVisible = MutableLiveData<Boolean>()
    val isVisible: LiveData<Boolean> = _isVisible

    private val _isEnable = MutableLiveData<Boolean>()
    val isEnable: LiveData<Boolean> = _isEnable

    private val _createAccountSuccess = MutableLiveData<Boolean>()
    val createAccountSuccess: LiveData<Boolean> = _createAccountSuccess

    fun changeVisibility(visibility: Boolean) {
        _isVisible.value = !visibility
    }

    fun changeAccountSuccess(boolean: Boolean) {
        _createAccountSuccess.value = boolean
    }

    fun changeAccountFailed(boolean: Boolean) {
        _createAccountFailed.value = boolean
    }

    fun setEmail(text: String) {
        _email.value = text
    }

    fun setPassword(text: String) {
        _password.value = text
    }

    fun setPhone(text: String) {
        _phone.value = text
    }

    fun setAddress(text: String) {
        _address.value = text
    }

    fun setName(text: String) {
        _name.value = text
    }

    fun enableButton(
        email: String,
        password: String,
        phone: String,
        address: String,
        name: String
    ) {
        _isEnable.value =
            Patterns.EMAIL_ADDRESS.matcher(email)
                .matches() && password.length > 6 && phone.length == 9 && name.length > 3 && address.length > 5
    }

    fun cleanFields() {
        _name.value = ""
        _email.value = ""
        _phone.value = ""
        _address.value = ""
        _password.value = ""
    }

    fun createNewUser(onCreateSucces: (Boolean) -> Unit) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                var user = UserModel(
                    name = _name.value!!,
                    gmail = _email.value!!,
                    phone = _phone.value!!,
                    password = Util.hashPassword(_password.value!!),
                    address = _address.value!!
                )
                user = userRepository.createNewUser(user)
                if (user.id != 0) {
                    cleanFields()
                }
                onCreateSucces(user.id != 0)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}