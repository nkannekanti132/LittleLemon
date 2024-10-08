package com.example.myapplication.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MenuX(
    @SerialName("category")
    val category: String,
    @SerialName("description")
    val description: String,
    @SerialName("id")
    val id: Int,
    @SerialName("image")
    val image: String,
    @SerialName("price")
    val price: String,
    @SerialName("title")
    val title: String

){
    fun toDatabaseMenu():MenuItem
    {
        return MenuItem(this.category,this.description,this.id,this.image,this.price,this.title)

    }
}