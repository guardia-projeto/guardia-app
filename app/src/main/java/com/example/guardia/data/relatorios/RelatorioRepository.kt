package com.example.guardia.data.relatorios

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RelatorioRepository(
    private val dao: RelatorioDao
) {

    suspend fun salvar(relatorio: RelatorioEntity) {
        withContext(Dispatchers.IO) {
            dao.inserir(relatorio)
        }
    }

    suspend fun listarTodos(): List<RelatorioEntity> {
        return withContext(Dispatchers.IO) {
            dao.listarTodos()
        }
    }
}
