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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pawnbet_frontend.R
import com.example.pawnbet_frontend.ui.theme.NavyBlue
import com.example.pawnbet_frontend.ui.theme.Orange

@Composable
@Preview(showSystemUi = true)
fun ProductPreviewScreenPreview() {
    ProductPreviewScreen()
}

@Composable
fun ProductPreviewScreen() {

    val AuctionDetails by remember { mutableStateOf(false) }
    val isLive by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFFAF8F4))
    ) {

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            item {
                Column(
                    modifier = Modifier,
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

                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .size(350.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.test_image),
                            contentDescription = "test image",
                            modifier = Modifier.fillMaxSize()
                        )

                        IconButton(
                            onClick = {},
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .offset(x = (12).dp, y = (8).dp)
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

                        if(isLive){
                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .align(Alignment.CenterStart)
                                    .offset(x = (-25).dp)
                                    .background(color = Color.Red, shape = CircleShape)
                            ){
                                Column(
                                    modifier = Modifier.fillMaxSize().padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(4.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ){
                                    Text(
                                        text =  "LIVE",
                                        color = Color.White,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                    )

                                    Text(
                                        text = "ends in:\n" +
                                                "1:00:39",
                                        fontSize = 16.sp,
                                        color = Color.White,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }

                            }
                        }
                    }


                    Text(
                        text = "Antique Wrist Watch",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = NavyBlue,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Text(
                        text = "hfhidyfbadoufhafvyihaofpip\n" +
                                "- iduiyfvhefuewyvfbewfouweg\n" +
                                "- fifhbwbeufwevbjfoehf\n" +
                                "- jbfhwfbwofasjbdascihuvbdw\n" +
                                "idfuwefefyeugfhoqwiujdnsbufjfnsifdkncbviug8rwogfvybboijncoubxjn\n",
                        fontSize = 16.sp,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(top = 8.dp),
                        fontWeight = FontWeight.Medium
                    )

                    if (AuctionDetails) {
                        AuctionDetailsBox()
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
                        fontSize =  30.sp,
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )

                    if (isLive) {
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
                                LazyColumn(
                                    modifier = Modifier
                                ){
                                    item{
                                        LatestBid()
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }

        if (isLive){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(Color.Red)
                    .height(70.dp)
            ) {
                TextButton(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxSize(),
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
fun AuctionDetailsBox() {
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
                    text = "Start At:  Today 21:00:00",
                    color = Color(0xFF0C1739),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "End At:  Tomorrow 00:00:00",
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







