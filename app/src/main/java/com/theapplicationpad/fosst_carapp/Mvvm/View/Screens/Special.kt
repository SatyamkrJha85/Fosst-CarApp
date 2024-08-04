package com.theapplicationpad.fosst_carapp.Mvvm.View.Screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Phonelink
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.theapplicationpad.fosst_carapp.Mvvm.Model.MapModel.MapModel
import com.theapplicationpad.fosst_carapp.Mvvm.Model.MapModel.Maplist
import com.theapplicationpad.fosst_carapp.Mvvm.View.Navigation.Route.Route
import com.theapplicationpad.fosst_carapp.R
import com.theapplicationpad.fosst_carapp.ui.theme.BackGroundColor

@Composable
fun Special(modifier: Modifier = Modifier,navController: NavController) {

    Column(modifier = modifier
        .fillMaxSize()
        .background(BackGroundColor)){
        Row(
            modifier.fillMaxWidth().padding(top = 40.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Special Cars",fontSize = 23.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
        }
        LazyColumn (
            modifier.fillMaxWidth(),

            ){

            items(Maplist){ model ->
                SpecialBox(mapModel = model, onArclick = {
                    navController.navigate(
                        Route.ArView.createRoute(
                            model.glb
                        )
                    )
                }, onMapclick = {
                    navController.navigate(
                        Route.Map.createRoute(
                            model.location
                        )
                    )
                })
            }
        }
    }
}

@Composable
fun SpecialBox(modifier: Modifier = Modifier,mapModel: MapModel,onArclick:()->Unit,onMapclick:()->Unit) {
    val context = LocalContext.current

    Card (
        modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(160.dp),
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(15.dp)
    ){

        Row(
            modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Image(painter = painterResource(id = mapModel.img), contentDescription = null,
                    modifier.size(120.dp))
                Text(text = mapModel.title, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)

            }

            Column {

                Button(onClick = { onArclick() }, colors = ButtonDefaults.buttonColors(Color.Black)) {
                    Text(text = "Vitrual Mode", color = Color.White)
                    Spacer(modifier = modifier.width(5.dp))
                    Icon(imageVector = Icons.Filled.Phonelink, contentDescription =null , tint = Color.White)
                }

                Button(onClick = {
                    //onMapclick()
                    val gmmIntentUri = Uri.parse("geo:0,0?q=${mapModel.location}")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    context.startActivity(mapIntent)
                                 }, colors = ButtonDefaults.buttonColors(Color.Black)) {
                    Text(text = "Google Map", color = Color.White)
                    Spacer(modifier = modifier.width(5.dp))
                    Icon(imageVector = Icons.Filled.Map, contentDescription =null, tint = Color.White )
                }


            }
        }

    }
}