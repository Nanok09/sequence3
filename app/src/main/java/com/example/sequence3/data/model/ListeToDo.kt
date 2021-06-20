package com.example.sequence3.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "listeToDo_table")
data class ListeToDo (
    @PrimaryKey(autoGenerate = true)
    var id_local : Int? = null,
    @SerializedName("id")
    var id_remote: Int? = null,
    @SerializedName("label")
    var titreListToDo: String = "",
    @ColumnInfo(name = "profil_to_do_login")
    var profilToDoLogin: String?,
    var isUpdated: Int? = 0
) {
override fun toString(): String {

    return "ListeToDo(titreListToDo=\"$titreListToDo\")"

}

}