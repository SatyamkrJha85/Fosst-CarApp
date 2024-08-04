package com.theapplicationpad.fosst_carapp.Mvvm.View.Screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.theapplicationpad.fosst_carapp.Mvvm.VIewModel.UserViewModel
import com.theapplicationpad.fosst_carapp.Mvvm.View.Navigation.Route.Route
import com.theapplicationpad.fosst_carapp.R
import com.theapplicationpad.fosst_carapp.ui.theme.BackGroundColor
import kotlinx.coroutines.launch

@Composable
 fun RegisterScreen (modifier: Modifier=Modifier,navController: NavHostController, userViewModel: UserViewModel) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
   // var profilePic by remember { mutableStateOf("") }
    var registerMessage by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

   var imguri by remember {
       mutableStateOf<Uri?>(null)
   }

    var launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
        imguri=it
    }

    val showPassword = remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    Box(
        modifier
            .background(BackGroundColor)
            .fillMaxSize()
    ) {
        Column(modifier.padding(20.dp)) {
//            Image(
//                painter = painterResource(id = com.theapplicationpad.fosst_carapp.R.drawable.loginimg),
//                contentDescription = null,
//                modifier.size(200.dp)
//            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(text = "Happy Journey", fontSize = 27.sp, fontWeight = FontWeight.SemiBold)
            Text(text = "Champ", fontSize = 22.sp, fontWeight = FontWeight.Normal)


            Spacer(modifier = Modifier.height(12.dp))

            Row (modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
                ){

                if (imguri!=null){
                    AsyncImage(model = imguri, contentDescription =null,
                        modifier
                            .size(100.dp)
                            .clip(
                                CircleShape
                            )
                            .border(width = 2.dp, shape = CircleShape, color = Color.Gray)
                            .clickable {
                                launcher.launch("image/*")
                            } , contentScale = ContentScale.Crop)

                }
                else{
                    Image(painter = painterResource(id = R.drawable.user), contentDescription =null,
                        modifier
                            .size(100.dp)
                            .clip(
                                CircleShape
                            )
                            .clickable {
                                launcher.launch("image/*")
                            } )
                }
            }

            AppTextField(
                value = username,
                onValueChange = { username = it },
                placeholder = "Enter Username",
                label = "User Name",
                Leading = { Icon(imageVector = Icons.Filled.Person, contentDescription = null) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            AppTextField(
                value = password,
                onValueChange = {
                    password = it
                },
                label = "Password",
                placeholder = "Enter Password",
                visualTransformation = if (showPassword.value)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
                triling = {
                    Icon(
                        modifier = Modifier.clickable {
                            showPassword.value = !showPassword.value
                        },
                        painter = painterResource(
                            id = if (showPassword.value)
                                com.theapplicationpad.fosst_carapp.R.drawable.ic_eye_off else com.theapplicationpad.fosst_carapp.R.drawable.ic_eye_open
                        ),
                        contentDescription = null
                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            AppTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = "Enter Email",
                label = "Email",
                Leading = { Icon(imageVector = Icons.Filled.Email, contentDescription = null) }
            )

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(
                onClick = {

                    scope.launch {
                        if (username.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty()){
                            userViewModel.insertUser(username, password, email, profilePic = imguri.toString())
                            registerMessage = "User registered!"
                            navController.navigate(Route.Login.route)
                            Toast.makeText(context, "Register successful!", Toast.LENGTH_SHORT).show()

                        }else{
                            Toast.makeText(context, "Invalid credentials.", Toast.LENGTH_SHORT).show()

                        }

                    }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                )
            ) {
                Text(
                    text = "Register",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Row(
                modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Already have an account?", fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray)
                Spacer(modifier = Modifier.width(6.dp))


                Text(text = "Login", fontSize = 18.sp,
                    modifier=Modifier.clickable {
                        navController.navigate(Route.Login.route)
                    },
                    fontWeight = FontWeight.Bold,
                    color = Color.Black)
            }

        }
    }
}


