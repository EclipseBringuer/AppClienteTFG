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

    init {
        _isFirst.value = true
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
                // Actualizamos los pedidos actuales
                updateOrders(newOrders)
                // Seleccionamos el primer pedido si es la primera vez y la lista no está vacía
                if (_isFirst.value == true && _orders.value!!.isNotEmpty()) {
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

    private fun updateOrders(newOrders: List<OrderModel>) {
        _orders.value?.let { currentOrders ->
            val updatedOrders = currentOrders.toMutableList()
            newOrders.forEach { newOrder ->
                val existingOrder = currentOrders.find { it.id == newOrder.id }
                if (existingOrder != null) {
                    handleExistingOrder(newOrder, existingOrder, updatedOrders)
                } else if (newOrder.state != Constants.CANCELED && newOrder.state != Constants.COMPLETED) {
                    updatedOrders.add(newOrder)
                }
            }
            _orders.value = updatedOrders
        } ?: run {
            _orders.value = newOrders
        }
    }

    private fun handleExistingOrder(newOrder: OrderModel, existingOrder: OrderModel, updatedOrders: MutableList<OrderModel>) {
        if (newOrder.state == Constants.CANCELED || newOrder.state == Constants.COMPLETED) {
            showDismissDialog(newOrder)
            updatedOrders.remove(existingOrder)
        } else {
            val index = updatedOrders.indexOf(existingOrder)
            updatedOrders[index] = newOrder
        }
    }

    private fun showDismissDialog(order: OrderModel) {
        _orderOfEvent.value = order
        _orderEvent.value = true
    }
}