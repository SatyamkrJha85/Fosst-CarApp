package com.theapplicationpad.fosst_carapp.Mvvm.Model.GeneralModal

import com.theapplicationpad.fosst_carapp.R

data class BrandModel (
    val img:Int,
    val carname:String
)



val Brandlist = listOf(
    BrandModel(R.drawable.bmw,"BMW"),
    BrandModel(R.drawable.hundai,"Hundai"),
    BrandModel(R.drawable.mercedes,"Mercedes"),
    BrandModel(R.drawable.opel,"Opel"),
    BrandModel(R.drawable.toyota,"Toyota"),

    )