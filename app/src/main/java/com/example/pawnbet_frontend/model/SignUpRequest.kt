package com.example.pawnbet_frontend.model

data class SignUpRequest (
    val email: String,
    val username: String,
    val password: String,
    val profileImageUrl: String
)