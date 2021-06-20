package com.example.sequence3.data.model

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class ListeToDo (


    @PrimaryKey
    var id: Int = 0,
    @SerializedName("label")
    var titreListToDo: String = "",

) {


override fun toString(): String {
    return "{\"id\": \"${id}\", \"titreListToDo\": \"${titreListToDo}\"}"

}

}