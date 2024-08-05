package com.theapplicationpad.fosst_carapp.Mvvm.View.Screens

import android.Manifest
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import com.theapplicationpad.fosst_carapp.Mvvm.VIewModel.CarViewModel
import com.theapplicationpad.fosst_carapp.Mvvm.VIewModel.UserViewModel
import com.theapplicationpad.fosst_carapp.Mvvm.View.Navigation.Route.Route
import com.theapplicationpad.fosst_carapp.Mvvm.View.Screens.Component.FirebaseMessagingNotificationPermissionDialog
import com.theapplicationpad.fosst_carapp.R


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun BottomScreen(navController1: NavHostController,userViewModel: UserViewModel,carViewModel: CarViewModel,modifier: Modifier = Modifier) {


    val navController = rememberNavController()

    val showNotificationDialog = remember { mutableStateOf(false) }

    // Android 13 Api 33 - runtime notification permission has been added
    val notificationPermissionState = rememberPermissionState(
        permission = Manifest.permission.POST_NOTIFICATIONS
    )
    if (showNotificationDialog.value) FirebaseMessagingNotificationPermissionDialog(
        showNotificationDialog = showNotificationDialog,
        notificationPermissionState = notificationPermissionState
    )

    LaunchedEffect(key1=Unit){
        if (notificationPermissionState.status.isGranted ||
            Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU
        ) {
            Firebase.messaging.subscribeToTopic("Tutorial")
        } else showNotificationDialog.value = true
    }


    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = { MyBottomBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Route.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Route.Home.route) {
                HomeScreen(Bottomnavcontroller = navController, carViewModel = carViewModel, Mainnavcontroller = navController1, userViewModel = userViewModel)
            }
            composable(Route.Like.route) {
                LikeScreen(Bottomnavcontroller = navController, carViewModel = carViewModel, userViewModel = userViewModel)
            }
            composable(Route.Cart.route) {
                CartScreen(Bottomnavcontroller = navController, HomenavController = navController1, carViewModel = carViewModel, userViewModel = userViewModel)
            }

            composable(Route.Profile.route) {
                ProfileScreen(userViewModel = userViewModel, HomenavController = navController1, BottomnavController = navController)
            }
        }
    }
}


@Composable
fun MyBottomBar(navController: NavHostController) {

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val (selectedRoute, setSelectedRoute) = remember { mutableStateOf(Route.Home.route) }

    // Define navigation items
    val homeItem = BottomNavItem("Home", Route.Home.route, R.drawable.home)
    val LikeItem = BottomNavItem("Like", Route.Like.route, R.drawable.like)
    val Cartitem = BottomNavItem("Cart", Route.Cart.route, R.drawable.cart)
    val Profileitem = BottomNavItem("Profile", Route.Profile.route, R.drawable.profile)


    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 25.dp, end = 25.dp, bottom = 10.dp)
            .padding(10.dp)
        ,
        shape = CircleShape,
        colors = CardDefaults.cardColors(Color.Black),
        elevation = CardDefaults.elevatedCardElevation(10.dp)
    ) {


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // first side IconButton
            IconButton(
                onClick = {
                    if (currentRoute != homeItem.route) {
                        navController.navigate(homeItem.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                        }
                        setSelectedRoute(homeItem.route)
                    }
                },

            ) {
                Icon(
                    painter = painterResource(id = homeItem.icon),
                    contentDescription = homeItem.title,
                    tint = if (selectedRoute == homeItem.route) Color.White else Color.Gray,
                )


            }

            // second side IconButton
            IconButton(
                onClick = {
                    if (currentRoute != LikeItem.route) {
                        navController.navigate(LikeItem.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                        }
                        setSelectedRoute(LikeItem.route)
                    }
                },
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Icon(
                    painter = painterResource(id = LikeItem.icon),
                    contentDescription = homeItem.title,
                    tint = if (selectedRoute == LikeItem.route) Color.White else Color.Gray,
                )
            }

            // Spacer for right side alignment
            //  Spacer(modifier = Modifier.weight(1f))

            // third side IconButton
            IconButton(
                onClick = {
                    if (currentRoute != Cartitem.route) {
                        navController.navigate(Cartitem.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                        }
                        setSelectedRoute(Cartitem.route)
                    }
                },
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Icon(
                    painter = painterResource(id = Cartitem.icon),
                    contentDescription = homeItem.title,
                    tint = if (selectedRoute == Cartitem.route) Color.White else Color.Gray,
                )
            }


            // Fourth side IconButton
            IconButton(
                onClick = {
                    if (currentRoute != Profileitem.route) {
                        navController.navigate(Profileitem.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                        }
                        setSelectedRoute(Profileitem.route)
                    }
                },
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Icon(
                    painter = painterResource(id = Profileitem.icon),
                    contentDescription = Profileitem.title,
                    tint = if (selectedRoute == Profileitem.route) Color.White else Color.Gray,
                )
            }
        }
    }
}

data class BottomNavItem(
    val title: String,
    val route: String,
    val icon: Int,
    val secondicon: Int = 0
)