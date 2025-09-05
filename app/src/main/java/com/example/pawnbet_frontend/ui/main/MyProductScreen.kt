package com.example.pawnbet_frontend.ui.main

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.offset
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
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.example.pawnbet_frontend.model.AuctionRequest
import com.example.pawnbet_frontend.model.AuctionStatus
import com.example.pawnbet_frontend.model.ProductRequest
import com.example.pawnbet_frontend.ui.theme.Beige
import com.example.pawnbet_frontend.ui.theme.DarkGrey
import com.example.pawnbet_frontend.ui.theme.LightGrey
import com.example.pawnbet_frontend.ui.theme.NavyBlue
import com.example.pawnbet_frontend.ui.theme.Orange
import com.example.pawnbet_frontend.ui.theme.Red
import com.example.pawnbet_frontend.viewmodel.AuctionViewModel
import com.example.pawnbet_frontend.viewmodel.ProductViewModel
import java.util.Calendar


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyProductScreen(
    productViewModel: ProductViewModel,
    auctionViewModel: AuctionViewModel,
    navController: NavController
) {

    var liveSelected by remember { mutableStateOf(true) }

    val myProducts by productViewModel.myProducts

    val filteredProducts = if (liveSelected) {
        myProducts.filter { it.auctionStatus == AuctionStatus.LIVE }
    } else {
        myProducts.filter { it.auctionStatus != AuctionStatus.LIVE }
    }

    var addAuctionDetails by remember { mutableStateOf(false) }
    val addAuctionProduct by productViewModel.auctionSelectedProduct.collectAsState()
    val auctionDetailsMap by auctionViewModel.auctionDetailsMap

    var editProductDialog by remember { mutableStateOf(false) }
    var editProductId by remember { mutableStateOf<Long?>(null) }

    val context = LocalContext.current

    val calendar = Calendar.getInstance()
    val startTime by productViewModel.startTime
    val endTime by productViewModel.endTime


    val startDatePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val date = "%04d-%02d-%02d".format(year, month + 1, dayOfMonth)
            val timePickerDialog = TimePickerDialog(
                context,
                { _, hour: Int, minute: Int ->
                    productViewModel.setStartTime("${date}T%02d:%02d:00".format(hour, minute))
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            )
            timePickerDialog.show()
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val endDatePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val date = "%04d-%02d-%02d".format(year, month + 1, dayOfMonth)
            val timePickerDialog = TimePickerDialog(
                context,
                { _, hour: Int, minute: Int ->
                    productViewModel.setEndTime("${date}T%02d:%02d:00".format(hour, minute))
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            )
            timePickerDialog.show()
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )


    LaunchedEffect(Unit) {
        productViewModel.getMyProducts()
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

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
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
                            onClick = {
                                liveSelected = true
                            },
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
                            onClick = {
                                liveSelected = false
                            },
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = "LISTED",
                                fontSize = 30.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (liveSelected) DarkGrey else Orange
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
            if (filteredProducts.isEmpty()) {
                Toast.makeText(context, "No products are listed yet!", Toast.LENGTH_SHORT).show()
            } else {
                items(filteredProducts) { product ->
                    auctionViewModel.getAuctionDetails(product.id)
                    TextButton(
                        onClick = {
                            editProductDialog = true
                            editProductId = product.id
                        },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(y = 16.dp, x = 44.dp)
                    ) {
                        Text(
                            text = "Edit Product",
                            fontSize = 16.sp,
                            color = NavyBlue,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    ProductCard(
                        product = product,
                        navController = navController,
                        productViewModel = productViewModel
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    if (product.auctionStatus == AuctionStatus.YET_TO_DECLARE) {
                        Button(
                            onClick = {
                                addAuctionDetails = true
                                productViewModel.auctionSelectProduct(product)
                            },
                            modifier = Modifier
                                .height(50.dp)
                                .width(150.dp)
                                .offset(y = -(34).dp, x = -(8).dp),
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.White,
                                backgroundColor = Red
                            ),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text(
                                text = "Add Auction",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium,
                                fontStyle = FontStyle.Italic
                            )
                        }
                    }
                    val details = auctionDetailsMap[product.id]
                    if (product.auctionStatus == AuctionStatus.DETAILS_ADDED && details != null) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .offset(x = -(130).dp, y = -(210).dp)
                                .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                                .border(1.dp, LightGrey, RoundedCornerShape(12.dp))
                                .padding(10.dp)
                                .width(80.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Start:", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                Text(details.auctionStartTime.split("T")[0], fontSize = 12.sp)
                                Text(
                                    details.auctionStartTime.split("T")[1].substring(0, 5),
                                    fontSize = 12.sp
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                Text("End:", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                Text(details.auctionEndTime.split("T")[0], fontSize = 12.sp)
                                Text(
                                    details.auctionEndTime.split("T")[1].substring(0, 5),
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }

                    if (addAuctionDetails) {
                        Dialog(
                            onDismissRequest = { addAuctionDetails = false }
                        ) {
                            Box(
                                modifier = Modifier
                                    .height(500.dp)
                                    .width(500.dp)
                                    .background(Color.White)
                                    .clip(shape = RoundedCornerShape(20.dp))
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    Text(
                                        text = "Add Auction Details",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = NavyBlue
                                    )


                                    OutlinedTextField(
                                        value = startTime,
                                        onValueChange = {},
                                        label = { Text("Start Time") },
                                        modifier = Modifier.fillMaxWidth(),
                                        enabled = false,
                                        trailingIcon = {
                                            IconButton(onClick = { startDatePickerDialog.show() }) {
                                                Icon(
                                                    Icons.Default.DateRange,
                                                    contentDescription = "Pick Start DateTime"
                                                )
                                            }
                                        }
                                    )

                                    OutlinedTextField(
                                        value = endTime,
                                        onValueChange = {},
                                        label = { Text("End Time") },
                                        modifier = Modifier.fillMaxWidth(),
                                        enabled = false,
                                        trailingIcon = {
                                            IconButton(onClick = { endDatePickerDialog.show() }) {
                                                Icon(
                                                    Icons.Default.DateRange,
                                                    contentDescription = "Pick End DateTime"
                                                )
                                            }
                                        }
                                    )

                                    Button(
                                        onClick = {
                                            if (startTime.isBlank() || endTime.isBlank()) {
                                                Toast.makeText(
                                                    context,
                                                    "Enter remaining fields",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            } else {
                                                val request = AuctionRequest(
                                                    auctionStartTime = startTime,
                                                    auctionEndTime = endTime
                                                )
                                                val productId = addAuctionProduct?.id
                                                auctionViewModel.addAuctionDetails(
                                                    productId,
                                                    request
                                                )
                                            }
                                            addAuctionDetails = false
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
                                            text = "Add Details",
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.SemiBold,
                                        )
                                    }

                                }
                            }
                        }
                    }
                    if (editProductDialog && editProductId == product.id) {
                        var title by remember { mutableStateOf(product.title) }
                        var description by remember { mutableStateOf(product.description) }
                        var basePrice by remember { mutableStateOf(product.basePrice.toString()) }
                        var tag by remember { mutableStateOf(product.tag ?: "") }
                        var imageUrl by remember { mutableStateOf(product.imageUrl ?: "") }
                        Dialog(
                            onDismissRequest = { editProductDialog = false }
                        ) {
                            Box(
                                modifier = Modifier
                                    .height(500.dp)
                                    .width(500.dp)
                                    .background(Color.White)
                                    .clip(shape = RoundedCornerShape(20.dp))
                            ) {
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
                                        modifier = Modifier.padding(top = 16.dp)
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
                                        label = { Text("Base Price") },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    TagDropdown(
                                        selectedTag = tag,
                                        onTagSelected = { tag = it },
                                        tags = tags
                                    )

                                    OutlinedTextField(
                                        value = imageUrl,
                                        onValueChange = { imageUrl = it },
                                        label = { Text("ImageUrl") },
                                        modifier = Modifier.fillMaxWidth()
                                    )

                                    Button(
                                        onClick = {
                                            val request = ProductRequest(
                                                title = title,
                                                description = description,
                                                tag = tag,
                                                basePrice = basePrice.toBigDecimal(),
                                                imageUrl = imageUrl,
                                            )

                                            productViewModel.editProduct(request, product.id)
                                            editProductDialog = false
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
                                            text = "Edit Product",
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.SemiBold,
                                        )
                                    }

                                }
                            }
                        }
                    }
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

val tags = listOf(
    "Watch", "Jewelry", "Sculpture", "Coin", "Antique", "Rare", "Fashion"
)

@Composable
fun AddProductButton(
    productViewModel: ProductViewModel,
    modifier: Modifier
) {

    val context = LocalContext.current

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
    ) {
        TextButton(
            onClick = { buttonState = true }
        ) {
            Icon(
                painter = painterResource(R.drawable.add),
                contentDescription = "add icon",
                tint = Color.White
            )
        }
    }

    if (buttonState) {
        Dialog(
            onDismissRequest = { buttonState = false }
        ) {
            Box(
                modifier = Modifier
                    .height(500.dp)
                    .width(500.dp)
                    .background(Color.White)
                    .clip(shape = RoundedCornerShape(20.dp))
            ) {
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
                        modifier = Modifier.padding(top = 16.dp)
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
                        onTagSelected = { tag = it },
                        tags = tags
                    )

                    OutlinedTextField(
                        value = imageUrl,
                        onValueChange = { imageUrl = it },
                        label = { Text("imageUrl") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = {
                            if (imageUrl.isBlank() || title.isBlank() || description.isBlank() || basePrice.isBlank() || tag.isBlank()) {
                                Toast.makeText(
                                    context,
                                    "Enter remaining fields",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
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