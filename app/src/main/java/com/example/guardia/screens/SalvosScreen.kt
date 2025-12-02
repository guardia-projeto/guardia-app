package com.example.guardia.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.guardia.ui.theme.GuardiaTheme

data class Report(val title: String, val info: String)

@Composable
fun SalvosScreen(
    onBackClick: () -> Unit = {},
    onItemClick: (String) -> Unit = {}
) {
    val reports = remember {
        listOf(
            Report("Relatório Guardiã - 24/10/2025", "2,24 MB | PDF"),
            Report("Relatório Guardiã - 13/09/2025", "2,28 MB | PDF"),
            Report("Relatório Guardiã - 01/07/2025", "3,26 MB | PDF"),
            Report("Relatório Guardiã - 01/07/2025", "3,26 MB | PDF"),
            Report("Relatório Guardiã - 28/06/2025", "2,06 MB | PDF"),
            Report("Relatório Guardiã - 10/10/2025", "5,08 MB | PDF")
        )
    }

    Scaffold(
        topBar = { SalvosTopAppBar(onBackClick = onBackClick) },
        bottomBar = { GuardiaBottomBar(currentRoute = "salvos", onItemClick = onItemClick) },
        containerColor = Color(0xFF88D3CE)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Gerencie e faça download dos seus relatórios.",
                color = Color(0xFF14265B),
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(reports) { report ->
                    ReportItem(report = report)
                }
            }
        }
    }
}

@Composable
private fun SalvosTopAppBar(onBackClick: () -> Unit) {
    Column(modifier = Modifier.padding(top = 30.dp)) {
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
                text = "Salvos",
                color = Color(0xFF1E40AF),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.weight(1.5f))
        }
        Divider(color = Color.White.copy(alpha = 0.7f), thickness = 3.dp)
    }
}

@Composable
private fun ReportItem(report: Report) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = 0.7f), shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = report.title, color = Color(0xFF14265B), fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = report.info, color = Color(0xFF14265B).copy(alpha = 0.7f), fontSize = 14.sp)
        }
        IconButton(onClick = { /* TODO: Download report */ }) {
            Icon(imageVector = Icons.Default.Download, contentDescription = "Download", tint = Color(0xFF14265B))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SalvosScreenPreview() {
    GuardiaTheme {
        SalvosScreen()
    }
}
