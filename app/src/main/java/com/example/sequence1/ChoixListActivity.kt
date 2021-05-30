package com.example.sequence1

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class ChoixListActivity : AppCompatActivity() {
    private var sp: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    var item1 : ItemToDo = ItemToDo("Trop cool", true)
    var item2 : ItemToDo = ItemToDo("Moins bien", true)
    var liste1: ListeToDo = ListeToDo("todolist1")
    var liste2: ListeToDo = ListeToDo("todolist2", mutableListOf(item1, item2))
//

    var profilListe : ProfilListeToDo = ProfilListeToDo("Nanok", mutableListOf(liste1, liste2))







    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choix_list_layout)




        sp = PreferenceManager.getDefaultSharedPreferences(this)
        val smartCastSp = sp
        if (smartCastSp != null){
            editor = smartCastSp.edit()
        }


        val bdl = this.intent.extras
        val pseudo = bdl?.getString("string") //pseudo


        var list2ProfilListJson = """[{"login": "Macud", "mesListeToDo": [{"titreListToDo": "todolist1macud", "lesItems": [{"description": "", "fait": false}]}, {"titreListToDo": "todolist2macud", "lesItems": [{"description": "Trop cool", "fait": true}, {"description": "Moins bien", "fait": true}]}]}, {"login": "Nanok", "mesListeToDo": [{"titreListToDo": "todolist1nanok", "lesItems": [{"description": "", "fait": false}]}, {"titreListToDo": "todolist2", "lesItems": [{"description": "Trop cool", "fait": true}, {"description": "Moins bien", "fait": true}]}]}, {"login": "Paul", "mesListeToDo": [{"titreListToDo": "todolist1paul", "lesItems": [{"description": "", "fait": true}]}, {"titreListToDo": "todolist2", "lesItems": [{"description": "Trop cool", "fait": false}, {"description": "Moins bien", "fait": true}]}]}]"""
//        var list2ProfilListJson = sp!!.getString("profilList", "no profil")


        val list2profillisttype = object : TypeToken<MutableList<ProfilListeToDo>>() {}.type
        var listDeProfilList : MutableList<ProfilListeToDo> = Gson().fromJson(list2ProfilListJson, list2profillisttype)


        var listDeProfilListJson = Gson().toJson(listDeProfilList)
        var profilList : ProfilListeToDo = ProfilListeToDo()
        var dataSet: MutableList<String> = mutableListOf()

        var compteur = 0
        listDeProfilList.forEach {
            if (it.login == pseudo){
                profilList = it
                compteur++
            }
        }
        if (compteur == 0){
            listDeProfilList.add(ProfilListeToDo(pseudo!!))
            profilList = listDeProfilList.last()
        }

        profilList.mesListeToDo.forEach{
            dataSet.add(it.titreListToDo)
        }






        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        val adapter: RecyclerViewAdapter = RecyclerViewAdapter(dataSet)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)



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


    override fun onStart(){
        super.onStart()
        Log.i(CAT, "onStart")
        val profilListJson = sp!!.getString("profilList", "")

    }


}
