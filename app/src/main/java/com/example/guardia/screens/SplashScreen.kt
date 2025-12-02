package com.example.guardia.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.guardia.R
import kotlinx.coroutines.delay
import androidx.compose.ui.layout.ContentScale

@Composable
fun SplashScreen(onNavigateToLogin: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(4000)
        onNavigateToLogin()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.shield),
                contentDescription = "Logo Guardiã",
                modifier = Modifier.size(350.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Guardiã", color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(onNavigateToLogin = {})
}
