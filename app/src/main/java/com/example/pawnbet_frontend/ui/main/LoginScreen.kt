package com.example.pawnbet_frontend.ui.main

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pawnbet_frontend.ui.navigation.Screen
import com.example.pawnbet_frontend.ui.theme.NavyBlue
import com.example.pawnbet_frontend.ui.theme.Orange
import com.example.pawnbet_frontend.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewmodel: AuthViewModel
){

    val context = LocalContext.current

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(180.dp))

        Text(
            text = "Log into your Account",
            fontSize = 25.sp,
            fontWeight = FontWeight.W500
        )

        Spacer(modifier = Modifier.height(120.dp))

        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value =  username,
                onValueChange = {username = it},
                shape = RoundedCornerShape(20.dp),
                label = {Text(text =  "Enter username/email", color = NavyBlue)}
            )

            OutlinedTextField(
                value =  password,
                onValueChange = {password = it},
                shape = RoundedCornerShape(20.dp),
                label = {Text(text =  "Enter password", color = NavyBlue)}
            )

            TextButton(onClick =  {}) {
                Text(
                    text = "forgot password ?",
                    fontSize =  15.sp,
                    color = NavyBlue
                )
            }
        }

        Spacer(modifier = Modifier.height(120.dp))

        Button(
            onClick = {
                if (username.isNotBlank() && password.isNotBlank()) {
                    viewmodel.login(username, password)
                    navController.navigate(Screen.MainScreen.route) {
                        popUpTo(Screen.Onboarding.route) {inclusive = true}
                    }
                } else {
                    Toast.makeText(context, "Enter remaining fields", Toast.LENGTH_SHORT).show()
                }
            },
            modifier=Modifier
                .width(280.dp)
                .padding(8.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Orange,
                contentColor = Color.White
            )
        ) {
            Text(text = "Login", fontSize = 20.sp)
        }

    }
}