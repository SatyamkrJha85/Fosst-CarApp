package com.theapplicationpad.fosst_carapp.Mvvm.View.Screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DirectionsCarFilled
import androidx.compose.material.icons.filled.EventSeat
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.SportsMotorsports
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.theapplicationpad.fosst_carapp.Mvvm.Model.FirebaseModel.Car
import com.theapplicationpad.fosst_carapp.Mvvm.VIewModel.CarViewModel
import com.theapplicationpad.fosst_carapp.Mvvm.VIewModel.UserViewModel
import com.theapplicationpad.fosst_carapp.Mvvm.View.Navigation.Route.Route
import com.theapplicationpad.fosst_carapp.Mvvm.View.Screens.Component.startPayment
import com.theapplicationpad.fosst_carapp.R
import com.theapplicationpad.fosst_carapp.ui.theme.BackGroundColor
@Composable
 fun CarDetailsScreen(modifier: Modifier = Modifier,car: Car,HomenavController: NavController,carViewModel: CarViewModel,userViewModel: UserViewModel) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(Color.Black)
     val userEmail = userViewModel.userEmail.collectAsState(initial ="")
    Box(
        modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 40.dp)
                .align(Alignment.TopCenter)
        ) {
            TopHeader(
                firsticon = Icons.AutoMirrored.Filled.ArrowBack,
                secondicon = Icons.Filled.DirectionsCarFilled,
                headertitle = "Car Details",
                tint = Color.White,
                Background = Color.Black
            )
        }

        Row(
            modifier
                .fillMaxWidth()
                .padding(top = 50.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = car.imageUrl,
                contentDescription = null,
                modifier.size(400.dp)
            )
        }

        Card(
            modifier
                .padding()
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                ,
            shape = RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp),
            colors = CardDefaults.cardColors(Color.White)
        ) {
            Row(
                modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = car.name,
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "⭐(${car.rating})",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Row(
                modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${car.details}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
            }

            Row(
                modifier
                    .fillMaxWidth()
                    .padding(start = 13.dp, top = 12.dp)
            ) {
                Text(
                    text = "Features", color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Row(
                modifier
                    .fillMaxWidth()
                    .padding(start = 13.dp, top = 12.dp)
            ) {
                featurebox(
                    icon = Icons.Filled.EventSeat,
                    firstitle = "Total\nCapacity",
                    secondtitle = "${car.capacity} Seats"
                )
                featurebox(
                    icon = Icons.Filled.Speed,
                    firstitle = "Highest\nSpeed",
                    secondtitle = "${car.speed} KM/H"
                )
                featurebox(
                    icon = Icons.Filled.SportsMotorsports,
                    firstitle = "Engile\nOutput",
                    secondtitle = "${car.speed} TP"
                )

            }

            Row(
                modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 12.dp, end = 20.dp, bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Price", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    Text(
                        text = "$${car.price}",
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Button(
                    onClick = {
                        carViewModel.addToPurchaseHistory(
                            userEmail.toString(),
                            car
                        )
                        HomenavController.navigate(
                            Route.PaymentScreen.createRoute(
                                car.price.toDouble()
                            )
                        )
                    },
                    colors = ButtonDefaults.buttonColors(Color.Black),
                    modifier = modifier
                        .width(180.dp)
                        .height(50.dp)
                ) {
                    Text(text = "Buy Now", color = Color.White)
                }
            }

        }
    }
}

@Composable
fun featurebox(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    firstitle: String,
    secondtitle: String
) {
    Box(
        modifier = modifier
            .width(110.dp)
            .height(160.dp)
            .padding(6.dp)
            .clip(shape = RoundedCornerShape(15.dp))
            .background(BackGroundColor), contentAlignment = Alignment.Center
    ) {
        Column {

            IconButton(
                onClick = { /*TODO*/ },
                modifier
                    .size(40.dp)
                    .clip(shape = CircleShape)
                    .background(
                        Color.White
                    )
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
            Spacer(modifier = modifier.height(10.dp))

            Text(
                text = firstitle,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = modifier.height(15.dp))
            Text(text = secondtitle, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)

        }
    }
}