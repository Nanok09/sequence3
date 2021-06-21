package com.example.sequence3.data.source.local

import android.app.Application
import androidx.room.Room
import com.example.sequence3.data.model.ItemToDo
import com.example.sequence3.data.model.ListeToDo
import com.example.sequence3.data.model.ProfilListeToDo
import com.example.sequence3.data.source.local.database.ToDoDatabase

class LocalDataSource(
    application: Application
) {

    private val roomDatabase =
        Room.databaseBuilder(application, ToDoDatabase::class.java, "room-database").build()


    private val itemDao = roomDatabase.itemDao()
    private val listDao = roomDatabase.listDao()
    private val profilDao = roomDatabase.profilDao()



    suspend fun saveOrUpdateItems(items: MutableList<ItemToDo>) = itemDao.saveOrUpdateItems(items)
    suspend fun getItem(idItem: Int): ItemToDo? = itemDao.getItem(idItem)
    suspend fun getItems(idList: Int) = itemDao.getItems(idList)

    suspend fun saveOrUpdateLists(lists: MutableList<ListeToDo>) = listDao.saveOrUpdateLists(lists)
    suspend fun getList(idList: Int) = listDao.getList(idList)
    suspend fun getLists() = listDao.getLists()


    suspend fun saveOrUpdateProfil(profil: ProfilListeToDo) = profilDao.saveOrUpdateProfil(profil)
    suspend fun getProfils() = profilDao.getProfils()

}