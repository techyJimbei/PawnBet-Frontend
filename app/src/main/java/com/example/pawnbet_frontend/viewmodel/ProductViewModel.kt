package com.example.pawnbet_frontend.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawnbet_frontend.api.ApiEndPoints
import com.example.pawnbet_frontend.jwt.TokenManager
import com.example.pawnbet_frontend.model.ProductRequest
import com.example.pawnbet_frontend.model.ProductResponse
import com.example.pawnbet_frontend.model.WishlistRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel(
    private val api: ApiEndPoints,
    private val tokenManager: TokenManager
): ViewModel() {

    private val _products = mutableStateOf<List<ProductResponse>>(emptyList())
    val products: State<List<ProductResponse>> = _products

    private val _myProducts = mutableStateOf<List<ProductResponse>>(emptyList())
    val myProducts: State<List<ProductResponse>> = _myProducts

    private val _searchProducts = mutableStateOf<List<ProductResponse>>(emptyList())
    val searchProducts: State<List<ProductResponse>> = _searchProducts

    private val _selectedProduct = MutableStateFlow<ProductResponse?>(null)
    val selectedProduct: StateFlow<ProductResponse?> = _selectedProduct

    private val _auctionSelectedProduct = MutableStateFlow<ProductResponse?>(null)
    val auctionSelectedProduct: StateFlow<ProductResponse?> = _auctionSelectedProduct

    fun selectProduct(productResponse: ProductResponse){
        _selectedProduct.value = productResponse
    }

    fun auctionSelectProduct(productResponse : ProductResponse){
        _auctionSelectedProduct.value = productResponse
    }

    private val _startTime = mutableStateOf("")
    val startTime: State<String> = _startTime

    private val _endTime = mutableStateOf("")
    val endTime: State<String> = _endTime

    fun setStartTime(time: String) {
        _startTime.value = time
    }

    fun setEndTime(time: String) {
        _endTime.value = time
    }


    fun toggleWishlist(product: ProductResponse) {
        viewModelScope.launch {
            try {
                val token = "Bearer ${tokenManager.getToken() ?: ""}"

                if (product.isWishlisted) {
                    val response = api.deleteWishlistProduct(token, product.id)
                    if (response.isSuccessful) {
                        _products.value = _products.value.map {
                            if (it.id == product.id) it.copy(isWishlisted = false) else it
                        }
                        Log.e("Product", "Wishlist removed for product ${product.id}")
                    } else {
                        Log.e("Product", "Failed to remove from wishlist: ${response.errorBody()?.string()}")
                    }
                } else {
                    val response = api.addWishlistProduct(token, WishlistRequest(product.id))
                    if (response.isSuccessful) {
                        _products.value = _products.value.map {
                            if (it.id == product.id) it.copy(isWishlisted = true) else it
                        }
                        Log.e("Product", "Wishlist added for product ${product.id}")
                    } else {
                        Log.e("Product", "Failed to add to wishlist: ${response.errorBody()?.string()}")
                    }
                }

            } catch (e: Exception) {
                Log.e("Product", "Wishlist toggle failed: ${e.message}")
            }
        }
    }

    fun listProduct(request: ProductRequest){
        viewModelScope.launch {
            try{
                val token = "Bearer ${tokenManager.getToken() ?: ""}"
                val response = api.listProduct(token, request)
                if(response.isSuccessful){
                    val newProduct = response.body() !!
                    _myProducts.value = _myProducts.value + newProduct
                    Log.e("Product", "Product added successfully")
                }
                else{
                    Log.e("Product", "Product cannot be added")
                }
            }
            catch(e: Exception){
                Log.e("Product", "Exception occurred ${e.message}")
            }
        }
    }

    fun getMyProducts(){
        viewModelScope.launch {
            try{
                val token = "Bearer ${tokenManager.getToken() ?: ""}"
                val response = api.getMyProducts(token)
                if(response.isSuccessful && response.body() != null){
                    _myProducts.value = response.body() !!
                    Log.e("Product", "My Product list received")
                }
                else{
                    Log.e("Product", "My Product list is empty")
                }
            }
            catch(e: Exception){
                Log.e("Product", "Exception Occurred ${e.message}")
            }
        }
    }

    fun getAllProduct(){
        viewModelScope.launch {
            try{
                val token = "Bearer ${tokenManager.getToken() ?: ""}"
                val response = api.getAllProducts(token)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        _products.value = body
                        Log.e("Product", "Product data received: ${body.size} items")
                    } else {
                        Log.e("Product", "Response successful but body is null")
                    }
                } else {
                    Log.e("Product", "Error fetching products: ${response.code()} - ${response.message()} - ${response.errorBody()?.string()}")
                }

            }
            catch(e: Exception){
                Log.e("Product", "Cannot retrieve data"+e.message)
            }
        }
    }

    fun editProduct(request: ProductRequest, id: Long){
        viewModelScope.launch {
            try{
                val token = "Bearer ${tokenManager.getToken() ?: ""}"
                val response = api.editProduct(token, request, id)
                if(response.isSuccessful && response.body() != null){
                    val updatedProduct = response.body()!!

                    _products.value = _products.value.map {
                        if (it.id == id) updatedProduct else it
                    }

                    _myProducts.value = _myProducts.value.map {
                        if (it.id == id) updatedProduct else it
                    }
                    Log.e("Product", "Product edited successfully")
                }
                else{
                    Log.e("Product", "product cannot be edited")
                }
            }
            catch(e: Exception){
                Log.e("product", "Exception occurred" + e.message)
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