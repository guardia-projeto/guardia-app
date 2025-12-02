package com.example.guardia.screens

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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Paleta bem próxima do protótipo dos relatórios
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

    val feedbackOptions = listOf("Sugestão", "Elogio", "Problema", "Reclamação")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        FeedbackBgTop,
                        FeedbackBgBottom
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            // ===== TOP BAR – estilo "Meus Relatórios" =====
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
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

                    // "Meus Feedbacks" com a segunda palavra destacada
                    Text(
                        text = buildAnnotatedString {
                            withStyle(SpanStyle(color = FeedbackHeaderText)) {
                                append("Meus ")
                            }
                            withStyle(
                                SpanStyle(
                                    color = FeedbackAccentBlue,
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append("Feedbacks")
                            }
                        },
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(Modifier.weight(1f))

                    // Ícone de destaque na direita (como o clipboard dos relatórios)
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

                // Linha separadora inferior
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.White.copy(alpha = 0.55f))
                )
            }

            Spacer(Modifier.height(10.dp))

            // ===== CARD PRINCIPAL (como se fosse um "card de lista grande") =====
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .weight(1f),
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(
                    containerColor = FeedbackCardBg
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 18.dp, vertical = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Faixa superior azul – remete à faixa do topo dos relatórios
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(
                                elevation = 4.dp,
                                shape = RoundedCornerShape(18.dp),
                                clip = false
                            )
                            .clip(RoundedCornerShape(18.dp))
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        FeedbackAccentBlue,
                                        FeedbackAccentBlueDark
                                    )
                                )
                            )
                            .padding(vertical = 10.dp, horizontal = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Somos todo ouvidos!",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(Modifier.height(12.dp))

                    Text(
                        text = "O quanto você recomenda a Guardiã para amigos e colegas?",
                        fontSize = 14.sp,
                        color = FeedbackSubText,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    Spacer(Modifier.height(16.dp))

                    // ===== LABEL NPS =====
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Sua nota",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = FeedbackHeaderText
                        )

                        Text(
                            "0 = Não recomendaria   |   10 = Recomendo muito",
                            fontSize = 11.sp,
                            color = FeedbackSubText
                        )
                    }

                    Spacer(Modifier.height(10.dp))

                    // ===== NPS 0–10 =====
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                            .padding(horizontal = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        (0..10).forEach { number ->
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (selectedScore == number)
                                            FeedbackAccentBlue
                                        else Color.White
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = FeedbackAccentBlue,
                                        shape = CircleShape
                                    )
                                    .clickable { selectedScore = number },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = number.toString(),
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = if (selectedScore == number)
                                        Color.White
                                    else
                                        FeedbackHeaderText
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(20.dp))

                    // ===== DROPDOWN =====
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
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
                                onClick = { expanded = true },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = Color(0xFFF6FAFF),
                                    contentColor = FeedbackHeaderText
                                ),
                                border = ButtonDefaults.outlinedButtonBorder.copy(
                                    width = 1.dp,
                                    brush = Brush.linearGradient(
                                        listOf(
                                            FeedbackAccentBlue.copy(alpha = 0.6f),
                                            FeedbackAccentBlue.copy(alpha = 0.3f)
                                        )
                                    )
                                )
                            ) {
                                Text(
                                    selectedType,
                                    fontSize = 14.sp
                                )
                            }

                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                feedbackOptions.forEach {
                                    DropdownMenuItem(
                                        text = { Text(it) },
                                        onClick = {
                                            selectedType = it
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    Spacer(Modifier.height(18.dp))

                    // ===== TEXTAREA =====
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            "Deixe suas observações (Opcional)",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = FeedbackHeaderText
                        )

                        Spacer(Modifier.height(6.dp))

                        OutlinedTextField(
                            value = text,
                            onValueChange = { text = it },
                            placeholder = {
                                Text(
                                    "Descreva o que está pensando...",
                                    color = FeedbackSubText.copy(alpha = 0.7f)
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(130.dp)
                                .shadow(
                                    elevation = 4.dp,
                                    shape = RoundedCornerShape(16.dp),
                                    clip = false
                                ),
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = FeedbackAccentBlue,
                                unfocusedBorderColor = Color(0xFFCED7E5),
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White
                            )
                        )
                    }

                    Spacer(Modifier.height(22.dp))

                    // ===== BOTÃO ENVIAR =====
                    Button(
                        onClick = { /* TODO enviar depois */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = FeedbackAccentBlue
                        )
                    ) {
                        Text(
                            "Enviar",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            // ===== BOTTOM NAVIGATION BAR =====
            GuardiaBottomBar(
                currentRoute = "feedback",
                onItemClick = onBottomItemClick
            )

            Spacer(Modifier.height(8.dp))
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
