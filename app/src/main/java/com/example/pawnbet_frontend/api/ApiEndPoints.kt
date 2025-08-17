package com.example.pawnbet_frontend.api

import com.example.pawnbet_frontend.model.LoginRequest
import com.example.pawnbet_frontend.model.LoginResponse
import com.example.pawnbet_frontend.model.SignUpRequest
import com.example.pawnbet_frontend.model.UserProfileResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiEndPoints {

    @POST("/api/auth/signup")
    suspend fun signup(@Body request: SignUpRequest): Response<Void>

    @POST("/api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("/api/auth/profile")
    suspend fun getUserProfile(@Header("Authorization") token: String): Response<UserProfileResponse>


}