package com.example.pawnbet_frontend.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawnbet_frontend.api.ApiEndPoints
import com.example.pawnbet_frontend.jwt.TokenManager
import com.example.pawnbet_frontend.model.ProductResponse
import kotlinx.coroutines.launch

class ProductViewModel(
    private val api: ApiEndPoints,
    private val tokenManager: TokenManager
): ViewModel() {

    private val _products = mutableStateOf<List<ProductResponse>>(emptyList())
    val products: State<List<ProductResponse>> = _products

    private val _searchProducts = mutableStateOf<List<ProductResponse>>(emptyList())
    val searchProducts: State<List<ProductResponse>> = _searchProducts

    fun getAllProduct(){
        viewModelScope.launch {
            try{
                val token = "Bearer ${tokenManager.getToken() ?: ""}"
                val response = api.getAllProducts(token)
                if(response.isSuccessful && response.body() != null){
                    _products.value = response.body()!!
                    Log.e("Product", "Product data received")
                }
                else{
                    Log.e("Product", "Product data is null")
                }
            }
            catch(e: Exception){
                Log.e("Product", "Cannot retrieve data"+e.message)
            }
        }
    }

    fun getSearchProduct(keyword: String){
        viewModelScope.launch {
            try{
                val token = "Bearer ${tokenManager.getToken() ?: ""}"
                val response = api.getSearchProducts(token, keyword)
                if(response.isSuccessful && response.body() != null){
                    _searchProducts.value = response.body()!!
                    Log.e("Product", "Search Product data received")
                }
                else{
                    Log.e("Product", "Search Product data is null")
                }
            }
            catch(e: Exception){
                Log.e("Product", "Cannot retrieve data"+e.message)
            }
        }
    }

}