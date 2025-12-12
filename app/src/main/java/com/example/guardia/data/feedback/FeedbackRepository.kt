package com.example.guardia.data.feedback

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class FeedbackRepository {

    // 👉 AQUI é Realtime Database, não Firestore
    private val db = FirebaseDatabase.getInstance().reference

    suspend fun enviarFeedback(feedback: FeedbackData): Boolean =
        suspendCancellableCoroutine { cont ->

            // Vai gravar em: raiz/feedbacks/<idGerado>
            db.child("feedbacks")
                .push()
                .setValue(feedback)
                .addOnCompleteListener { task ->
                    if (!cont.isActive) return@addOnCompleteListener

                    if (task.isSuccessful) {
                        Log.d("FeedbackRepository", "Feedback salvo com sucesso")
                        cont.resume(true)
                    } else {
                        Log.e(
                            "FeedbackRepository",
                            "Erro ao salvar feedback",
                            task.exception
                        )
                        cont.resume(false)
                    }
                }
        }
}
