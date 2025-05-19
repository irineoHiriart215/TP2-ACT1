package com.example.tp2.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tp2.ui.theme.Background
import com.example.tp2.ui.theme.Primary
import com.example.tp2.ui.theme.Secondary
import com.example.tp2.ui.theme.ErrorRed
import com.example.tp2.ui.theme.SurfaceVariant
import com.example.tp2.ui.theme.TextPrimary
import com.example.tp2.viewmodel.GameViewModel

@Composable
fun GameScreen(gameViewModel: GameViewModel) {
    val uiState = gameViewModel.uiState
    val focusManager = LocalFocusManager.current

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
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 24.sp
                ),
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = SurfaceVariant),
                elevation = CardDefaults.cardElevation(8.dp)
            ){
                Column(
                    modifier = Modifier
                        .padding(32.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {

                    Text(
                        text = "\uD83C\uDFC6 Puntaje actual: ${uiState.puntajeActual}",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            color = TextPrimary
                        )
                    )
                    Text(
                        text = "\uD83C\uDF1F Mejor puntaje: ${uiState.mayorPuntaje}",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 18.sp,
                            color = TextPrimary
                        ),
                    )

                    Spacer(modifier = Modifier.height(36.dp))

                    OutlinedTextField(value = uiState.numeroIngresado, onValueChange = { gameViewModel.actualizarInput(it) }, label = {
                            Text(
                                "Tu número (1 a 5)",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Light
                                )
                            )
                        }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp), textStyle = LocalTextStyle.current.copy(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent, focusedIndicatorColor = Primary, unfocusedIndicatorColor = Primary.copy(alpha=0.5f), cursorColor = Primary, focusedLabelColor = Primary, unfocusedLabelColor = Primary.copy(alpha = 0.6f))
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    val guessButtonColors = ButtonDefaults.buttonColors(
                        containerColor = Primary,
                        contentColor = Background
                    )
                    val resetButtonColors = ButtonDefaults.buttonColors(
                        containerColor = ErrorRed,
                        contentColor = TextPrimary
                    )

                    if (!uiState.perdio) {
                        Button(
                            onClick = {
                                focusManager.clearFocus()
                                gameViewModel.chequearInput()
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .height(45.dp),
                            colors = guessButtonColors,
                            elevation = ButtonDefaults.buttonElevation(10.dp),
                            border = BorderStroke(0.3.dp, Primary)
                        ) {
                            Text("Adivinar", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }

                    if (uiState.mensaje.isNotBlank()) {
                        Text(
                            text = uiState.mensaje,
                            color = if (uiState.perdio) ErrorRed else TextPrimary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    if (uiState.perdio){
                        Button(
                            onClick = {
                                focusManager.clearFocus()
                                gameViewModel.resetGame()},
                            colors = resetButtonColors,
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .height(45.dp),
                            elevation = ButtonDefaults.buttonElevation(10.dp)
                        ){
                            Text("Reiniciar", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }
    }
}
@Preview(showBackground = false, showSystemUi = true)
@Composable
fun GameScreenPreview() {
    GameScreen(gameViewModel = viewModel())
}