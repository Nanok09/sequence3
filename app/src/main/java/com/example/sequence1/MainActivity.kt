package com.example.sequence1

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val CAT = "EDPMR"
    private var edtPseudo: EditText? = null
    private var cbRemember: CheckBox? = null
    private var btnOK: Button? = null
    private var sp: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    private fun alerter(s: String) {
        Log.i(CAT, s)
        val t = Toast.makeText(this, s, Toast.LENGTH_SHORT)
        t.show()
    }

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(CAT, "onCreate")


        btnOK = findViewById(R.id.btnOK)
        edtPseudo = findViewById(R.id.pseudo)

        cbRemember = findViewById(R.id.cbRemember)
        sp = PreferenceManager.getDefaultSharedPreferences(this)
        val smartCastSp = sp
        if (smartCastSp != null){
            editor = smartCastSp.edit()
        }


        //Demande a MainActivity d'implémenter l'interface onClickListener
        val smartCastBtn = btnOK
        if(smartCastBtn != null){
            smartCastBtn.setOnClickListener(this)
        }
        val smartCastCheckBox = cbRemember
        if(smartCastCheckBox != null){
            smartCastCheckBox.setOnClickListener(this)
        }
    }

    // affiche le menu si la méthode renvoie vrai
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id){
            R.id.menu_settings -> {
                alerter("Menu : click sur préférences")
                val iGP = Intent(this, GestionPreferences::class.java)
                startActivity(iGP)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        Log.i(CAT, "onStart")

        // Relire les préférences partagées de l'application
        val cbR = sp!!.getBoolean("remember", false)

        //acutaliser l'état de la case á cocher
        cbRemember!!.isChecked = cbR

        //SI la case est cochée, on utilise les préférences pour définir le login
        if(cbRemember!!.isChecked){
            val l = sp!!.getString("login", "login inconnu")
            edtPseudo!!.setText(l)
        } else{
            // Sinon, le champ doit etre vide
            edtPseudo!!.setText("")
        }
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(CAT, "onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.i(CAT, "onResume")
    }

    override fun onClick(v: View){
        when (v.id){
            R.id.btnOK -> {
                alerter(edtPseudo!!.text.toString())

                //on enregistre le login dans les préférences
                if(cbRemember!!.isChecked){
                    editor!!.putString("login", edtPseudo!!.text.toString())
                    editor!!.commit()
                }

                // Fabrication d'un bundle de données
                val bdl = Bundle()
                bdl.putString("string", edtPseudo!!.text.toString())
                //Changer d'activité
                val versSecondAct: Intent
                // Intent explicite
                versSecondAct = Intent(this@MainActivity, ChoixListActivity::class.java)
                // Ajout d'un bundle a l'intent
                versSecondAct.putExtras(bdl)
                startActivity(versSecondAct)
            }

            R.id.cbRemember -> {
                alerter("click sur CB")

                //On clique sur la case : il faut mettre á jour les préférences
                editor!!.putBoolean("rembmer", cbRemember!!.isChecked)
                editor!!.commit()
                if(!cbRemember!!.isChecked){
                    // on supprime le login de préférences
                    editor!!.putString("login", "")
                    editor!!.commit()
                }
            }

            R.id.pseudo -> alerter("Veuillez entrer votre pseudo")

        }
    }


}

