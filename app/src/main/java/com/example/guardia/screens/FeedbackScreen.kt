package com.example.guardia.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.guardia.data.feedback.FeedbackData
import com.example.guardia.data.feedback.FeedbackRepository
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope

// Cores
private val FeedbackBgTop = Color(0xFFBDEFFF)
private val FeedbackBgBottom = Color(0xFF7CB8E4)
private val FeedbackHeaderText = Color(0xFF0E3B5E)
private val FeedbackAccentBlue = Color(0xFF0052A3)
private val FeedbackAccentBlueDark = Color(0xFF003A70)
private val FeedbackCardBg = Color(0xF7FFFFFF)
private val FeedbackSubText = Color(0xFF6B7A90)

@Composable
fun FeedbackScreen(
    onBackClick: () -> Unit = {},
    onBottomItemClick: (String) -> Unit = {}
) {
    var selectedScore by remember { mutableStateOf<Int?>(null) }
    var expanded by remember { mutableStateOf(false) }
    var selectedType by remember { mutableStateOf("Sugestão") }
    var text by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val repo = remember { FeedbackRepository() }

    val feedbackOptions = listOf("Sugestão", "Elogio", "Problema", "Reclamação")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(FeedbackBgTop, FeedbackBgBottom)))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // ===== TOP BAR =====
            TopBar(onBackClick)

            Spacer(Modifier.height(10.dp))

            // ===== CARD PRINCIPAL =====
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .weight(1f),
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = FeedbackCardBg),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 18.dp, vertical = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    BlueHeader()

                    Spacer(Modifier.height(16.dp))

                    Text(
                        "O quanto você recomenda a Guardiã?",
                        fontSize = 14.sp,
                        color = FeedbackSubText,
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.height(16.dp))

                    // ===== NOTAS NPS =====
                    ScoreSelector(selectedScore) { selectedScore = it }

                    Spacer(Modifier.height(18.dp))

                    // ===== DROPDOWN =====
                    FeedbackTypeDropdown(
                        selectedType = selectedType,
                        expanded = expanded,
                        onExpandChange = { expanded = it },
                        options = feedbackOptions,
                        onSelect = { selectedType = it }
                    )

                    Spacer(Modifier.height(18.dp))

                    // ===== TEXTAREA =====
                    OutlinedTextField(
                        value = text,
                        onValueChange = { text = it },
                        placeholder = {
                            Text("Descreva o que está pensando...", color = FeedbackSubText)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                            .shadow(4.dp, RoundedCornerShape(16.dp)),
                        shape = RoundedCornerShape(16.dp),
                    )

                    Spacer(Modifier.height(22.dp))

                    // ===== BOTÃO ENVIAR (lógica corrigida) =====
                    Button(
                        onClick = {
                            if (selectedScore == null) {
                                Toast.makeText(
                                    context,
                                    "Escolha uma nota de 0 a 10.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@Button
                            }

                            isLoading = true

                            val data = FeedbackData(
                                score = selectedScore!!,
                                type = selectedType,
                                message = text,
                                timestamp = System.currentTimeMillis()
                            )

                            scope.launch {
                                // enviarFeedback deve retornar Boolean (true = sucesso, false = erro)
                                val ok = repo.enviarFeedback(data)
                                isLoading = false

                                if (ok) {
                                    Toast.makeText(
                                        context,
                                        "Feedback enviado! 💙",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    // limpa os campos
                                    selectedScore = null
                                    selectedType = "Sugestão"
                                    text = ""
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Erro ao enviar. Tente novamente.",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(16.dp),
                        enabled = !isLoading,
                        colors = ButtonDefaults.buttonColors(containerColor = FeedbackAccentBlue)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(22.dp)
                            )
                        } else {
                            Text(
                                "Enviar",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            GuardiaBottomBar(
                currentRoute = "feedback",
                onItemClick = onBottomItemClick
            )

            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun TopBar(onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "voltar",
                tint = FeedbackHeaderText,
                modifier = Modifier
                    .size(26.dp)
                    .clickable { onBackClick() }
            )

            Spacer(Modifier.width(12.dp))

            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(color = FeedbackHeaderText)) { append("Meus ") }
                    withStyle(
                        SpanStyle(
                            color = FeedbackAccentBlue,
                            fontWeight = FontWeight.Bold
                        )
                    ) { append("Feedbacks") }
                },
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White.copy(alpha = 0.9f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "destaque",
                    tint = FeedbackAccentBlue
                )
            }
        }
    }
}

@Composable
private fun BlueHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(18.dp))
            .clip(RoundedCornerShape(18.dp))
            .background(
                Brush.verticalGradient(
                    listOf(FeedbackAccentBlue, FeedbackAccentBlueDark)
                )
            )
            .padding(vertical = 10.dp, horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Somos todo ouvidos!",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun ScoreSelector(selected: Int?, onSelect: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        (0..10).forEach { number ->
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(
                        if (selected == number) FeedbackAccentBlue else Color.White
                    )
                    .border(1.dp, FeedbackAccentBlue, CircleShape)
                    .clickable { onSelect(number) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = number.toString(),
                    fontSize = 13.sp,
                    color = if (selected == number) Color.White else FeedbackHeaderText
                )
            }
        }
    }
}

@Composable
private fun FeedbackTypeDropdown(
    selectedType: String,
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    options: List<String>,
    onSelect: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                "Tipo de feedback",
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = FeedbackHeaderText
            )
            Spacer(Modifier.width(4.dp))
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = FeedbackSubText,
                modifier = Modifier.size(16.dp)
            )
        }

        Spacer(Modifier.height(6.dp))

        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
                onClick = { onExpandChange(true) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(selectedType, fontSize = 14.sp)
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { onExpandChange(false) }
            ) {
                options.forEach {
                    DropdownMenuItem(
                        text = { Text(it) },
                        onClick = {
                            onSelect(it)
                            onExpandChange(false)
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewFeedbackScreen() {
    MaterialTheme {
        FeedbackScreen()
    }
}
