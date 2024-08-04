package com.theapplicationpad.fosst_carapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.theapplicationpad.fosst_carapp.Mvvm.FirebaseRepo.CarRepository
import com.theapplicationpad.fosst_carapp.Mvvm.VIewModel.CarViewModelFactory
import com.theapplicationpad.fosst_carapp.Mvvm.View.Navigation.NavGraph.NavGraph
import com.theapplicationpad.fosst_carapp.ui.theme.FosstCarAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val carRepository = CarRepository()
        val carViewModelFactory = CarViewModelFactory(carRepository)
        setContent {
            FosstCarAppTheme {
                NavGraph(context = this@MainActivity, carViewModelFactory = carViewModelFactory)
            }
        }
    }
}



