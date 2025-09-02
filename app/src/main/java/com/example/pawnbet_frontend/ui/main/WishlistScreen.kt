package com.example.pawnbet_frontend.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pawnbet_frontend.ui.theme.Beige
import com.example.pawnbet_frontend.ui.theme.Orange
import com.example.pawnbet_frontend.viewmodel.ProductViewModel
import com.example.pawnbet_frontend.viewmodel.WishlistViewModel

@Composable
fun WishlistScreen(
    productViewModel: ProductViewModel,
    navController: NavController,
    wishlistViewModel: WishlistViewModel
) {
    val listState = rememberLazyListState()

    val wishlist by wishlistViewModel.products



    LaunchedEffect(Unit) {
        wishlistViewModel.getWishlistProducts()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Beige)
            .padding(top = 8.dp, start =  8.dp, end =  8.dp),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = "WISHLIST",
                    color = Orange,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp),
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            items(wishlist) { product ->
                ProductCard(
                    product = product.product,
                    navController = navController,
                    productViewModel = productViewModel
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        Spacer(modifier = Modifier.height(100.dp))
    }
}
