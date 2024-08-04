package com.theapplicationpad.fosst_carapp.Mvvm.View.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.theapplicationpad.fosst_carapp.Mvvm.Model.FirebaseModel.Car
import com.theapplicationpad.fosst_carapp.Mvvm.VIewModel.CarViewModel
import com.theapplicationpad.fosst_carapp.Mvvm.VIewModel.UserViewModel
import com.theapplicationpad.fosst_carapp.ui.theme.BackGroundColor


@Composable
fun PurchaseHistoryScreen(
    carViewModel: CarViewModel,
    HomenavController: NavHostController,
    userViewModel: UserViewModel,
) {
    val purchasedCars by carViewModel.purchasedCars.collectAsState()
    val userEmail by userViewModel.userEmail.collectAsState(initial = "")

    LaunchedEffect(Unit) {
        carViewModel.getPurchasedCars(userEmail.toString())
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(BackGroundColor)
        .padding(16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Purchase History",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 16.dp)
        )

        if (purchasedCars.isEmpty()) {
            Text(text = "No purchases found.")
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(purchasedCars) { car ->
                    PurchaseHistoryItem(car)
                }
            }
        }
    }
}

@Composable
fun PurchaseHistoryItem(car: Car) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp), elevation  = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(Color.White)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = car.name, style = MaterialTheme.typography.titleMedium, color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Price: ${car.price}", color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Details: ${car.details}", color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Rating: ${car.rating}", color = Color.Black)
        }
    }
}