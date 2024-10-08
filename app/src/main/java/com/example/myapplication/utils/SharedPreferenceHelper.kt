package com.example.myapplication.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.myapplication.data.User
import com.google.gson.Gson

class SharedPreferenceHelper(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveUser(user: User) {
        val jsonString = gson.toJson(user)
        sharedPreferences.edit().putString("user", jsonString).apply()
    }

    fun getUser(): User? {
        val jsonString = sharedPreferences.getString("user", null)
        return if (jsonString != null) {
            gson.fromJson(jsonString, User::class.java)
        } else {
            null
        }
    }

    fun deleteUser() {
        sharedPreferences.edit().remove("user").apply()
    }
}