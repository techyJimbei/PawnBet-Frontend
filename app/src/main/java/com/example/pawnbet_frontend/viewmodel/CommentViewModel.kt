package com.example.pawnbet_frontend.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawnbet_frontend.api.ApiEndPoints
import com.example.pawnbet_frontend.jwt.TokenManager
import com.example.pawnbet_frontend.model.CommentRequest
import com.example.pawnbet_frontend.model.CommentResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CommentViewModel(
    private val api: ApiEndPoints,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _comments = mutableStateOf<List<CommentResponse>>(emptyList())
    val comments: State<List<CommentResponse>> = _comments

    private val _selectedComment = MutableStateFlow<CommentResponse?>(null)
    val selectedComment: StateFlow<CommentResponse?> = _selectedComment

    fun selectComment(commentResponse: CommentResponse) {
        _selectedComment.value = commentResponse
    }

    fun addComment(commentRequest: CommentRequest) {
        viewModelScope.launch {
            try {
                val token = "Bearer ${tokenManager.getToken() ?: ""}"
                val response = api.addComment(token, commentRequest)
                if (response.isSuccessful && response.body() != null) {
                    val newComment = response.body()!!

                    _comments.value = if (commentRequest.parentId == null) {
                        listOf(newComment) + _comments.value
                    } else {
                        insertReplyRecursive(_comments.value, commentRequest.parentId, newComment)
                    }

                    Log.e("Comment", "Comment added")
                } else {
                    Log.e("Comment", "Cannot add Comment")
                }
            } catch (e: Exception) {
                Log.e("Comment", "Exception Occurred: ${e.message}")
            }
        }
    }


    fun getAllComments(productId: Long) {
        viewModelScope.launch {
            try {
                val token = "Bearer ${tokenManager.getToken() ?: ""}"
                val response = api.getAllComments(token, productId)
                if (response.isSuccessful && response.body() != null) {
                    _comments.value = response.body()!!
                    Log.e("Comment", "Comment List Received")
                } else {
                    Log.e("Comment", "Comment List is empty")
                }
            } catch (e: Exception) {
                Log.e("Comment", "Exception Occurred ${e.message}")
            }
        }
    }

    private fun insertReplyRecursive(
        comments: List<CommentResponse>,
        parentId: Long,
        newReply: CommentResponse
    ): List<CommentResponse> {
        return comments.map { comment ->
            if (comment.id == parentId) {
                comment.copy(replies = listOf(newReply) + comment.replies)
            } else {
                comment.copy(
                    replies = insertReplyRecursive(comment.replies, parentId, newReply)
                )
            }
        }
    }


}