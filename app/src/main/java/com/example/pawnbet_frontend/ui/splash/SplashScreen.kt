package com.example.pawnbet_frontend.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pawnbet_frontend.R
import com.example.pawnbet_frontend.ui.theme.Orange

@Composable
@Preview(showSystemUi = true)
fun SplashScreenPreview() {
    SplashScreen()
}

@Composable
fun SplashScreen() {


    val jomhuriaFont = FontFamily(
        Font(R.font.jomhuria)
    )

    val roboto_light = FontFamily(
        Font(R.font.roboto_light)
    )

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
                fontFamily = jomhuriaFont,
                fontSize = 58.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
            Text(
                text = "powered by shruti",
                fontFamily = roboto_light,
                fontSize = 16.sp,
                color = Color.White
            )
        }


    }
}