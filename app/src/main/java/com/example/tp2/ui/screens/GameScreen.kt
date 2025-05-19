package com.example.tp2.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.tp2.data.GameDatabaseHelper
import com.example.tp2.viewmodel.GameViewModel

@Composable
fun GameScreen(gameViewModel: GameViewModel) {
    val uiState = gameViewModel.uiState

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Hola, ${uiState.user?.nombre}",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                elevation = CardDefaults.cardElevation(4.dp)
            ){
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    Text(
                        text = "Puntaje actual: ${uiState.puntajeActual}",
                        style = MaterialTheme.typography.bodyLarge,
                    )

                    Text(
                        text = "Mejor puntaje: ${uiState.mayorPuntaje}",
                        style = MaterialTheme.typography.bodyMedium,
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    OutlinedTextField(
                        value = uiState.numeroIngresado,
                        onValueChange = { gameViewModel.actualizarInput(it) },
                        label = { Text("Tu número (1 a 5)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(0.8f),
                        singleLine = true
                    )

                    if (!uiState.perdio) {
                        Button(
                            onClick = { gameViewModel.chequearInput() },
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .height(50.dp)
                        ) {
                            Text("Adivinar")
                        }
                    }

                    if (uiState.mensaje.isNotBlank()) {
                        Text(
                            text = uiState.mensaje,
                            color = if (uiState.perdio) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                            fontSize = 16.sp
                        )
                    }

                    if (uiState.perdio){
                        Button(
                            onClick = { gameViewModel.resetGame()},
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                        ){
                            Text("Reiniciar")
                        }
                    }

                }
            }

        }
    }
}
