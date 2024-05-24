package com.grl.clientapptfg.data.repositories

import com.grl.clientapptfg.data.models.OrderModel
import com.grl.clientapptfg.data.services.OrderService
import javax.inject.Inject

class OrderRepository @Inject constructor(private val api: OrderService) {
    suspend fun createNewOrder(newOrder: OrderModel): OrderModel {
        return api.createNewOrder(newOrder)
    }

    suspend fun getOrdersByUser(id: Int): List<OrderModel> {
        return api.getOrdersByUser(id)
    }

    suspend fun getAllNotCompleted(userId: Int): List<OrderModel> {
        return api.getAllNotCompleted(userId)
    }
}