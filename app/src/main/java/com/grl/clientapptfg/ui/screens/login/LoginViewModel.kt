package com.grl.clientapptfg.ui.screens.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grl.clientapptfg.core.UserSession
import com.grl.clientapptfg.data.repositories.UserRepository
import com.grl.clientapptfg.utils.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _isVisible = MutableLiveData<Boolean>()
    val isVisible: LiveData<Boolean> = _isVisible

    private val _isLoginEnable = MutableLiveData<Boolean>()
    val isLoginEnable: LiveData<Boolean> = _isLoginEnable

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _logedIsBad = MutableLiveData<Boolean>()
    val logedIsBad: LiveData<Boolean> = _logedIsBad

    private val _dividerText = MutableLiveData<String>()
    val dividerText: LiveData<String> = _dividerText

    fun changeVisibility(visibility: Boolean) {
        _isVisible.value = !visibility
    }

    fun setEmail(newText: String) {
        _email.value = newText.trim()
        _logedIsBad.value = false
        changeDividerText(true)
    }

    fun setPassword(newText: String) {
        _password.value = newText.trim()
        _logedIsBad.value = false
        changeDividerText(true)
    }

    fun enableLogin(emailText: String, passwordText: String) {
        _isLoginEnable.value =
            Patterns.EMAIL_ADDRESS.matcher(emailText).matches() && passwordText.length > 6
    }

    private fun changeDividerText(boolean: Boolean) {
        _dividerText.value = if (boolean) {
            "O"
        } else {
            "Error al iniciar Sesión"
        }
    }

    private fun changeLogedIsBad(boolean: Boolean) {
        _logedIsBad.value = !boolean
    }

    fun getUser(onLoginSuccess: (Boolean) -> Unit) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val user =
                    userRepository.doLogin(_email.value!!, Util.hashPassword(_password.value!!))
                if (user.id != 0) {
                    UserSession.setUser(user)
                }
                changeDividerText(user.id != 0)
                changeLogedIsBad(user.id != 0)
                onLoginSuccess(user.id != 0)
            } catch (e: Exception) {
                print(e.stackTrace)
            } finally {
                _isLoading.value = false
            }
        }
    }
}