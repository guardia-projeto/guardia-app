package com.example.guardia.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

data class ChatRequest(
    val text: String,
    val userId: String? = null
)

interface ChatApi {

    // ------------------------------
    // 📩 ENVIO NORMAL (SEM ARQUIVO)
    // ------------------------------
    @POST("chat1")
    suspend fun send(
        @Body body: ChatRequest
    ): Response<ResponseBody>


    // ------------------------------
    // 📎 ENVIO COM ARQUIVO (MULTIPART)
    // ------------------------------
    @Multipart
    @POST("chat1_file")
    suspend fun sendMessageWithFile(
        @Part("message") message: RequestBody,
        @Part file: MultipartBody.Part
    ): Response<ResponseBody>
}
