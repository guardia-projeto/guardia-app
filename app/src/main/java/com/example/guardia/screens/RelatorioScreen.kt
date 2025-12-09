package com.example.guardia.screens

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
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
import com.example.guardia.data.relatorios.RelatorioDatabase
import com.example.guardia.data.relatorios.RelatorioEntity
import com.example.guardia.data.relatorios.RelatorioRepository
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// ===== CORES DA TELA (parecido com o protótipo) =====
private val BgTop = Color(0xFFBDEFFF)
private val BgBottom = Color(0xFF7CB8E4)
private val HeaderMain = Color(0xFF0E3B5E)
private val HeaderAccent = Color(0xFF0052A3)
private val CardBg = Color.White
private val CardBorder = Color(0xFFE3F1F7)
private val CardTextMain = Color(0xFF2B4A6F)
private val CardTextSub = Color(0xFF6B8299)

// ===== THEME =====
@Composable
fun MeusRelatoriosTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = androidx.compose.material3.lightColorScheme(
            primary = HeaderAccent,
            background = BgTop,
            surface = Color.White
        ),
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeusRelatoriosScreen(
    onBackClick: () -> Unit = {},
    onHomeClick: () -> Unit = {}
) {
    val context = LocalContext.current

    // DB e repositório
    val db: RelatorioDatabase = remember(context) {
        RelatorioDatabase.getInstance(context)
    }

    val repo: RelatorioRepository = remember(db) {
        RelatorioRepository(db.relatorioDao())
    }

    // estado com a lista de relatórios
    var relatorios by remember { mutableStateOf<List<RelatorioEntity>>(emptyList()) }

    // carrega do banco quando a tela abre
    LaunchedEffect(Unit) {
        val lista = repo.listarTodos()
        relatorios = lista
    }

    var selectedTab by remember { mutableIntStateOf(2) }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .background(
                        Brush.verticalGradient(
                            listOf(BgTop, BgBottom.copy(alpha = 0.7f))
                        )
                    )
            ) {
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            androidx.compose.material3.Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Voltar",
                                tint = HeaderMain,
                                modifier = Modifier
                                    .size(26.dp)
                                    .clickable { onBackClick() }
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(
                                text = buildAnnotatedString {
                                    withStyle(SpanStyle(color = HeaderMain)) {
                                        append("Meus ")
                                    }
                                    withStyle(
                                        SpanStyle(
                                            color = HeaderAccent,
                                            fontWeight = FontWeight.Bold
                                        )
                                    ) {
                                        append("Relatórios")
                                    }
                                },
                                fontSize = 24.sp,
                                fontWeight = FontWeight.SemiBold
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            ClipboardIconDetailed()
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    ),
                    modifier = Modifier.height(72.dp)
                )
                Divider(
                    color = Color.White.copy(alpha = 0.6f),
                    thickness = 1.dp
                )
            }
        },
        bottomBar = {
            GuardiaBottomBar(
                currentRoute = "home",
                onItemClick = {
                    onHomeClick()
                }
            )
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(BgTop, BgBottom)
                    )
                )
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(count = relatorios.size) { index ->
                    val relatorio = relatorios[index]

                    RelatorioCardDetailed(
                        relatorio = relatorio,
                        onOpenClick = {
                            // 👉 Abrir PDF (mesma ideia da Guardiã)
                            abrirPdfRelatorio(context, relatorio)
                        },
                        onShareClick = {
                            // 👉 Compartilhar PDF
                            compartilharPdfRelatorio(context, relatorio)
                        },
                        onDeleteClick = {
                            // 👉 Excluir do banco + apagar arquivo local
                            scope.launch {
                                // Ajuste o nome do método conforme seu RelatorioRepository
                                repo.excluir(relatorio)

                                val listaAtualizada = repo.listarTodos()
                                relatorios = listaAtualizada

                                // Remove o arquivo físico se existir
                                val dir = File(context.getExternalFilesDir("reports"), "")
                                val file = File(dir, "relatorio_${relatorio.id}.pdf")
                                if (file.exists()) {
                                    file.delete()
                                }
                            }
                        }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

// ===== ICON DO CLIPBOARD =====
@Composable
fun ClipboardIconDetailed() {
    Box(
        modifier = Modifier
            .size(44.dp)
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(HeaderAccent),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(32.dp)) {
            val width = size.width
            val height = size.height

            drawRoundRect(
                color = Color.White,
                topLeft = Offset(width * 0.3f, height * 0.05f),
                size = androidx.compose.ui.geometry.Size(width * 0.4f, height * 0.12f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(4f, 4f)
            )

            drawRoundRect(
                color = Color.White,
                topLeft = Offset(width * 0.15f, height * 0.15f),
                size = androidx.compose.ui.geometry.Size(width * 0.7f, height * 0.75f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(6f, 6f)
            )

            val lineStartX = width * 0.25f
            val checkboxSize = 8f

            for (i in 0..2) {
                val lineY = height * (0.3f + i * 0.18f)

                drawLine(
                    color = HeaderAccent,
                    start = Offset(lineStartX, lineY),
                    end = Offset(lineStartX + checkboxSize * 0.4f, lineY + checkboxSize * 0.4f),
                    strokeWidth = 3f,
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = HeaderAccent,
                    start = Offset(lineStartX + checkboxSize * 0.4f, lineY + checkboxSize * 0.4f),
                    end = Offset(lineStartX + checkboxSize, lineY - checkboxSize * 0.2f),
                    strokeWidth = 3f,
                    cap = StrokeCap.Round
                )

                drawLine(
                    color = HeaderAccent,
                    start = Offset(lineStartX + checkboxSize + 8f, lineY),
                    end = Offset(lineStartX + (width * 0.4f), lineY),
                    strokeWidth = 3f,
                    cap = StrokeCap.Round
                )
            }
        }
    }
}

// ===== CARD DO RELATÓRIO COM TRÊS BOLINHAS =====
@Composable
fun RelatorioCardDetailed(
    relatorio: RelatorioEntity,
    onOpenClick: () -> Unit,
    onShareClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val dataFormatada = remember(relatorio.dataHora) {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        sdf.format(Date(relatorio.dataHora))
    }

    var menuExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable { onOpenClick() }, // clique no card abre o PDF
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBg
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(CardBg)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .shadow(0.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Relatório Guardiã - $dataFormatada",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = CardTextMain,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "Risco: ${relatorio.risco} • Categoria: ${relatorio.categoria}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal,
                        color = CardTextSub,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "PDF gerado automaticamente",
                        fontSize = 11.sp,
                        color = CardTextSub.copy(alpha = 0.8f)
                    )
                }

                // Três bolinhas (menu)
                Box {
                    IconButton(
                        onClick = { menuExpanded = true }
                    ) {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Mais opções",
                            tint = CardTextSub
                        )
                    }

                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Abrir PDF") },
                            onClick = {
                                menuExpanded = false
                                onOpenClick()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Compartilhar") },
                            onClick = {
                                menuExpanded = false
                                onShareClick()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Excluir") },
                            onClick = {
                                menuExpanded = false
                                onDeleteClick()
                            }
                        )
                    }
                }
            }
        }
    }
}

