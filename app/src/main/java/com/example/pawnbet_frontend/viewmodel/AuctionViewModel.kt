package com.example.pawnbet_frontend.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawnbet_frontend.api.ApiEndPoints
import com.example.pawnbet_frontend.jwt.TokenManager
import com.example.pawnbet_frontend.model.AuctionResponse
import com.example.pawnbet_frontend.model.ProductResponse
import kotlinx.coroutines.launch

class AuctionViewModel(
    private val api: ApiEndPoints,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _auctionDetails = mutableStateOf<AuctionResponse?>(null)
    val auctionDetails: State<AuctionResponse?> = _auctionDetails

    fun getAuctionDetails(productResponse: ProductResponse?){
        viewModelScope.launch {
            try{
                val token = "Bearer ${tokenManager.getToken() ?: ""}"
                val response = api.getAuctionDetails(token, productResponse)
                if(response.isSuccessful && response.body() != null){
                    _auctionDetails.value = response.body()!!
                    Log.e("Auction", "Auction data received")
                }
                else{
                    Log.e("Auction", "Auction data is null")
                }
            }
            catch(e: Exception){
                Log.e("Auction", "Cannot retrieve data"+e.message)
            }
        }
    }


}