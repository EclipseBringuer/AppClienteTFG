package com.grl.clientapptfg.ui.screens.tracking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grl.clientapptfg.core.Constants
import com.grl.clientapptfg.data.models.OrderModel
import com.grl.clientapptfg.data.repositories.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackingViewModel @Inject constructor(private val orderRepository: OrderRepository) :
    ViewModel() {
    private val _orders = MutableLiveData<List<OrderModel>>()
    val orders: LiveData<List<OrderModel>> = _orders

    private val _orderSelected = MutableLiveData<OrderModel>()
    val orderSelected: LiveData<OrderModel> = _orderSelected

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isFirst = MutableLiveData<Boolean>()
    val isFirst: LiveData<Boolean> = _isFirst

    private val _orderEvent = MutableLiveData<Boolean>()
    val orderEvent: LiveData<Boolean> = _orderEvent

    private val _orderOfEvent = MutableLiveData<OrderModel>()
    val orderOfEvent: LiveData<OrderModel> = _orderOfEvent

    fun changeIsFirst(boolean: Boolean) {
        _isFirst.value = boolean
    }

    fun changeOrderSelected(order: OrderModel) {
        _orderSelected.value = order
    }

    fun cleanOrderList() {
        _orders.value = listOf()
        _orderSelected.value = OrderModel(
            price = 0.0,
            paymentMethod = "",
            delivery = "",
            items = listOf(),
            state = "",
            user = null
        )
    }

    fun closeOrderEvent() {
        _orderEvent.value = false
    }

    /*fun getOrdersByUser(id: Int) {
        if (_isFirst.value!!) {
            _isLoading.value = true
        }
        viewModelScope.launch {
            try {
                _orders.value = orderRepository.getOrdersByUser(id)
                if (_isFirst.value!!) {
                    _orderSelected.value = _orders.value!![0]
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (_isFirst.value!!) {
                    _isLoading.value = false
                    _isFirst.value = false
                }
            }
        }
    }*/
    fun getOrdersByUser(id: Int) {
        if (_isFirst.value == true) {
            _isLoading.value = true
        }
        viewModelScope.launch {
            try {
                // Obtenemos los nuevos pedidos
                val newOrders = if (_isFirst.value == true) {
                    orderRepository.getAllNotCompleted(id)
                } else {
                    orderRepository.getOrdersByUser(id)
                }

                // Comparamos con los pedidos actuales
                _orders.value?.let { currentOrders ->
                    newOrders.forEach { newOrder ->
                        // Verificamos si el pedido ya está en la lista actual
                        val existingOrder = currentOrders.find { it.id == newOrder.id }
                        if (existingOrder != null) {
                            // Si el pedido está cancelado o completado, mostramos un diálogo y eliminamos de la lista
                            if (newOrder.state == Constants.CANCELED || newOrder.state == Constants.COMPLETED) {
                                showDismissDialog(newOrder)
                                _orders.value = currentOrders.filterNot { it.id == newOrder.id }
                            }
                        }
                    }
                }

                // Actualizamos la lista de pedidos
                _orders.value = newOrders

                // Si es la primera vez, seleccionamos el primer pedido
                if (_isFirst.value == true) {
                    _orderSelected.value = _orders.value?.firstOrNull()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (_isFirst.value == true) {
                    _isLoading.value = false
                    _isFirst.value = false
                }
            }
        }
    }

    private fun showDismissDialog(order: OrderModel) {
        _orderOfEvent.value = order
        _orderEvent.value = true
    }
}