package com.example.pawnbet_frontend.ui.main


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pawnbet_frontend.R
import com.example.pawnbet_frontend.model.AuctionResponse
import com.example.pawnbet_frontend.model.AuctionStatus
import com.example.pawnbet_frontend.ui.theme.NavyBlue
import com.example.pawnbet_frontend.ui.theme.Orange
import com.example.pawnbet_frontend.viewmodel.AuctionViewModel
import com.example.pawnbet_frontend.viewmodel.ProductViewModel


@Composable
fun ProductPreviewScreen(
    productViewModel: ProductViewModel,
    auctionViewModel: AuctionViewModel
) {
    val product by productViewModel.selectedPost.collectAsState()
    val auction by auctionViewModel.auctionDetails
    val AuctionDetails by remember { mutableStateOf(product?.auctionStatus) }


    LaunchedEffect(Unit) {
        auctionViewModel.getAuctionDetails(product)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFFAF8F4))
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(onClick = {}) {
                            Icon(
                                painter = painterResource(R.drawable.back),
                                contentDescription = "back button",
                                tint = Color.Unspecified
                            )
                        }
                        IconButton(onClick = {}) {
                            Icon(
                                painter = painterResource(R.drawable.share),
                                contentDescription = "share button",
                                tint = Color.Unspecified
                            )
                        }
                    }

                    Text(
                        text = "Starting Bid: ${product?.basePrice ?: 0}",
                        fontSize =  20.sp,
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )

                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .size(350.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        AsyncImage(
                            model = product?.imageUrl ?: "",
                            contentDescription = "Product Image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        IconButton(
                            onClick = {},
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .offset(x = 12.dp, y = 8.dp)
                                .size(40.dp)
                                .background(color = Color.White, shape = CircleShape)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.unselected_heart),
                                contentDescription = "Wishlist Icon",
                                tint = Orange,
                                modifier = Modifier.size(36.dp)
                            )
                        }

                        if (AuctionDetails== AuctionStatus.LIVE) {
                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .align(Alignment.CenterStart)
                                    .offset(x = (-25).dp)
                                    .background(color = Color.Red, shape = CircleShape)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(4.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "LIVE",
                                        color = Color.White,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                    )
                                    Text(
                                        text = "ends in:\n1:00:39",
                                        fontSize = 16.sp,
                                        color = Color.White,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }
                    }

                    Text(
                        text = product?.title ?: "",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = NavyBlue,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Text(
                        text = product?.description ?: "",
                        fontSize = 16.sp,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(top = 8.dp),
                        fontWeight = FontWeight.Medium
                    )

                    if (AuctionDetails== AuctionStatus.DETAILS_ADDED) {
                        AuctionDetailsBox(auction = auction)
                    } else {
                        Text(
                            text = "Auction Details to be announced",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Red,
                            modifier = Modifier.padding(8.dp),
                            textAlign = TextAlign.Center,
                            fontStyle = FontStyle.Italic,
                        )
                    }

                    Text(
                        text = "Current Highest Bid : 1200",
                        fontSize = 30.sp,
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )

                    if (AuctionDetails== AuctionStatus.LIVE) {
                        Box(
                            modifier = Modifier
                                .height(340.dp)
                                .width(360.dp)
                                .padding(8.dp)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Card(
                                modifier = Modifier.fillMaxSize(),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFFE08604)
                                ),
                                shape = RoundedCornerShape(20.dp)
                            ) {
                                LazyColumn {
                                    item {
                                        LatestBid()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (AuctionDetails== AuctionStatus.LIVE) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(Color.Red)
                    .height(70.dp)
            ) {
                TextButton(
                    onClick = {},
                    modifier = Modifier.fillMaxSize(),
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(0.dp),
                    contentPadding = PaddingValues()
                ) {
                    Text(
                        text = "BID YOUR PRICE",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp,
                        letterSpacing = 1.sp
                    )
                }
            }
        }
    }
}



@Composable
fun AuctionDetailsBox(auction: AuctionResponse?) {
    Box(
        modifier = Modifier
            .width(350.dp)
            .height(200.dp)
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            border = BorderStroke(1.dp, Color(0xFFE08604)),
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "Auction Details",
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    modifier = Modifier
                        .padding(top = 6.dp, bottom = 6.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Start At:  ${auction?.auctionStartTime}",
                    color = Color(0xFF0C1739),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "End At:  ${auction?.auctionEndTime}",
                    color = Color(0xFF0C1739),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

        }
    }
}

@Composable
fun LatestBid(){
    Box(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .padding(8.dp)
    ){
        Card(
            modifier = Modifier.fillMaxSize(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ){
            Row(
                modifier = Modifier.fillMaxSize().padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Image(
                        painter = painterResource(R.drawable.profile_picture_default),
                        contentDescription = "profile picture",
                        modifier = Modifier.size(60.dp).padding(end = 8.dp)
                    )

                    Column(
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "@Username",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color =  Color(0xFF0C1739)
                        )
                        Text(
                            text = "2 sec ago",
                            color = Color.DarkGray,
                            fontSize = 16.sp,
                        )
                    }
                }

                Text(
                    text = "1200 INR",
                    color = Color.Red,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}







