package com.example.pawnbet_frontend.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
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
import com.example.pawnbet_frontend.ui.main.ProductPreviewScreen
import com.example.pawnbet_frontend.ui.main.SignUpScreen
import com.example.pawnbet_frontend.ui.splash.SplashScreen
import com.example.pawnbet_frontend.viewmodel.AuctionViewModel
import com.example.pawnbet_frontend.viewmodel.AuctionViewModelFactory
import com.example.pawnbet_frontend.viewmodel.AuthViewModel
import com.example.pawnbet_frontend.viewmodel.AuthViewModelFactory
import com.example.pawnbet_frontend.viewmodel.BidViewModel
import com.example.pawnbet_frontend.viewmodel.BidViewModelFactory
import com.example.pawnbet_frontend.viewmodel.CommentViewModel
import com.example.pawnbet_frontend.viewmodel.CommentViewModelFactory
import com.example.pawnbet_frontend.viewmodel.OrderViewModel
import com.example.pawnbet_frontend.viewmodel.OrderViewModelFactory
import com.example.pawnbet_frontend.viewmodel.ProductViewModel
import com.example.pawnbet_frontend.viewmodel.ProductViewModelFactory
import com.example.pawnbet_frontend.viewmodel.WishlistViewModel
import com.example.pawnbet_frontend.viewmodel.WishlistViewModelFactory

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object Onboarding : Screen("onboarding_screen")
    object SignUp : Screen("signup_screen")
    object Login : Screen("login_screen")
    object MainScreen : Screen("main_screen")
    object ProductPreviewScreen : Screen("product_preview_screen")
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {

    val context = LocalContext.current
    val tokenManager = TokenManager(context.dataStore)
    val apiService = ApiService.api


    val authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(apiService, tokenManager)
    )

    val productViewModel: ProductViewModel = viewModel (
        factory = ProductViewModelFactory(apiService, tokenManager)
    )

    val auctionViewModel: AuctionViewModel = viewModel (
        factory = AuctionViewModelFactory(apiService, tokenManager)
    )

    val commentViewModel: CommentViewModel = viewModel (
        factory = CommentViewModelFactory(apiService, tokenManager)
    )

    val bidViewModel: BidViewModel = viewModel(
        factory = BidViewModelFactory(apiService, tokenManager)
    )

    val wishlistViewModel: WishlistViewModel = viewModel (
        factory = WishlistViewModelFactory(apiService, tokenManager)
    )

    val orderViewModel: OrderViewModel = viewModel (
        factory = OrderViewModelFactory(apiService, tokenManager)
    )

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {

        composable(Screen.Splash.route) {
            SplashScreen(navController, authViewModel)
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
            MainScreen(
                rootNavController = navController,
                authViewModel = authViewModel,
                productViewModel = productViewModel,
                wishlistViewModel = wishlistViewModel,
                auctionViewModel = auctionViewModel,
                orderViewModel = orderViewModel,
                tokenManager = tokenManager
                )
        }

        composable(Screen.ProductPreviewScreen.route){
            ProductPreviewScreen(
                productViewModel,
                auctionViewModel,
                commentViewModel,
                bidViewModel,
                navController
            )
        }

    }
}
