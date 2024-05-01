package com.grl.clientapptfg.data.services

import com.grl.clientapptfg.data.clients.LoginClient
import com.grl.clientapptfg.data.models.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginService @Inject constructor(private val loginClient: LoginClient) {
    suspend fun doLogin(gmail: String, password: String): UserModel {
        return withContext(Dispatchers.IO) {
            val response = loginClient.doLogin(gmail, password)
            response.body()!!
        }
    }
}