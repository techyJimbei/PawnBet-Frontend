package com.example.pawnbet_frontend.model

import java.math.BigDecimal

data class ProductResponse (
    val id: Long,
    val title: String,
    val description: String,
    val tag: String?,
    val basePrice: BigDecimal,
    val productStatus: ProductStatus?,
    val auctionStatus: AuctionStatus?,
    val seller: UserProfileResponse,
    val imageUrl: String,
    val createdAt: String
)