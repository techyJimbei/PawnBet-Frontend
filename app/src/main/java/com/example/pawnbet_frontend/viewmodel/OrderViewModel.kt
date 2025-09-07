package com.example.pawnbet_frontend.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawnbet_frontend.api.ApiEndPoints
import com.example.pawnbet_frontend.jwt.TokenManager
import com.example.pawnbet_frontend.model.OrderResponse
import com.example.pawnbet_frontend.model.PaymentRequest
import com.example.pawnbet_frontend.model.ProductResponse
import kotlinx.coroutines.launch
import java.math.BigDecimal

class OrderViewModel(
    private val api: ApiEndPoints,
    private val tokenManager: TokenManager
): ViewModel() {

    private val _orders = mutableStateOf<List<OrderResponse>>(emptyList())
    val orders: State<List<OrderResponse>> = _orders

    private val _winnnings = mutableStateOf<List<ProductResponse>>(emptyList())
    val winnings: State<List<ProductResponse>> = _winnnings

    private val _clientSecret = mutableStateOf<String?>(null)
    val clientSecret: State<String?> = _clientSecret

    private val _paymentError = mutableStateOf<String?>(null)
    val paymentError: State<String?> = _paymentError

    fun createPaymentIntent(amountInPaise: Int, onSuccess: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val token = tokenManager.getToken() ?: ""
                val response = api.createPaymentIntent("Bearer $token", PaymentRequest(amountInPaise))
                if (response.isSuccessful) {
                    response.body()?.get("clientSecret")?.let { secret ->
                        onSuccess(secret)
                    }
                } else {
                    Log.e("Payment", "Error: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("Payment", "Exception: ${e.message}")
            }
        }
    }



    fun clearPaymentStates() {
        _clientSecret.value = null
        _paymentError.value = null
    }

    fun getOrders(){
        viewModelScope.launch {
            try{
                val token = "Bearer ${tokenManager.getToken() ?: ""}"
                val response = api.getOrders(token)
                if(response.isSuccessful && response.body() != null){
                    _orders.value = response.body() !!
                    Log.e("Order", "Order list received")
                    Log.e("Order", "Orders count = ${_orders.value.size}")

                }
                else{
                    Log.e("Order", "Order list is null")
                }
            }
            catch(e: Exception){
                Log.e("Order", "Exception occurred ${e.message}")
            }
        }

    }

    fun addPayment(productId: Long) {
        viewModelScope.launch {
            try {
                val token = "Bearer ${tokenManager.getToken() ?: ""}"
                val response = api.addPayment(token, productId)

                if (response.isSuccessful && response.body() != null) {
                    val updatedOrder = response.body()!!

                    _orders.value = _orders.value.map { order ->
                        if (order.product.id == productId) updatedOrder else order
                    }

                    Log.e("Order", "Payment successful for productId = $productId")
                } else {
                    Log.e("Order", "Payment failed: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("Order", "Exception in addPayment: ${e.localizedMessage}")
            }
        }
    }


    fun getWinningAuction(){
        viewModelScope.launch {
            try{
                val token = "Bearer ${tokenManager.getToken() ?: ""}"
                val response = api.getWinningAuctions(token)
                if(response.isSuccessful && response.body() != null){
                    _winnnings.value = response.body()!!
                    Log.e("Order", "Winnings list received")
                    Log.e("Order", "List Count ${_winnnings.value.size}")
                }
                else{
                    Log.e("Order", "Order list is null")
                }
            }
            catch(e: Exception){
                Log.e("Order", "Exception Occurred ${e.message}")
            }
        }
    }
}