package com.example.sequence3


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
import com.example.sequence3.adapter.ShowListRecyclerViewAdapter
import com.example.sequence3.data.source.remote.api.Provider
import com.example.sequence3.data.model.ItemToDo
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
        adapter!!.onChange = { item: ItemToDo, fait: Boolean,  ->
            updateItem(id!!, hash!!, item.id_remote!!, if (fait) 1 else 0)
        }




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

                addItem(id!!, hash!!, description)
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

    private fun addItem(id: Int, hash: String, description: String){


        activityScope.launch {
            try {
                if (verifReseau()){
                    val addItemsResp = Provider.addItem(id, hash, description)
                    Log.i(ChoixListActivity.CAT, addItemsResp.toString())

                    if (addItemsResp.success){
                        Log.i(ChoixListActivity.CAT, "Success")

                        val itemResp = addItemsResp.item
                        val remoteId = itemResp.id




                        val item: ItemToDo = ItemToDo(
                            id_local = null,
                            id_remote = remoteId,
                            description = itemResp.label,
                            url = itemResp.url!!,
                            fait = itemResp.checked,
                            listeToDoId = null,
                            listeToDoLocalId = null,
                            isUpdated = 0
                        )
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

    private fun updateItem(idList: Int, hash: String, idItem: Int, fait: Int){

        activityScope.launch {
            try {
                if (verifReseau()){




                    val updateItemResp = Provider.updateItem(idList, hash, idItem, fait)
                    Log.i(ChoixListActivity.CAT, updateItemResp.toString())

                    if (updateItemResp.success){
                        Log.i(ChoixListActivity.CAT, "update item Success")




                        val itemResp = updateItemResp.item
                        val remoteId = itemResp.id



                        val item: ItemToDo = ItemToDo(
                            id_local = null,
                            id_remote = remoteId,
                            description = itemResp.label,
                            url = itemResp.url!!,
                            fait = itemResp.checked,
                            listeToDoId = idList,
                            listeToDoLocalId = null,
                            isUpdated = 0
                            )



                        Log.i(ChoixListActivity.CAT, item.toString())

                        for (item in adapter!!.itemList){
                            if (item.id_remote == idItem){
                                item.fait = fait
                            }
                        }

                        adapter!!.notifyDataSetChanged()






                    } else{
                        Log.i(ChoixListActivity.CAT, "Erreur de l ajout de l item")
                    }
                } else{
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