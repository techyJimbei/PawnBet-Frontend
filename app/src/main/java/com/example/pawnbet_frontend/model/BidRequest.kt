package com.example.pawnbet_frontend.model

import java.math.BigDecimal

data class BidRequest(
    val bidAmount: BigDecimal,
    val productId: Long
)
