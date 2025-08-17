package com.example.pawnbet_frontend.ui.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pawnbet_frontend.R
import com.example.pawnbet_frontend.ui.navigation.Screen
import com.example.pawnbet_frontend.ui.theme.Orange


@Composable
fun OnboardingScreen(
    navController: NavController
){

    val jomhuria = FontFamily(
        Font(R.font.jomhuria)
    )

    val robotoLight = FontFamily(
        Font(R.font.roboto_light)
    )

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){

        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ){
            Image(
                painter = painterResource(R.drawable.onboarding_bid_image),
                contentDescription = "bidding image",
                Modifier.size(140.dp)
            )

            Text(
                text = "PAWNBET",
                fontFamily = jomhuria,
                fontSize = 54.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "powered by shruti",
                fontFamily =  robotoLight,
                fontSize = 20.sp
            )

            Spacer(modifier =  Modifier.height(220.dp))

            Button(onClick = {
                navController.navigate(Screen.Login.route)
            },
                modifier = Modifier
                    .width(280.dp)
                    .padding(8.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Orange,
                    contentColor = Color.White
                )
            )
            {
                Text(
                    text = "Login",
                    fontSize = 20.sp
                    )
            }

            OutlinedButton(onClick = {
                navController.navigate(Screen.SignUp.route)
            },
                modifier = Modifier
                    .width(280.dp)
                    .padding(8.dp)
                    .height(50.dp),
                border = BorderStroke(2.dp, Orange),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Orange
                )
            )
            {
                Text(
                    text = "SignUp",
                    fontSize = 20.sp
                )
            }
        }



    }
}