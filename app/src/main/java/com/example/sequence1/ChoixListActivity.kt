package com.example.sequence1

import android.content.ClipData
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson

class ChoixListActivity : AppCompatActivity(), View.OnClickListener {

    var item1 : ItemToDo = ItemToDo("Trop cool", true)
    var item2 : ItemToDo = ItemToDo("Moins bien", true)
    var liste1: ListeToDo = ListeToDo("todolist1")
    var liste2: ListeToDo = ListeToDo("todolist2", mutableListOf(item1, item2))

    var profilListe : ProfilListeToDo = ProfilListeToDo("Macud", mutableListOf(liste1, liste2))

    val profil2 = Gson().fromJson(profilListe.toString(), ProfilListeToDo::class.java)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choix_list_layout)
        val bdl = this.intent.extras
        val chaine_carac = bdl?.getString("string") //pseudo


        val btnDisp: Button? = findViewById(R.id.displayStuff)
        btnDisp?.setOnClickListener(this)

    }

    private fun alerter(s: String?) {
        if (s != null) {
            Log.i(CAT, s)
        }
        val t = Toast.makeText(this, s, Toast.LENGTH_SHORT)
        t.show()
    }

    companion object {
        private val CAT: String = "EDPMR"
    }

    override fun onClick(v: View) {
        when (v.id){
            R.id.displayStuff -> {
                alerter(profil2.getLogin())
            }
        }
    }


}
