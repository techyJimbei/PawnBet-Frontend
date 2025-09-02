package com.example.pawnbet_frontend.ui.main

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.pawnbet_frontend.R
import com.example.pawnbet_frontend.model.AuctionStatus
import com.example.pawnbet_frontend.model.ProductRequest
import com.example.pawnbet_frontend.ui.theme.Beige
import com.example.pawnbet_frontend.ui.theme.DarkGrey
import com.example.pawnbet_frontend.ui.theme.LightGrey
import com.example.pawnbet_frontend.ui.theme.NavyBlue
import com.example.pawnbet_frontend.ui.theme.Orange
import com.example.pawnbet_frontend.viewmodel.ProductViewModel


@Composable
fun MyProductScreen(
    productViewModel: ProductViewModel,
    navController: NavController
){

    var liveSelected by remember { mutableStateOf(true) }

    val myProducts by productViewModel.myProducts

    val filteredProducts = if (liveSelected) {
        myProducts.filter { it.auctionStatus == AuctionStatus.LIVE }
    } else {
        myProducts.filter { it.auctionStatus != AuctionStatus.LIVE }
    }

    val addAuctionDetails by remember { mutableStateOf(false) }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        productViewModel.getMyProducts()
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Beige)
    ){
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) { 
            item{

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Box(
                        modifier = Modifier
                            .background(color = if(liveSelected) Color.White else LightGrey)
                            .height(80.dp)
                            .width(185.dp)
                            .border(if(liveSelected) 2.dp else 0.dp, if(liveSelected) Orange else LightGrey),
                        contentAlignment = Alignment.Center
                    ){
                        TextButton(
                            onClick = {
                                liveSelected = true
                            },
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = "LIVE",
                                fontSize = 30.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if(liveSelected) Orange else DarkGrey
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .background(color = if(liveSelected) LightGrey else Color.White)
                            .height(80.dp)
                            .width(185.dp)
                            .border(if(liveSelected) 0.dp else 2.dp, if(liveSelected) DarkGrey else Orange),
                        contentAlignment = Alignment.Center
                    ){
                        TextButton(
                            onClick = {
                                liveSelected = false
                            },
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = "LISTED",
                                fontSize = 30.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if(liveSelected) DarkGrey else Orange
                            )
                        }
                    }
                }

                Text(
                    text = "Your Products",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Medium,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.align(Alignment.Center)
                )
                
            }
            if(filteredProducts.isEmpty()){
                Toast.makeText(context, "No products are listed yet!", Toast.LENGTH_SHORT).show()
            }
            else{
                items(filteredProducts) { product ->
                    ProductCard(
                        product = product,
                        navController = navController,
                        productViewModel = productViewModel
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(100.dp))
        AddProductButton(
            productViewModel = productViewModel,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 32.dp, end = 24.dp)
        )
    }
}


@Composable
fun AddProductButton(
    productViewModel: ProductViewModel,
    modifier: Modifier
){

    val context = LocalContext.current

    val tags = listOf(
        "Watch", "Jewelry", "Sculpture", "Coin", "Antique", "Rare", "Fashion"
    )

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var basePrice by remember { mutableStateOf("") }
    var tag by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }

    var buttonState by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .height(60.dp)
            .width(60.dp)
            .background(color = Orange, shape = RoundedCornerShape(50)),
        contentAlignment = Alignment.Center
    ){
        TextButton(
            onClick = {buttonState = true}
        ) {
            Icon(
                painter = painterResource(R.drawable.add),
                contentDescription = "add icon",
                tint = Color.White
            )
        }
    }

    if(buttonState){
        Dialog(
            onDismissRequest = {buttonState = false}
        ) {
            Box(
                modifier = Modifier
                    .height(500.dp)
                    .width(500.dp)
                    .background(Color.White)
                    .clip(shape = RoundedCornerShape(20.dp))
            ){
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Text(
                        text = "Add Product Details",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Italic,
                        color = NavyBlue,
                        modifier = Modifier.padding(top =  16.dp)
                    )

                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Title") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Description") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = basePrice,
                        onValueChange = { basePrice = it },
                        label = { Text("BasePrice") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    TagDropdown(
                        selectedTag = tag,
                        onTagSelected = {tag = it},
                        tags = tags
                    )

                    OutlinedTextField(
                        value = imageUrl,
                        onValueChange = { imageUrl = it },
                        label = { Text("imageUrl") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick =  {
                            if(imageUrl.isBlank() || title.isBlank() || description.isBlank() || basePrice.isBlank() || tag.isBlank() ){
                                Toast.makeText(context, "Enter remaining fields", Toast.LENGTH_SHORT).show()
                            }
                            else{
                                val request = ProductRequest(
                                    title = title,
                                    description = description,
                                    tag = tag,
                                    basePrice = basePrice.toBigDecimal(),
                                    imageUrl = imageUrl,
                                )

                                productViewModel.listProduct(request)
                                buttonState = false
                            }
                        },
                        modifier = Modifier
                            .height(50.dp)
                            .width(200.dp),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            backgroundColor = Orange
                        )
                    ) {
                        Text(
                            text = "List Product",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }

                }
            }

        }
    }
}



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TagDropdown(
    selectedTag: String,
    onTagSelected: (String) -> Unit,
    tags: List<String>
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedTag,
            onValueChange = {},
            readOnly = true,
            label = { Text("Tag") },
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = "Dropdown"
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            tags.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        onTagSelected(option)
                        expanded = false
                    }
                ) {
                    Text(text = option)
                }
            }
        }
    }
}