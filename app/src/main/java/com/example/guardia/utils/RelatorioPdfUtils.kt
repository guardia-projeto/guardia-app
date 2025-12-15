package com.example.guardia.utils

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import com.example.guardia.data.relatorios.RelatorioEntity
import java.io.File

fun abrirRelatorioComChooser(
    context: Context,
    relatorio: RelatorioEntity,
    gerarPdf: (Context, RelatorioEntity) -> File
) {
    val file = gerarPdf(context, relatorio)

    val uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        file
    )

    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(uri, "application/pdf")
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    context.startActivity(
        Intent.createChooser(intent, "Abrir relatório em PDF")
    )
}
