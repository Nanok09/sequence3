package com.example.sequence3.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sequence3.data.model.ListeToDo

@Dao
interface ListToDoDao {

    @Query("SELECT * FROM listeToDo_table")
    suspend fun getLists(): List<ListeToDo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOrUpdate(list: MutableList<ListeToDo>)

}