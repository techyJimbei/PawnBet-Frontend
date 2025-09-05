package com.example.pawnbet_frontend.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawnbet_frontend.api.ApiEndPoints
import com.example.pawnbet_frontend.jwt.TokenManager
import com.example.pawnbet_frontend.model.AuctionRequest
import com.example.pawnbet_frontend.model.AuctionResponse
import kotlinx.coroutines.launch

class AuctionViewModel(
    private val api: ApiEndPoints,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _auctionDetails = mutableStateOf<AuctionResponse?>(null)
    val auctionDetails: State<AuctionResponse?> = _auctionDetails

    private val _auctionDetailsMap = mutableStateOf<Map<Long?, AuctionResponse>>(emptyMap())
    val auctionDetailsMap: State<Map<Long?, AuctionResponse>> = _auctionDetailsMap


    fun addAuctionDetails(productId: Long?, auctionRequest: AuctionRequest){
        viewModelScope.launch {
            try{
                val token = "Bearer ${tokenManager.getToken() ?: ""}"
                val response = api.addAuctionDetails(token, auctionRequest, productId)

                if(response.isSuccessful && response.body() != null){
                    val details = response.body()!!

                    _auctionDetailsMap.value = _auctionDetailsMap.value + (productId to details)
                    _auctionDetails.value = details
                    Log.e("Auction", "Auction details added")
                }
                else{
                    Log.e("Auction", "Auction details cannot be added")
                }
            }
            catch(e: Exception){
                Log.e("Auction", "Exception occurred ${e.message}")
            }
        }
    }

    fun getAuctionDetails(productId: Long) {
        viewModelScope.launch {
            try {
                val token = "Bearer ${tokenManager.getToken() ?: ""}"
                val response = api.getAuctionDetails(token, productId)
                if (response.isSuccessful && response.body() != null) {
                    val details = response.body()!!
                    _auctionDetailsMap.value = _auctionDetailsMap.value + (productId to details)
                    _auctionDetails.value = details
                    Log.e("Auction", "Auction data received for product $productId")
                } else {
                    Log.e("Auction", "Auction data is null for product $productId")
                }
            } catch (e: Exception) {
                Log.e("Auction", "Cannot retrieve data: ${e.message}")
            }
        }
    }




}