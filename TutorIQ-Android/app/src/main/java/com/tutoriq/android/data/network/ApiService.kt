package com.tutoriq.android.data.network

import com.tutoriq.android.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    
    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<ApiResponse<User>>
    
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<ApiResponse<User>>
    
    @POST("api/auth/logout")
    suspend fun logout(): Response<ApiResponse<Unit>>
    
    @GET("api/profile")
    suspend fun getProfile(): Response<Profile>
    
    @POST("api/profile/update")
    suspend fun updateProfile(@Body profile: Profile): Response<Profile>
    
    @POST("api/ai/chat")
    suspend fun chat(@Body request: ChatRequest): Response<ChatResponse>
}
