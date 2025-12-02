package com.example.guardia.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.guardia.ui.theme.GuardiaTheme

@Composable
fun SenhaScreen(
    onBackClick: () -> Unit = {},
    onItemClick: (String) -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    Scaffold(
        topBar = { SenhaTopAppBar(onBackClick = onBackClick) },
        bottomBar = { GuardiaBottomBar(currentRoute = "senha", onItemClick = onItemClick) },
        containerColor = Color(0xFF88D3CE)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Gerencie sua senha, email e preferência de login",
                color = Color(0xFF14265B),
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(60.dp))

            InfoField(label = "Email", value = email, onValueChange = { email = it })

            Spacer(modifier = Modifier.height(24.dp))

            InfoField(
                label = "Senha",
                value = senha,
                onValueChange = { senha = it },
                visualTransformation = PasswordVisualTransformation('●')
            )

            Spacer(modifier = Modifier.height(80.dp))

            Button(
                onClick = { /* TODO: Alterar Email */ },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(20),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E40AF))
            ) {
                Text(text = "Alterar Email", fontSize = 20.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /* TODO: Alterar Senha */ },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(20),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E40AF))
            ) {
                Text(text = "Alterar senha", fontSize = 20.sp, color = Color.White)
            }
        }
    }
}


@Composable
private fun SenhaTopAppBar(onBackClick: () -> Unit) {
    Column(modifier = Modifier
        .padding(top = 30.dp)) { 

        Divider(color = Color.White.copy(alpha = 0.7f), thickness = 3.dp)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White.copy(alpha = 0.3f))
                .padding(vertical = 4.dp, horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Voltar",
                    tint = Color(0xFF1E40AF)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Senha e segurança",
                color = Color(0xFF1E40AF),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(48.dp)) 
        }
        Divider(color = Color.White.copy(alpha = 0.7f), thickness = 3.dp)
    }
}

@Composable
private fun InfoField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = Color(0xFF14265B),
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 8.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White.copy(alpha = 0.4f), shape = CircleShape) 
                .border(1.dp, Color.White.copy(alpha = 0.8f), CircleShape) 
                .padding(horizontal = 16.dp, vertical = 14.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(
                    color = Color(0xFF1E40AF).copy(alpha = 0.8f),
                    fontSize = 20.sp
                ),
                visualTransformation = visualTransformation,
                cursorBrush = SolidColor(Color(0xFF1E40AF)),
                singleLine = true
            )
        }
    }
}

// A função GuardiaBottomBar duplicada foi removida.
// Agora o arquivo usará a versão principal do seu projeto.

@Preview(showBackground = true)
@Composable
fun SenhaScreenPreview() {
    GuardiaTheme {
        SenhaScreen()
    }
}
