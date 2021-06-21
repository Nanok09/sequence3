package com.example.sequence3.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sequence3.data.model.ItemToDo
import com.example.sequence3.data.model.ListeToDo
import com.example.sequence3.data.model.ProfilListeToDo
import com.example.sequence3.data.source.local.database.dao.ItemDao
import com.example.sequence3.data.source.local.database.dao.ListDao
import com.example.sequence3.data.source.local.database.dao.ProfilDao

@Database(
    entities = [
        ItemToDo::class,
        ListeToDo::class,
        ProfilListeToDo::class
    ],
    version = 1
)
abstract class ToDoDatabase: RoomDatabase() {

    abstract fun itemDao(): ItemDao
    abstract fun listDao(): ListDao
    abstract fun profilDao(): ProfilDao

}