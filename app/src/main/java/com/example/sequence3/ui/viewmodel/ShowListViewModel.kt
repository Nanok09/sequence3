package com.example.sequence3.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.sequence3.data.ToDoRepository
import com.example.sequence3.data.model.ItemToDo
import kotlinx.coroutines.launch
import java.lang.Exception

class ShowListViewModel(application: Application): AndroidViewModel(application) {

    private val toDoRepository by lazy { ToDoRepository.newInstance(application)}

    val items = MutableLiveData<ViewState>()

    fun getItems(idList: Int, hash: String){
        viewModelScope.launch {
            items.value = ViewState.Loading
            try {
                items.value = ViewState.Content(items = toDoRepository.getItems(idList, hash))
                Log.d("EDPMR", "Items.value: ${items.value}")
            } catch (e:Exception){
                items.value = ViewState.Error(e.message.orEmpty())
            }
        }
    }

    fun updateItem(idList: Int, hash: String, idItem: Int, fait: Int) {
        viewModelScope.launch {
            try {
                toDoRepository.updateItem(idList, hash, idItem, fait)
                Log.d("EDPMR", "Items.value: ${items.value}")
            } catch (e:Exception){
                items.value = ViewState.Error(e.message.orEmpty())
            }
        }
    }


    sealed class ViewState{
        object Loading: ViewState()
        data class Content(val items: MutableList<ItemToDo>): ViewState()
        data class Error(val message: String): ViewState()
    }


}