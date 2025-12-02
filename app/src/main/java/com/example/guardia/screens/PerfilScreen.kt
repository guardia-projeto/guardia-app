package com.example.guardia.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.guardia.R
import com.example.guardia.ui.theme.GuardiaTheme

@Composable
fun PerfilScreen(
    onItemClick: (String) -> Unit = {},
    onNavigateToEdit: () -> Unit,
    onNavigateToSecurity: () -> Unit,
    onNavigateToSaved: () -> Unit,
    onNavigateToPlans: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFB4F5EF),
                        Color(0xFF6EA9CE)
                    )
                )
            )
    ) {
        // ===== CONTEÚDO SCROLLÁVEL =====
        Column(
            modifier = Modifier
                .weight(1f) // ocupa o espaço de cima, deixando a bottom bar embaixo
                .verticalScroll(rememberScrollState())
                .padding(top = 32.dp, bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // FOTO
            Box(contentAlignment = Alignment.BottomEnd) {
                Image(
                    painter = painterResource(id = R.drawable.livia),
                    contentDescription = "Foto de Perfil",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .border(4.dp, Color.White, CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // NOME + INFO
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Livia Oliveira",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF14265B)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "São Paulo/SP",
                    fontSize = 16.sp,
                    color = Color(0xFF14265B)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // BOTÕES
            Column(verticalArrangement = Arrangement.spacedBy(25.dp)) {
                ProfileButton(
                    text = "Editar Perfil",
                    iconPainter = painterResource(id = R.drawable.person),
                    onClick = onNavigateToEdit
                )
                ProfileButton(
                    text = "Senha e segurança",
                    iconPainter = painterResource(id = R.drawable.shield_check),
                    onClick = onNavigateToSecurity // Conectado!
                )
                ProfileButton(
                    text = "Salvos",
                    iconPainter = painterResource(id = R.drawable.saved),
                    onClick = onNavigateToSaved // Conectado!
                )
                ProfileButton(
                    text = "Planos Guardiã",
                    iconPainter = painterResource(id = R.drawable.star),
                    onClick = onNavigateToPlans        // ➜ navega pra planos
                )
                ProfileButton(
                    text = "Ajuda",
                    iconPainter = painterResource(id = R.drawable.help)
                )
            }
        }

        // ===== BOTTOM BAR IGUAL ÀS OUTRAS TELAS =====
        GuardiaBottomBar(
            currentRoute = "perfil",
            onItemClick = { route -> onItemClick(route) }
        )
    }
}

// --- COMPONENTES DOS BOTÕES DO MEIO DA TELA ---
@Composable
fun ProfileButton(text: String, iconPainter: Painter, onClick: () -> Unit = {}) {
    ProfileButtonBase(text = text, onClick = onClick, iconContent = {
        Image(painter = iconPainter, contentDescription = text, modifier = Modifier.size(50.dp))
    })
}

@Composable
fun ProfileButtonBase(text: String, onClick: () -> Unit, iconContent: @Composable () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(35),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White.copy(alpha = 0.7f)
        ),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.8f)),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.Transparent, CircleShape)
                    .align(Alignment.CenterStart),
                contentAlignment = Alignment.Center
            ) {
                iconContent()
            }
            Text(
                text = text,
                color = Color(0xFF1E40AF),
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun CustomBottomAppBar(onItemClick: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp)
                .background(
                    color = Color(0xFFF0F4F8),
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                )
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onItemClick("perfil") }, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Outlined.Person, contentDescription = "Perfil", tint = Color(0xFF0E3B5E))
                }
                IconButton(onClick = { onItemClick("chat") }, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Outlined.ChatBubbleOutline, contentDescription = "Chat", tint = Color(0xFF0E3B5E))
                }
                Spacer(modifier = Modifier.width(60.dp))
                IconButton(onClick = { onItemClick("grupo") }, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Outlined.Group, contentDescription = "Grupo", tint = Color(0xFF0E3B5E))
                }
                IconButton(onClick = { onItemClick("config") }, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Outlined.Settings, contentDescription = "Configurações", tint = Color(0xFF0E3B5E))
                }
            }
        }

        FloatingActionButton(
            onClick = { onItemClick("home") },
            modifier = Modifier.align(Alignment.TopCenter),
            shape = CircleShape,
            containerColor = Color(0xFF0E3B5E),
            contentColor = Color.White
        ) {
            Icon(imageVector = Icons.Default.Home, contentDescription = "Início")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PerfilScreenPreview() {
    GuardiaTheme {
        PerfilScreen(
            onNavigateToEdit = {},
            onNavigateToPlans = {},
            onNavigateToSecurity = {},
            onNavigateToSaved = {},
            onItemClick = {}
        )
    }
}
