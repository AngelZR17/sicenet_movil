package com.brian_angel.sicenet

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun showInfo(
    navController: NavController,
    text: String?
) {
    val estudiante = text?.split("(", ")")?.get(1)?.split(",")
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
                title = { Text("Datos", color = MaterialTheme.colorScheme.onPrimary) },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(Icons.Default.ArrowBack,
                            contentDescription = "Botón de Retroceso",
                            tint = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(7.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            Text(text = estudiante?.get(0)?.split("=")?.get(1).toString(), fontWeight = FontWeight.Bold)
            Text(text = "Fecha de reinscripción: " + estudiante?.get(1)?.split("=")?.get(1))
            Text(text = "Semestre actual: " + estudiante?.get(2)?.split("=")?.get(1))
            Text(text = "Creditos acumulados: " + estudiante?.get(3)?.split("=")?.get(1))
            Text(text = "Creditos actuales: " + estudiante?.get(4)?.split("=")?.get(1))
            Text(text = "Carrera: " + estudiante?.get(5)?.split("=")?.get(1))
            Text(text = "No. Control: " + estudiante?.get(6)?.split("=")?.get(1))
            Text(text = estudiante?.get(7)?.split("=")?.get(1).toString())
        }
    }
}


