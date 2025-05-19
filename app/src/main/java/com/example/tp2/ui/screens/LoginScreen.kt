package com.example.tp2.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tp2.data.GameDatabaseHelper
import com.example.tp2.data.User
import com.example.tp2.ui.theme.Background
import com.example.tp2.ui.theme.ErrorRed
import com.example.tp2.ui.theme.Primary
import com.example.tp2.ui.theme.Secondary
import com.example.tp2.ui.theme.TextPrimary
import com.example.tp2.ui.theme.TextSecondary
import com.example.tp2.ui.theme.TP2Theme

@Composable
fun LoginScreen( context: Context, onStartGame: (User) -> Unit) {

    var username by remember { mutableStateOf("") }
    var dbHelper = remember { GameDatabaseHelper(context) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Text(
                text = "¡Bienvenido!",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Ingrese su nombre de usuario",
                fontSize = 18.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(44.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Nombre de usuario", color = TextSecondary) },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )

            Spacer(modifier = Modifier.height(64.dp))

        Button(
            onClick = {
                val user = dbHelper.getUser(username)
                user?.let { onStartGame(it) }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary,
            )
        ) {
            Text("Comenzar", color = Background, fontSize = 20.sp)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    TP2Theme {
        val dummyContext = LocalContext.current
        LoginScreen(
            context = dummyContext,
            onStartGame = {}
        )
    }
}