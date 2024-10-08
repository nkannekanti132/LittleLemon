package com.example.myapplication.presentation.ui

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.data.User
import com.example.myapplication.ui.theme.LittleLemonColor
import com.example.myapplication.utils.SharedPreferenceHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Onboarding(navHostController: NavHostController,context: Context) {

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Scaffold(
        topBar = {TopAppBar(navHostController)}
    ){innerPadding->
        Column(modifier = Modifier.padding(innerPadding)) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.background(LittleLemonColor.green).fillMaxWidth().padding(36.dp)) {
                Text(text = "Let's get to know you",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                    )
            }

            CustomOutlinedTextView("First Name",firstName) { firstName = it }
            CustomOutlinedTextView("Last Name",lastName) { lastName = it }
            CustomOutlinedTextView("Email",email) { email = it }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { register(firstName,lastName,email,navHostController,context) },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(LittleLemonColor.yellow),
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, bottom = 40.dp)
            ) {
                Text(text = "Register", color = Color.Black)
            }
        }

    }




}

fun register(firstName:String, lastName:String, email:String, navController:NavHostController,context: Context)
{
    if(firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty())
    {
        val sharedPreferenceHelper = SharedPreferenceHelper(context)
        sharedPreferenceHelper.saveUser(User(firstName,lastName,email))
        navController.navigate(com.example.myapplication.navigation.Home.route)
    }
    else
    {
        Toast.makeText(context, "Registration unsuccessful. Please enter all data.",Toast.LENGTH_SHORT).show()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(navHostController: NavHostController, isHome:Boolean = false)
{
    CenterAlignedTopAppBar(
        title = {

                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = "logo",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.padding(16.dp).size(180.dp)
                )

        },
        actions = {
            if(isHome)
            {
                Image(
                    painter = painterResource(R.drawable.profile),
                    contentDescription = "Profile",
                    modifier = Modifier.size(45.dp)
                        .clickable {
                            navHostController.navigate(com.example.myapplication.navigation.Profile.route)
                        }
                )

            }

        },
        modifier = Modifier.fillMaxWidth().padding(16.dp),

    )

}


@Composable
fun CustomOutlinedTextView(hint:String,value:String,enabled
                           :Boolean = true,onValueChange:(String)->Unit)
{
    Column(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)){
        Text(text = hint,
            fontWeight = FontWeight.W600,
            modifier = Modifier.padding(start = 16.dp, bottom = 2.dp)
        )
        Box(
            modifier = Modifier
                .padding(start = 13.dp, end = 13.dp)
                .height(40.dp)
                .border(BorderStroke(2.dp, Color.LightGray), shape = RoundedCornerShape(10.dp))
                .fillMaxWidth()
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp
                ),
                enabled = enabled,
                modifier = Modifier
                    .padding(8.dp) // This padding will be applied to the text inside the field
            )
        }

    }

}

@Composable
@Preview(showBackground = true)
fun OnboardingPreview()
{

}