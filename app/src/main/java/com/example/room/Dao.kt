package com.example.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert
    fun insertItem(item: Item) //Добавление данных
    @Query("SELECT * FROM items")
    fun getAllItem(): Flow<List<Item>> // Извлечение данных

}