package com.example.guardia.screens

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.example.guardia.R
import com.example.guardia.data.relatorios.RelatorioDatabase
import com.example.guardia.data.relatorios.RelatorioEntity
import com.example.guardia.data.relatorios.RelatorioRepository
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/* ================== CORES ================== */
private val BgTop = Color(0xFFBDEFFF)
private val BgBottom = Color(0xFF7CB8E4)
private val HeaderMain = Color(0xFF0E3B5E)
private val HeaderAccent = Color(0xFF0052A3)
private val CardBg = Color.White
private val CardTextMain = Color(0xFF2B4A6F)
private val CardTextSub = Color(0xFF6B8299)

/* ================== TEMA ================== */
@Composable
fun MeusRelatoriosTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = HeaderAccent,
            background = BgTop,
            surface = Color.White
        ),
        content = content
    )
}

/* ================== TELA ================== */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeusRelatoriosScreen(
    onBackClick: () -> Unit = {},
    onHomeClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val db = remember { RelatorioDatabase.getInstance(context) }
    val repo = remember { RelatorioRepository(db.relatorioDao()) }

    var relatorios by remember { mutableStateOf<List<RelatorioEntity>>(emptyList()) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        relatorios = repo.listarTodos()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Voltar",
                            tint = HeaderMain,
                            modifier = Modifier
                                .size(26.dp)
                                .clickable { onBackClick() }
                        )
                        Spacer(Modifier.width(12.dp))
                        Text(
                            buildAnnotatedString {
                                withStyle(SpanStyle(color = HeaderMain)) { append("Meus ") }
                                withStyle(
                                    SpanStyle(
                                        color = HeaderAccent,
                                        fontWeight = FontWeight.Bold
                                    )
                                ) { append("Relatórios") }
                            },
                            fontSize = 24.sp
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color.Transparent
    ) { padding ->
        Box(
            Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(BgTop, BgBottom)))
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(relatorios.size) { index ->
                    val r = relatorios[index]
                    RelatorioCardDetailed(
                        relatorio = r,
                        onOpenClick = { abrirPdfRelatorio(context, r) },
                        onShareClick = { compartilharPdfRelatorio(context, r) },
                        onDeleteClick = {
                            scope.launch {
                                repo.excluir(r)
                                relatorios = repo.listarTodos()

                                // remove arquivo físico se existir
                                val dir = File(context.getExternalFilesDir("reports"), "")
                                val file = File(dir, "relatorio_${r.id}.pdf")
                                if (file.exists()) file.delete()
                            }
                        }
                    )
                }
            }
        }
    }
}

