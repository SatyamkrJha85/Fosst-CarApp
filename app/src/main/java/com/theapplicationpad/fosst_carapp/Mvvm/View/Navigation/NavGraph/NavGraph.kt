package com.theapplicationpad.fosst_carapp.Mvvm.View.Navigation.NavGraph
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.theapplicationpad.fosst_carapp.MainActivity
import com.theapplicationpad.fosst_carapp.Mvvm.PreferenceDatabase.UserDatabase
import com.theapplicationpad.fosst_carapp.Mvvm.PreferenceDatabase.UserPreferences
import com.theapplicationpad.fosst_carapp.Mvvm.PreferenceDatabase.UserViewModelFactory
import com.theapplicationpad.fosst_carapp.Mvvm.VIewModel.CarViewModel
import com.theapplicationpad.fosst_carapp.Mvvm.VIewModel.CarViewModelFactory
import com.theapplicationpad.fosst_carapp.Mvvm.VIewModel.UserViewModel
import com.theapplicationpad.fosst_carapp.Mvvm.View.ArView.ARScreen
import com.theapplicationpad.fosst_carapp.Mvvm.View.Navigation.Route.Route
import com.theapplicationpad.fosst_carapp.Mvvm.View.Screens.BottomScreen
import com.theapplicationpad.fosst_carapp.Mvvm.View.Screens.CarDetailsScreen
import com.theapplicationpad.fosst_carapp.Mvvm.View.Screens.LoginScreen
import com.theapplicationpad.fosst_carapp.Mvvm.View.Screens.MapScreen
import com.theapplicationpad.fosst_carapp.Mvvm.View.Screens.OnboardingScreen
import com.theapplicationpad.fosst_carapp.Mvvm.View.Screens.PaymentScreen
import com.theapplicationpad.fosst_carapp.Mvvm.View.Screens.PurchaseHistoryScreen
import com.theapplicationpad.fosst_carapp.Mvvm.View.Screens.RegisterScreen
import com.theapplicationpad.fosst_carapp.Mvvm.View.Screens.Special
import com.theapplicationpad.fosst_carapp.Mvvm.View.Screens.SplashScreen

@Composable
fun NavGraph(context: MainActivity, modifier: Modifier = Modifier, carViewModelFactory: CarViewModelFactory) {


    val userDao = UserDatabase.getDatabase(context.applicationContext).userDao()
    val userPreferences = UserPreferences(context.applicationContext)
    val userViewModelFactory = UserViewModelFactory(userDao, userPreferences)
    val userViewModel: UserViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = userViewModelFactory)
    val carViewModel: CarViewModel = viewModel(factory = carViewModelFactory)

    val navController = rememberNavController()
    val isLoggedIn by userViewModel.isLoggedIn.collectAsState(initial = false)

    NavHost(navController = navController, startDestination =  Route.Splash.route) {
        composable(Route.Splash.route) {
            SplashScreen(navController = navController, context = context, userViewModel = userViewModel)
        }

        composable(Route.Login.route) {
            LoginScreen(navController = navController, userViewModel = userViewModel)
        }

        composable(Route.Register.route) {
            RegisterScreen(navController = navController, userViewModel = userViewModel)
        }

        composable(Route.Onbording.route) {
            OnboardingScreen(navController = navController, context = context)
        }

        composable(Route.BottomBar.route) {
            BottomScreen(navController1 = navController, carViewModel = carViewModel, userViewModel = userViewModel)
        }

        composable(Route.PaymentScreen.route) { backStackEntry ->
            val amount = backStackEntry.arguments?.getString("amount")?.toDoubleOrNull() ?: 0.0
            PaymentScreen(navController = navController, amount = amount)
        }

        composable(Route.Map.route, arguments = listOf(navArgument("location") { type = NavType.StringType })) { backStackEntry ->
            val location = backStackEntry.arguments?.getString("location") ?: ""
            MapScreen(location)
        }

        composable(Route.Details.route) { backStackEntry ->
            val carId = backStackEntry.arguments?.getString("carId") ?: return@composable
            val car = carViewModel.featureCars.collectAsState(initial = emptyList()).value.find { it.id == carId }
            car?.let {
                CarDetailsScreen(car = it, userViewModel = userViewModel, carViewModel = carViewModel, HomenavController = navController)
            }
        }

        composable(Route.PurchaseHistory.route) {
            PurchaseHistoryScreen(HomenavController = navController, carViewModel = carViewModel, userViewModel = userViewModel)
        }

        composable(Route.Special.route) {
            Special(navController = navController)
        }

        composable(Route.ArView.route) { backStackEntry ->
            val model = backStackEntry.arguments?.getString("model")?.toString() ?: ""
            ARScreen(model = model)
        }

    }
}
