package com.theapplicationpad.fosst_carapp.Mvvm.View.Screens


import android.content.Context
import android.location.Geocoder
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import java.util.Locale

@Composable
fun MapScreen(location: String) {
    val context = LocalContext.current
    val coordinates = remember { mutableStateOf<LatLng?>(null) }

    LaunchedEffect(location) {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocationName(location, 1)
        if (addresses != null && addresses.isNotEmpty()) {
            val address = addresses[0]
            coordinates.value = LatLng(address.latitude, address.longitude)
        }
    }

    val cameraPositionState = rememberCameraPositionState {
        coordinates.value?.let {
            position = CameraPosition.fromLatLngZoom(it, 12f)
        }
    }

    if (coordinates.value != null) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            coordinates.value?.let {
                Marker(position = it, title = location)
            }
        }
    }
}
