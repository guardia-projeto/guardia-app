package com.example.guardia.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.guardia.R
import com.example.guardia.ui.theme.GuardiaTheme

// Paleta puxando pro estilo do app
private val AzureLight = Color(0xFFE8F5FF)
private val AzureMid   = Color(0xFFD3ECFF)
private val TitleDark  = Color(0xFF0E3B5E)
private val PrimaryTeal = Color(0xFF33B2B2)
private val PrimaryBlue = Color(0xFF0E6D90)

// AQUI: Login moderno com cara de Guardiã
@Composable
fun LoginScreen(
    onRegisterClick: () -> Unit,
    onLoginClick: (email: String, password: String) -> Unit = { _, _ -> }
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFFB2EBF2),
                        Color(0xFFE0F7FA),
                        Color(0xFF8EC7E3)
                    )
                )
            )

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            // Logo / Escudo
            Image(
                painter = painterResource(id = R.drawable.shield),
                contentDescription = "Logo Guardiã",
                modifier = Modifier
                    .size(150.dp)
                    .padding(top = 8.dp, bottom = 4.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = "Bem-vindo(a) de volta",
                fontSize = 22.sp,
                color = TitleDark,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Segurança na tela proteção para a vida",
                fontSize = 13.sp,
                color = TitleDark.copy(alpha = 0.8f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
            )

            // Card branco com os campos
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.96f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp, vertical = 22.dp),
                    horizontalAlignment = Alignment.Start
                ) {

                    // Título card
                    Text(
                        text = "Login",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TitleDark,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // EMAIL
                    Text(
                        text = "E-mail",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TitleDark,
                        modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
                    )

                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier
                            .fillMaxWidth(),
                        placeholder = { Text("seuemail@gmail.com") },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = TitleDark,
                            unfocusedTextColor = TitleDark,
                            cursorColor = PrimaryBlue,
                            focusedContainerColor = Color(0xFFF4F7FB),
                            unfocusedContainerColor = Color(0xFFF4F7FB),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // SENHA
                    Text(
                        text = "Senha",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TitleDark,
                        modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
                    )

                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier
                            .fillMaxWidth(),
                        placeholder = { Text("Digite sua senha") },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        visualTransformation = if (passwordVisible)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible)
                                        Icons.Filled.VisibilityOff
                                    else
                                        Icons.Filled.Visibility,
                                    contentDescription = if (passwordVisible) "Ocultar senha" else "Mostrar senha",
                                    tint = PrimaryBlue.copy(alpha = 0.9f)
                                )
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = TitleDark,
                            unfocusedTextColor = TitleDark,
                            cursorColor = PrimaryBlue,
                            focusedContainerColor = Color(0xFFF4F7FB),
                            unfocusedContainerColor = Color(0xFFF4F7FB),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )

                    // Esqueci a senha (clicável no futuro)
                    Text(
                        text = "Esqueci minha senha",
                        fontSize = 12.sp,
                        color = PrimaryBlue,
                        modifier = Modifier
                            .padding(top = 8.dp, end = 4.dp)
                            .align(Alignment.End)
                            .clickable {
                                // TODO: fluxo de recuperação de senha
                            }
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // Botão Entrar
                    Button(
                        onClick = { onLoginClick(email, password) },
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        contentPadding = PaddingValues(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(PrimaryTeal, PrimaryBlue)
                                    ),
                                    shape = RoundedCornerShape(24.dp)
                                )
                                .padding(vertical = 14.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Entrar",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // "Não tem conta? Cadastre-se"
            Text(
                fontSize = 14.sp,
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = TitleDark)) {
                        append("Não tem uma conta? ")
                    }
                    withStyle(style = SpanStyle(color = PrimaryBlue, fontWeight = FontWeight.SemiBold)) {
                        append("Cadastre-se")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onRegisterClick() },
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    GuardiaTheme {
        LoginScreen(onRegisterClick = {})
    }
}
