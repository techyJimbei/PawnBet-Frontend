package com.example.pawnbet_frontend.model

data class UserProfileResponse (
    val id: Long,
    val username: String,
    val email: String,
    val imageBase64: String
)