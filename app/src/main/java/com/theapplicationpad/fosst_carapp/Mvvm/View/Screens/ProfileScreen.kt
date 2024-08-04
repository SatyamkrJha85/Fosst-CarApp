package com.theapplicationpad.fosst_carapp.Mvvm.View.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AirlineSeatReclineNormal
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.LocalCarWash
import androidx.compose.material.icons.filled.SettingsSuggest
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.theapplicationpad.fosst_carapp.Mvvm.VIewModel.UserViewModel
import com.theapplicationpad.fosst_carapp.Mvvm.View.Navigation.Route.Route
import com.theapplicationpad.fosst_carapp.R
import com.theapplicationpad.fosst_carapp.ui.theme.BackGroundColor
import kotlinx.coroutines.launch
@Composable
 fun ProfileScreen(modifier: Modifier=Modifier,BottomnavController: NavHostController, userViewModel: UserViewModel,HomenavController: NavHostController) {

    val userName by userViewModel.userName.collectAsState(initial = "")
    val userEmail by userViewModel.userEmail.collectAsState(initial = "")
    val userProfilePic by userViewModel.userProfilePic.collectAsState(initial = "")
    val scope = rememberCoroutineScope()
    Box (
        modifier
            .fillMaxSize()
            .background(BackGroundColor)
    ){
        Column (
            modifier.align(Alignment.TopCenter)
        ){

            TopHeader(
                firsticon = Icons.Filled.ArrowBack,
                secondicon = Icons.Filled.VerifiedUser,
                headertitle = "Profile"
            )
            Row(
                modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (userProfilePic.toString().isEmpty()){
                    Image(
                        painter = painterResource(id = R.drawable.user), contentDescription = null,
                        modifier
                            .size(60.dp)
                            .clip(
                                CircleShape
                            ), contentScale = ContentScale.Crop
                    )
                }
                else {

                    AsyncImage(
                        model = userProfilePic, contentDescription = null,
                        modifier
                            .size(80.dp)
                            .clip(
                                CircleShape
                            ), contentScale = ContentScale.Crop
                    )
                }
            }

            Column(
                modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "$userName", fontWeight = FontWeight.SemiBold, fontSize = 24.sp)
                Text(text = "$userEmail", fontSize = 15.sp, fontWeight = FontWeight.Normal)
            }
        }

        Box(modifier = modifier
            .clip(shape = RoundedCornerShape(topStart = 45.dp, topEnd = 45.dp))
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 10.dp)
            .align(Alignment.BottomCenter)
            .background(Color.White)
          ){
            Column(
                modifier.padding(start = 20.dp, end = 20.dp, top = 5.dp)
            ) {
                Text(text = "Common Functions", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)

                Row (
                    modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp)
                ){
                    FunctionBox(icon = Icons.Filled.SettingsSuggest, title = "Maintain")
                    FunctionBox(icon = Icons.Filled.ElectricBolt, title = "Auto Parts")
                    FunctionBox(icon = Icons.Filled.LocalCarWash, title = "Driving \n Skill")

                }

                CommonButton(icon = Icons.Filled.AirlineSeatReclineNormal, title = "My Cars", onclick = {
                    BottomnavController.navigate(Route.Like.route)
                })
                Spacer(modifier = Modifier.height(5.dp))
                CommonButton(icon = Icons.Filled.Share, title = "Social Links", onclick = {})
                Spacer(modifier = Modifier.height(5.dp))
                CommonButton(icon = Icons.Filled.ShoppingBasket, title = "My Purchase History", onclick = {
                    HomenavController.navigate(Route.PurchaseHistory.route)
                })
                Spacer(modifier = Modifier.height(5.dp))

                Row (
                    modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){


                    Button(
                        onClick = {
                            scope.launch {
                                userViewModel.clearUser()
                                HomenavController.navigate("login") {
                                    HomenavController.popBackStack()
                                }
                            }
                        }, colors = ButtonDefaults.buttonColors(Color.Black), modifier = modifier
                            .width(180.dp)
                            .height(60.dp)
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "Log Out",
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FunctionBox(modifier: Modifier = Modifier,title:String,icon:ImageVector) {
    Box(modifier = modifier
        .padding(5.dp)
        .clip(shape = RoundedCornerShape(23.dp))
        .width(90.dp)
        .height(110.dp)
        .background(BackGroundColor), contentAlignment = Alignment.Center){

        Column (
            modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){


            IconButton(
                onClick = { /*TODO*/ },
                modifier
                    .size(40.dp)
                    .clip(shape = CircleShape)
                    .background(
                        Color.White
                    )
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = title, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
        }
    }
}

@Composable
fun CommonButton(modifier: Modifier = Modifier,icon: ImageVector,title: String,onclick:()->Unit) {
    Row(
        modifier
            .clickable {
                onclick()
            }
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 10.dp)
            .clip(shape = RoundedCornerShape(25.dp))
            .background(
                BackGroundColor
            )
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Spacer(modifier = Modifier.width(20.dp))
        IconButton(
            onClick = { /*TODO*/ },
            modifier
                .size(50.dp)
                .clip(shape = CircleShape)
                .background(
                    Color.White
                )
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.Black
            )
        }
        Spacer(modifier = Modifier.width(20.dp))

        Text(text = title, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
    }
}