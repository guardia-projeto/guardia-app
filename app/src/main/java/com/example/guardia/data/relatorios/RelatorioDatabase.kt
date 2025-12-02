package com.example.guardia.data.relatorios

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [RelatorioEntity::class],
    version = 1,
    exportSchema = false
)
abstract class RelatorioDatabase : RoomDatabase() {

    abstract fun relatorioDao(): RelatorioDao

    companion object {
        @Volatile
        private var INSTANCE: RelatorioDatabase? = null

        fun getInstance(context: Context): RelatorioDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    RelatorioDatabase::class.java,
                    "relatorios_db"
                )
                    .fallbackToDestructiveMigration() // 🔴 não crasha em alteração de schema
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
