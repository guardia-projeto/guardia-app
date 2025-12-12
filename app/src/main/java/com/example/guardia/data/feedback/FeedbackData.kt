package com.example.guardia.data.feedback

data class FeedbackData(
    val score: Int = 0,
    val type: String = "",
    val message: String = "",
    val timestamp: Long = 0L
)
