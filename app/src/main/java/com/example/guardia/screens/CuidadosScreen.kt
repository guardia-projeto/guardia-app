package com.example.guardia.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PriorityHigh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.guardia.R
import com.example.guardia.ui.theme.GuardiaTheme

private val text_color = Color(0xFF1F367C)

@Composable
fun CuidadosScreen(onNavigateToGuardia: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFFD1EAE7), Color(0xFFB4F5EF), Color(0xFF88D3CE))))
            .verticalScroll(rememberScrollState())
    ) {
        // --- Título Superior ---
        CuidadosTopAppBar()

        Spacer(modifier = Modifier.height(24.dp))

        // --- Seção de Riscos com Imagem Sobreposta ---
        Box(modifier = Modifier.fillMaxWidth()) {
            // O conteúdo de texto é desenhado primeiro (atrás)
            Column {
                SectionHeader("Riscos do Ambiente Virtual e\nDesafios da Família")
                Spacer(modifier = Modifier.height(24.dp))
                Column(
                    modifier = Modifier.padding(start = 24.dp, end = 120.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    InfoText(
                        "O ambiente online apresenta riscos que vão além do uso excessivo, sendo classificados nos chamados “4 Cs”:",
                        highlights = emptyList()
                    )
                    InfoText(
                        "Conteúdo (Content): Refere-se à exposição a informações ou imagens prejudiciais e impróprias para a idade, como violência, pornografia, linguagem ofensiva, automutilação, ou discursos de ódio.",
                        highlights = listOf("Conteúdo (Content)")
                    )
                    InfoText(
                        "Contato (Contact): Envolve a interação com atores de risco (adultos mal-intencionados, criminosos, assediadores) ou com pares que promovem comportamentos prejudiciais.",
                        highlights = listOf("Contato (Contact)")
                    )
                    InfoText(
                        "Conduta (Conduct): Diz respeito às opções e procedimentos danosos que a criança ou o adolescente pode realizar ou ser incentivado.",
                        highlights = listOf("Conduta (Conduct)")
                    )
                    InfoText(
                        "Contrato (Contract): Relaciona-se aos termos de serviço das plataformas, que muitas vezes resultam na exploração de dados e da atenção do usuário, sem que este compreenda as contrapartidas impostas.",
                        highlights = listOf("Contrato (Contract)")
                    )
                }
            }

            // Seção Circulos coloridos
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 40.dp, y = 90.dp)
                    .size(130.dp)
                    .background(color = Color(0xFF4CA593), shape = CircleShape)
            )
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = (-25).dp, y = 65.dp) // AQUI: Ajuste fino da posição
                    .size(75.dp)
                    .background(color = text_color, shape = CircleShape)
            )

            // A imagem é desenhada por último (na frente)
            Image(
                painter = painterResource(id = R.drawable.png_guardia_duvida),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(230.dp)
                    .offset(x = 70.dp, y = 10.dp),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- Seção: Principais Riscos ---
        SectionCard("Principais Riscos Psicológicos e de Segurança")
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF4CA593), shape = RoundedCornerShape(20.dp))
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                RiskItem("Exposição a Conteúdo Inadequado")
                RiskItem("Cyberbullying")
                RiskItem("Privacidade e Segurança")
                RiskItem("Vício em Tecnologia/Uso Problemático")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- Seção: Impactos na Saúde Física e Cognitiva ---
        SectionCard("Impactos na Saúde Física e Cognitiva")
        Spacer(modifier = Modifier.height(16.dp))
        Column(modifier = Modifier.padding(horizontal = 24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            InfoText("Atrasos no Desenvolvimento: O uso excessivo de telas na primeira infância (até 6 anos) é um fator de risco para atrasos no desenvolvimento da fala e no desenvolvimento cognitivo.", highlights = listOf("Atrasos no Desenvolvimento:"))
            InfoText("Problemas Físicos: O tempo de tela contribui para o sedentarismo e a obesidade. A exposição prolongada causa problemas de visão, como miopia e fadiga visual.", highlights = listOf("Problemas Físicos:"))
            InfoText("Prejuízo Cognitivo: Muitos aplicativos promovem o 'estado de rolagem', enfraquecendo a capacidade de concentração.", highlights = listOf("Prejuízo Cognitivo:"))
            InfoText("Substituição do Brincar Livre: O tempo gasto em dispositivos digitais substitui o brincar livre, atividades fundamentais para o desenvolvimento social e cognitivo em suas várias dimensões.", highlights = listOf("Substituição do Brincar Livre:"))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- Seção: Impactos na Saúde Mental ---
        SectionCard("Impactos na Saúde Mental")
        Spacer(modifier = Modifier.height(16.dp))
        Column(modifier = Modifier.padding(horizontal = 24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            InfoText("Sintomas Psicopatológicos: O uso excessivo está associado a sintomas de ansiedade, depressão e agressividade, além de dificuldades de autorregulação.", highlights = listOf("Sintomas Psicopatológicos:"))
            InfoText("Redes Sociais e Vulnerabilidade: Estudos relacionam o uso problemático de redes sociais a:", highlights = listOf("Redes Sociais e Vulnerabilidade:"))
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(text = "• Problemas de Sono: Dificuldades para dormir e padrões de sono alterados.", color = text_color, fontSize = 14.sp, lineHeight = 20.sp)
                Text(text = "• Transtornos Alimentares, Programas de Autoimagem, devido à comparação e à exposição a padrões irreais.", color = text_color, fontSize = 14.sp, lineHeight = 20.sp)
                Text(text = "• FoMO (Fear of Missing Out): O 'medo de ficar de fora', gerando o desejo de estar constantemente conectado.", color = text_color, fontSize = 14.sp, lineHeight = 20.sp)
            }
            InfoText("Riscos Extremos: A exposição a cyberbullying, assédio, ou comunidades onde se abordam autolesão ou suicídio pode aumentar o risco de desenvolvimento de comportamento suicida ou de autolesão, especialmente em adolescentes que já enfrentam quadros de adoecimento mental.", highlights = listOf("Riscos Extremos:"))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- Seção: Canais de Denúncia ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(listOf(Color(0xFFD1EAE7), Color.White.copy(alpha = 0.5f))),
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                )
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "É crucial que familiares e educadores conheçam os canais para denunciar violações de direitos e crimes cibernéticos:",
                color = text_color,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                DenunciaChannel("Disque 100 ou 180", "Violações de direitos contra crianças e adolescentes.")
                DenunciaChannel("Polícia Federal (COMUNICA PF)", "Crimes cibernéticos com repercussão internacional, como abuso sexual infantil.")
                DenunciaChannel("Polícia Civil", "Recebimento e investigação de fatos criminosos ocorridos ou suspeitos.")
                DenunciaChannel("Conselho Tutelar", "Acolhimento da vítima e sua família, e garantia de cumprimento dos direitos.")
                DenunciaChannel("SAFERNET Brasil", "Denúncia anônima, segura e gratuita de conteúdos ilegais ou inadequados.")
                DenunciaChannel("Canal ESCOLA SEGURA", "Denúncias de ameaças e ataques contra escolas.")
            }
            Spacer(modifier = Modifier.height(32.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Ainda com dúvidas?", color = text_color, fontWeight = FontWeight.Bold)
                Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFD54F))
                Button(
                    onClick = onNavigateToGuardia, // AQUI: Conectamos a navegação
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White), 
                    shape = CircleShape
                ) {
                    Text(text = "Converse com a Guardiã", color = text_color, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// --- Componentes Reutilizáveis ---
@Composable
private fun CuidadosTopAppBar() {
    Column(modifier = Modifier.padding(top = 30.dp)) {
        Divider(color = Color(0xFFEBF8FF).copy(alpha = 0.5f), thickness = 5.dp)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent) // Fundo transparente
                .padding(vertical = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Cuidados nas Redes Sociais",
                color = text_color,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.White,
                        offset = Offset(0f, 0f),
                        blurRadius = 15f
                    )
                )
            )
        }
        Divider(color = Color(0xFFFEFEFF).copy(alpha = 0.5f), thickness = 5.dp)
    }
}

