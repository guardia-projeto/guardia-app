package com.example.guardia.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val N8N_BASE_URL = "https://arthursilvaasoares.app.n8n.cloud/webhook/" // termina com /

fun provideChatApi(): ChatApi {
    val log = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val client = OkHttpClient.Builder()
        .addInterceptor(log)
        .build()

    return Retrofit.Builder()
        .baseUrl(N8N_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
        .create(ChatApi::class.java)
}
