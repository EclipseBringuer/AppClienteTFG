package com.grl.clientapptfg.ui.screens.user_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grl.clientapptfg.core.UserSession
import com.grl.clientapptfg.data.models.UserModel
import com.grl.clientapptfg.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {
    private val _closeSession = MutableLiveData<Boolean>()
    val closeSession: LiveData<Boolean> = _closeSession

    private val _phone = MutableLiveData<String>()
    val phone: LiveData<String> = _phone

    private val _address = MutableLiveData<String>()
    val address: LiveData<String> = _address

    private val _isUpdateEnable = MutableLiveData<Boolean>()
    val isUpdateEnable: LiveData<Boolean> = _isUpdateEnable

    private val _isEditing = MutableLiveData<Boolean>()
    val isEditing: LiveData<Boolean> = _isEditing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _wantToUpdate = MutableLiveData<Boolean>()
    val wantToUpdate: LiveData<Boolean> = _wantToUpdate

    private val _updateWasGood = MutableLiveData<Boolean>()
    val updateWasGood: LiveData<Boolean> = _updateWasGood

    fun setPhone(newText: String) {
        _phone.value = newText.trim()
    }

    fun setAddress(newText: String) {
        _address.value = newText
    }

    fun changeIsEditing(boolean: Boolean) {
        _isEditing.value = boolean
    }

    fun changeWantToUpdate(boolean: Boolean) {
        _wantToUpdate.value = boolean
    }

    fun changeUpdateWasGood(boolean: Boolean) {
        _updateWasGood.value = boolean
    }

    fun enableUpdate(address: String, phone: String) {
        _isUpdateEnable.value =
            phone.length == 9 && address.length > 5
    }

    fun changeCloseSession(boolean: Boolean) {
        _closeSession.value = boolean
    }

    fun updateUser(address: String, phone: String, user: UserModel) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                var updatedUser = user
                updatedUser.phone = phone
                updatedUser.address = address
                updatedUser = userRepository.updateUser(updatedUser)
                if (updatedUser.id != 0) {
                    UserSession.setUser(updatedUser)
                    setPhone(updatedUser.phone)
                    setAddress(updatedUser.address)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
                _updateWasGood.value = true
            }
        }
    }
}