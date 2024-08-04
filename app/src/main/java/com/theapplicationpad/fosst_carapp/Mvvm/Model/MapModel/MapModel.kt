package com.theapplicationpad.fosst_carapp.Mvvm.Model.MapModel

import com.theapplicationpad.fosst_carapp.R

data class MapModel (
    val img:Int,
    val title:String,
    val glb:String,
    val location:String
)



val Maplist = listOf(
    MapModel(img = R.drawable.car1, title = "Apex GT85", glb = "car1", location = "S.R.S Automobile Overhauling Garage And Luxury Car Service Center Patna"),
    MapModel(img = R.drawable.car2lambo, title = "Lamborghini Aventador", glb = "car2lambo", location = "5, SH 50, Mirzapur, Darbhanga, Bihar 846004"),
    MapModel(img = R.drawable.car3lambo, title = "Lamborghini Huracan", glb = "car3lambo", location = "Car service station in Ho Chi Minh City, Vietnam"),
    MapModel(img = R.drawable.car4benz, title = "Benz GLC", glb = "car4benz", location = "47V5+Q9V, ĐT190, TT. Vĩnh Lộc, Chiêm Hóa, Tuyên Quang,"),
    MapModel(img = R.drawable.car5, title = "Hundai", glb = "car5", location = "City Centre Boulevard, No. 105 MX08 (CBD4, Phnom Penh 120210, Cambodia"),

    )