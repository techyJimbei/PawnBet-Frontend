package com.example.pawnbet_frontend.model

import java.math.BigDecimal

data class ProductResponse (
    val id: Long,
    var title: String,
    var description: String,
    var tag: String?,
    var basePrice: BigDecimal,
    val productStatus: ProductStatus?,
    val auctionStatus: AuctionStatus?,
    val seller: UserProfileResponse,
    var imageUrl: String?,
    val createdAt: String?,
    val isWishlisted: Boolean
)