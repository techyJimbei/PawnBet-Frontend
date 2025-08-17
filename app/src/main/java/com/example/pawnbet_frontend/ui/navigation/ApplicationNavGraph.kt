package com.example.pawnbet_frontend.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pawnbet_frontend.api.ApiService
import com.example.pawnbet_frontend.jwt.TokenManager
import com.example.pawnbet_frontend.jwt.dataStore
import com.example.pawnbet_frontend.ui.main.LoginScreen
import com.example.pawnbet_frontend.ui.main.MainScreen
import com.example.pawnbet_frontend.ui.main.OnboardingScreen
import com.example.pawnbet_frontend.ui.main.SignUpScreen
import com.example.pawnbet_frontend.ui.splash.SplashScreen
import com.example.pawnbet_frontend.viewmodel.AuthViewModel
import com.example.pawnbet_frontend.viewmodel.AuthViewModelFactory

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object Onboarding : Screen("onboarding_screen")
    object SignUp : Screen("signup_screen")
    object Login : Screen("login_screen")
    object MainScreen : Screen("main_screen")
}

@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {

    val context = LocalContext.current
    val tokenManager = TokenManager(context.dataStore)
    val apiService = ApiService.api


    val authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(apiService, tokenManager)
    )

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {

        composable(Screen.Splash.route) {
            SplashScreen(navController)
        }

        composable(Screen.Onboarding.route) {
            OnboardingScreen(navController)
        }

        composable(Screen.SignUp.route) {
            SignUpScreen(authViewModel, navController)
        }

        composable(Screen.Login.route) {
            LoginScreen(navController, authViewModel)
        }

        composable(Screen.MainScreen.route){
            MainScreen()
        }
    }
}
