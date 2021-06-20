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
import com.example.sequence3.adapter.RecyclerViewAdapter
import com.example.sequence3.data.source.remote.api.Provider
import com.example.sequence3.data.model.ListeToDo
import com.example.sequence3.data.model.ProfilListeToDo
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.runBlocking
import java.lang.Exception


class ChoixListActivity : AppCompatActivity(), View.OnClickListener {
    private var sp: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private var btnCreateTodo: Button? = null
    private var edtCreateToDo: EditText? = null



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
        val hash = sp!!.getString("hash", "") //hash




        var toDolists = getToDoLists(hash!!)
        Log.i(CAT, toDolists.toString())








        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        val adapter: RecyclerViewAdapter = RecyclerViewAdapter(toDolists, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)


        edtCreateToDo = findViewById(R.id.edtCreateToDo)
        btnCreateTodo = findViewById(R.id.btnCreateToDo)
        btnCreateTodo!!.setOnClickListener(this)


    }

    private fun getToDoLists(hash: String): MutableList<ListeToDo> {
        return runBlocking {
            try {
                if (verifReseau()){
                    val getListsResp = Provider.getLists(hash)
                    Log.i(CAT, getListsResp.toString())
                    if (getListsResp.success){
                        return@runBlocking getListsResp.lists
                    } else{
                        Log.i(CAT, "Erreur de recuperation de liste")
                    }
                } else {
                    Log.i(CAT, "pas de connexion")
                }
            } catch (e: Exception){
                Log.i(CAT, "Erreur: " + e.message)
            }
            return@runBlocking mutableListOf<ListeToDo>()
        }

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
        Log.i(CAT, sType)
        return bStatut
    }

}
