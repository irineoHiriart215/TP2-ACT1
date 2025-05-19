package com.example.tp2.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tp2.data.GameDatabaseHelper
import com.example.tp2.ui.screens.GameScreen
import com.example.tp2.ui.screens.LoginScreen
import com.example.tp2.viewmodel.GameViewModel

@Composable
fun TP2NavHost(navController: NavHostController) {
    val context = LocalContext.current
    val dbHelper = remember { GameDatabaseHelper(context) }

    var gameViewModel: GameViewModel = viewModel(factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return GameViewModel(dbHelper) as T
        }
    })

    NavHost(
        navController = navController,
        startDestination = "login",
    ) {

        composable("login") {
            LoginScreen(
                context = context,
                onStartGame = { user ->
                    gameViewModel.setUser(user)
                    navController.navigate("game")
                }
            )
        }

        composable("game") {
            GameScreen(gameViewModel = gameViewModel)
        }
    }
}
