package com.example.sequence1

class ProfilListeToDo (var login: String = "", var mesListeToDo : MutableList<ListeToDo> = mutableListOf<ListeToDo>(ListeToDo(""))) {


@JvmName("getMesListeToDo1")
fun getMesListeToDo(): MutableList<ListeToDo> {
    return this.mesListeToDo
}

fun setMesListeToDO(mesListeToDo: MutableList<ListeToDo>){

    this.mesListeToDo = mesListeToDo
}

fun ajouteList(uneListe: ListeToDo){
    this.mesListeToDo.add(uneListe)
}

@JvmName("getLogin1")
fun getLogin(): String{
    return this.login
}

fun getLogin(unLogin: String){
    this.login = unLogin
}

override fun toString(): String {
    //TODO
    return "{\"login\": \"${login}\", \"mesListeToDo\": ${mesListeToDo}}"

}



}
