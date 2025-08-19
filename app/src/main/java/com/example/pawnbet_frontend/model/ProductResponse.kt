package com.example.pawnbet_frontend.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class ProductResponse (
    val id: Long,
    val title: String,
    val description: String,
    val tag: String?,
    val basePrice: BigDecimal,
    val productStatus: ProductStatus?,
    val auctionStatus: AuctionStatus?,
    val seller: UserProfileResponse,
    val imageUrls: List<String>,
    val createdAt: String
)