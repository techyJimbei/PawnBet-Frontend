package com.example.pawnbet_frontend.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pawnbet_frontend.ui.navigation.Screen
import com.example.pawnbet_frontend.ui.theme.Orange
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavController) {

    LaunchedEffect(Unit) {
        delay(3000)
        navController.navigate(Screen.Onboarding.route) {
            popUpTo(Screen.Splash.route) { inclusive = true }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Orange),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            Text(
                text = "PAWNBET",
                fontSize = 74.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
            Text(
                text = "powered by shruti",
                fontSize = 16.sp,
                color = Color.White
            )
        }


    }
}