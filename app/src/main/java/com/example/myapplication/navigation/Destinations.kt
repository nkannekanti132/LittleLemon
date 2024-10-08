package com.example.myapplication.navigation


interface Destinations {
    val route:String
}

object Home: Destinations
{
    override val route = "Home"
}

object OnBoard: Destinations
{
    override val route = "OnBoard"
}

object Profile: Destinations
{
    override val route = "OnBoarding"
}