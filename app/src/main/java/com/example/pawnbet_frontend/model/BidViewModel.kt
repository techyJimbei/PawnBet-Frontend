package com.example.pawnbet_frontend.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawnbet_frontend.api.ApiEndPoints
import com.example.pawnbet_frontend.jwt.TokenManager
import kotlinx.coroutines.launch

class BidViewModel(
    private val api: ApiEndPoints,
    private val tokenManager: TokenManager
): ViewModel() {

    fun raiseBid(bidRequest: BidRequest){
        viewModelScope.launch {
            try{
                val token = "Bearer ${tokenManager.getToken() ?: ""}"
                val response = api.raiseBid(token, bidRequest)
                if(response.isSuccessful && response.body() != null){
                    Log.e("Bid", "Bid Raised")
                }
                else{
                    Log.e("Bid", "Bid Cannot be Raised")
                }
            }
            catch(e: Exception){
                Log.e("Bid", "Exception occurred ${e.message}")
            }
        }
    }
}