package com.example.myapplication.presentation.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.MenuItem
import com.example.myapplication.data.MenuX
import com.example.myapplication.data.Network
import com.example.myapplication.navigation.NavigationComposable
import com.example.myapplication.ui.theme.LittleLemonColor
import com.example.myapplication.ui.theme.MyApplicationTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database by lazy { MenuInventory(this) }
        lifecycleScope.launch(Dispatchers.Default) {
            val menuItems =Network.getMenu()
            val dbMenu = database.getMenu().value
            for(menuItem in menuItems)
            {
                   val dbMenuItem = menuItem.toDatabaseMenu()
                if (dbMenu != null) {
                    if(!dbMenu.contains(dbMenuItem)) {
                        database.addaMenu(menuItem.toDatabaseMenu())
                    }
                }
            }
        }
        enableEdgeToEdge()
        setContent {
                val navController = rememberNavController()
                NavigationComposable(navController,this@MainActivity, database)
        }
    }
}




class MenuInventory(context: Context)
{
    private val database = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "app_database"
    ).build()

    fun getMenu() = database.menuDao().getAllUsers()
    fun addaMenu(menu: MenuItem) = database.menuDao().insert(menu)
}