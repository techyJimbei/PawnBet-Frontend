package com.example.pawnbet_frontend.ui.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.pawnbet_frontend.R
import com.example.pawnbet_frontend.model.AuctionStatus
import com.example.pawnbet_frontend.model.ProductResponse
import com.example.pawnbet_frontend.ui.navigation.Screen
import com.example.pawnbet_frontend.ui.theme.Grey
import com.example.pawnbet_frontend.ui.theme.LightGrey
import com.example.pawnbet_frontend.ui.theme.NavyBlue
import com.example.pawnbet_frontend.ui.theme.Orange
import com.example.pawnbet_frontend.ui.theme.Red
import com.example.pawnbet_frontend.viewmodel.ProductViewModel

@Composable
fun HomeScreen(
    productViewModel: ProductViewModel,
    navController: NavController
) {
    println("HomeScreen NavController hash: ${navController.hashCode()}")

    val keyboardController = LocalSoftwareKeyboardController.current

    val products by productViewModel.products
    val searchProducts by productViewModel.searchProducts

    var selectedTag by remember { mutableStateOf<String?>(null) }

    val filteredProducts =
        if(selectedTag.isNullOrEmpty()) products
        else products.filter { it.tag == selectedTag }

    val displayedProducts =
        if (searchProducts.isNotEmpty()) searchProducts
        else products

    var searchQuery by remember { mutableStateOf("") }


    LaunchedEffect(Unit) {
        productViewModel.getAllProduct()
    }

    val tags = listOf(
        "Watch", "Jewelry", "Sculpture", "Coin", "Antique", "Rare", "Fashion"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAF8F4))
    ) {
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth()
                .verticalScroll(scrollState)
        ) {

            Row(
                modifier = Modifier.padding(top = 34.dp),
                horizontalArrangement = Arrangement.spacedBy(150.dp)
            ) {
                Text(
                    text = "PAWNBET",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = NavyBlue,
                    modifier = Modifier.padding(top =  8.dp)
                )
                Image(
                    painter = painterResource(R.drawable.profile_picture_default),
                    contentDescription = "profile picture",
                    modifier = Modifier.size(70.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Trending",
                fontSize = 30.sp,
                fontWeight = FontWeight.W800,
                fontStyle = FontStyle.Italic
            )

            LazyRow {
                items(tags) { tag ->
                    Button(
                        onClick = { selectedTag = if (selectedTag == tag) null else tag },
                        modifier = Modifier
                            .width(130.dp)
                            .height(50.dp)
                            .padding(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Orange,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = tag, fontSize = 14.sp)
                    }
                }
            }

            LazyRow {
                items(filteredProducts){product ->
                    ProductCard(product = product, navController = navController, productViewModel = productViewModel)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .width(280.dp)
                    .padding(8.dp)
                    .height(70.dp)
                    .padding(vertical = 8.dp),
                placeholder = { Text("Search products...") },
                trailingIcon = {
                    IconButton(onClick = {
                        if(searchQuery.isNotEmpty()){
                            productViewModel.getSearchProduct(searchQuery)
                        }
                        keyboardController?.hide()
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.search),
                            contentDescription = "Search Icon",

                        )
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(40.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Orange,
                    focusedTextColor = NavyBlue,
                    unfocusedBorderColor = Grey
                )
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .heightIn(min = 400.dp, max = 800.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(displayedProducts) { product ->
                    ProductCard(product = product, navController = navController, productViewModel = productViewModel)
                }
            }
        }
    }
}

@Composable
fun ProductCard(
    product: ProductResponse,
    navController: NavController,
    productViewModel: ProductViewModel
) {
    val isLive = product.auctionStatus == AuctionStatus.LIVE
    var isWishlisted = product.isWishlisted

    Box(
        modifier = Modifier
            .width(220.dp)
            .height(300.dp)
            .padding(8.dp)
            .clickable {
                productViewModel.selectProduct(product)
                navController.navigate(Screen.ProductPreviewScreen.route)
            }
    ) {
        Card(
            modifier = Modifier.fillMaxSize(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(1.dp, LightGrey)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
            ) {

                Text(
                    text = "Starting Bid: ${product.basePrice} INR",
                    color = Red,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 8.dp)
                        .fillMaxWidth()
                )

                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = product.title,
                    modifier = Modifier
                        .size(160.dp)
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 8.dp)
                )

                Text(
                    text = product.tag ?: "No tag",
                    color = Orange,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = product.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = NavyBlue
                )

                Text(
                    text = product.description,
                    color = Grey,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        IconButton(
            onClick = {
                isWishlisted = !isWishlisted
                productViewModel.toggleWishlist(product)
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(y = 15.dp, x = 5.dp)
                .size(28.dp)
                .background(color = Color.White, shape = CircleShape)
        ) {
            Icon(
                painter = painterResource(
                    if (isWishlisted) R.drawable.filled_heart
                    else R.drawable.unselected_heart
                ),
                contentDescription = "Wishlist Icon",
                tint = Orange,
                modifier = Modifier.size(24.dp)
            )
        }

        // Live Badge
        if (isLive) {
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .align(Alignment.CenterStart)
                    .offset(x = 55.dp)
                    .background(color = Red, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "LIVE",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