/* ================== CARD ================== */
@Composable
fun RelatorioCardDetailed(
    relatorio: RelatorioEntity,
    onOpenClick: () -> Unit,
    onShareClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val date = remember(relatorio.dataHora) {
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            .format(Date(relatorio.dataHora))
    }

    var menuExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onOpenClick() },
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    "Relatório Guardiã - $date",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = CardTextMain,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    "Risco: ${relatorio.risco} • Categoria: ${relatorio.categoria}",
                    fontSize = 13.sp,
                    color = CardTextSub,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Box {
                IconButton(onClick = { menuExpanded = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Mais opções",
                        tint = CardTextSub
                    )
                }

                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false }
                ) {
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                        modifier = Modifier.width(220.dp)
                    ) {
                        Column(modifier = Modifier.padding(vertical = 6.dp)) {

                            DropdownMenuItem(
                                text = {
                                    Text(
                                        "Abrir PDF",
                                        fontSize = 15.sp,
                                        color = CardTextMain,
                                        fontWeight = FontWeight.Medium
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Description,
                                        contentDescription = null,
                                        tint = HeaderAccent
                                    )
                                },
                                onClick = {
                                    menuExpanded = false
                                    onOpenClick()
                                },
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp)
                            )

                            DropdownMenuItem(
                                text = {
                                    Text(
                                        "Compartilhar",
                                        fontSize = 15.sp,
                                        color = CardTextMain,
                                        fontWeight = FontWeight.Medium
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Share,
                                        contentDescription = null,
                                        tint = HeaderAccent
                                    )
                                },
                                onClick = {
                                    menuExpanded = false
                                    onShareClick()
                                },
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp)
                            )

                            Divider(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                color = CardTextSub.copy(alpha = 0.2f)
                            )

                            DropdownMenuItem(
                                text = {
                                    Text(
                                        "Excluir",
                                        fontSize = 15.sp,
                                        color = MaterialTheme.colorScheme.error,
                                        fontWeight = FontWeight.Medium
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                },
                                onClick = {
                                    menuExpanded = false
                                    onDeleteClick()
                                },
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

/* ================== PDF ================== */
private fun criarPdfDoRelatorio(
    context: Context,
    relatorio: RelatorioEntity
): File {

    val pageWidth = 595 // A4
    val minPageHeight = 600

    val margin = 40f
    val marginTop = 40f
    val marginBottom = 50f

    val bodyTextSize = 28f
    val titleTextSize = 36f
    val lineHeight = 44f

    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = android.graphics.Color.BLACK
        textSize = bodyTextSize
        typeface = Typeface.DEFAULT
    }

    val maxWidth = pageWidth - margin * 2

    // --------- helpers de cálculo (pra altura dinâmica) ----------
    fun countWrappedLines(text: String, textSize: Float, bold: Boolean = false): Int {
        if (text.isBlank()) return 1

        val temp = Paint(paint).apply {
            this.textSize = textSize
            this.typeface =
                if (bold) Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                else Typeface.DEFAULT
        }

        val words = text.trim().split(Regex("\\s+"))
        var line = ""
        var lines = 0

        for (w in words) {
            val test = if (line.isEmpty()) w else "$line $w"
            if (temp.measureText(test) > maxWidth) {
                lines++
                line = w
            } else {
                line = test
            }
        }
        if (line.isNotEmpty()) lines++
        return lines
    }

    // tenta ler a logo para calcular o espaço dela no topo
    val logo: Bitmap? = runCatching {
        BitmapFactory.decodeResource(context.resources, R.drawable.shield)
    }.getOrNull()

    val logoDesiredWidth = 170f
    val logoHeight: Float = if (logo != null && logo.width > 0) {
        val scale = logoDesiredWidth / logo.width.toFloat()
        logo.height * scale
    } else 0f

    val blocks = listOf(
        Triple("Relatório Guardiã", titleTextSize, true),
        Triple("Resumo da situação:", bodyTextSize, true),
        Triple(relatorio.resumo.ifBlank { "-" }, bodyTextSize, false),
        Triple("Categoria:", bodyTextSize, true),
        Triple(relatorio.categoria.ifBlank { "-" }, bodyTextSize, false),
        Triple("Nível de risco:", bodyTextSize, true),
        Triple(relatorio.risco.ifBlank { "-" }, bodyTextSize, false),
        Triple("Orientação:", bodyTextSize, true),
        Triple(relatorio.orientacao.ifBlank { "-" }, bodyTextSize, false),
        Triple("Encaminhamento:", bodyTextSize, true),
        Triple(relatorio.encaminhamento.ifBlank { "-" }, bodyTextSize, false),
    )

    var totalLines = 0
    blocks.forEach { (text, size, bold) ->
        totalLines += countWrappedLines(text, size, bold)
    }

    val spacingAfterTitle = (lineHeight / 2)
    val spacingBetweenSections = (lineHeight / 2)
    val extraSpaces = 10 * spacingBetweenSections + spacingAfterTitle

    val contentHeight =
        marginTop +
                (if (logoHeight > 0f) logoHeight + 24f else 0f) +
                (totalLines * lineHeight) +
                extraSpaces +
                marginBottom

    val pageHeight = maxOf(minPageHeight, contentHeight.toInt())

    val pdf = PdfDocument()
    val page = pdf.startPage(
        PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
    )
    val canvas = page.canvas
    var y = marginTop

    fun drawWrapped(text: String, textSize: Float, bold: Boolean = false) {
        val clean = if (text.isBlank()) "-" else text

        paint.textSize = textSize
        paint.typeface =
            if (bold) Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            else Typeface.DEFAULT

        val words = clean.trim().split(Regex("\\s+"))
        var line = ""

        for (w in words) {
            val test = if (line.isEmpty()) w else "$line $w"
            if (paint.measureText(test) > maxWidth) {
                canvas.drawText(line, margin, y, paint)
                y += lineHeight
                line = w
            } else {
                line = test
            }
        }
        if (line.isNotEmpty()) {
            canvas.drawText(line, margin, y, paint)
            y += lineHeight
        }
    }

    // ✅ NOVO: desenhar texto centralizado (1 linha)
    fun drawCenteredText(text: String, textSize: Float, bold: Boolean = false) {
        val clean = if (text.isBlank()) "-" else text

        paint.textSize = textSize
        paint.typeface =
            if (bold) Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            else Typeface.DEFAULT

        val textWidth = paint.measureText(clean)
        val x = (pageWidth - textWidth) / 2f

        canvas.drawText(clean, x, y, paint)
        y += lineHeight
    }

    fun drawField(label: String, value: String) {
        drawWrapped("$label:", bodyTextSize, bold = true)
        drawWrapped(value.ifBlank { "-" }, bodyTextSize, bold = false)
        y += lineHeight / 2
    }

    // ===== LOGO =====
    if (logo != null && logo.width > 0 && logo.height > 0) {
        val scale = logoDesiredWidth / logo.width.toFloat()
        val desiredHeight = (logo.height * scale).toInt()

        val scaled = Bitmap.createScaledBitmap(
            logo,
            logoDesiredWidth.toInt(),
            desiredHeight,
            true
        )

        val x = (pageWidth - logoDesiredWidth) / 2f
        canvas.drawBitmap(scaled, x, y, null)
        y += desiredHeight + 24f
    }

    // ===== TÍTULO (CENTRALIZADO) =====
    drawCenteredText("Relatório Guardiã", titleTextSize, bold = true)
    y += lineHeight / 2

    // ===== CAMPOS (DESTACADOS) =====
    drawField("Resumo da situação", relatorio.resumo)
    drawField("Categoria", relatorio.categoria)
    drawField("Nível de risco", relatorio.risco)
    drawField("Orientação", relatorio.orientacao)
    drawField("Encaminhamento", relatorio.encaminhamento)

    pdf.finishPage(page)

    val dir = File(context.getExternalFilesDir("reports"), "")
    if (!dir.exists()) dir.mkdirs()

    val file = File(dir, "relatorio_${relatorio.id}.pdf")
    pdf.writeTo(file.outputStream())
    pdf.close()

    return file
}

/* ================== ABRIR / COMPARTILHAR ================== */
private fun abrirPdfRelatorio(context: Context, relatorio: RelatorioEntity) {
    val file = criarPdfDoRelatorio(context, relatorio)

    val uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        file
    )

    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(uri, "application/pdf")
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    context.startActivity(Intent.createChooser(intent, "Abrir relatório em PDF"))
}

private fun compartilharPdfRelatorio(context: Context, relatorio: RelatorioEntity) {
    val file = criarPdfDoRelatorio(context, relatorio)

    val uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        file
    )

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "application/pdf"
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    context.startActivity(Intent.createChooser(intent, "Compartilhar relatório em PDF"))
}

/* ================== PREVIEW ================== */
@Preview(showBackground = true)
@Composable
fun PreviewRelatorios() {
    MeusRelatoriosTheme {
        MeusRelatoriosScreen()
    }
}
