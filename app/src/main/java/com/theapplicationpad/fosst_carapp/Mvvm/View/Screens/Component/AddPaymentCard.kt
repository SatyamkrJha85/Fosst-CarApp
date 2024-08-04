package com.theapplicationpad.fosst_carapp.Mvvm.View.Screens.Component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.api.ResourceDescriptor.History
import com.theapplicationpad.fosst_carapp.Mvvm.View.Screens.TopHeader
import com.theapplicationpad.fosst_carapp.R
import com.theapplicationpad.fosst_carapp.ui.theme.BackGroundColor

@ExperimentalAnimationApi
@Composable
fun AddPaymentCard(price:Double) {
    var nameText by remember { mutableStateOf(TextFieldValue()) }
    var cardNumber by remember { mutableStateOf(TextFieldValue()) }
    var expiryNumber by remember { mutableStateOf(TextFieldValue()) }
    var cvcNumber by remember { mutableStateOf(TextFieldValue()) }

    var addcardbutton by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackGroundColor)
    ) {

        Spacer(modifier = Modifier.height(25.dp))

        TopHeader(
            firsticon = Icons.Filled.ArrowBack,
            secondicon = Icons.Filled.CreditCard,
            headertitle = "Wallet"
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Available Balance", fontWeight = FontWeight.Normal)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "$32,322", fontSize = 28.sp, fontWeight = FontWeight.SemiBold)
        }

        PaymentCardAdd(
            secondicon = if(addcardbutton){
                 Icons.Filled.Remove
            }else{
                Icons.Filled.Add
            },
            headertitle = "Card", onAddclick = {
            addcardbutton = !addcardbutton
        })

        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            item {
                PaymentCard(
                    nameText,
                    cardNumber,
                    expiryNumber,
                    cvcNumber
                )
            }
            if (addcardbutton) {
                item {
                    InputItem(
                        textFieldValue = nameText,
                        label = stringResource(id = R.string.card_holder_name),
                        onTextChanged = { nameText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )
                    //  }

                    //  item {
                    InputItem(
                        textFieldValue = cardNumber,
                        label = stringResource(id = R.string.card_holder_number),
                        keyboardType = KeyboardType.Number,
                        onTextChanged = { cardNumber = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        visualTransformation = CreditCardFilter
                    )
                    //  }

                    //   item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        InputItem(
                            textFieldValue = expiryNumber,
                            label = stringResource(id = R.string.expiry_date),
                            keyboardType = KeyboardType.Number,
                            onTextChanged = { expiryNumber = it },
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp)
                        )
                        InputItem(
                            textFieldValue = cvcNumber,
                            label = stringResource(id = R.string.cvc),
                            keyboardType = KeyboardType.Number,
                            onTextChanged = { cvcNumber = it },
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 8.dp)
                        )
                    }
                    //}


                }
            }
            item {
                Row {
                    Text(text = "History", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                }
                HistoryPaymentItem(
                    carname = "Mercedes-Benz E-Class",
                    carprice = "34,42",
                    date = "22 may, 2024",
                    cardname = "Master Card"
                )
                HistoryPaymentItem(
                    carname = "Mercedes-Benz E-Class",
                    carprice = "34,42",
                    date = "22 may, 2024",
                    cardname = "Master Card"
                )
            }
            item {

                ConfirmationButton(price=price)
            }

        }
    }
}

@Composable
fun PaymentCardAdd(
    modifier: Modifier = Modifier,
    secondicon: ImageVector,
    headertitle: String,
    onAddclick: () -> Unit
) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 20.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {


        Text(text = headertitle, fontSize = 22.sp, fontWeight = FontWeight.SemiBold)

        IconButton(
            onClick = { onAddclick() },
            modifier
                .size(40.dp)
                .clip(shape = CircleShape)
                .background(
                    Color.White
                )
        ) {
            Icon(
                imageVector = secondicon,
                contentDescription = null,
                tint = Color.Black
            )
        }
    }
}

@Composable
fun HistoryPaymentItem(
    modifier: Modifier = Modifier,
    carname: String,
    carprice: String,
    date: String,
    cardname: String
) {
    ElevatedCard(
        modifier
            .fillMaxWidth()
            .height(110.dp)
            .padding(10.dp),
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = carname, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                Text(text = "$ $carprice", fontSize = 17.sp, fontWeight = FontWeight.Bold)

            }
            
            Spacer(modifier = modifier.height(14.dp))
            Row(
                modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = date, fontSize = 15.sp, fontWeight = FontWeight.Normal)
                Text(text = cardname, fontSize = 15.sp, fontWeight = FontWeight.Normal)
            }
        }

    }
}

