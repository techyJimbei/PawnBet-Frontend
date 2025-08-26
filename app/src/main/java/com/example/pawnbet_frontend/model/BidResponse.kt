package com.example.pawnbet_frontend.model

import java.math.BigDecimal

data class BidResponse(
    val id: Long,
    val bidAmount: BigDecimal,
    val bidder: UserProfileResponse,
    val product: ProductResponse,
    val accepted: Boolean,
    val createdAt: String
)
