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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pawnbet_frontend.ui.navigation.Screen
import com.example.pawnbet_frontend.ui.theme.NavyBlue
import com.example.pawnbet_frontend.ui.theme.Orange
import com.example.pawnbet_frontend.viewmodel.AuthViewModel


@Composable
fun SignUpScreen(
    viewmodel: AuthViewModel,
    navController: NavController
) {

    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rePassword by remember { mutableStateOf("") }

    var profileUrl by remember { mutableStateOf("") }


    Column(
        modifier = Modifier.fillMaxSize().padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(130.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            OutlinedTextField(
                value = profileUrl,
                onValueChange = {profileUrl = it},
                label =  {Text(text ="Enter profile image url", color = NavyBlue)},
                shape = RoundedCornerShape(20.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Enter Email", color = NavyBlue) },
                shape = RoundedCornerShape(20.dp)
            )

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Enter Username", color = NavyBlue) },
                shape = RoundedCornerShape(20.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Enter Password", color = NavyBlue) },
                shape = RoundedCornerShape(20.dp)
            )

            OutlinedTextField(
                value = rePassword,
                onValueChange = { rePassword = it },
                label = { Text("Confirm Password", color = NavyBlue) },
                shape = RoundedCornerShape(20.dp)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                when {
                    email.isBlank() || username.isBlank() || password.isBlank() || rePassword.isBlank() -> {
                        Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show()
                    }
                    password != rePassword -> {
                        Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        viewmodel.signup(email, username, password, profileUrl)
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.SignUp.route) { inclusive = true }
                        }
                    }
                }
            },
            modifier = Modifier
                .width(280.dp)
                .padding(8.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Orange,
                contentColor = Color.White
            )
        ) {
            Text(text = "Create Account", fontSize = 20.sp)
        }

    }

}
