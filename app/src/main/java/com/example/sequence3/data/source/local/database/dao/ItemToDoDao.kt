package com.example.sequence3.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sequence3.data.model.ItemToDo

@Dao
interface ItemToDoDao {

    @Query("SELECT * FROM itemToDo_table")
    suspend fun getItems(): MutableList<ItemToDo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOrUpdate(item: MutableList<ItemToDo>)
}