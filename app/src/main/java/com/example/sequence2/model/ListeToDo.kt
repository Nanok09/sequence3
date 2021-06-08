package com.example.sequence2.model

import com.google.gson.annotations.SerializedName

data class ListeToDo (
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("label")
    var titreListToDo: String = "",
    var lesItems: MutableList<ItemToDo> = mutableListOf<ItemToDo>(ItemToDo())

) {

@JvmName("setTitreListToDo1")
fun setTitreListToDo(titre: String){
    this.titreListToDo = titre
}

@JvmName("getTitreListToDo1")
fun getTitreListToDo(): String{
    return this.titreListToDo
}

@JvmName("setLesItems1")
fun setLesItems(lesItems: MutableList<ItemToDo>){
    this.lesItems = lesItems
}

@JvmName("getLesItems1")
fun getLesItems(): MutableList<ItemToDo>{
    return this.lesItems
}


fun rechercherItem(descriptionItem: String): String {

    return ""
}

fun printList(itemList: MutableList<ItemToDo>): String{

    var returnString: String = "["
    itemList.forEach {
        returnString.plus("{'description': ${it.description}, 'fait': ${it.fait.toString()}}")
    }
    returnString.plus("]")
    return returnString

}
override fun toString(): String {
    //TODO
    return "{\"id\": \"${id}\", \"titreListToDo\": \"${titreListToDo}\", \"lesItems\": ${lesItems}}"

}

}