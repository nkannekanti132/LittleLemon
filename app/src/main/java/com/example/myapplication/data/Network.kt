package com.example.myapplication.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json

object Network {
    var client = HttpClient(Android){
        install(ContentNegotiation){
            json(contentType = ContentType("text","plain"))
        }
    }
    suspend fun getMenu():List<MenuX>
    {
        val response = client.get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
            .body<Menu>()
        return response.menu

    }
}