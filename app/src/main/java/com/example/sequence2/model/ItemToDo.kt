package com.example.sequence2.model

import com.google.gson.annotations.SerializedName


data class ItemToDo(
    @SerializedName("label")
    var description: String = "",
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("url")
    var url: String = "",
    @SerializedName("checked")
    var fait: Int = 0
) {

@JvmName("setDescription1")
fun setDescription(description: String){
    this.description = description
}

@JvmName("getDescription1")
fun getDescription(): String{
    return this.description
}


@JvmName("setFait1")
fun setFait(fait: Int){
    this.fait = fait
}

@JvmName("getFait1")
fun getFait(): Int{
    return this.fait
}
override fun toString(): String {
    //TODO
    return "{\"id\": \"${id}\", \"description\": \"${description}\",\"url\": \"${url}\", \"checked\": ${fait}}"
}


}