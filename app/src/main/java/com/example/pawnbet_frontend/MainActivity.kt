package com.example.pawnbet_frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.pawnbet_frontend.ui.navigation.AppNavGraph
import com.example.pawnbet_frontend.ui.theme.PawnBetFrontendTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PawnBetFrontendTheme {
                val navController = rememberNavController()
                AppNavGraph(navController)
            }
        }
    }
}
