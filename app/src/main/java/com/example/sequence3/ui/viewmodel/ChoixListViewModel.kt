package com.example.sequence3.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.sequence3.data.ToDoRepository
import com.example.sequence3.data.model.ListeToDo
import kotlinx.coroutines.launch

class ChoixListViewModel(application: Application): AndroidViewModel(application) {


    private val toDoRepository by lazy { ToDoRepository.newInstance(application) }

    val lists = MutableLiveData<ViewState>()

    fun getToDoLists(login: String, hash: String){
        viewModelScope.launch {
            lists.value = ViewState.Loading
            try {
                lists.value = ViewState.Content(lists = toDoRepository.getLists(login, hash))
                Log.d("EDPMR", "Lists.value: ${lists.value}")
            } catch (e: Exception){
                lists.value = ViewState.Error(e.message.orEmpty())
            }
        }
    }


    sealed class ViewState {
        object Loading : ViewState()
        data class Content(val lists: MutableList<ListeToDo>) : ViewState()
        data class Error(val message: String) : ViewState()
    }

}