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
    suspend fun saveOrUpdateItem(item: ItemToDo) = itemDao.saveOrUpdateItem(item)
    suspend fun getItem(idItem: Int): ItemToDo? = itemDao.getItem(idItem)
    suspend fun getItems(idList: Int) = itemDao.getItems(idList)
    suspend fun getNotUpdatedItems(): MutableList<ItemToDo> = itemDao.getNotUpdatedItem()


        suspend fun saveOrUpdateLists(lists: MutableList<ListeToDo>) = listDao.saveOrUpdateLists(lists)
    suspend fun getList(idList: Int): ListeToDo = listDao.getList(idList)
    suspend fun getLists(): MutableList<ListeToDo> = listDao.getLists()
    suspend fun getLists(login: String): MutableList<ListeToDo> = listDao.getLists(login)


    suspend fun saveOrUpdateProfil(profil: ProfilListeToDo) = profilDao.saveOrUpdateProfil(profil)
    suspend fun authenticate(login: String, pass: String) = profilDao.authenticate(login, pass)
    suspend fun getProfils() = profilDao.getProfils()

    }

