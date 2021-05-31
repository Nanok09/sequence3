package com.example.sequence1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ShowListActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choix_list_layout)
        var dataSet: MutableList<String> = mutableListOf("todo1","todo1","todo1","todo1")
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        val adapter: RecyclerViewAdapter = RecyclerViewAdapter(dataSet)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }

}