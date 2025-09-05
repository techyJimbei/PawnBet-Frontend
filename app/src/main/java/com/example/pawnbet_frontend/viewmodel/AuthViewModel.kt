package com.example.pawnbet_frontend.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawnbet_frontend.api.ApiEndPoints
import com.example.pawnbet_frontend.jwt.TokenManager
import com.example.pawnbet_frontend.model.LoginRequest
import com.example.pawnbet_frontend.model.SignUpRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object LoginSuccess : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel(
    private val api: ApiEndPoints,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _profile = MutableStateFlow<String?>("")
    val profile: StateFlow<String?> = _profile

    private val _username = MutableStateFlow<String>("")
    val username: StateFlow<String> = _username

    private val _loginState = MutableStateFlow<AuthState>(AuthState.Idle)
    val loginState: StateFlow<AuthState> = _loginState

    private val _signupState = MutableStateFlow<AuthState>(AuthState.Idle)
    val signupState: StateFlow<AuthState> = _signupState

    fun signup(email: String, username: String, password: String, profileImageUrl: String) {
        viewModelScope.launch {
            _signupState.value = AuthState.Loading
            try {
                val response = api.signup(SignUpRequest(email, username, password, profileImageUrl))
                if (response.isSuccessful) {
                    Log.d("Auth", "Sign up successful")
                    _signupState.value = AuthState.LoginSuccess
                } else {
                    Log.e("Auth", "Sign up failed: ${response.code()} ${response.message()}")
                    _signupState.value = AuthState.Error("Sign up failed: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("Auth", "Sign Up failed", e)
                _signupState.value = AuthState.Error("Cannot sign up: ${e.message}")
            }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = AuthState.Loading
            tokenManager.clearToken()
            try {
                val response = api.login(LoginRequest(username, password))
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.token.isNotEmpty()) {
                        tokenManager.saveToken(body.token)
                        Log.e("Auth", "Login Successful")
                        _loginState.value = AuthState.LoginSuccess
                    } else {
                        Log.e("Auth", "Login Failed: Empty token")
                        _loginState.value = AuthState.Error("Login failed: Empty token")
                    }
                } else {
                    Log.e("Auth", "Login Failed: ${response.code()} - ${response.message()}")
                    _loginState.value = AuthState.Error("Login failed: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("Auth", "Cannot Login: ${e.message}")
                _loginState.value = AuthState.Error("Cannot login: ${e.message}")
            }
        }
    }

    suspend fun verifyToken(): Boolean {
        val token = tokenManager.getToken()
        return if (token.isNullOrEmpty()) {
            false
        } else {
            try {
                val response = api.verifyToken("Bearer $token")
                response.isSuccessful && (response.body() == true)
            } catch (e: Exception) {
                false
            }
        }
    }

    fun getUserProfile() {
        viewModelScope.launch {
            try {
                val token = tokenManager.getToken()
                if (token.isNullOrEmpty()) {
                    Log.e("Auth", "No JWT token found")
                    return@launch
                }
                val authHeader = "Bearer $token"
                val response = api.getUserProfile(authHeader)

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        tokenManager.saveUserId(body.id)
                        tokenManager.saveUsername(body.username)
                        _profile.value = body.profileImageUrl.toString()
                        _username.value = body.username
                        Log.d("Auth", "Profile fetched successfully")
                    }
                } else {
                    Log.e("Auth", "Profile fetch failed: ${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("Auth", "Failed to fetch profile", e)
            }
        }
    }
}
