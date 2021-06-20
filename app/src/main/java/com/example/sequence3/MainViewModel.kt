package com.example.sequence3

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.sequence3.data.sequ3Repository
import com.example.sequence3.data.model.ItemToDo
import com.example.sequence3.data.model.ListeToDo
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {


    private val repository by lazy { sequ3Repository.newInstance(application) }

    val items = MutableLiveData<ViewState>()
    val lists = MutableLiveData<ViewState>()
    val hash = MutableLiveData<ViewState>()



    fun loadItems(idList: Int, hash: String) {

        viewModelScope.launch{
            items.value = ViewState.Loading
            try{
                items.value = ViewState.Item(items = repository.getItems(idList, hash))
            } catch (e: Exception){
                items.value = ViewState.Error(e.message.orEmpty())
            }
        }
    }


    fun loadLists(hash: String) {

        viewModelScope.launch{
            lists.value = ViewState.Loading
            try{
                items.value = ViewState.List(lists = repository.getLists(hash = hash))
            } catch (e: Exception){
                items.value = ViewState.Error(e.message.orEmpty())
            }
        }
    }

    fun authenticate(login: String, pass: String){

        viewModelScope.launch {
        hash.value = ViewState.Loading
            try {
                hash.value = ViewState.Hash(hash = repository.authenticate(login, pass))
            } catch (e: Exception){
                hash.value = ViewState.Error(e.message.orEmpty())
            }
        }
    }




    sealed class ViewState{
        object Loading: ViewState()
        data class Item(val items: MutableList<ItemToDo>): ViewState()
        data class List<T>(val lists: kotlin.collections.List<ListeToDo>): ViewState()
        data class Hash(val hash: String): ViewState()
        data class Error(val message: String): ViewState()
    }









}