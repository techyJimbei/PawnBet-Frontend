package com.example.pawnbet_frontend.model

data class OrderResponse (
    val id: Long,
    val product: ProductResponse,
    val winningBid: BidResponse,
    val isPaid: Boolean
)