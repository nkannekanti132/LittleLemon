package com.example.myapplication.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Entity(tableName = "menuItem")
data class MenuItem(
    val category: String,
    val description: String,
    @PrimaryKey
    val id: Int,
    val image: String,
    val price: String,
    val title: String
)

@Dao
interface MenuDao {

    @Insert
    fun insert(menuItem: MenuItem)

    @Query("SELECT * FROM menuItem")
    fun getAllUsers(): LiveData<List<MenuItem>>

}

@Database(entities = [MenuItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun menuDao(): MenuDao

}