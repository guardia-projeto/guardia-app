package com.example.guardia.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.guardia.R
import com.example.guardia.ui.theme.GuardiaTheme

@Composable
fun EditScreen(onUpdateClick: () -> Unit = {}) {
    var nome by remember { mutableStateOf("") }
    var sobrenome by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf("São Paulo") }
    var isStateDropdownExpanded by remember { mutableStateOf(false) }

    val brazilianStates = remember {
        listOf(
            "Acre", "Alagoas", "Amapá", "Amazonas", "Bahia", "Ceará", "Espírito Santo",
            "Goiás", "Maranhão", "Mato Grosso", "Minas Gerais", "Pará", "Paraíba", 
            "Paraná", "Pernambuco", "Piauí", "Rio de Janeiro", "Rio Grande do Norte",
            "Rio Grande do Sul", "Rondônia", "Roraima", "Santa Catarina", "São Paulo", 
            "Sergipe", "Tocantins"
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFB4F5EF), // Azul claro em cima
                        Color(0xFF6EA9CE)  // Azul escuro embaixo
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- Seção da Imagem de Perfil ---
            Box(
                modifier = Modifier
                    .size(170.dp)
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(contentAlignment = Alignment.BottomEnd) {
                    Image(
                        painter = painterResource(id = R.drawable.livia),
                        contentDescription = "Foto de Perfil",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                            .border(4.dp, White, CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    IconButton(
                        onClick = { /* Lógica para editar a foto */ },
                        modifier = Modifier
                            .size(40.dp)
                            .background(White, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar foto",
                            tint = Color(0xFF1D4ED8)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // --- Campos de Texto ---
            Column(modifier = Modifier.fillMaxWidth()) {
                StyledEditTextField(label = "Nome", value = nome, onValueChange = { nome = it })
                Spacer(modifier = Modifier.height(24.dp))
                StyledEditTextField(label = "Sobrenome", value = sobrenome, onValueChange = { sobrenome = it })
                Spacer(modifier = Modifier.height(24.dp))

                // --- Campo de Estado com Dropdown ---
                Box {
                    StyledEditTextField(
                        label = "Estado",
                        value = estado,
                        onValueChange = { estado = it },
                        readOnly = true, // Impede a edição direta pelo teclado
                        onClick = { isStateDropdownExpanded = true }, // Abre o menu ao clicar
                        trailingIcon = {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown", tint = Color(0xFF1E40AF))
                        }
                    )

                    DropdownMenu(
                        expanded = isStateDropdownExpanded,
                        onDismissRequest = { isStateDropdownExpanded = false },
                        modifier = Modifier.background(Color.White)
                    ) {
                        brazilianStates.forEach { stateName ->
                            DropdownMenuItem(text = { Text(stateName) }, onClick = {
                                estado = stateName
                                isStateDropdownExpanded = false
                            })
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(130.dp))

            // --- Botão Atualizar ---
            Button(
                onClick = onUpdateClick, // AQUI: Conectamos a função
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(30),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1E40AF)
                )
            ) {
                Text(text = "Atualizar", fontSize = 18.sp, color = White)
            }
        }
    }
}

/**
 * Componente reutilizável para um campo de texto com fundo e cantos arredondados.
 */
@Composable
fun StyledEditTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    readOnly: Boolean = false,
    onClick: () -> Unit = {},
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Column(modifier = Modifier.fillMaxWidth().clickable(onClick = onClick)) {
        Text(
            text = label,
            color = Color(0xFF1E40AF),
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 8.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(White.copy(alpha = 0.7f), shape = CircleShape)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier.weight(1f),
                    readOnly = readOnly,
                    textStyle = TextStyle(
                        color = Color(0xFF1E40AF),
                        fontSize = 18.sp
                    ),
                    cursorBrush = SolidColor(Color(0xFF1E40AF)),
                    singleLine = true
                )
                if (trailingIcon != null) {
                    trailingIcon()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun EditScreenPreview() {
    GuardiaTheme {
        EditScreen(onUpdateClick = {}) // Adicionado para o preview não quebrar
    }
}
