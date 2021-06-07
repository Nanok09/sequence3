package com.example.sequence2

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sequence2.adapter.RecyclerViewAdapter
import com.example.sequence2.model.ListeToDo
import com.example.sequence2.model.ProfilListeToDo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class ChoixListActivity : AppCompatActivity(), View.OnClickListener {
    private var sp: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private var btnCreateTodo: Button? = null
    private var edtCreateToDo: EditText? = null



//    private var list2ProfilListJson = """[{"login": "Macud", "mesListeToDo": [{"titreListToDo": "todolist1macud", "lesItems": [{"description": "", "fait": false}]}, {"titreListToDo": "todolist2macud", "lesItems": [{"description": "Trop cool", "fait": true}, {"description": "Moins bien", "fait": true}]}]}, {"login": "Nanok", "mesListeToDo": [{"titreListToDo": "todolist1nanok", "lesItems": [{"description": "", "fait": false}]}, {"titreListToDo": "todolist2", "lesItems": [{"description": "Trop cool", "fait": true}, {"description": "Moins bien", "fait": true}]}]}, {"login": "Paul", "mesListeToDo": [{"titreListToDo": "todolist1paul", "lesItems": [{"description": "", "fait": true}]}, {"titreListToDo": "todolist2", "lesItems": [{"description": "Trop cool", "fait": false}, {"description": "Moins bien", "fait": true}]}]}]"""
    private var list2ProfilListJson : String? = null

    private val list2profillisttype = object : TypeToken<MutableList<ProfilListeToDo>>() {}.type
    private var profilList : ProfilListeToDo? = null

    private var listDeProfilList : MutableList<ProfilListeToDo>? = null
    private var dataSet: MutableList<String>? = null








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


        list2ProfilListJson = sp!!.getString("profilList", "[]")

        profilList  = ProfilListeToDo()

        listDeProfilList = Gson().fromJson(list2ProfilListJson, list2profillisttype)
        dataSet = mutableListOf()

        //list2ProfilListJson = sp!!.getString("profilList", "no profil")





        var compteur = 0
        listDeProfilList?.forEach {
            if (it.login == pseudo){
                //On prend les liste de todolist de la personne connectée
                profilList = it
                compteur++
            }
        }
        if (compteur == 0){
            //Si jamais la personne connectée n'a pas encore de liste de todolist on la crée
            listDeProfilList?.add(ProfilListeToDo(pseudo!!))
            profilList = listDeProfilList?.last()
        }


        //On crée le dataset
        profilList!!.mesListeToDo.forEach{
            dataSet!!.add(it.titreListToDo)
        }






        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        val adapter: RecyclerViewAdapter = RecyclerViewAdapter(profilList!!, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)


        edtCreateToDo = findViewById(R.id.edtCreateToDo)
        btnCreateTodo = findViewById(R.id.btnCreateToDo)
        btnCreateTodo!!.setOnClickListener(this)


    }

    private fun alerter(s: String?) {
        if (s != null) {
            Log.i(CAT, s)
        }
        val t = Toast.makeText(this, s, Toast.LENGTH_SHORT)
        t.show()
    }

    companion object {
        val CAT: String = "EDPMR"
    }


    override fun onStart(){
        super.onStart()
        Log.i(CAT, "onStart")
        val profilListJson = sp!!.getString("profilList", "")

    }

    override fun onClick(v: View) {
        when (v.id){
            R.id.btnCreateToDo -> {
                val bdl = this.intent.extras
                val pseudo = bdl?.getString("string") //pseudo

                list2ProfilListJson = sp!!.getString("profilList", "[]")

                profilList  = ProfilListeToDo()

                listDeProfilList = Gson().fromJson(list2ProfilListJson, list2profillisttype)
                dataSet = mutableListOf()


                var compteur = 0
                listDeProfilList?.forEach {
                    if (it.login == pseudo){
                        //On prend les liste de todolist de la personne connectée
                        profilList = it
                        compteur++
                    }
                }
                if (compteur == 0){
                    //Si jamais la personne connectée n'a pas encore de liste de todolist on la crée
                    listDeProfilList?.add(ProfilListeToDo(pseudo!!))
                    profilList = listDeProfilList?.last()
                }


                //On crée le dataset
                profilList!!.mesListeToDo.forEach{
                    dataSet!!.add(it.titreListToDo)
                }


                val addedToDo = ListeToDo(edtCreateToDo!!.text.toString())
                profilList!!.ajouteList(addedToDo)
                listDeProfilList?.add(profilList!!)
                dataSet!!.add(profilList!!.mesListeToDo.last().titreListToDo)

                var indexAModifier = 0

                for ((index, value) in listDeProfilList!!.withIndex()){
                    if(value.login == pseudo){
                        indexAModifier = index
                    }
                }

                listDeProfilList?.set(indexAModifier, profilList!!)

                editor!!.putString("profilList", listDeProfilList.toString())
                editor!!.commit()




                val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
                val adapter: RecyclerViewAdapter = RecyclerViewAdapter(profilList!!, this)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)
            }
        }
    }


}
