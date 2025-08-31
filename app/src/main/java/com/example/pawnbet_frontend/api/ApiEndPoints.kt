package com.example.pawnbet_frontend.api

import com.example.pawnbet_frontend.model.AuctionResponse
import com.example.pawnbet_frontend.model.BidRequest
import com.example.pawnbet_frontend.model.CommentRequest
import com.example.pawnbet_frontend.model.CommentResponse
import com.example.pawnbet_frontend.model.LoginRequest
import com.example.pawnbet_frontend.model.LoginResponse
import com.example.pawnbet_frontend.model.ProductResponse
import com.example.pawnbet_frontend.model.SignUpRequest
import com.example.pawnbet_frontend.model.UserProfileResponse
import com.example.pawnbet_frontend.model.WishlistRequest
import com.example.pawnbet_frontend.model.WishlistResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiEndPoints {

    // User Credentials endpoints
    @POST("/api/auth/signup")
    suspend fun signup(@Body request: SignUpRequest): Response<Void>

    @POST("/api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("/api/auth/verify")
    suspend fun verifyToken(@Header("Authorization") token : String): Boolean

    @GET("/api/auth/profile")
    suspend fun getUserProfile(@Header("Authorization") token: String): Response<UserProfileResponse>

    // Product endpoints
    @GET("/api/products")
    suspend fun getAllProducts(@Header("Authorization") token: String): Response<List<ProductResponse>>

    @GET("/api/product/search")
    suspend fun getSearchProducts(@Header("Authorization") token:String, @Query("keyword") keyword: String): Response<List<ProductResponse>>

    // Auction endpoints
    @GET("/api/product/{id}/auction")
    suspend fun getAuctionDetails(@Header("Authorization") token: String, @Path("id") productId: Long): Response<AuctionResponse>

    //Comment endpoints
    @POST("/api/comment")
    suspend fun addComment(@Header("Authorization") token : String, @Body request: CommentRequest): Response<CommentResponse>

    @GET("/api/comment/{product_id}")
    suspend fun getAllComments(@Header("Authorization") token : String, @Path("product_id") productId: Long): Response<List<CommentResponse>>

    //Bid endpoints
    @POST("/api/bid/{product_id}")
    suspend fun raiseBid(@Header("Authorization") token : String, @Body request : BidRequest): Response<BidRequest>


    //Wishlist endpoints
    @POST("/api/wishlist")
    suspend fun addWishlistProduct(@Header("Authorization") token: String, @Body request: WishlistRequest): Response<Unit>

    @GET("/api/wishlist")
    suspend fun getWishlistProducts(@Header("Authorization") token: String): Response<List<WishlistResponse>>

    @DELETE("/api/wishlist/{id}")
    suspend fun deleteWishlistProduct(@Header("Authorization") token: String, @Path("id") id: Long): Response<Unit>
}