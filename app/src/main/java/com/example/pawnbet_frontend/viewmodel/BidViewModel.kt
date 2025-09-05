package com.example.pawnbet_frontend.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawnbet_frontend.api.ApiEndPoints
import com.example.pawnbet_frontend.jwt.TokenManager
import com.example.pawnbet_frontend.model.BidRequest
import com.example.pawnbet_frontend.model.BidResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BidViewModel(
    private val api: ApiEndPoints,
    private val tokenManager: TokenManager
): ViewModel() {

    private val _bids = mutableStateOf<List<BidResponse>>(emptyList())
    val bids: State<List<BidResponse>> = _bids

    private val _highest = MutableStateFlow<BidResponse?>(null)
    val highest: StateFlow<BidResponse?> = _highest

    fun raiseBid(id: Long?, request: BidRequest){

        viewModelScope.launch {
            try{
                val token = "Bearer ${tokenManager.getToken() ?: ""}"
                val response = api.raiseBid(token, request, id)

                if(response.isSuccessful && response.body() != null){
                    val currentBid = response.body() !!
                    _bids.value = listOf(currentBid) + _bids.value

                    Log.e("Bid", "Bid Raised Successfully")
                }
                else{
                    Log.e("Bid", "Bid cannot be raised")
                }
            }
            catch(e: Exception){
                Log.e("Bid", "Exception occurred ${e.message}")
            }
        }
    }

    fun getHighestBid(productId: Long){
        viewModelScope.launch {
            try{
                val token = "Bearer ${tokenManager.getToken() ?: ""}"
                val response = api.getHighestBid(token, productId)

                if(response.isSuccessful && response.body() != null){
                    _highest.value = response.body() !!
                    Log.e("Bid", "Highest Bid Received")
                }
                else{
                    Log.e("Bid", "Highest Bid is null")
                }
            }
            catch(e: Exception){
                Log.e("Bid", "Exception occurred ${e.message}")
            }
        }
    }

    fun getBids(id: Long) {
        viewModelScope.launch {
            try {
                Log.d("Bid", "Fetching bids for product id: $id")
                val token = "Bearer ${tokenManager.getToken() ?: ""}"
                val response = api.getBids(token, id)

                Log.d("Bid", "Raw response code: ${response.code()}")
                Log.d("Bid", "Raw response message: ${response.message()}")

                // Log raw response body as string (for debugging only)
                val rawBody = response.errorBody()?.string()
                if (rawBody != null) {
                    Log.e("Bid", "Error body: $rawBody")
                } else {
                    Log.d("Bid", "No error body, response likely successful")
                }

                val body = response.body()
                Log.d("Bid", "Parsed body: $body")  // Will print null if parsing fails

                if (response.isSuccessful && body != null) {
                    _bids.value = body
                    Log.i("Bid", "✅ Bids received: ${body.size} items")
                    body.forEachIndexed { index, bid ->
                        Log.i("Bid", "Bid[$index] => id=${bid.id}, amount=${bid.bidAmount}, bidder=${bid.bidder}, product=${bid.product}")
                    }
                } else {
                    Log.w("Bid", "⚠️ Bid data is null or response unsuccessful")
                }
            } catch (e: Exception) {
                Log.e("Bid", "❌ Exception occurred: ${e.message}", e)
            }
        }
    }

}