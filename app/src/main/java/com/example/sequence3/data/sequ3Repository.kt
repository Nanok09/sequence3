package com.example.sequence3.data

import android.app.Application
import com.example.sequence3.data.model.ItemToDo
import com.example.sequence3.data.model.ListeToDo
import com.example.sequence3.data.model.ProfilListeToDo
import com.example.sequence3.data.source.local.LocalDataSource
import com.example.sequence3.data.source.remote.RemoteDataSource
import java.lang.Exception

class sequ3Repository (
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
){
    suspend fun getItems(id: Int, hash: String): MutableList<ItemToDo>{
        return try {
            remoteDataSource.getItems(id, hash).items.also {
                localDataSource.saveOrUpdateItems(it)
            }
        } catch (e: Exception){
            localDataSource.getItems()
        }
    }

    suspend fun getLists(hash: String): List<ListeToDo>{
        return try {
            remoteDataSource.getLists(hash).lists.also {
                localDataSource.saveOrUpdateLists(it)
            }
        } catch (e: Exception){
            localDataSource.getLists()
        }
    }

    suspend fun authenticate(login: String, pass: String): String{
        return try {
            remoteDataSource.authenticate(login, pass).hash.also {
                localDataSource.saveOrUpdateProfilList(ProfilListeToDo(login = login, pass = pass, hash = it))
            }
        } catch (e: Exception){
            localDataSource.authenticate(login, pass).toString()
        }
    }


    companion object {
        fun newInstance(application: Application): sequ3Repository {
            return sequ3Repository(
                localDataSource = LocalDataSource(application),
                remoteDataSource = RemoteDataSource()
            )
        }
    }


}