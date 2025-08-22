package com.example.pawnbet_frontend.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.pawnbet_frontend.R
import com.example.pawnbet_frontend.ui.theme.Orange
import com.example.pawnbet_frontend.viewmodel.ProductViewModel


data class NavItem(
    val title: String,
    val route: String,
    val selectedIcon: Int,
    val unselectedIcon: Int
)

@Composable
fun MainScreen(
    productViewModel: ProductViewModel,
    navController: NavController
) {
    val items = listOf(
        NavItem("Home", "home_screen", R.drawable.selected_home, R.drawable.unselected_home),
        NavItem("Auction", "auction_screen", R.drawable.selected_bid, R.drawable.unselected_bid),
        NavItem("WishList", "wishlist_screen", R.drawable.selected_heart, R.drawable.unselected_heart),
        NavItem("MyProducts", "my_product_screen", R.drawable.selected_coin, R.drawable.unselected_coin),
        NavItem("Orders", "orders_screen", R.drawable.selected_delivery, R.drawable.unselected_delivery)
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                items = items,
                currentRoute = currentRoute,
                onItemSelected = { navItem ->
                    if (currentRoute != navItem.route) {
                        navController.navigate(navItem.route) {
                            // Pop up only within MainScreen graph
                            popUpTo("main_screen") { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    ) { innerPadding ->

        Box(modifier = Modifier.padding(innerPadding)) {

        }
    }
}

@Composable
fun BottomNavigationBar(
    items: List<NavItem>,
    currentRoute: String?,
    onItemSelected: (NavItem) -> Unit
) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 4.dp
    ) {
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
                                    Modifier.clip(CircleShape)
                                        .background(Orange)
                                else Modifier
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(
                                id = if (isSelected) item.selectedIcon else item.unselectedIcon
                            ),
                            contentDescription = item.title,
                        )
                    }
                },
                label = { Text(text = item.title) },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.White
                )
            )
        }
    }
}
