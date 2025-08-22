package com.example.pawnbet_frontend.model

import java.math.BigDecimal

data class AuctionResponse(
    val id: Long,
    val productId: Long,
    val basePrice: BigDecimal,
    val winningBidder: UserProfileResponse,
    val auctionStartTime: String,
    val auctionEndTime: String,
)
