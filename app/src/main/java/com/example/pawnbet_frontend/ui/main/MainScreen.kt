package com.example.pawnbet_frontend.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pawnbet_frontend.R
import com.example.pawnbet_frontend.ui.theme.Orange
import com.example.pawnbet_frontend.viewmodel.ProductViewModel
import com.example.pawnbet_frontend.viewmodel.WishlistViewModel

data class NavItem(
    val title: String,
    val route: String,
    val selectedIcon: Int,
    val unselectedIcon: Int
)

@Composable
fun MainScreen(
    rootNavController: NavHostController,
    productViewModel: ProductViewModel,
    wishlistViewModel: WishlistViewModel
) {
    val tabNavController = rememberNavController()

    val items = listOf(
        NavItem("Home", "home_screen", R.drawable.selected_home, R.drawable.unselected_home),
        NavItem("Auction", "auction_screen", R.drawable.selected_bid, R.drawable.unselected_bid),
        NavItem("WishList", "wishlist_screen", R.drawable.selected_heart, R.drawable.unselected_heart),
        NavItem("MyProducts", "my_product_screen", R.drawable.selected_coin, R.drawable.unselected_coin),
        NavItem("Orders", "orders_screen", R.drawable.selected_delivery, R.drawable.unselected_delivery)
    )

    val navBackStackEntry by tabNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    LaunchedEffect(currentRoute) {
        if (currentRoute == null) {
            tabNavController.navigate("home_screen") { launchSingleTop = true }
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                items = items,
                currentRoute = currentRoute,
                onItemSelected = { navItem ->
                    if (navItem.route != currentRoute) {
                        tabNavController.navigate(navItem.route) {
                            popUpTo(tabNavController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = tabNavController,
            startDestination = "home_screen",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home_screen") { HomeScreen(
                navController = rootNavController,
                productViewModel = productViewModel
            ) }
            composable("auction_screen") { AuctionScreen() }
            composable("wishlist_screen") { WishlistScreen(
                productViewModel = productViewModel,
                navController = rootNavController,
                wishlistViewModel = wishlistViewModel
            ) }
            composable("my_product_screen") { MyProductScreen(
                productViewModel = productViewModel,
                navController = rootNavController
            ) }
            composable("orders_screen") { OrdersScreen() }
        }
    }
}

@Composable
fun BottomNavigationBar(
    items: List<NavItem>,
    currentRoute: String?,
    onItemSelected: (NavItem) -> Unit
) {
    NavigationBar(containerColor = Color.White, tonalElevation = 4.dp) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route
            NavigationBarItem(
                selected = isSelected,
                onClick = { onItemSelected(item) },
                icon = {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .then(
                                if (isSelected)
                                    Modifier.clip(CircleShape).background(Orange)
                                else Modifier
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = if (isSelected) item.selectedIcon else item.unselectedIcon),
                            contentDescription = item.title
                        )
                    }
                },
                label = { Text(item.title) },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.White)
            )
        }
    }
}
