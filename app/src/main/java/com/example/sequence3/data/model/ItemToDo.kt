package com.example.sequence3.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "itemToDo_table")
data class ItemToDo(
    @PrimaryKey(autoGenerate = true)
    var id_local: Int? = null,
    @SerializedName("id")
    var id_remote: Int? = null,
    @SerializedName("label")
    var description: String = "",
    var url: String = "",
    @SerializedName("checked")
    var fait: Int = 0,
    @ColumnInfo(name = "liste_to_do_id")
    var listeToDoId: Int? = null,
    @ColumnInfo(name = "liste_to_do_local_id")
    var listeToDoLocalId: Int? = null,
    var isUpdated: Int? = 0

)


{



    override fun toString(): String {
        return "ItemToDo(description=\"$description\", fait=$fait"
    }


}