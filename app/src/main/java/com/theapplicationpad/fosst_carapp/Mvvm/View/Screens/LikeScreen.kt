package com.theapplicationpad.fosst_carapp.Mvvm.View.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.TouchApp
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.theapplicationpad.fosst_carapp.Mvvm.Model.FirebaseModel.Car
import com.theapplicationpad.fosst_carapp.Mvvm.VIewModel.CarViewModel
import com.theapplicationpad.fosst_carapp.Mvvm.VIewModel.UserViewModel
import com.theapplicationpad.fosst_carapp.R
import com.theapplicationpad.fosst_carapp.ui.theme.BackGroundColor

@Composable
fun LikeScreen(
    Bottomnavcontroller: NavHostController,
    carViewModel: CarViewModel,
    userViewModel: UserViewModel,
    modifier: Modifier = Modifier
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(BackGroundColor)

    val userEmail by userViewModel.userEmail.collectAsState(initial = "")

    val likedCars = carViewModel.likedCars.collectAsState().value
    // carViewModel.getLikedCars(userEmail.toString())
    Column(
        modifier
            .fillMaxSize()
            .background(BackGroundColor)
    ) {

        TopHeader(
            firsticon = Icons.Filled.ArrowBack,
            secondicon = Icons.Filled.Favorite,
            headertitle = "Favorite Car"
        )
        if (userEmail.toString().isNotEmpty()) {
            carViewModel.getLikedCars(userEmail.toString())




            likedCars.let {
                if (it.isEmpty()) {

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.emptylike),
                            contentDescription = null
                        )
                        Text(text = "Empty Liked List")
                    }

                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                        //.padding(16.dp)
                    ) {
                        items(likedCars) { car ->
                            LikeItem(car = car,
                                onAddToCart = { carViewModel.addToCart(userEmail.toString(), car) },
                                onRemoveFromLike = {
                                    carViewModel.removeFromLike(
                                        userEmail.toString(), car.id
                                    )
                                })
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
                Text(text = "Please log in to see liked cars.")
            }
        }
    }
}


@Composable
fun LikeItem(

    car: Car, onAddToCart: () -> Unit, onRemoveFromLike: () -> Unit
) {
    val likecaricon = remember {
        mutableStateOf(true)
    }

    ElevatedCard(
        modifier = Modifier

            .fillMaxWidth()
            .height(200.dp)
            .padding(12.dp),
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(22.dp)
    ) {
        Box(modifier = Modifier) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AsyncImage(
                    model = car.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(180.dp)
                        .padding(top = 1.dp, start = 1.dp),
//                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier.padding(end = 1.dp),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 1.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(
                            onClick = { onRemoveFromLike() },
                            modifier = Modifier
                                .padding(5.dp)
                                .size(25.dp)
                                .clip(shape = CircleShape)
                                .background(
                                    BackGroundColor
                                )
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Favorite,
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
                        text = "Price: $ ${car.price}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "⭐ Rating (${car.rating})", fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                    )

                    Row(
                        modifier = Modifier.padding(top = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,

                        ) {
                        Button(
                            onClick = { onAddToCart() },
                            colors = ButtonDefaults.buttonColors(Color.Black),
                            modifier = Modifier
                                .height(38.dp)
                                .width(120.dp)
                        ) {
                            Text(text = "Add to cart", color = Color.White)
                        }


                    }

                }
            }
        }

    }

}

@Composable
fun TopHeader(
    modifier: Modifier = Modifier,
    firsticon: ImageVector,
    secondicon: ImageVector,
    headertitle: String,
    tint: Color = Color.Black,
    Background: Color = Color.White
) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        IconButton(
            onClick = { /*TODO*/ },
            modifier
                .size(40.dp)
                .clip(shape = CircleShape)
                .background(
                    Background
                )
        ) {
            Icon(
                imageVector = firsticon,
                contentDescription = null,
                tint = tint
            )
        }

        Text(text = headertitle, fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = tint)

        IconButton(
            onClick = { /*TODO*/ },
            modifier
                .size(40.dp)
                .clip(shape = CircleShape)
                .background(
                    Background
                )
        ) {
            Icon(
                imageVector = secondicon,
                contentDescription = null,
                tint = tint
            )
        }
    }
}