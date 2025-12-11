package com.example.guardia.data.relatorios

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RelatorioRepository(
    private val dao: RelatorioDao
) {

    /**
     * Salva um relatório no banco.
     * Você pode usar tanto para criar quanto para atualizar,
     * dependendo de como o RelatorioDao.inserir() está configurado
     * (se for @Insert(onConflict = OnConflictStrategy.REPLACE), por exemplo).
     */
    suspend fun salvar(relatorio: RelatorioEntity) {
        withContext(Dispatchers.IO) {
            dao.inserir(relatorio)
        }
    }

    /**
     * Retorna todos os relatórios salvos, ordenados conforme a query do DAO.
     */
    suspend fun listarTodos(): List<RelatorioEntity> {
        return withContext(Dispatchers.IO) {
            dao.listarTodos()
        }
    }

    /**
     * Exclui um relatório específico.
     * Usado na MeusRelatoriosScreen quando você clica em "Excluir".
     */
    suspend fun excluir(relatorio: RelatorioEntity) {
        withContext(Dispatchers.IO) {
            dao.deletar(relatorio)
        }
    }

    /**
     * (Opcional) Buscar por ID, caso você queira usar em outras telas depois.
     * Só funciona se você tiver esse método no DAO.
     */
    // suspend fun buscarPorId(id: Long): RelatorioEntity? {
    //     return withContext(Dispatchers.IO) {
    //         dao.buscarPorId(id)
    //     }
    // }
}
