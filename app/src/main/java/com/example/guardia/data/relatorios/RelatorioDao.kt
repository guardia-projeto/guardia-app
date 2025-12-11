package com.example.guardia.data.relatorios

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RelatorioDao {

    @Insert
    fun inserir(relatorio: RelatorioEntity)

    @Query("SELECT * FROM relatorios ORDER BY dataHora DESC")
    fun listarTodos(): List<RelatorioEntity>

    // 🔥 NOVO: Método para excluir um relatório
    @Delete
    fun deletar(relatorio: RelatorioEntity)
}
