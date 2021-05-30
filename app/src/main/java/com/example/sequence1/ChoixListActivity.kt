package com.example.sequence1

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ChoixListActivity : AppCompatActivity() {

    var dataSet: MutableList<String> = mutableListOf("Liste 1","Liste 2", "Liste 3")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choix_list_layout)

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



}
