package com.example.guardia.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.guardia.R

// === DEFINIÇÃO DAS CORES (PADRONIZADAS) ===
private val brand900 = Color(0xFF1F367C) // Azul Escuro (Texto)
private val brand200 = Color(0xFFCDE0FF) // Azul Claro (Fundo)
private val yellow50 = Color(0xFFFFEB3B) // Amarelo (Destaque)
private val redDark = Color(0xFFB71C1C)  // Vermelho Escuro
private val redLight = Color(0xFFE53935) // Vermelho Claro

@Composable
fun GroomingScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brand200)
            .verticalScroll(scrollState)
    ) {

        // === 1. TÍTULO SUPERIOR ===
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, bottom = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Glossário Grooming",
                color = brand900,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // === 2. HERO SECTION ===
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            // Bola Vermelha Esquerda (Média)
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterStart)
                    .offset(x = (-20).dp, y = (-20).dp)
                    .background(redDark, CircleShape)
            )

            // Bola Vermelha Pequena (Abaixo da esquerda)
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .align(Alignment.BottomStart)
                    .offset(x = 40.dp, y = (-10).dp)
                    .background(redLight, CircleShape)
            )

            // Bola Vermelha Grande (Direita - Cortada)
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = 40.dp, y = 0.dp)
                    .background(redDark, CircleShape)
            )

            // A Pílula Branca
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(Color.White, RoundedCornerShape(50.dp))
                    .padding(horizontal = 40.dp, vertical = 20.dp)
            ) {
                Text(
                    text = "O que é\nGrooming?",
                    color = brand900,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = 28.sp
                )
            }
        }

        // === 3. TEXTO INTRODUTÓRIO ===
        Text(
            text = "Grooming é o processo que um predador (aliciador) usa para ganhar a confiança de uma criança ou adolescente na internet. É uma manipulação lenta e calculada.",
            color = brand900,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp)
        )

        // === 4. FAIXA AMARELA ===
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(yellow50)
                .padding(vertical = 20.dp, horizontal = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Códigos mais comuns usados por aliciadores para se comunicar e camuflar na internet:",
                color = brand900,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 17.sp
            )
        }

        // === 5. A LISTA DE ITENS ===
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            GroomingItem("Stars (Estrelas)", "Criança \"populares\" por suas fotos famosas trocadas em grupos e fóruns.")
            GroomingItem("DM/Kik/Wickr/Snap", "Pedido para migrar a conversa para chats privados e criptografados.")
            GroomingItem("Acompanhamento/Patrocínio", "Referência a Sugar Daddy/Mommy ou promessas de presentes.")
            GroomingItem("ASL", "Age, Sex, Location (Idade, Sexo, Localização).")
            GroomingItem("M / F", "Male / Female (Masculino / Feminino).")
            GroomingItem("MIRL", "Meet in real life.")
            GroomingItem("IYKYK", "If You Know, You Know: código secreto.")
            GroomingItem("KOTC", "Kiss on the cheek.")
            GroomingItem("LSKOL", "Long slow kiss on the lips.")
            GroomingItem("PS (Parents)", "Parents: Alerta sobre presença dos pais.")
            GroomingItem("Upado", "Upload: subindo conteúdo.")
            GroomingItem("Down", "Download: baixando conteúdo.")
            GroomingItem("Trade DMs", "Troca de material via DM.")
            GroomingItem("G.A.S.", "Gamer as Sender ou código para abuso.")
            GroomingItem("Sua Princesa", "Afeto excessivo para criar intimidade falsa.")
            GroomingItem("👀 (Olhos)", "Espiar/Observar a vítima.")
            GroomingItem("😋 (Rosto)", "Gostou do conteúdo.")
            GroomingItem("🤸", "Pode referir a posições sexuais.")
            GroomingItem("👉👈", "Timidez fingida para manipular.")
            GroomingItem("🌽 (milho)", "Tradução de corn -> porn.")
            GroomingItem("🌀", "Interesse sexual por meninos.")
            GroomingItem("🍜", "Noodles soa como nudes.")
            GroomingItem("💗 e 🧀", "Busca por imagens de meninas.")
            GroomingItem("🍭", "Remete à obra Lolita.")
            GroomingItem("🍬 e 🍕", "Códigos para interesse em crianças.")
            GroomingItem("👉 + 👌", "Representar relação sexual.")
            GroomingItem("🍆 / 🍌", "Representação da genitália masculina.")
            GroomingItem("😏", "Sorriso Malicioso.")
        }

        // === 6. BOX DE ATENÇÃO ===
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 10.dp)
                .shadow(
                    elevation = 6.dp,
                    shape = RoundedCornerShape(30.dp),
                    spotColor = Color.Black.copy(alpha = 0.2f)
                )
                .background(yellow50, RoundedCornerShape(30.dp))
                .border(4.dp, Color.White, RoundedCornerShape(30.dp))
                .padding(24.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Atenção:",
                    fontWeight = FontWeight.Black,
                    fontSize = 20.sp,
                    color = brand900,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                Text(
                    text = "Os aliciadores usam uma linguagem secreta de códigos e símbolos para agir sem serem detectados online. Eles evoluem, mas a Guardiã também, trabalhando incessantemente para desvendar cada novo truque e manter a segurança de todos.",
                    textAlign = TextAlign.Center,
                    color = brand900,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // === 7. RODAPÉ AJUSTADO ===
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(bottom = 20.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // IMAGEM DA MENINA
            Image(
                painter = painterResource(id = R.drawable.guardi),
                contentDescription = "Personagem Guardiã",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .height(180.dp)
                    .offset(x = (-10).dp, y = 10.dp)
            )

            // COLUNA DA DIREITA
            Column(
                modifier = Modifier
                    .padding(end = 24.dp, bottom = 20.dp)
                    .weight(1f),
                horizontalAlignment = Alignment.End
            ) {
                // Texto "Ainda com dúvidas?"
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "Ainda com\ndúvidas?",
                        color = brand900,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.End,
                        fontSize = 15.sp,
                        lineHeight = 18.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFD54F),
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // BOTÃO
                Button(
                    onClick = { navController.navigate("guardia") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White.copy(alpha = 0.4f),
                        contentColor = brand900
                    ),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, Color.White),
                    shape = RoundedCornerShape(50),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp)
                ) {
                    Text(
                        text = "Converse com a Guardiã",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
    }
}

// === COMPONENTE ITEM DA LISTA ===
@Composable
fun GroomingItem(title: String, desc: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            "•",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = brand900,
            modifier = Modifier.padding(end = 8.dp) // ✅ sem padding negativo
        )
        Text(
            buildAnnotatedString {
                withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = brand900)) {
                    append(title)
                }
                withStyle(SpanStyle(color = brand900)) {
                    append(" $desc")
                }
            },
            fontSize = 14.sp,
            lineHeight = 18.sp
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewGroomingScreen() {
    val navController = rememberNavController()
    MaterialTheme {
        GroomingScreen(navController)
    }
}
