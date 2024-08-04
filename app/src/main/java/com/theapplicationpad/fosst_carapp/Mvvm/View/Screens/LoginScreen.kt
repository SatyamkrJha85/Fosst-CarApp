package com.theapplicationpad.fosst_carapp.Mvvm.View.Screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.theapplicationpad.fosst_carapp.Mvvm.VIewModel.UserViewModel
import com.theapplicationpad.fosst_carapp.Mvvm.View.Navigation.Route.Route
import com.theapplicationpad.fosst_carapp.ui.theme.BackGroundColor
import kotlinx.coroutines.launch


@Composable
 fun LoginScreen(modifier: Modifier=Modifier,navController: NavHostController, userViewModel: UserViewModel) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginMessage by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

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
            Image(
                painter = painterResource(id = com.theapplicationpad.fosst_carapp.R.drawable.loginimg),
                contentDescription = null,
                modifier.size(300.dp)
            )
            Text(text = "Welcome Back", fontSize = 27.sp, fontWeight = FontWeight.SemiBold)
            Text(text = "Our Champ", fontSize = 22.sp, fontWeight = FontWeight.Normal)


            Spacer(modifier = Modifier.height(22.dp))

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

            Spacer(modifier = Modifier.height(26.dp))
            OutlinedButton(
                onClick = {
                    scope.launch {
                        val user = userViewModel.getUser(username, password)
                        if (user != null) {
                            userViewModel.insertUser(
                                user.username,
                                user.password,
                                user.email,
                                user.profilePic
                            )
                            navController.navigate(Route.BottomBar.route) {
                                // Clear the back stack to prevent returning to the login screen
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = true
                                }
                            }
                            // Show success toast
                            Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT).show()
                        } else {
                            // Show error toast
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
                    text = "Login",
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
                Text(text = "Don't have an account?", fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray)
                Spacer(modifier = Modifier.width(6.dp))


                Text(text = "SignUp", fontSize = 18.sp,
                    modifier=Modifier.clickable {
                        navController.navigate(Route.Register.route)
                    },
                    fontWeight = FontWeight.Bold,
                    color = Color.Black)
            }

        }
    }
}


@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    triling: (@Composable () -> Unit)? = null,
    Leading: (@Composable () -> Unit)? = null
) {
    Column {
        Text(text = label)
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .shadow(3.dp, RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = value, onValueChange = onValueChange,
                visualTransformation = visualTransformation,
                singleLine = true,
                placeholder = { Text(text = placeholder) },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.White,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedTrailingIconColor = Color.Black,
                    focusedLeadingIconColor = Color.Black,
                    disabledTrailingIconColor = Color.Black,
                    unfocusedTrailingIconColor = Color.Black,
                    disabledLeadingIconColor = Color.Black,
                    unfocusedLeadingIconColor = Color.Black,
                    unfocusedContainerColor = Color.White,

                    ), trailingIcon = triling,
                leadingIcon = Leading
            )
        }
    }

}