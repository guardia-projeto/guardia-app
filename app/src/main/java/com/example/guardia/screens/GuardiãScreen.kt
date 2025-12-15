package com.example.guardia.screens

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.guardia.R
import com.example.guardia.data.relatorios.RelatorioDatabase
import com.example.guardia.data.relatorios.RelatorioEntity
import com.example.guardia.data.relatorios.RelatorioRepository
import com.example.guardia.network.ChatApi
import com.example.guardia.network.ChatRequest
import com.example.guardia.network.provideChatApi
import com.example.guardia.utils.abrirRelatorioComChooser
import kotlinx.coroutines.launch
import org.json.JSONObject

// quem fala
enum class Role { USER, ASSISTANT }

data class MessageUi(
    val id: String,
    val role: Role,
    val text: String
)

// -------------------------------
// BOLHA "Digitando…"
// -------------------------------
@Composable
private fun TypingBubble() {
    val infiniteTransition = rememberInfiniteTransition(label = "starAnimation")

    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing)
        ),
        label = "rotation"
    )

    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 70.dp, top = 4.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            color = Color(0xFF21A189),
            contentColor = Color.White,
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Estrela animada",
                    modifier = Modifier
                        .size(20.dp)
                        .rotate(angle)
                        .graphicsLayer { this.alpha = alpha },
                    tint = Color(0xFFFFD700)
                )

                Spacer(Modifier.width(6.dp))

                Text(
                    text = "Digitando…",
                    fontSize = 13.sp
                )
            }
        }
    }
}

// -------------------------------
// MENSAGEM ASSISTENTE
// -------------------------------
@Composable
private fun AssistantMessage(msg: MessageUi) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 70.dp, top = 6.dp, bottom = 6.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.guardia4),
                contentDescription = "Guardiã",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(Modifier.width(8.dp))

        Surface(
            color = Color(0xFF21A189),
            contentColor = Color.White,
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = msg.text,
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                fontSize = 14.sp,
                lineHeight = 18.sp
            )
        }
    }
}

// -------------------------------
// MENSAGEM DO USUÁRIO
// -------------------------------
@Composable
private fun UserMessage(msg: MessageUi) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 70.dp, end = 10.dp, top = 6.dp, bottom = 6.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Bottom
    ) {

        Surface(
            modifier = Modifier
                .weight(1f, false)
                .padding(end = 8.dp)
                .widthIn(max = 250.dp),
            color = Color(0xFF21A189),
            contentColor = Color.White,
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = msg.text,
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                fontSize = 14.sp,
                lineHeight = 18.sp
            )
        }

        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(Color(0xFF21A189)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.livia),
                contentDescription = "Usuária",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
private fun ChatBubble(msg: MessageUi) {
    if (msg.role == Role.ASSISTANT) AssistantMessage(msg) else UserMessage(msg)
}

// -------------------------------
// ROW DE MENSAGENS RÁPIDAS (compacto)
// -------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun QuickRepliesRow(
    options: List<String>,
    onSelect: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = "Sugestões da Guardiã",
            fontSize = 13.sp,
            color = Color(0xFF004D40),
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 4.dp, bottom = 6.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            options.forEach { text ->
                AssistChip(
                    onClick = { onSelect(text) },
                    label = {
                        Text(
                            text = text,
                            fontSize = 12.sp,
                            maxLines = 2
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFFFFD700),
                            modifier = Modifier.size(16.dp)
                        )
                    },
                    shape = RoundedCornerShape(50),
                    border = BorderStroke(0.8.dp, Color(0xFF1A7C6A)),
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = Color(0xFF21A189),
                        labelColor = Color.White,
                        leadingIconContentColor = Color.White
                    )
                )
            }
        }
    }
}

