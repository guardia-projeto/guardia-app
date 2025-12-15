package com.example.guardia.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.guardia.R

private val primary_bg_color = Color(0xFF8E94FF)
private val card_bg_color = Color(0xFF565BAD)
private val section_header_bg_color = Color(0xFF14265B)
private val text_color_on_dark = Color.White
private val title_color = Color(0xFF1F367C)

@Composable
fun PerigoScreen(onNavigateToGuardia: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(primary_bg_color)
            .verticalScroll(rememberScrollState())
    ) {
        PerigoTopAppBar()

        Spacer(modifier = Modifier.height(20.dp))

        // Header com imagem e círculos decorativos (RESPONSIVO: altura varia com a largura)
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val w = maxWidth

            // ✅ Altura responsiva do header (evita ficar "gigante" em alguns celulares)
            val headerHeight = (w * 0.70f).coerceIn(220.dp, 290.dp)
            val h = headerHeight

            // --- valores responsivos (com limites para não ficar gigante/pequeno demais)
            val circle1: Dp = (w * 0.28f).coerceIn(80.dp, 120.dp)
            val circle2: Dp = (w * 0.20f).coerceIn(60.dp, 95.dp)
            val circle3: Dp = (w * 0.12f).coerceIn(36.dp, 60.dp)

            // Círculos (posições responsivas)
            val c1x = (w * 0.04f)
            val c1y = (h * 0.64f)

            val c2x = (w * 0.13f)
            val c2y = (h * 0.54f)

            val c3x = (w * 0.22f)
            val c3y = (h * 0.47f)

            // Texto de introdução: posição responsiva (não some e não invade)
            val introStart = (w * 0.45f).coerceIn(130.dp, 210.dp)
            val introTopFix = 22.dp // ↑ aumenta pra descer mais
            val introTop = ((h * 0.34f) + introTopFix).coerceIn(86.dp, 150.dp)
            val introMaxWidth = (w * 0.52f).coerceIn(160.dp, 260.dp)

            // Imagem responsiva
            val imgSize = (w * 0.78f).coerceIn(220.dp, 320.dp)
            val imgOffsetX = -(w * 0.10f).coerceIn(18.dp, 50.dp)
            val imgOffsetY = (h * 0.34f).coerceIn(72.dp, 120.dp)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(headerHeight)
            ) {
                // Texto de introdução (fundo)
                Text(
                    text = "Os jogos online, por sua natureza interativa e muitas vezes anônima, criam um ecossistema com vulnerabilidades específicas que exigem atenção redobrada dos pais e cuidadores.",
                    color = text_color_on_dark,
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                    modifier = Modifier
                        .offset(x = introStart, y = introTop)
                        .widthIn(max = introMaxWidth)
                        .padding(end = 12.dp)
                )

                // Header principal
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(section_header_bg_color)
                        .padding(vertical = 16.dp, horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Riscos do Ambiente dos Jogos Online:",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "A diversão também merece atenção!",
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }

                // Círculos decorativos
                Box(
                    modifier = Modifier
                        .offset(x = c1x, y = c1y)
                        .size(circle1)
                        .background(color = Color(0xFF323ACE), shape = CircleShape)
                )
                Box(
                    modifier = Modifier
                        .offset(x = c2x, y = c2y)
                        .size(circle2)
                        .background(color = card_bg_color, shape = CircleShape)
                )
                Box(
                    modifier = Modifier
                        .offset(x = c3x, y = c3y)
                        .size(circle3)
                        .background(color = Color(0xFF0A0647), shape = CircleShape)
                )

                // Imagem do personagem
                Image(
                    painter = painterResource(id = R.drawable.perigo),
                    contentDescription = "Personagem Guardiã",
                    modifier = Modifier
                        .size(imgSize)
                        .align(Alignment.CenterStart)
                        .offset(x = imgOffsetX, y = imgOffsetY)
                        .scale(1.2f),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        TitleCard("Perigos de Interação e Conteúdo (Contato e Conduta)")

        Spacer(modifier = Modifier.height(16.dp))

        InfoText(
            "Os jogos online apresentam riscos significativos para os jovens, que vão além do cyberbullying. Os perigos se destacam por ocorrerem em ambientes fechados e imersivos, dos quais a vítima depende para se divertir, tornando a fuga mais difícil.",
            modifier = Modifier.padding(horizontal = 24.dp),
            color = text_color_on_dark
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Principais riscos - Card escuro
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(card_bg_color)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Principais riscos:",
                color = text_color_on_dark,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.White.copy(alpha = 0.3f),
                        offset = Offset(0f, 0f),
                        blurRadius = 8f
                    )
                )
            )
            RiskInfoItem(
                "Assédio e Comportamento Tóxico:",
                "Inclui sabotagem de jogo (\"griefing\"), rumores e linguagem agressiva. O anonimato e a competição frequentemente levam à naturalização de discursos de ódio, como racismo e homofobia."
            )
            RiskInfoItem(
                "Exposição a Conteúdo Inadequado:",
                "As plataformas são usadas para disseminar ideologias extremistas em comunidades privadas e há exposição, acidental ou não, a pornografia e violência extrema em chats e servidores."
            )
            RiskInfoItem(
                "Ação de Predadores (Grooming):",
                "Predadores abordam jovens de forma gradual, construindo confiança durante o jogo para depois isolá-los e manipulá-los a compartilhar informações íntimas ou pessoais, explorando vulnerabilidades como a solidão."
            )
            RiskInfoItem(
                "Jogos de Azar Disfarçados:",
                "Mecanismos como \"loot boxes\" e \"Gacha\" funcionam como apostas, incentivando gastos repetidos pela chance de itens raros. Essa exposição precoce pode naturalizar o vício em jogos de azar na vida adulta."
            )
        }

        // Riscos adicionais - Fundo roxo claro
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(primary_bg_color)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            RiskInfoItem(
                "Vazamento de Dados:",
                "Empresas coletam muitos dados pessoais, que podem ser vazados em falhas de segurança, levando a roubo de identidade e outros crimes."
            )
            RiskInfoItem(
                "Gastos Inesperados:",
                "O modelo \"freemium\" (grátis para jogar) incentiva compras dentro do aplicativo para progresso, o que pode resultar em cobranças acidentais e elevadas no cartão de crédito, especialmente por crianças."
            )
            RiskInfoItem(
                "Invasão de Dispositivos:",
                "Ao baixar mods, \"cheats\" ou jogos piratas, o usuário pode instalar malware sem saber. Esse software malicioso pode roubar dados ou até mesmo assumir o controle da webcam e do microfone."
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        TitleCard("Benefícios (Com Moderação e Supervisão)")

        Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)) {
            BenefitItem(
                "Desenvolvimento Cognitivo",
                "Jogos de estratégia (RTS) ou quebra-cabeças complexos aprimoram o raciocínio lógico, a memória de trabalho e a capacidade de tomar decisões rápidas sob pressão."
            )
            HorizontalDivider(
                color = Color.White.copy(alpha = 0.3f),
                modifier = Modifier.padding(vertical = 8.dp)
            )
            BenefitItem(
                "Socialização e Cooperação",
                "Jogos cooperativos exigem comunicação efetiva, trabalho em equipe, e resolução de conflitos em tempo real, habilidades sociais cruciais que são treinadas no ambiente seguro do jogo."
            )
            HorizontalDivider(
                color = Color.White.copy(alpha = 0.3f),
                modifier = Modifier.padding(vertical = 8.dp)
            )
            BenefitItem(
                "Criatividade e Resolução de Problemas",
                "Jogos \"sandbox\" (como Minecraft ou Roblox) incentivam a imaginação, a criação de estruturas complexas e o planejamento de longo prazo."
            )
            HorizontalDivider(
                color = Color.White.copy(alpha = 0.3f),
                modifier = Modifier.padding(vertical = 8.dp)
            )
            BenefitItem(
                "Coordenação Motora e Reflexos",
                "Jogos de ação ou esportivos aprimoram a coordenação óculo-manual e o tempo de reação a estímulos visuais e sonoros."
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Botão de dúvidas
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Ainda com dúvidas?",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFFFD700),
                    modifier = Modifier.size(24.dp)
                )
            }
            Button(
                onClick = onNavigateToGuardia,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(48.dp)
            ) {
                Text(
                    text = "Converse com a Guardiã",
                    color = title_color,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

// --- Componentes Reutilizáveis ---

@Composable
private fun PerigoTopAppBar() {
    Column {
        HorizontalDivider(color = Color.White.copy(alpha = 0.5f), thickness = 1.dp)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(card_bg_color)
                .padding(vertical = 14.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Perigo dos jogos online",
                color = text_color_on_dark,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.White.copy(alpha = 0.4f),
                        offset = Offset(0f, 0f),
                        blurRadius = 8f
                    )
                )
            )
        }
        HorizontalDivider(color = Color.White.copy(alpha = 0.5f), thickness = 1.dp)
    }
}

