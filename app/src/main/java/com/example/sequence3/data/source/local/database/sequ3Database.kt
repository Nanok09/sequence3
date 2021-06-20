package com.example.sequence3.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sequence3.data.model.ItemToDo
import com.example.sequence3.data.model.ListeToDo
import com.example.sequence3.data.model.ProfilListeToDo
import com.example.sequence3.data.source.local.database.dao.ItemToDoDao
import com.example.sequence3.data.source.local.database.dao.ListToDoDao
import com.example.sequence3.data.source.local.database.dao.ProfilListToDoDao

@Database(
    entities = [
        ItemToDo::class,
        ListeToDo::class,
        ProfilListeToDo::class
    ],
    version = 1
)
abstract class sequ3Database : RoomDatabase() {

    abstract fun itemDao(): ItemToDoDao
    abstract fun listDao(): ListToDoDao
    abstract fun profilListDao(): ProfilListToDoDao
}