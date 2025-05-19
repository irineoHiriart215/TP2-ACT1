package com.example.tp2.data

data class UiState(
    var user: User? = null,
    val numeroIngresado: String = "",
    val puntajeActual: Int = 0,
    val mayorPuntaje: Int = 0,
    val mensaje: String = "",
    val fallos: Int = 0,
    val numeroAleatorio: Int = (1..5).random(),
    val mostrarMensaje: Boolean = false,
    val perdio: Boolean = false,
)