// ===== BOTTOM BAR CUSTOM (se ainda estiver usando) =====
@Composable
fun BottomNavigationBarCustom(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .shadow(12.dp, RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp))
                .background(Color(0xFFE8F5F5))
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavIconItem(
                icon = Icons.Default.Person,
                selected = selectedTab == 0,
                onClick = { onTabSelected(0) }
            )
            BottomNavIconItem(
                icon = Icons.Default.ChatBubbleOutline,
                selected = selectedTab == 1,
                onClick = { onTabSelected(1) }
            )

            Box(
                modifier = Modifier
                    .size(62.dp)
                    .shadow(8.dp, CircleShape)
                    .clip(CircleShape)
                    .background(Color(0xFF537FA8))
                    .clickable { onTabSelected(2) },
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }

            BottomNavIconItem(
                icon = Icons.Default.Person,
                selected = selectedTab == 3,
                onClick = { onTabSelected(3) }
            )
            BottomNavIconItem(
                icon = Icons.Default.Settings,
                selected = selectedTab == 4,
                onClick = { onTabSelected(4) }
            )
        }
    }
}

@Composable
fun BottomNavIconItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(52.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.material3.Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF537FA8),
            modifier = Modifier.size(30.dp)
        )
    }
}

// ===== FUNÇÕES DE PDF =====

// Abre o PDF com um visualizador (igual comportamento "abrir" da Guardiã)
private fun abrirPdfRelatorio(
    context: Context,
    relatorio: RelatorioEntity
) {
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

    context.startActivity(
        Intent.createChooser(intent, "Abrir relatório em PDF")
    )
}

