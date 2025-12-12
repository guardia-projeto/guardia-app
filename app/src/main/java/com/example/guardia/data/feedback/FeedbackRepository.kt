package com.example.guardia.data.feedback

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers

data class FeedbackData(
    val score: Int,
    val type: String,
    val message: String,
    val timestamp: Long
)

class FeedbackRepository {

    private val db = FirebaseFirestore.getInstance()

    suspend fun enviarFeedback(data: FeedbackData): Boolean = withContext(Dispatchers.IO) {
        try {
            // Tira o withTimeout por enquanto pra ver o erro real
            db.collection("feedbacks")
                .add(data)
                .await()

            Log.d("FeedbackRepository", "Feedback enviado com sucesso")
            true
        } catch (e: Exception) {
            Log.e("FeedbackRepository", "Erro ao enviar feedback", e)
            false
        }
    }
}
