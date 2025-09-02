package com.example.pawnbet_frontend.model

import java.math.BigDecimal

data class ProductRequest(
    val title: String,
    val description: String,
    val tag: String,
    val basePrice: BigDecimal,
    val imageUrl: String
)