// -------------------------------
// TELA PRINCIPAL
// -------------------------------
@Composable
fun GuardiaScreen() {
    val scope = rememberCoroutineScope()
    val chatApi: ChatApi = remember { provideChatApi() }

    val messages = remember { mutableStateListOf<MessageUi>() }
    var userMessage by remember { mutableStateOf("") }
    var isTyping by remember { mutableStateOf(false) }

    // ✅ Guardar o relatório REAL do banco (o recém-salvo)
    var lastRelatorio by remember { mutableStateOf<RelatorioEntity?>(null) }

    val context = LocalContext.current

    val db = remember(context) { RelatorioDatabase.getInstance(context) }
    val relatorioRepo = remember(db) { RelatorioRepository(db.relatorioDao()) }

    val quickReplies = listOf(
        "Suspeito que minha filha está falando com pessoas mais velhas.",
        "Acho que meu filho está sendo vítima de grooming.",
        "Meu filho está sendo zombado no grupo da escola.",
        "Minha filha recebeu pedido de fotos íntimas.",
        "Meu filho está usando celular demais.",
        "Quero proteger a privacidade online do meu filho."
    )

    val speechLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val matches = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val text = matches?.firstOrNull()
            if (!text.isNullOrBlank()) userMessage = text
        }
    }

    fun startVoiceInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "pt-BR")
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Pode falar, estou te ouvindo…")
        }
        try {
            speechLauncher.launch(intent)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    fun sendMessage(originalInput: String) {
        val original = originalInput.trim()
        if (original.isBlank()) return

        messages += MessageUi(
            id = System.currentTimeMillis().toString() + "_u",
            role = Role.USER,
            text = original
        )

        userMessage = ""
        isTyping = true
        lastRelatorio = null

        scope.launch {
            try {
                val res = chatApi.send(ChatRequest(original))

                if (res.isSuccessful) {
                    val raw = res.body()?.string()

                    val replyText = try {
                        val json = JSONObject(raw ?: "{}")

                        val reply = json.optString(
                            "reply",
                            "Não consegui entender a resposta da Guardiã."
                        )

                        val risk = json.optJSONObject("risk")
                        val nivel = risk?.optString("nivel") ?: "Moderado"
                        val orientacao = risk?.optString("orientacao") ?: ""
                        val encaminhamento = risk?.optString("encaminhamento") ?: ""

                        val contextoJson = json.optJSONObject("contexto")
                        val resumo = contextoJson?.optString("resumoSituacao") ?: ""
                        val categoria = contextoJson?.optString("categoria") ?: ""

                        val entity = RelatorioEntity(
                            resumo = resumo.ifBlank { original },
                            categoria = categoria.ifBlank { "nao_informada" },
                            risco = nivel,
                            orientacao = orientacao,
                            encaminhamento = encaminhamento,
                            textoCompleto = buildString {
                                appendLine("Resumo da situação: $resumo")
                                appendLine("Categoria: $categoria")
                                appendLine("Nível de risco: $nivel")
                                if (orientacao.isNotBlank()) appendLine("Orientação: $orientacao")
                                if (encaminhamento.isNotBlank()) appendLine("Encaminhamento: $encaminhamento")
                            }.trim()
                        )

                        // ✅ salva no banco
                        relatorioRepo.salvar(entity)

                        // ✅ pega do banco o mais recente "de verdade"
                        // (evita abrir relatório errado por ordem não garantida)
                        val ultimo = relatorioRepo
                            .listarTodos()
                            .maxByOrNull { it.dataHora }

                        lastRelatorio = ultimo

                        reply.replace("\n", " ").trim()
                    } catch (e: Exception) {
                        raw?.replace("\n", " ")?.trim()
                            ?: "Não consegui processar a resposta."
                    }

                    messages += MessageUi(
                        id = System.currentTimeMillis().toString() + "_a",
                        role = Role.ASSISTANT,
                        text = replyText
                    )

                } else {
                    val err = res.errorBody()?.string()
                    messages += MessageUi(
                        id = System.currentTimeMillis().toString() + "_e",
                        role = Role.ASSISTANT,
                        text = "Erro HTTP ${res.code()}: ${err ?: "sem corpo"}"
                    )
                }

            } catch (e: Exception) {
                messages += MessageUi(
                    id = System.currentTimeMillis().toString() + "_e",
                    role = Role.ASSISTANT,
                    text = "Falha de rede no app: ${e.message ?: "desconhecida"}"
                )
            } finally {
                isTyping = false
            }
        }
    }

    LaunchedEffect(Unit) {
        if (messages.isEmpty()) {
            messages += MessageUi(
                id = "welcome",
                role = Role.ASSISTANT,
                text = "Olá, Lívia! Me diga, o que temos para hoje?"
            )
        }
    }

    val listState = rememberLazyListState()
    LaunchedEffect(messages.size, isTyping) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFFB2EBF2), Color(0xFFE0F7FA))
                        )
                    )
                    .padding(top = 16.dp, bottom = 12.dp),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(
                        onClick = {
                            (context as? androidx.activity.ComponentActivity)
                                ?.onBackPressedDispatcher?.onBackPressed()
                        },
                        modifier = Modifier.align(Alignment.CenterStart)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            "Voltar",
                            tint = Color(0xFF003E3A)
                        )
                    }

                    Image(
                        painterResource(id = R.drawable.ic_guardia_escudo),
                        contentDescription = "Logo Guardiã",
                        modifier = Modifier.size(130.dp)
                    )
                }

                Spacer(Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.White.copy(alpha = 0.7f))
                )
            }
        },
        containerColor = Color.Transparent
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFFB2EBF2), Color(0xFFE0F7FA), Color(0xFF8EC7E3))
                    )
                )
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(6.dp)
            ) {

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    state = listState,
                    contentPadding = PaddingValues(6.dp)
                ) {
                    items(messages, key = { it.id }) { msg ->
                        ChatBubble(msg)
                    }

                    if (isTyping) {
                        item { TypingBubble() }
                    }

                    // ✅ abre o relatório MAIS RECENTE salvo (correto do contexto)
                    val r = lastRelatorio
                    if (r != null) {
                        item {
                            TextButton(
                                onClick = {
                                    abrirRelatorioComChooser(
                                        context = context,
                                        relatorio = r,
                                        gerarPdf = ::criarPdfDoRelatorio
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Text("📄 Abrir relatório em PDF", color = Color.White)
                            }
                        }
                    }
                }

                QuickRepliesRow(
                    options = quickReplies,
                    onSelect = { selected ->
                        if (!isTyping) sendMessage(selected)
                    }
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                        .clip(RoundedCornerShape(999.dp))
                        .background(Color(0xFFEFF2F4)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { /* futuro */ }) {
                        Icon(Icons.Default.Add, "Mais", tint = Color(0xFF003E3A))
                    }

                    TextField(
                        value = userMessage,
                        onValueChange = { userMessage = it },
                        placeholder = { Text("Pergunte à Guardiã") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        )
                    )

                    val canSend = userMessage.isNotBlank()

                    IconButton(
                        onClick = {
                            if (!canSend) startVoiceInput() else sendMessage(userMessage)
                        }
                    ) {
                        if (canSend) {
                            Icon(Icons.Default.Send, "Enviar", tint = Color(0xFF003E3A))
                        } else {
                            Icon(Icons.Default.Mic, "Falar", tint = Color(0xFF003E3A))
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GuardiaScreenPreview() {
    MaterialTheme {
        GuardiaScreen()
    }
}
