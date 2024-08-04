package com.theapplicationpad.fosst_carapp.Mvvm.View.Screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.theapplicationpad.fosst_carapp.Mvvm.View.Screens.Component.AddPaymentCard
import com.theapplicationpad.fosst_carapp.ui.theme.BackGroundColor


//@Preview(showSystemUi = true)
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PaymentScreen(modifier: Modifier = Modifier,navController: NavController, amount: Double) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(BackGroundColor)
    AddPaymentCard(price = amount)
}