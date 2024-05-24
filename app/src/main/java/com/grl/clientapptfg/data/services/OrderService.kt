package com.grl.clientapptfg.data.services

import com.grl.clientapptfg.core.Constants
import com.grl.clientapptfg.data.clients.OrderClient
import com.grl.clientapptfg.data.models.OrderModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OrderService @Inject constructor(private val orderClient: OrderClient) {
    suspend fun createNewOrder(newOrder: OrderModel): OrderModel {
        return withContext(Dispatchers.IO) {
            val response = orderClient.createOrder(Constants.TOKEN, newOrder)
            response.body()!!
        }
    }

    suspend fun getOrdersByUser(userId:Int):List<OrderModel>{
        return withContext(Dispatchers.IO){
            val response = orderClient.getOrdersByUser(Constants.TOKEN, userId)
            response.body()!!
        }
    }

    suspend fun getAllNotCompleted(userId: Int): List<OrderModel> {
        return withContext(Dispatchers.IO) {
            val response = orderClient.getAllNotCompleted(Constants.TOKEN, userId)
            response.body()!!
        }
    }
}