// Compartilha o PDF
private fun compartilharPdfRelatorio(
    context: Context,
    relatorio: RelatorioEntity
) {
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

    context.startActivity(
        Intent.createChooser(intent, "Compartilhar relatório em PDF")
    )
}

// ===== PDF COM ALTURA DINÂMICA + FONTE BEM GRANDE =====
private fun criarPdfDoRelatorio(
    context: Context,
    relatorio: RelatorioEntity
): File {
    val pageWidth = 595                  // largura A4 em pontos
    val minPageHeight = 400              // altura mínima

    val marginStart = 40f
    val marginEnd = 40f
    val marginTop = 50f
    val marginBottom = 50f

    // 🔥 FONTE GRANDONA
    val bodyTextSize = 32f               // texto
    val titleTextSize = 40f              // título
    val lineHeight = 50f                 // espaçamento entre linhas

    val paint = Paint().apply {
        isAntiAlias = true
        textSize = bodyTextSize
        color = android.graphics.Color.BLACK
        typeface = android.graphics.Typeface.DEFAULT
    }

    val maxWidth = pageWidth - marginStart - marginEnd

    // Conta quantas linhas serão necessárias
    fun countLines(text: String, isTitle: Boolean = false): Int {
        val tempPaint = Paint(paint)
        tempPaint.textSize = if (isTitle) titleTextSize else bodyTextSize

        if (text.isEmpty()) return 1

        val words = text.split(" ")
        var line = ""
        var lines = 0

        for (word in words) {
            val candidate = if (line.isEmpty()) word else "$line $word"
            if (tempPaint.measureText(candidate) > maxWidth) {
                lines++
                line = word
            } else {
                line = candidate
            }
        }

        if (line.isNotEmpty()) lines++
        return lines
    }

    // Blocos de conteúdo
    val conteudo: List<Pair<String, Boolean>> = listOf(
        "Relatório Guardiã" to true,
        "" to false,
        "Resumo da situação: ${relatorio.resumo}" to false,
        "Categoria: ${relatorio.categoria}" to false,
        "Nível de risco: ${relatorio.risco}" to false,
        "Orientação: ${relatorio.orientacao}" to false,
        "Encaminhamento: ${relatorio.encaminhamento}" to false,
        "" to false
    )

    var totalLines = 0
    conteudo.forEach { (text, isTitle) ->
        totalLines += countLines(text, isTitle)
    }

    val contentHeight = marginTop + totalLines * lineHeight + marginBottom
    val pageHeight = if (contentHeight < minPageHeight) {
        minPageHeight
    } else {
        contentHeight.toInt()
    }

    val pdfDocument = PdfDocument()
    val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
    val page = pdfDocument.startPage(pageInfo)
    val canvas = page.canvas

    var y = marginTop

    fun drawWrapped(text: String, isTitle: Boolean = false) {
        if (text.isEmpty()) {
            y += lineHeight
            return
        }

        paint.textSize = if (isTitle) titleTextSize else bodyTextSize
        paint.typeface = if (isTitle) {
            android.graphics.Typeface.create(
                android.graphics.Typeface.DEFAULT,
                android.graphics.Typeface.BOLD
            )
        } else {
            android.graphics.Typeface.DEFAULT
        }

        val words = text.split(" ")
        var line = ""

        for (word in words) {
            val candidate = if (line.isEmpty()) word else "$line $word"
            if (paint.measureText(candidate) > maxWidth) {
                canvas.drawText(line, marginStart, y, paint)
                y += lineHeight
                line = word
            } else {
                line = candidate
            }
        }

        if (line.isNotEmpty()) {
            canvas.drawText(line, marginStart, y, paint)
            y += lineHeight
        }
    }

    conteudo.forEach { (text, isTitle) ->
        drawWrapped(text, isTitle)
    }

    pdfDocument.finishPage(page)

    val dir = File(context.getExternalFilesDir("reports"), "")
    if (!dir.exists()) dir.mkdirs()

    val fileName = "relatorio_${relatorio.id}.pdf"
    val file = File(dir, fileName)

    pdfDocument.writeTo(file.outputStream())
    pdfDocument.close()

    return file
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewRelatorios() {
    MeusRelatoriosTheme {
        MeusRelatoriosScreen()
    }
}
