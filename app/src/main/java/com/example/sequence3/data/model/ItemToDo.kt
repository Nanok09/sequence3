package com.example.sequence3.data.model

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class ItemToDo(

    @PrimaryKey
    var id: Int = 0,
    @SerializedName("label")
    var description: String = "",
    var url: String = "",
    @SerializedName("checked")
    var fait: Int = 0,
    var idList: Int = 0,
    var isUpdated: Int = 0
) {



@JvmName("getFait1")
fun getFait(): Int{
    return this.fait
}
override fun toString(): String {
    return "{\"id\": \"${id}\", \"description\": \"${description}\",\"url\": \"${url}\", \"checked\": ${fait}}"
}


}