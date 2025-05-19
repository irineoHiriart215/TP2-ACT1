package com.example.tp2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.tp2.data.GameDatabaseHelper
import com.example.tp2.data.UiState
import com.example.tp2.data.User
import kotlinx.coroutines.launch

class GameViewModel(private val dbHelper: GameDatabaseHelper): ViewModel(){

    var uiState by mutableStateOf(UiState())
        private set

    fun actualizarInput(input: String){
        uiState = uiState.copy(numeroIngresado = input)
    }

    fun chequearInput(){
        val guess = uiState.numeroIngresado.toIntOrNull()

        if (guess == null || guess !in 1..5){
            uiState = uiState.copy(
                mostrarMensaje = true,
                mensaje = "El numero debe estar entre 1 y 5."
            )
            return
        }

        if (guess == uiState.numeroAleatorio){
            val nuevoPuntaje = uiState.puntajeActual + 10
            val nuevoMayor= maxOf (nuevoPuntaje, uiState.mayorPuntaje)
            val actualizado = uiState.user?.copy(
                puntajeActual = nuevoPuntaje,
                mayorPuntaje = nuevoMayor
            )
            uiState = uiState.copy(
                user = actualizado,
                puntajeActual = nuevoPuntaje,
                mayorPuntaje = nuevoMayor,
                numeroIngresado = "",
                numeroAleatorio = (1..5).random(),
                fallos = 0,
                mostrarMensaje = true,
                mensaje = "¡Correcto!"
            )
            viewModelScope.launch {
                actualizado?.let{
                    dbHelper.updatePuntaje(it.nombre, it.puntajeActual, it.mayorPuntaje)
                    dbHelper.updateFallos(actualizado.nombre, 0)
                }
            }
        } else {
            val nuevoFallo = uiState.fallos + 1
            val perdio = nuevoFallo >= 5
            val actualizado = uiState.user?.copy(
                fallos = nuevoFallo
            )

        viewModelScope.launch {
            if (uiState.user!!.nombre.isNotBlank() && nuevoFallo <= 5) {
                dbHelper.updateFallos(uiState.user!!.nombre, nuevoFallo)
            }
        }

            uiState = uiState.copy(
                user=actualizado,
                fallos = nuevoFallo,
                puntajeActual = if (perdio) 0 else uiState.puntajeActual,
                numeroIngresado = "",
                perdio = perdio,
                mostrarMensaje = true,
                mensaje = if (perdio)
                    "Ha fallado 5 veces. ¡Juegue otra vez!"
                else
                    "¡Incorrecto! Intentos fallidos: $nuevoFallo"
            )
        }
    }

    fun resetGame() {
        val actualizado = uiState.user?.copy(
            fallos = 0,
            puntajeActual = 0
        )

        viewModelScope.launch {
            if (uiState.user!!.nombre.isNotBlank()) {
                dbHelper.updateFallos(uiState.user!!.nombre,0)
            }
        }
        uiState = UiState(
            user = actualizado,
            fallos = 0,
            puntajeActual = 0,
            numeroIngresado = "",
            perdio = false,
            mostrarMensaje = true,
            mensaje = "¡Buena Suerte!",
            mayorPuntaje = uiState.mayorPuntaje,
        )
    }

    fun setUser(user: User) {
        viewModelScope.launch {
            var userbd = dbHelper.getUser(user.nombre)
            if (userbd == null) {
                dbHelper.addUser(user.nombre)
                userbd = dbHelper.getUser(user.nombre)
            }
            userbd
            uiState = uiState.copy(user = user, mayorPuntaje = user.mayorPuntaje)
        }
    }
}