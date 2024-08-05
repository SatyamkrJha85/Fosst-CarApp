package com.theapplicationpad.fosst_carapp.Mvvm.View.Screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.NotificationImportant
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.theapplicationpad.fosst_carapp.Mvvm.Model.FirebaseModel.Car
import com.theapplicationpad.fosst_carapp.Mvvm.Model.GeneralModal.BrandModel
import com.theapplicationpad.fosst_carapp.Mvvm.Model.GeneralModal.Brandlist
import com.theapplicationpad.fosst_carapp.Mvvm.VIewModel.CarViewModel
import com.theapplicationpad.fosst_carapp.Mvvm.VIewModel.UserViewModel
import com.theapplicationpad.fosst_carapp.Mvvm.View.Navigation.Route.Route
import com.theapplicationpad.fosst_carapp.Mvvm.View.Screens.Component.ShimmerEffect
import com.theapplicationpad.fosst_carapp.R
import com.theapplicationpad.fosst_carapp.ui.theme.BackGroundColor
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    Bottomnavcontroller: NavHostController,
    carViewModel: CarViewModel,
    Mainnavcontroller: NavHostController,
    userViewModel: UserViewModel
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(BackGroundColor)

    val cars = carViewModel.featureCars.collectAsState().value
    val userEmail by userViewModel.userEmail.collectAsState(initial = "")

    var searchQuery by remember { mutableStateOf("") }
    val filteredCars = cars.filter { it.name.contains(searchQuery, ignoreCase = true) }
    var showShimmer by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(600)
        showShimmer = false
    }

    LazyColumn(
        modifier = Modifier
            .background(BackGroundColor)
            .fillMaxSize()
    ) {
        item {
            Header(userViewModel = userViewModel)
            HomeSearchBar(navController = Mainnavcontroller, searchQuery = searchQuery, onSearchQueryChange = { query ->
               searchQuery = query
            })
            LogosRow()
            Column(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .clip(shape = RoundedCornerShape(topStart = 23.dp, topEnd = 23.dp))
                    .background(
                        Color.White
                    )
                    .padding(10.dp)
            ) {
                Textrow()
            }
        }

        item {

            Box(
                modifier = Modifier
                    .background(Color.White)
                    .padding(10.dp)
                    .fillMaxWidth()
                    .heightIn(max = 500.dp) // Ensure bounded height for the grid
            ) {

                if (cars.isNotEmpty()  && !showShimmer){
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .background(Color.White)

                    ) {

                        items(filteredCars) { car ->
                            PopularCarItem(
                                modifier = Modifier,
                                car = car,
                                onDetailsClick = {
                                    Mainnavcontroller.navigate(Route.Details.createRoute(car.id))
                                },
                                onAddToCart = {
                                    carViewModel.addToCart(userEmail.toString(), car)
                                },
                                onAddToLike = {
                                    carViewModel.addToLike(userEmail.toString(), car)
                                },
                                onRemoveLike = {
                                    carViewModel.removeFromLike(
                                        userEmail.toString(),
                                        car.id
                                    )
                                }
                            )
                        }
                    }
                }
                else{
//                    Column(modifier = Modifier.fillMaxSize(),
//                        verticalArrangement = Arrangement.Center,
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        Text(text = "Empty Product")
//
//                    }

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .background(Color.White)
                    ) {
                        items(10) {
                            ShimmerEffect()
                        }
                    }
                }

            }
        }


    }
}


@Composable
fun Header(userViewModel: UserViewModel, modifier: Modifier = Modifier) {
    val img by userViewModel.userProfilePic.collectAsState(initial = "")
    val username by userViewModel.userName.collectAsState(initial = "")


    Row(
        modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            AsyncImage(
                model =img, contentDescription = null,
                modifier
                    .size(50.dp)
                    .clip(shape = CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(text = "Welcome 👋")
                Text(text = "$username", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
        }


        Row {
            IconButton(
                onClick = { /*TODO*/ },
                modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(
                        Color.White
                    )
            ) {
                Icon(imageVector = Icons.Filled.NotificationsActive, contentDescription = null)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeSearchBar(  searchQuery: String,
                    onSearchQueryChange: (String) -> Unit,modifier: Modifier = Modifier,navController: NavController) {
    var searchtext by remember {
        mutableStateOf("")
    }
    Row(
        modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        TextField(
            modifier = modifier
                .width(350.dp)
                .clip(shape = CircleShape),
            value = searchQuery,
            onValueChange = onSearchQueryChange ,
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Search, contentDescription = null)
            },
            singleLine = true,
            maxLines = 1,
            trailingIcon = {
                IconButton(
                    onClick = { navController.navigate(Route.Special.route) },
                    modifier
                        .size(40.dp)
                        .clip(shape = CircleShape)
                        .background(
                            Color.Black
                        )
                ) {
                    Icon(
                        imageVector = Icons.Filled.TouchApp,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            },
            placeholder = { Text(text = "Search Your Car") },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedTextColor = Color.Black,
                disabledTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            )
        )
    }
}

@Composable
fun LogosRow(modifier: Modifier = Modifier) {
    Row(
        modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        LazyRow {
            items(Brandlist) { model ->
                LogoItem(model = model)
            }
        }
    }
}

@Composable
fun LogoItem(modifier: Modifier = Modifier, model: BrandModel) {
    Column(
        modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally


    ) {
        Box(
            modifier = modifier
                .padding(5.dp)
                .size(55.dp)
                .clip(CircleShape)
                .background(Color.Black), contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = model.img),
                contentDescription = null,
                modifier.size(35.dp)
            )
        }

        Text(text = model.carname, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
    }

}

@Composable
fun PopularCarItem(
    modifier: Modifier = Modifier,
    car: Car,
    onDetailsClick: () -> Unit,
    onAddToCart: () -> Unit,
    onAddToLike: () -> Unit,
    onRemoveLike: () -> Unit
) {
    var buttonliked = remember {
        mutableStateOf(false)
    }
    Column {
        Box(
            modifier = modifier
                .padding(5.dp)
                .clip(shape = RoundedCornerShape(16.dp))
                .width(190.dp)

                .clickable {
                    onDetailsClick()
                }
                .height(260.dp)
                .background(BackGroundColor)
        ) {
            IconButton(onClick = {
                buttonliked.value = !buttonliked.value
                if (buttonliked.value == true) {
                    onAddToLike()
                } else {
                    onRemoveLike()
                }


            }) {
                Icon(
                    imageVector = if (buttonliked.value == false) Icons.Filled.FavoriteBorder else Icons.Filled.Favorite,
                    contentDescription = null,
                    modifier
                        .padding(top = 5.dp, start = 5.dp)
                        .align(Alignment.CenterStart)

                )
            }
            Box(
                modifier = modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp), contentAlignment = Alignment.Center
            ) {
                AsyncImage(model = car.imageUrl, contentDescription = null, modifier.size(200.dp))
            }
            Column(
                modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 5.dp)
            ) {
                Text(text = car.name, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)

                Row(
                    modifier
                        .fillMaxWidth()
                        .padding(end = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "$ ${car.price}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "⭐ ${car.rating}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                }
                Row {
                    Button(
                        onClick = { onAddToCart() },
                        modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .clip(shape = CircleShape),
                        colors = ButtonDefaults.buttonColors(Color.White)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ShoppingCart,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                }
            }

        }
    }
}

@Composable
fun Textrow(modifier: Modifier = Modifier) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Popular Car", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Text(text = "View all")
    }
}