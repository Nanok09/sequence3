package com.example.sequence3.data.model

data class ProfilListeToDo (
    var login: String = "",
    var pass: String = ""
)

{




override fun toString(): String {
    return "{\"login\": \"${login}\"}"
}



}
