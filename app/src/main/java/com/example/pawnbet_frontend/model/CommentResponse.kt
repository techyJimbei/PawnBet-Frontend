package com.example.pawnbet_frontend.model

data class CommentResponse(
    val id: Long,
    val text: String?,
    val user: UserProfileResponse,
    val createdAt: String?,
    val replies: List<CommentResponse> = emptyList()
)
