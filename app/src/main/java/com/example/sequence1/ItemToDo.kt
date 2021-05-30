package com.example.sequence1



class ItemToDo(var description: String = "", var fait: Boolean = false) {

@JvmName("setDescription1")
fun setDescription(description: String){
    this.description = description
}

@JvmName("getDescription1")
fun getDescription(): String{
    return this.description
}

@JvmName("setFait1")
fun setFait(fait: Boolean){
    this.fait = fait
}

@JvmName("getFait1")
fun getFait(): Boolean{
    return this.fait
}
override fun toString(): String {
    //TODO
    return "{\"description\": \"${description}\", \"fait\": ${fait.toString()}}"
}


}