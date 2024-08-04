package com.theapplicationpad.fosst_carapp.Mvvm.View.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.RemoveShoppingCart
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.theapplicationpad.fosst_carapp.Mvvm.Model.FirebaseModel.Car
import com.theapplicationpad.fosst_carapp.Mvvm.VIewModel.CarViewModel
import com.theapplicationpad.fosst_carapp.Mvvm.VIewModel.UserViewModel
import com.theapplicationpad.fosst_carapp.Mvvm.View.Navigation.Route.Route
import com.theapplicationpad.fosst_carapp.R
import com.theapplicationpad.fosst_carapp.ui.theme.BackGroundColor

@Composable
fun CartScreen(
    Bottomnavcontroller: NavHostController,
    carViewModel: CarViewModel,
    HomenavController: NavHostController,
    userViewModel: UserViewModel,
    modifier: Modifier = Modifier
) {

    val userEmail by userViewModel.userEmail.collectAsState(initial = "")

    val cartCars = carViewModel.cartCars.collectAsState().value
    Column(
        modifier
            .fillMaxSize()
            .background(BackGroundColor)
    ) {

        TopHeader(
            firsticon = Icons.Filled.ArrowBack,
            secondicon = Icons.Filled.ShoppingCart,
            headertitle = "Your Cart"
        )


        if (userEmail.toString().isNotEmpty()) {
            carViewModel.getCartCars(userEmail.toString())

            cartCars.let {
                if (it.isEmpty()) {

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.emptycart),
                            contentDescription = null
                        )
                        Text(text = "Empty Cart Item")
                    }

                } else {


                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                        // .padding(16.dp)
                    ) {
                        items(cartCars) { car ->
                            CartItem(
                                car = car,
                                PaymentScreen = {
                                    val amount = car.price.toDouble()
                                    carViewModel.addToPurchaseHistory(
                                        userEmail.toString(),
                                        car
                                    )
                                    HomenavController.navigate(
                                        Route.PaymentScreen.createRoute(
                                            amount
                                        )
                                    )
                                    carViewModel.removeFromCart(
                                        userEmail.toString(),
                                        car.id
                                    )
                                },
                                onRemoveFromCart = {
                                    carViewModel.removeFromCart(
                                        userEmail.toString(),
                                        car.id
                                    )
                                }
                            )
                        }

                        item {

                            //  Price and button in Bottom

                            val totalAmount = cartCars.sumOf { it.price.toInt() }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Total: $$totalAmount",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Button(
                                    onClick = {

                                        cartCars.forEach { car ->
                                            carViewModel.addToPurchaseHistory(userEmail.toString(), car)
                                            carViewModel.removeFromCart(userEmail.toString(), car.id)
                                        }

                                        HomenavController.navigate(
                                            Route.PaymentScreen.createRoute(
                                                totalAmount.toDouble()
                                            )
                                        )

                                    },
                                    colors = ButtonDefaults.buttonColors(Color.Black)
                                ) {
                                    Text(text = "Proceed to Payment", color = Color.White)
                                }
                            }
                        }
                    }


                }
            }
        } else {
            // Handle user not logged in
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Please log in to see cart items.")
            }
        }
    }
}


@Composable
fun CartItem(
    modifier: Modifier = Modifier, car: Car,
    PaymentScreen: () -> Unit,
    onRemoveFromCart: () -> Unit
) {


    ElevatedCard(
        modifier = Modifier

            .fillMaxWidth()
            .height(200.dp)
            .padding(12.dp),
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(22.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                if (car.imageUrl.isNotEmpty()) {


                    AsyncImage(
                        model = car.imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .size(180.dp)
                            .padding(top = 1.dp, start = 1.dp),
                        // contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.user), contentDescription = null,
                        modifier = Modifier
                            .size(140.dp)
                            .padding(top = 10.dp, start = 10.dp),
                        contentScale = ContentScale.Crop
                    )
                }




                Column(
                    modifier = Modifier.padding(end = 5.dp, top = 5.dp, start = 10.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(
                            onClick = { onRemoveFromCart() },
                            modifier = Modifier
                                .padding(5.dp)
                                .size(25.dp)
                                .clip(shape = CircleShape)
                                .background(
                                    BackGroundColor
                                )
                        ) {
                            Icon(
                                imageVector = Icons.Filled.RemoveShoppingCart,
                                contentDescription = null,
                                tint = Color.Black
                            )
                        }
                    }
                    Text(
                        text = car.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        text = "Price: $${car.price}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "⭐ Rating (${car.rating})", fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            PaymentScreen()
                        },
                        colors = ButtonDefaults.buttonColors(Color.Black),
                        modifier = Modifier
                            .height(38.dp)
                            .width(150.dp)
                    ) {
                        Text(text = "Buy Now", color = Color.White)
                    }
                }

            }
        }


    }
}


