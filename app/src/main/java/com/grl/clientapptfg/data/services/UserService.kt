package com.grl.clientapptfg.data.services

import com.grl.clientapptfg.core.Constants
import com.grl.clientapptfg.data.clients.UserClient
import com.grl.clientapptfg.data.models.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserService @Inject constructor(private val userClient: UserClient) {
    suspend fun doLogin(gmail: String, password: String): UserModel {
        return withContext(Dispatchers.IO) {
            val response = userClient.doLogin(gmail, password, Constants.TOKEN)
            response.body()!!
        }
    }

    suspend fun createNewUser(newUser: UserModel): UserModel {
        return withContext(Dispatchers.IO) {
            val response = userClient.createUser(Constants.TOKEN, newUser)
            response.body()!!
        }
    }

    suspend fun updateUser(updatedUser: UserModel): UserModel {
        return withContext(Dispatchers.IO) {
            val response = userClient.updateUser(Constants.TOKEN, updatedUser)
            response.body()!!
        }
    }
}