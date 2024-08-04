package com.theapplicationpad.fosst_carapp.Mvvm.View.Screens.Component

import android.app.Activity
import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import com.razorpay.Checkout
import com.theapplicationpad.fosst_carapp.Mvvm.View.Screens.State.ConfirmationState
import com.theapplicationpad.fosst_carapp.ui.theme.LightGray
import org.json.JSONObject
import kotlin.math.roundToInt

@Composable
private fun DraggableControl(
    modifier: Modifier,
    progress: Float
) {


    Box(
        modifier = modifier
            .padding(4.dp)
            .shadow(
                elevation = 2.dp,
                CircleShape,
                clip = false
            )
            .background(Color.White, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        val isConfirmed by remember { derivedStateOf { progress >= 0.8f } }

        Crossfade(targetState = isConfirmed) {
            if (it) {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Confirm Icon",
                    tint = Color.Black
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = "Forward Icon",
                    tint = Color.Black
                )
            }
        }
    }
}

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun ConfirmationButton(
    modifier: Modifier = Modifier
        .fillMaxWidth(),
    price:Double
) {
    val width = 300.dp
    val dragSize = 50.dp
    val swipeableState = rememberSwipeableState(initialValue = ConfirmationState.DEFAULT)
    val sizePx = with(LocalDensity.current) {
        (width - dragSize).toPx()
    }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val anchors = mapOf(0f to ConfirmationState.DEFAULT, sizePx to ConfirmationState.CONFIRMED)
    val progress by remember {
        derivedStateOf {
            if (swipeableState.offset.value == 0f) 0f else swipeableState.offset.value / sizePx
        }
    }

    if (swipeableState.currentValue == ConfirmationState.CONFIRMED) {
        LaunchedEffect(Unit) {
            startPayment(price, context = context, activity = context as Activity, lifecycleOwner = lifecycleOwner)
        }
    }

    Box(
        modifier = modifier
            .width(width)
            .height(60.dp)
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.5f) },
                orientation = Orientation.Horizontal
            )
            .background(Color.Black, RoundedCornerShape(dragSize))
    ) {
        Column(
            Modifier
                .align(Alignment.Center)
                .alpha(1f - progress),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Your Order is Waiting",
                color = Color.White,
                fontSize = 18.sp
            )
            Text(
                text = "Swipe to Pay $$price",
                color = Color.White,
                fontSize = 12.sp
            )
        }

        DraggableControl(
            modifier = Modifier
                .padding(top = 5.dp)
                .offset {
                    IntOffset(swipeableState.offset.value.roundToInt(), 0)
                }
                .size(dragSize),
            progress = progress
        )
    }
}


fun startPayment(amount: Double, context: Context, activity: Activity, lifecycleOwner: LifecycleOwner) {
    val checkout = Checkout()
    checkout.setKeyID("rzp_test_EQzkRLIa9IeeTz")

    try {
        val options = JSONObject()
        options.put("Fosst", "Fosst Car Shop")
        options.put("description", "Payment for your order")
        options.put("currency", "INR")
        options.put("amount", (amount * 100).toInt()) // amount in paise

        // Register a lifecycle observer to handle unregistration of the receiver
        val lifecycleObserver = object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    // Unregister the receiver when the lifecycle is destroyed
                    try {
                        checkout.onDestroy()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    source.lifecycle.removeObserver(this)
                }
            }
        }

        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)

        checkout.open(activity, options)
    } catch (e: Exception) {
        Toast.makeText(context, "Error in payment: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
    }
}