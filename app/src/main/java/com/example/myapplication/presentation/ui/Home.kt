package com.example.myapplication.presentation.ui

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.myapplication.R
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.MenuItem
import com.example.myapplication.ui.theme.FontFamily1
import com.example.myapplication.ui.theme.FontFamily2
import com.example.myapplication.ui.theme.LittleLemonColor


@Composable
fun Home(navHostController: NavHostController, context: Context, database: MenuInventory) {
    var query by remember { mutableStateOf("") }
    val menuItems: List<MenuItem> by database.getMenu().observeAsState(emptyList())
    var filteredMenuItems by remember { mutableStateOf(menuItems) }

    LaunchedEffect(query) {
        filteredMenuItems = menuItems.filter { menuItem ->
            menuItem.title.contains(query, ignoreCase = true)
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(navHostController, isHome = true)
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
               HeroSection(query) {
                   query = it
                   Log.d("gggg",menuItems.toString())
               }
            Text(
                text = "Order for Delivery!",
                fontFamily = FontFamily1,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                modifier = Modifier.padding(16.dp)
            )
            FilterMenu(menuItems) { filteredMenuItems = it }
            if(filteredMenuItems.isNotEmpty())
            {
                MenuItem(filteredMenuItems)
            }
            else
            {
                MenuItem(menuItems)
            }


        }
    }
}

@Composable
fun FilterMenu(menuItems: List<MenuItem>, filter:(List<MenuItem>)->Unit)
{

    val grooupItems = menuItems.groupBy {
        it.category
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        for (category in grooupItems.keys)
        {
             Button(
                 onClick = {
                     val selectedFilter = grooupItems[category] ?: emptyList()
                     filter(selectedFilter)
                 },
                 colors = ButtonDefaults.buttonColors(Color.LightGray),
                 shape = RoundedCornerShape(10.dp)
             ) {
                 Text(
                     text = category.capitalize(),
                     color = LittleLemonColor.charcoal
                 )
             }
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeroSection(query:String, onQuery:(String)->Unit)
{
    Column(modifier = Modifier.fillMaxWidth()
        .padding(0.dp)
        .background(LittleLemonColor.green),
        verticalArrangement = Arrangement.Top
    ) {

        Text(text = "Little Lemon",
            color = LittleLemonColor.yellow,
            fontFamily = FontFamily1,
            fontSize = 58.sp,
            modifier = Modifier.padding(start=16.dp)
        )

        Row {
            Column {
                Text(text = "Chicago",
                    color = Color.White,
                    fontFamily = FontFamily2,
                    fontSize = 32.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )
                Text(text = "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist",
                    color = Color.White,
                    fontFamily = FontFamily2,
                    fontSize = 22.sp,
                    modifier = Modifier.fillMaxWidth(0.6f).padding(start = 16.dp, bottom = 16.dp)
                )

            }
            Image(
                painter = painterResource(R.drawable.hero_image),
                contentDescription = "hero Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.aspectRatio(1F).padding(start = 16.dp, bottom = 16.dp, end = 16.dp).clip(RoundedCornerShape(16.dp))
            )
        }

        TextField(
            value = query,
            onValueChange = onQuery,
            placeholder = { Text(text = "Enter Search Phrase") },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            leadingIcon = { Icon( imageVector = Icons.Default.Search, contentDescription = "") }

        )





    }
}

@Composable
fun MenuItem(menuItems:List<MenuItem>)
{
    LazyColumn(modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
        items(menuItems){ menuItem->
           MenuCard(menuItem.title,menuItem.description,menuItem.price,menuItem.image)
            HorizontalDivider(modifier = Modifier.height(0.5.dp).background(Color.LightGray).padding(start = 16.dp, end = 16.dp))


     }

    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuCard(itemName:String, itemDesc:String, itemPrice:String, itemImage: String)
{

    Row(modifier = Modifier.fillMaxWidth()
        .padding(16.dp),
    ) {
            Column {

        Text(text = itemName,
            fontFamily = FontFamily2,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start=16.dp)
        )
                Text(text = itemDesc,
                    fontFamily = FontFamily2,
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth(0.75f).padding(start = 16.dp)
                )
                Text(text = "$ $itemPrice",
                    fontFamily = FontFamily2,
                    fontSize = 23.sp,
                    modifier = Modifier.padding(start = 16.dp)

                )

            }
            GlideImage(
                model = itemImage,
                contentDescription = "Menu Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.aspectRatio(1F).padding(16.dp))

        }






}