@Composable
private fun RiskInfoItem(title: String, description: String) {
    Column {
        Text(
            title,
            color = text_color_on_dark,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            description,
            color = text_color_on_dark.copy(alpha = 0.95f),
            fontSize = 13.sp,
            lineHeight = 18.sp
        )
    }
}

@Composable
private fun BenefitItem(title: String, description: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Text(
            title,
            modifier = Modifier.weight(1f),
            color = text_color_on_dark,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            lineHeight = 18.sp
        )
        VerticalDivider(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 12.dp)
                .width(1.dp),
            color = Color.White.copy(alpha = 0.5f)
        )
        Text(
            description,
            modifier = Modifier.weight(1.5f),
            color = text_color_on_dark,
            fontSize = 13.sp,
            lineHeight = 17.sp
        )
    }
}

@Composable
private fun TitleCard(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(card_bg_color)
            .padding(vertical = 14.dp, horizontal = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = text_color_on_dark,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp,
            style = TextStyle(
                shadow = Shadow(
                    color = Color.White.copy(alpha = 0.3f),
                    offset = Offset.Zero,
                    blurRadius = 8f
                )
            )
        )
    }
}

@Composable
private fun InfoText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = text_color_on_dark
) {
    Text(
        text = text,
        color = color,
        fontSize = 13.sp,
        lineHeight = 18.sp,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun PerigoScreenPreview() {
    PerigoScreen()
}