@Composable
fun SectionHeader(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF46B7A2))
            .padding(vertical = 15.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = title, color = Color(0xFF14265B), fontSize = 20.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
    }
}

@Composable
fun SectionCard(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White.copy(alpha = 0.7f), RoundedCornerShape(50))
                .padding(vertical = 12.dp, horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                color = text_color,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun InfoText(text: String, highlights: List<String>, color: Color = text_color) {
    Text(buildAnnotatedString {
        val parts = text.split(*highlights.toTypedArray()).filter { it.isNotEmpty() }
        var lastIndex = 0
        text.forEachIndexed { index, _ ->
            highlights.find { text.substring(index).startsWith(it) }?.let {
                if(index > lastIndex) {
                    append(text.substring(lastIndex, index))
                }
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(it)
                }
                lastIndex = index + it.length
            }
        }
        if(lastIndex < text.length) {
            append(text.substring(lastIndex))
        }
    }, color = color, fontSize = 14.sp, lineHeight = 20.sp)
}

@Composable
fun RiskItem(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(Color(0xFFFFD54F), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = Icons.Default.PriorityHigh, contentDescription = null, tint = Color.White)
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = text, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun DenunciaChannel(channel: String, description: String) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(text = channel, modifier = Modifier.weight(1f), color = Color(0xFF944E4E), fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Text(text = description, modifier = Modifier.weight(1.5f), color = text_color, fontSize = 14.sp)
    }
}


@Preview(showBackground = true)
@Composable
fun CuidadosScreenPreview() {
    GuardiaTheme {
        CuidadosScreen()
    }
}
