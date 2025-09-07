package com.example.pawnbet_frontend.ui.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.pawnbet_frontend.model.OrderResponse
import com.example.pawnbet_frontend.ui.navigation.Screen
import com.example.pawnbet_frontend.ui.theme.Beige
import com.example.pawnbet_frontend.ui.theme.DarkGrey
import com.example.pawnbet_frontend.ui.theme.Green
import com.example.pawnbet_frontend.ui.theme.LightGrey
import com.example.pawnbet_frontend.ui.theme.NavyBlue
import com.example.pawnbet_frontend.ui.theme.Orange
import com.example.pawnbet_frontend.ui.theme.Red
import com.example.pawnbet_frontend.viewmodel.OrderViewModel
import com.example.pawnbet_frontend.viewmodel.ProductViewModel


@Composable
fun OrdersScreen(
    orderViewModel: OrderViewModel,
    productViewModel: ProductViewModel,
    navController: NavController
) {

    var auctionWon by remember { mutableStateOf(true) }

    val orders by orderViewModel.orders
    val winnings by orderViewModel.winnings

    LaunchedEffect(Unit) {
        orderViewModel.getOrders()
        orderViewModel.getWinningAuction()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
            .background(color = Beige)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .background(color = if (auctionWon) Color.White else LightGrey)
                            .height(80.dp)
                            .width(185.dp)
                            .border(
                                if (auctionWon) 2.dp else 0.dp,
                                if (auctionWon) Orange else LightGrey
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        TextButton(
                            onClick = {
                                auctionWon = true
                            },
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = "WINNINGS",
                                fontSize = 30.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (auctionWon) Orange else DarkGrey
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .background(color = if (auctionWon) LightGrey else Color.White)
                            .height(80.dp)
                            .width(185.dp)
                            .border(
                                if (auctionWon) 0.dp else 2.dp,
                                if (auctionWon) DarkGrey else Orange
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        TextButton(
                            onClick = {
                                auctionWon = false
                            },
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = "ORDERS",
                                fontSize = 30.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (auctionWon) DarkGrey else Orange
                            )
                        }
                    }
                }
            }
            if(auctionWon){
                items(winnings){product->
                    ProductCard(
                        product = product,
                        navController = navController,
                        productViewModel = productViewModel,
                        orderViewModel = orderViewModel
                    )
                }
            }
            else{
                items(orders){order->
                    OrderCard(
                        order = order,
                        productViewModel = productViewModel,
                        navController = navController
                    )
                }
            }
        }
    }


}


@Composable
fun OrderCard(
    order: OrderResponse,
    productViewModel: ProductViewModel,
    navController: NavController
) {

    val isPaid = order.isPaid

    Box(
        modifier = Modifier
            .height(300.dp)
            .width(400.dp)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .clickable {
                    productViewModel.selectProduct(order.product)
                    navController.navigate(Screen.ProductPreviewScreen.route) {
                        launchSingleTop = true
                    }
                },
            backgroundColor = Color.White,
            border = BorderStroke(1.dp, LightGrey),
            shape = RoundedCornerShape(20.dp)
        ) {

            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = if (isPaid) "PAID" else "Payment Pending",
                    color = if (isPaid) Green else Red,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AsyncImage(
                        model = order.product.imageUrl,
                        contentDescription = "Test Image",
                        modifier = Modifier.size(120.dp)
                    )

                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = order.product.title,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = NavyBlue
                        )

                        Text(
                            text = order.product.description,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = DarkGrey,
                            maxLines = 1
                        )

                        Text(
                            text = "starting bid = " + order.product.basePrice + "INR",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = DarkGrey
                        )

                        Text(
                            text = "Winner = @" + order.winningBid.bidder.username,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = NavyBlue
                        )

                        Text(
                            text = "Closed at = " + order.winningBid.bidAmount + "INR",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
                    }
                }
            }

        }

    }
}