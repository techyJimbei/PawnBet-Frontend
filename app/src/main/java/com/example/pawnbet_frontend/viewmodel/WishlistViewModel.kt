package com.example.pawnbet_frontend.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawnbet_frontend.api.ApiEndPoints
import com.example.pawnbet_frontend.jwt.TokenManager
import com.example.pawnbet_frontend.model.WishlistRequest
import com.example.pawnbet_frontend.model.WishlistResponse
import kotlinx.coroutines.launch

class WishlistViewModel(
    private val api: ApiEndPoints,
    private val tokenManager: TokenManager
) : ViewModel() {


    private val _products = mutableStateOf<List<WishlistResponse>>(emptyList())
    val products: State<List<WishlistResponse>> = _products

    fun addWishlistProduct(productId: Long) {
        viewModelScope.launch {
            try {
                val token = "Bearer ${tokenManager.getToken() ?: ""}"
                val request = WishlistRequest(productId = productId)
                val response = api.addWishlistProduct(token, request)

                if (response.isSuccessful) {
                    Log.d("Wishlist", "Wishlist added successfully")
                } else {
                    Log.e("Wishlist", "Failed to add wishlist: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("Wishlist", "Cannot add to wishlist ${e.message}")
            }
        }
    }

    fun getWishlistProducts() {
        viewModelScope.launch {
            try {
                val token = "Bearer ${tokenManager.getToken() ?: ""}"
                val response = api.getWishlistProducts(token)

                if (response.isSuccessful && response.body() != null) {
                    _products.value = response.body()!!
                    Log.d("Wishlist", "Wishlist data received")
                } else {
                    Log.e(
                        "Wishlist",
                        "Wishlist data is null or failed: ${response.errorBody()?.string()}"
                    )
                }
            } catch (e: Exception) {
                Log.e("Wishlist", "Runtime exception occurred ${e.message}")
            }
        }
    }

    fun deleteWishlistProduct(id: Long) {
        viewModelScope.launch {
            try {
                val token = "Bearer ${tokenManager.getToken() ?: ""}"
                val response = api.deleteWishlistProduct(token, id)

                if (response.isSuccessful) {
                    Log.d("Wishlist", "Wishlist deleted successfully")
                } else {
                    Log.e(
                        "Wishlist",
                        "Failed to delete wishlist: ${response.errorBody()?.string()}"
                    )
                }
            } catch (e: Exception) {
                Log.e("Wishlist", "Wishlist cannot be deleted: ${e.message}")
            }
        }
    }
}

