package com.example.guardia.screens

// ===== IMPORTS =====
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

// ===== CORES ESPECÍFICAS DA TELA DE UPGRADE =====
private val UpgradeTopLight = Color(0xFFCFF5FF)     // fundo top
private val UpgradeBottomDark = Color(0xFF355A86)   // fundo embaixo
private val UpgradeCardBg = Color(0xFFE0F7FA)       // fundo card
private val UpgradeCardText = Color(0xFF0E3B5E)     // textos principais
private val UpgradeBarBlue = Color(0xFF2F7FC9)      // barra azul
private val UpgradePrimaryGreen = Color(0xFF00BFA5) // botão upgrade
private val UpgradeStarYellow = Color(0xFFFFD54F)   // estrela topo
private val UpgradeStarSoft = Color(0xFFFFF59D)     // estrela botão
private val UpgradeCurrentPlanButton = Color(0xFFE0F2F1) // botão "Este é seu plano"
private val UpgradePriceLilac = Color(0xFF7F8CF2)   // cor dos preços / subtítulo

// ===== TELA PRINCIPAL =====
@Composable
fun UpgradeScreen(
    navController: NavHostController? = null,
    onBackClick: () -> Unit = {
        navController?.popBackStack()
    }
) {
    // Fundo com gradiente igual à referência
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        UpgradeTopLight,
                        UpgradeBottomDark
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .verticalScroll(rememberScrollState())
        ) {

            // ===== TOPO: Voltar + Título + Estrela =====
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Voltar",
                        tint = UpgradeCardText
                    )
                }

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = "Upgrade Guardiã",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = UpgradeCardText
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Upgrade",
                    tint = UpgradeStarYellow,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ===== SUBTÍTULO =====
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color(0x80D9F5FF),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "Este é o futuro da proteção digital.",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        color = UpgradeCardText
                    )
                )
                Text(
                    text = "Selecione sua assinatura.",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = UpgradeCardText
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ===== ESSENTIALS (PLANO ATUAL) =====
            PlanCard(
                title = "Essentials",
                price = "Gratuito",
                descriptionParagraph = "Para quem precisa agir rápido e com simplicidade. Converse, receba orientações da IA e acesse conteúdos educativos.",
                bulletItems = listOf(
                    "Formulário de denúncia guiado",
                    "Assistente de IA de apoio",
                    "Dashboard do responsável (histórico 90 dias)",
                    "Biblioteca de materiais educativos",
                    "Suporte por e-mail (até 72h)"
                ),
                buttonText = "Este é seu plano",
                isCurrentPlan = true,
                showStarIcon = false,
                showLinkStyleOnParagraph = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ===== PLUS =====
            PlanCard(
                title = "Plus",
                price = "R$ 29/mês",
                descriptionParagraph = "Detecção inteligente com triagem humana. Priorize casos graves com análise especializada.",
                bulletItems = listOf(
                    "Tudo do Essentials",
                    "Monitoramento ampliado",
                    "IA com pontuação de risco",
                    "1 análise humana gratuita por mês",
                    "Alertas push/SMS e relatórios prontos"
                ),
                buttonText = "Fazer Upgrade",
                isCurrentPlan = false,
                showStarIcon = true,
                showLinkStyleOnParagraph = false
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ===== PREMIUM =====
            PlanCard(
                title = "Premium",
                price = "R$ 79/mês",
                descriptionParagraph = "Atendimento humano completo e prioritário. Apoio jurídico e psicológico até a resolução do caso.",
                bulletItems = listOf(
                    "Tudo do Plus",
                    "Até 5 análises humanas por mês",
                    "SLA de resposta prioritária",
                    "Onboarding personalizado",
                    "Consultoria jurídica e psicológica"
                ),
                buttonText = "Fazer Upgrade",
                isCurrentPlan = false,
                showStarIcon = true,
                showLinkStyleOnParagraph = false
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

// ===== COMPONENTE DO CARD DE PLANO =====
@Composable
private fun PlanCard(
    title: String,
    price: String,
    descriptionParagraph: String,
    bulletItems: List<String>,
    buttonText: String,
    isCurrentPlan: Boolean,
    showStarIcon: Boolean,
    showLinkStyleOnParagraph: Boolean,
    onLinkClick: () -> Unit = {} // caso queira ligar isso depois a outra tela
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = UpgradeCardBg
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 18.dp, vertical = 16.dp)
        ) {

            // Título + preço + (estrela no Plus/Premium)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = UpgradeCardText,
                            fontSize = 20.sp
                        )
                    )
                    Text(
                        text = price,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = UpgradePriceLilac,
                            fontSize = 15.sp
                        )
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                if (showStarIcon) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = UpgradeStarYellow,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))


            Text(
                text = descriptionParagraph,
                style = TextStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    color = UpgradeCardText,
                    textDecoration = TextDecoration.None
                ),
                modifier = if (showLinkStyleOnParagraph) {
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 6.dp)
                        .let { mod ->
                            // No futuro: .clickable { onLinkClick() }
                            mod
                        }
                } else {
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 6.dp)
                }
            )

            // Linha divisória
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0x80C4E3ED))
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Lista de benefícios
            bulletItems.forEach { item ->
                Row(
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.padding(bottom = 4.dp)
                ) {
                    Text(
                        text = "• ",
                        color = UpgradeCardText,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                    Text(
                        text = item,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = UpgradeCardText,
                            fontSize = 13.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botão
            val buttonColor = 
                if (isCurrentPlan) UpgradeCurrentPlanButton else UpgradePrimaryGreen
            val textColor = 
                if (isCurrentPlan) UpgradeCardText else Color.White

            Button(
                onClick = {
                    // TODO: ação de upgrade ou nada se já é o atual
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonColor
                ),
                shape = RoundedCornerShape(50)
            ) {
                Text(
                    text = buttonText,
                    color = textColor,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )

                if (!isCurrentPlan) {
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = UpgradeStarSoft,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

// ===== PREVIEW =====
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UpgradeScreenPreview() {
    val navController = rememberNavController()
    MaterialTheme {
        UpgradeScreen(navController = navController)
    }
}
