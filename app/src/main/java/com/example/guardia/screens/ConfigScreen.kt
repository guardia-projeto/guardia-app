package com.example.guardia.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

// Reaproveitando a paleta da Home
private val AzureLight = Color(0xFFE8F5FF)
private val AzureMid   = Color(0xFFD3ECFF)
private val TitleDark  = Color(0xFF0E3B5E)
private val PrimaryTeal = Color(0xFF33B2B2)
private val PrimaryBlue = Color(0xFF0E6D90)
private val CardStroke = Color(0xFFE1ECF7)

// ====== TELA DE CONFIGURAÇÕES ======
@Composable
fun SettingsScreen(
    navController: NavHostController,
    onBackClick: () -> Unit = { navController.popBackStack() }
) {
    var notificationsEnabled by remember { mutableStateOf(true) }
    var sensitiveModeEnabled by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(AzureLight, AzureMid, AzureLight)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .verticalScroll(rememberScrollState())
        ) {

            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = TitleDark
                    )
                }

                Text(
                    text = "Configurações",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TitleDark
                )
            }

            // Subtítulo
            Text(
                text = "Ajuste como a Guardiã cuida de você e da sua família.",
                fontSize = 14.sp,
                color = TitleDark.copy(alpha = 0.8f),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // ==== BLOCO: Conta / Plano ====
            SettingsSectionTitle("Conta e plano")
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                ) {
                    SettingItem(
                        icon = Icons.Filled.Person,
                        title = "Dados do responsável",
                        subtitle = "Nome, e-mail e informações de contato",
                        onClick = {
                            // TODO: navegar para tela de perfil, se quiser
                            // navController.navigate("perfil")
                        }
                    )

                    DividerLine()

                    SettingItem(
                        icon = Icons.Filled.Star,
                        title = "Plano atual: Essentials",
                        subtitle = "Clique para ver opções de upgrade",
                        onClick = {
                            navController.navigate("upgrade")
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // ==== BLOCO: Segurança e privacidade ====
            SettingsSectionTitle("Segurança e privacidade")
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                ) {
                    SettingItem(
                        icon = Icons.Filled.Shield,
                        title = "Configurações de segurança",
                        subtitle = "Regras de uso, bloqueios e monitoramento",
                        onClick = {
                            // TODO: futura tela de segurança
                        }
                    )

                    DividerLine()

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Visibility,
                            contentDescription = null,
                            tint = PrimaryBlue,
                            modifier = Modifier.size(24.dp)
                        )
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 12.dp)
                        ) {
                            Text(
                                text = "Modo conteúdo sensível",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TitleDark
                            )
                            Text(
                                text = "Ocultar descrições mais gráficas nas explicações.",
                                fontSize = 12.sp,
                                color = TitleDark.copy(alpha = 0.75f)
                            )
                        }
                        Switch(
                            checked = sensitiveModeEnabled,
                            onCheckedChange = { sensitiveModeEnabled = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = PrimaryBlue
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // ==== BLOCO: Notificações ====
            SettingsSectionTitle("Notificações")
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Notifications,
                            contentDescription = null,
                            tint = PrimaryBlue,
                            modifier = Modifier.size(24.dp)
                        )
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 12.dp)
                        ) {
                            Text(
                                text = "Alertas da Guardiã",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TitleDark
                            )
                            Text(
                                text = "Receba avisos sobre riscos e atividades suspeitas.",
                                fontSize = 12.sp,
                                color = TitleDark.copy(alpha = 0.75f)
                            )
                        }
                        Switch(
                            checked = notificationsEnabled,
                            onCheckedChange = { notificationsEnabled = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = PrimaryTeal
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // ==== BLOCO: Preferências gerais ====
            SettingsSectionTitle("Preferências gerais")
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                ) {
                    SettingItem(
                        icon = Icons.Filled.Translate,
                        title = "Idioma",
                        subtitle = "Português (Brasil)",
                        onClick = {
                            // TODO: tela de idiomas no futuro
                        }
                    )

                    DividerLine()

                    SettingItem(
                        icon = Icons.Filled.Info,
                        title = "Sobre a Guardiã",
                        subtitle = "Versão do app, termos de uso e política de privacidade",
                        onClick = {
                            // TODO: navegar para tela 'sobre'
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // ==== BLOCO: Sair ====
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFDECEC)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            // TODO: lógica de logout
                        }
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Logout,
                        contentDescription = null,
                        tint = Color(0xFFD84343)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Sair da conta",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFFD84343)
                        )
                        Text(
                            text = "Você poderá entrar novamente quando quiser.",
                            fontSize = 12.sp,
                            color = Color(0xFFB45555)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

// ====== COMPONENTES REUTILIZÁVEIS ======

@Composable
private fun SettingsSectionTitle(text: String) {
    Text(
        text = text,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = TitleDark.copy(alpha = 0.9f),
        modifier = Modifier
            .padding(bottom = 6.dp, top = 4.dp)
    )
}

@Composable
private fun SettingItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = PrimaryBlue,
            modifier = Modifier.size(24.dp)
        )
        Column(
            modifier = Modifier
                .padding(start = 12.dp)
                .weight(1f)
        ) {
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = TitleDark
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = TitleDark.copy(alpha = 0.75f)
                )
            }
        }
    }
}

@Composable
private fun DividerLine() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .height(1.dp)
            .background(CardStroke.copy(alpha = 0.6f))
    )
}

// Preview
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingsScreenPreview() {
    val navController = rememberNavController()
    MaterialTheme {
        SettingsScreen(navController = navController)
    }
}
