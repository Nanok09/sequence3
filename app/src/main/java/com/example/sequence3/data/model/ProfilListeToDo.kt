package com.example.sequence3.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "profilListeToDo_table")
data class ProfilListeToDo (

    @PrimaryKey
    var login: String = "",
    var pass: String = "",
    val hash: String = "",

)

{



override fun toString(): String {
    //TODO
    return "{\"login\": \"${login}\"}"

}



}
