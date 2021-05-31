package com.example.sequence1


import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ShowListActivity: AppCompatActivity(), View.OnClickListener {

    private var sp: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private var btnCreateItem: Button? = null
    private var edtCreateItem: EditText? = null



    //    private var list2ProfilListJson = """[{"login": "Macud", "mesListeToDo": [{"titreListToDo": "todolist1macud", "lesItems": [{"description": "", "fait": false}]}, {"titreListToDo": "todolist2macud", "lesItems": [{"description": "Trop cool", "fait": true}, {"description": "Moins bien", "fait": true}]}]}, {"login": "Nanok", "mesListeToDo": [{"titreListToDo": "todolist1nanok", "lesItems": [{"description": "", "fait": false}]}, {"titreListToDo": "todolist2", "lesItems": [{"description": "Trop cool", "fait": true}, {"description": "Moins bien", "fait": true}]}]}, {"login": "Paul", "mesListeToDo": [{"titreListToDo": "todolist1paul", "lesItems": [{"description": "", "fait": true}]}, {"titreListToDo": "todolist2", "lesItems": [{"description": "Trop cool", "fait": false}, {"description": "Moins bien", "fait": true}]}]}]"""
    private var list2ProfilListJson : String? = null

    private val list2profillisttype = object : TypeToken<MutableList<ProfilListeToDo>>() {}.type
    private var profilList : ProfilListeToDo? = null

    private var listDeProfilList : MutableList<ProfilListeToDo>? = null
    private var dataSet: ListeToDo? = null

    private var position: Int? = null
    private var pseudo: String? = null

    private fun alerter(s: String?) {
        if (s != null) {
            Log.i(ChoixListActivity.CAT, s)
        }
        val t = Toast.makeText(this, s, Toast.LENGTH_SHORT)
        t.show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.showlist_layout)

        btnCreateItem = findViewById(R.id.btnItem)
        edtCreateItem = findViewById(R.id.edtItem)

        btnCreateItem!!.setOnClickListener(this)
        edtCreateItem!!.setOnClickListener(this)

        sp = PreferenceManager.getDefaultSharedPreferences(this)
        val smartCastSp = sp
        if (smartCastSp != null){
            editor = smartCastSp.edit()
        }

        val bdl = this.intent.extras

        pseudo = bdl?.getString("pseudo") //pseudo
        position = bdl?.getInt("position")

        alerter(pseudo)
        alerter(position.toString())



        list2ProfilListJson = sp!!.getString("profilList", "[]")

        profilList  = ProfilListeToDo()

        listDeProfilList = Gson().fromJson(list2ProfilListJson, list2profillisttype)
        dataSet = null

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
        dataSet = profilList?.mesListeToDo?.get(position!!)








        val showlistRecyclerView: RecyclerView = findViewById(R.id.showlist_recyclerview)
        val adapter: ShowListRecyclerViewAdapter = ShowListRecyclerViewAdapter(dataSet!!, this)
        showlistRecyclerView.adapter = adapter
        showlistRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)



    }

    override fun onStart() {
        super.onStart()
        Log.i(ChoixListActivity.CAT, "onStart showlist")

    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.btnItem -> {

                alerter("click bouton btn")
                dataSet?.lesItems?.add(ItemToDo(edtCreateItem!!.text.toString(), false))

                profilList?.mesListeToDo?.set(position!!, dataSet!!)

                var indexAModifier = 0

                for ((index, value) in listDeProfilList!!.withIndex()){
                    if(value.login == pseudo){
                        indexAModifier = index
                    }
                }
                listDeProfilList?.set(indexAModifier, profilList!!)


                editor!!.putString("profilList", listDeProfilList.toString())
                editor!!.commit()

                val showlistRecyclerView: RecyclerView = findViewById(R.id.showlist_recyclerview)
                val adapter: ShowListRecyclerViewAdapter = ShowListRecyclerViewAdapter(dataSet!!, this)
                showlistRecyclerView.adapter = adapter
                showlistRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)
            }

        }

    }


}