package com.example.tp2.ui.screens

import android.content.Context
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
import com.example.tp2.ui.theme.TP2Theme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LoginScreen( context: Context, onStartGame: (User) -> Unit) {

    var username by remember { mutableStateOf("")}
    var dbHelper = remember { GameDatabaseHelper(context) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "¡Bienvenido!",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Ingresa tu nombre de usuario para comenzar:",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it},
            label = { Text("Nombre de usuario") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer( modifier = Modifier.height(32.dp))

        Button( onClick = {
            val user = dbHelper.getUser(username)
            user?.let { onStartGame(it) }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
        ){
            Text("Comenzar")
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