package com.example.tp2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.tp2.navigation.TP2NavHost
import com.example.tp2.ui.theme.TP2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
                TP2Theme {
                    val navController = rememberNavController()
                    TP2NavHost(navController = navController)
            }

        }
    }
}