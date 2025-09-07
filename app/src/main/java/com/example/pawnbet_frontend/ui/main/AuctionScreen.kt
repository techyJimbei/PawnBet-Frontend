package com.example.pawnbet_frontend.ui.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pawnbet_frontend.model.AuctionStatus
import com.example.pawnbet_frontend.ui.theme.Beige
import com.example.pawnbet_frontend.ui.theme.DarkGrey
import com.example.pawnbet_frontend.ui.theme.LightGrey
import com.example.pawnbet_frontend.ui.theme.Orange
import com.example.pawnbet_frontend.viewmodel.AuctionViewModel
import com.example.pawnbet_frontend.viewmodel.OrderViewModel
import com.example.pawnbet_frontend.viewmodel.ProductViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AuctionScreen(
    productViewModel: ProductViewModel,
    auctionViewModel: AuctionViewModel,
    navController: NavController,
    orderViewModel: OrderViewModel
) {
    var liveSelected by remember { mutableStateOf(true) }
    var selectedFilter by remember { mutableStateOf<String?>(null) }

    val products by productViewModel.products
    val auctionDetailsMap by auctionViewModel.auctionDetailsMap
    val now = LocalDateTime.now()

    val filterDate = listOf("Today", "Tomorrow", "This Week", "This Month")


    LaunchedEffect(Unit) {
        productViewModel.getAllProduct()
    }

    LaunchedEffect(liveSelected, products) {
        if (!liveSelected) {
            products.forEach { product ->
                if (!auctionDetailsMap.containsKey(product.id)) {
                    auctionViewModel.getAuctionDetails(product.id)
                }
            }
        }
    }

    val formatter = DateTimeFormatter.ISO_DATE_TIME

    val filteredProducts by remember(products, selectedFilter, liveSelected, auctionDetailsMap) {
        derivedStateOf {
            if (liveSelected) {
                products.filter { it.auctionStatus == AuctionStatus.LIVE }
            } else {
                products.filter { it.auctionStatus == AuctionStatus.DETAILS_ADDED }.filter { product ->
                    val auctionStartString = auctionDetailsMap[product.id]?.auctionStartTime ?: return@filter false
                    val auctionStart = try {
                        LocalDateTime.parse(auctionStartString, formatter)
                    } catch (e: Exception) {
                        null
                    } ?: return@filter false

                    selectedFilter?.let { filter ->
                        when (filter) {
                            "Today" -> auctionStart.toLocalDate() == now.toLocalDate()
                            "Tomorrow" -> auctionStart.toLocalDate() == now.plusDays(1).toLocalDate()
                            "This Week" -> {
                                val startOfWeek = now.with(java.time.DayOfWeek.MONDAY)
                                val endOfWeek = now.with(java.time.DayOfWeek.SUNDAY)
                                auctionStart.toLocalDate() in startOfWeek.toLocalDate()..endOfWeek.toLocalDate()
                            }
                            "This Month" -> auctionStart.month == now.month && auctionStart.year == now.year
                            else -> true
                        }
                    } ?: true
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Beige)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                // LIVE / UPCOMING toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .background(color = if (liveSelected) Color.White else LightGrey)
                            .height(80.dp)
                            .width(185.dp)
                            .border(
                                if (liveSelected) 2.dp else 0.dp,
                                if (liveSelected) Orange else LightGrey
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        TextButton(
                            onClick = { liveSelected = true },
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = "LIVE",
                                fontSize = 30.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (liveSelected) Orange else DarkGrey
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .background(color = if (liveSelected) LightGrey else Color.White)
                            .height(80.dp)
                            .width(185.dp)
                            .border(
                                if (liveSelected) 0.dp else 2.dp,
                                if (liveSelected) DarkGrey else Orange
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        TextButton(
                            onClick = { liveSelected = false },
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = "UPCOMING",
                                fontSize = 30.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (liveSelected) DarkGrey else Orange
                            )
                        }
                    }
                }

                if (!liveSelected) {
                    LazyRow {
                        items(filterDate) { date ->
                            Button(
                                onClick = { selectedFilter = if (selectedFilter == date) null else date },
                                modifier = Modifier
                                    .width(130.dp)
                                    .height(50.dp)
                                    .padding(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (selectedFilter == date) DarkGrey else Orange,
                                    contentColor = Color.White
                                )
                            ) {
                                Text(text = date, fontSize = 14.sp)
                            }
                        }
                    }
                }
            }

            items(filteredProducts) { product ->
                ProductCard(
                    product = product,
                    navController = navController,
                    productViewModel = productViewModel,
                    orderViewModel = orderViewModel
                )
            }
        }
    }
}
