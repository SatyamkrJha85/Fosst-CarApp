package com.theapplicationpad.fosst_carapp.Mvvm.View.Screens.Component


import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.painterResource
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState

import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.theapplicationpad.fosst_carapp.R

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun FirebaseMessagingNotificationPermissionDialog(
    showNotificationDialog: MutableState<Boolean>,
    notificationPermissionState: PermissionState
) {
    if (showNotificationDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showNotificationDialog.value = false
                notificationPermissionState.launchPermissionRequest()
            },
            title = { Text(text = "Notification Permission") },
            text = { Text(text = "Please allow this app to send you a notification") },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.notifications),
                    contentDescription = null
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    showNotificationDialog.value = false
                    notificationPermissionState.launchPermissionRequest()
                    Firebase.messaging.subscribeToTopic("Cars")
                }) {
                    Text(text = "OK")
                }
            }
        )
    }
}