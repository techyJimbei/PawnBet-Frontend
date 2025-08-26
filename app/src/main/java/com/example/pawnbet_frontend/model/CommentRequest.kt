package com.example.pawnbet_frontend.model

data class CommentRequest(
    val text: String,
    val productId: Long,
    val parentId: Long? = null
)