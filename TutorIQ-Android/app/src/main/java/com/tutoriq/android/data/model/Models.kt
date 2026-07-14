package com.tutoriq.android.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String
)

data class Profile(
    @SerializedName("university") val university: String = "",
    @SerializedName("semester") val semester: String = "",
    @SerializedName("course") val course: String = "",
    @SerializedName("gemini_api_key") val geminiApiKey: String = ""
)

data class ToolData(
    @SerializedName("subject") val subject: String,
    @SerializedName("marks") val marks: String
)

data class ChatRequest(
    @SerializedName("toolType") val toolType: String = "answerMaker",
    @SerializedName("message") val message: String,
    @SerializedName("toolData") val toolData: ToolData
)

data class ChatResponse(
    @SerializedName("response") val response: String,
    @SerializedName("conversationId") val conversationId: String
)

data class LoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

data class RegisterRequest(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

data class ApiResponse<T>(
    @SerializedName("message") val message: String? = null,
    @SerializedName("error") val error: String? = null,
    @SerializedName("user") val user: T? = null,
    @SerializedName("token") val token: String? = null
)
