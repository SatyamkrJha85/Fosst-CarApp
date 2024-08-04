package com.theapplicationpad.fosst_carapp.Mvvm.View.Screens
import android.content.Context
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.theapplicationpad.fosst_carapp.MainActivity
import com.theapplicationpad.fosst_carapp.Mvvm.VIewModel.UserViewModel
import com.theapplicationpad.fosst_carapp.Mvvm.View.Navigation.Route.Route
import com.theapplicationpad.fosst_carapp.R
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavHostController, context: MainActivity,userViewModel: UserViewModel) {

    val alpha = remember {
        Animatable(0f)
    }

    var startAnimation by remember { mutableStateOf(false) }

    // Trigger the animation when the Composable enters the composition
    LaunchedEffect(Unit) {
        startAnimation = true
    }

    val offsetX by animateDpAsState(
        targetValue = if (startAnimation) 0.dp else (-200).dp, // Start off-screen to the left
        animationSpec = tween(durationMillis = 1000) // Duration of the animation
    )


    val isLoggedIn by userViewModel.isLoggedIn.collectAsState(initial = false)

    LaunchedEffect(key1 = true) {
        alpha.animateTo(
            1f,
            animationSpec = tween(2500)
        )
        delay(700)

        val onBoardingFinished = onBoardingIsFinished(context = context)
        Log.d("SplashScreen", "isLoggedIn: $isLoggedIn, onBoardingFinished: $onBoardingFinished")

        if (onBoardingFinished) {
            if (isLoggedIn) {
                navController.popBackStack()
                navController.navigate(Route.BottomBar.route)
            } else {
                navController.popBackStack()
                navController.navigate(Route.Login.route)
            }
        } else {
            navController.popBackStack()
            navController.navigate(Route.Onbording.route)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isSystemInDarkTheme()) Color.DarkGray else Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoaderAnimation(
            modifier = Modifier.size(400.dp), anim = R.raw.splash
        )
        Spacer(modifier = Modifier.height(15.dp))

        Box(
            modifier = Modifier
                .offset(x = offsetX)
        ) {
            Text(
                text = "Fosst Launched",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Box(
            modifier = Modifier
                .offset(y = offsetX)
        ) {
            Text(
                text = "Let's Go",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

    }
}

@Composable
fun LoaderAnimation(modifier: Modifier, anim: Int) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(anim))

    LottieAnimation(
        composition = composition, iterations = LottieConstants.IterateForever,
        modifier = modifier
    )
}

private fun onBoardingIsFinished(context: MainActivity): Boolean {
    val sharedPreferences = context.getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean("isFinished", false)

}