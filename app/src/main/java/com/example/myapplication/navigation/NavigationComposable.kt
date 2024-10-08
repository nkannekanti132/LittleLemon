package com.example.myapplication.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.presentation.ui.Home
import com.example.myapplication.presentation.ui.MenuInventory
import com.example.myapplication.presentation.ui.Onboarding
import com.example.myapplication.presentation.ui.Profile
import com.example.myapplication.utils.SharedPreferenceHelper


@Composable
fun NavigationComposable(navController:NavHostController, context:Context, database: MenuInventory) {
    val sharedPreferenceHelper = SharedPreferenceHelper(context )
    val startDestination = if(sharedPreferenceHelper.getUser() == null)  OnBoard.route else Home.route
    NavHost(navController = navController, startDestination = startDestination)
    {
        composable(Home.route) {
             Home(navController,context,database)
        }
        composable(OnBoard.route) {
             Onboarding(navController,context)
        }
        composable(Profile.route) {
            Profile(navController,context)
        }
    }
}