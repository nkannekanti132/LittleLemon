package com.example.myapplication.presentation.ui

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.navigation.OnBoard
import com.example.myapplication.ui.theme.LittleLemonColor
import com.example.myapplication.utils.SharedPreferenceHelper


@Composable
fun Profile(navHostController: NavHostController, context: Context) {
        val sharedPreferenceHelper = SharedPreferenceHelper(context)
        val user = sharedPreferenceHelper.getUser()

        var firstName = user?.firstName
        var lastName = user?.lastName
        var email = user?.email
        Log.d("Entertest",firstName.toString())

        Scaffold(
            topBar = {TopAppBar(navHostController)}
        ){innerPadding->
            Column(modifier = Modifier.padding(innerPadding)) {

                    Text(text = "Personal Information",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp)
                    )


                firstName?.let { CustomOutlinedTextView("First Name", it,false) { firstName = it } }
                lastName?.let { CustomOutlinedTextView("Last Name", it,false) { lastName = it } }
                email?.let { CustomOutlinedTextView("Email", it,false) { email = it } }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = { navHostController.navigate(OnBoard.route) },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(LittleLemonColor.yellow),
                    modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, bottom = 40.dp)
                ) {
                    Text(text = "Logout.", color = Color.Black)
                }
            }

        }




}