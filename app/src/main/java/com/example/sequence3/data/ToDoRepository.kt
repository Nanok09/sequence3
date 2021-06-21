package com.example.sequence3.data

import android.app.Application
import com.example.sequence3.data.model.ItemToDo
import com.example.sequence3.data.source.local.LocalDataSource
import com.example.sequence3.data.source.remote.RemoteDataSource
import com.example.sequence3.data.source.remote.api.responses.ItemResponse
import java.lang.Exception

class ToDoRepository (
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
)

{

    suspend fun getItems(idList: Int, hash: String): List<ItemToDo> {
        if (hash == null) return localDataSource.getItems(idList)
        val toDoList = localDataSource.getList(idList)
        return try {
            remoteDataSource.getItems(idList, hash).also {
                var items = mutableListOf<ItemToDo>()
                for (item_response: ItemResponse in it.items) {
                    var item = localDataSource.getItem(item_response.id)
                    if(item !== null){

                        item.description = item_response.description
                        item.fait = item_response.fait
                    } else {
                        item = ItemToDo(
                            id = item_response.id,
                            description = item_response.description,
                            fait = item_response.fait,
                            idList = toDoList.id,
                            isUpdated = 1
                        )
                    }
                    items.add(item)
                }
                localDataSource.saveOrUpdateItems(items)
            }
            localDataSource.getItems(toDoList.id)
        } catch (e: Exception) {
            localDataSource.getItems(toDoList.id)
        }
    }


    companion object {
        fun newInstance(application: Application): ToDoRepository{
            return ToDoRepository(
                localDataSource = LocalDataSource(application),
                remoteDataSource = RemoteDataSource()
            )
        }
    }
}