package com.example.sequence3.data.source.local

import android.app.Application
import androidx.room.Room
import com.example.sequence3.data.model.ItemToDo
import com.example.sequence3.data.model.ListeToDo
import com.example.sequence3.data.model.ProfilListeToDo
import com.example.sequence3.data.source.local.database.sequ3Database

class LocalDataSource (
    application: Application
){
    private val roomDatabase =
        Room.databaseBuilder(application, sequ3Database::class.java, "room-database").build()

    private val itemDao = roomDatabase.itemDao()
    private val listDao = roomDatabase.listDao()
    private val profilDao = roomDatabase.profilListDao()

    suspend fun getItems() = itemDao.getItems()
    suspend fun saveOrUpdateItems(items: MutableList<ItemToDo>) = itemDao.saveOrUpdate(items)

    suspend fun getLists() = listDao.getLists()
    suspend fun saveOrUpdateLists(lists: MutableList<ListeToDo>) = listDao.saveOrUpdate(lists)

    suspend fun authenticate(login: String, pass: String) = profilDao.authenticate(login, pass)
    suspend fun saveOrUpdateProfilList(profilListToDo: ProfilListeToDo) = profilDao.saveOrUpdateProfilListToDo(profilListToDo)

}