package com.example.pawnbet_frontend.model

data class WishlistResponse (
    val id: Long,
    val user: UserProfileResponse,
    val product: ProductResponse
)