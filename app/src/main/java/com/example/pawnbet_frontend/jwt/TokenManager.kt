package com.example.pawnbet_frontend.jwt

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.pawnbet_frontend.api.TokenProvider
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

val Context.dataStore: androidx.datastore.core.DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class TokenManager(private val dataStore: DataStore<Preferences>) : TokenProvider {

    companion object {
        val JWT_TOKEN = stringPreferencesKey("jwt_token")
        val USERNAME = stringPreferencesKey("username")
        val USER_ID = stringPreferencesKey("user_id")
    }


    suspend fun saveUserId(userId: Long) {
        dataStore.edit { it[USER_ID] = userId.toString() }
    }

    fun getUserId(): Long? = runBlocking {
        dataStore.data.map { prefs ->
            prefs[USER_ID]?.toLong()
        }.firstOrNull()
    }

    override fun getToken(): String? = runBlocking {
        dataStore.data.map { it[JWT_TOKEN] }.firstOrNull()
    }

    fun getUsername(): String? = runBlocking {
        dataStore.data.map { it[USERNAME] }.firstOrNull()
    }

    suspend fun saveToken(token: String) {
        dataStore.edit { it[JWT_TOKEN] = token }
    }

    suspend fun saveUsername(username: String) {
        dataStore.edit { it[USERNAME] = username }
    }

    suspend fun clearToken() {
        dataStore.edit {
            it.remove(JWT_TOKEN)
            it.remove(USERNAME)
            it.remove(USER_ID)
        }
    }

    suspend fun saveAuthData(token: String, username: String, userId: Long) {
        dataStore.edit {
            it[JWT_TOKEN] = token
            it[USERNAME] = username
            it[USER_ID] = userId.toString()
        }
    }
}
