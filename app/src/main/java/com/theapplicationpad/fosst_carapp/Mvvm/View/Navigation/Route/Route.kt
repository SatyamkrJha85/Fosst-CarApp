package com.theapplicationpad.fosst_carapp.Mvvm.View.Navigation.Route

sealed class Route(val route:String) {
    object Splash:Route("Splash")
    object Login:Route("Login")
    object Register:Route("Register")
    object Onbording:Route("Onbording")
    object BottomBar:Route("BottomBar")
    object PurchaseHistory:Route("PurchaseHistory")
    object Home:Route("Home")
    object Special:Route("Special")
    object ArView:Route("ArView/{model}"){
        fun createRoute(model:String)="ArView/$model"
    }
    object Details : Route("Details/{carId}") {
        fun createRoute(carId: String) = "Details/$carId"
    }
    object Map : Route("map/{location}") {
        fun createRoute(location: String) = "map/$location"
    }
    object PaymentScreen : Route("PaymentScreen/{amount}") {
        fun createRoute(amount: Double) = "PaymentScreen/$amount"
    }
    object Like:Route("Like")
    object Cart:Route("Cart")
    object Profile:Route("Profile")

}