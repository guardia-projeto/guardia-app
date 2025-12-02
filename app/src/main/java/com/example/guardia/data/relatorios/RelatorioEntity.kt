package com.example.guardia.data.relatorios

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "relatorios")
data class RelatorioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val resumo: String,
    val categoria: String,
    val risco: String,
    val orientacao: String,
    val encaminhamento: String,
    val textoCompleto: String,

    // 🔹 Campo oficial que vamos usar no projeto
    val dataHora: Long = System.currentTimeMillis()
)
