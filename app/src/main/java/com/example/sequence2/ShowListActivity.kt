package com.example.sequence2


import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
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
import com.example.sequence2.adapter.ShowListRecyclerViewAdapter
import com.example.sequence2.api.Provider
import com.example.sequence2.model.ItemToDo
import com.example.sequence2.model.ListeToDo
import com.example.sequence2.model.ProfilListeToDo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import java.lang.Exception

class ShowListActivity: AppCompatActivity(), View.OnClickListener {

    private val activityScope = CoroutineScope(
        SupervisorJob() +
                Dispatchers.Main
    )

    //SharedPreferences
    private var sp: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private var hash: String? = null


    //Initialize UI elements
    private var btnCreateItem: Button? = null
    private var edtCreateItem: EditText? = null

    //Received bundle extras
    private var id: Int? = null
    private var position: Int? = null

    //List of displayed elements
    private var itemList : MutableList<ItemToDo>? = null


    //Recyclerview adapter
    private var adapter: ShowListRecyclerViewAdapter? = null


    //Helper function displays a toast and prints a log
    private fun alerter(s: String?) {
        if (s != null) {
            Log.i(ChoixListActivity.CAT, s)
        }
        val t = Toast.makeText(this, s, Toast.LENGTH_SHORT)
        t.show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(ChoixListActivity.CAT, "onCreate")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.showlist_layout)

        //Get UI elements
        btnCreateItem = findViewById(R.id.btnItem)
        edtCreateItem = findViewById(R.id.edtItem)

        //Set Listeners
        btnCreateItem!!.setOnClickListener(this)
        edtCreateItem!!.setOnClickListener(this)



        sp = PreferenceManager.getDefaultSharedPreferences(this)
        val smartCastSp = sp
        if (smartCastSp != null){
            editor = smartCastSp.edit()
        }


        val bdl = this.intent.extras

        id = bdl?.getInt("id") //pseudo
        position = bdl?.getInt("position")

        alerter(id.toString())
        alerter(position.toString())
        hash = sp!!.getString("hash", "")

        //Get items list via HTTP request
        itemList  = getToDoItems(id!!, hash!!)




        adapter = ShowListRecyclerViewAdapter(itemList!!, this)





        //Recyclerview
        val showlistRecyclerView: RecyclerView = findViewById(R.id.showlist_recyclerview)
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
                alerter("click bouton btnItem")
                val description = edtCreateItem!!.text.toString()

                addItem(id!!, hash!!, description, itemList!!)
            }

        }

    }

    private fun getToDoItems(id: Int, hash: String): MutableList<ItemToDo> {
        return runBlocking {
            try {
                if (verifReseau()){
                    val getItemsResp = Provider.getItems(id, hash)
                    Log.i(ChoixListActivity.CAT, getItemsResp.toString())


                    if (getItemsResp.success){
                        return@runBlocking getItemsResp.items
                    } else{
                        Log.i(ChoixListActivity.CAT, "Erreur de recuperation des items")
                    }
                } else {
                    Log.i(ChoixListActivity.CAT, "pas de connexion")
                }
            } catch (e: Exception){
                Log.i(ChoixListActivity.CAT, "Erreur: " + e.message)
            }
            return@runBlocking mutableListOf<ItemToDo>()
        }

    }

    private fun addItem(id: Int, hash: String, description: String, todolist: MutableList<ItemToDo>){
        Log.i(ChoixListActivity.CAT, "id $id hash $hash description $description todolist $todolist")

        activityScope.launch {
            try {
                if (verifReseau()){
                    val addItemsResp = Provider.addItem(id, hash, description)
                    Log.i(ChoixListActivity.CAT, addItemsResp.toString())

                    if (addItemsResp.success){
                        Log.i(ChoixListActivity.CAT, "Success")


                        val item: ItemToDo = addItemsResp.item
                        Log.i(ChoixListActivity.CAT, item.toString())


                        adapter!!.itemList.add(item)
                        adapter!!.notifyDataSetChanged()






                    } else{
                        Log.i(ChoixListActivity.CAT, "Erreur de l ajout de l item")
                    }
                }else{
                    Log.i(ChoixListActivity.CAT, "Pas de connexion")
                }
            } catch (e: Exception){
                Log.i(ChoixListActivity.CAT, "Erreur: ${e.message}")
            }
        }
    }

    fun verifReseau(): Boolean {
        // On vérifie si le réseau est disponible,
        // si oui on change le statut du bouton de connexion
        val cnMngr = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cnMngr.activeNetworkInfo
        var sType = "Aucun réseau détecté"
        var bStatut = false
        if (netInfo != null) {
            val netState = netInfo.state
            if (netState.compareTo(NetworkInfo.State.CONNECTED) == 0) {
                bStatut = true
                val netType = netInfo.type
                when (netType) {
                    ConnectivityManager.TYPE_MOBILE -> sType = "Réseau mobile détecté"
                    ConnectivityManager.TYPE_WIFI -> sType = "Réseau wifi détecté"
                }
            }
        }
        Log.i(ChoixListActivity.CAT, sType)
        return bStatut
    }
}