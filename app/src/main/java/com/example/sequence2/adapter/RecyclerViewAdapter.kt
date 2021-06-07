package com.example.sequence2.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sequence2.model.ProfilListeToDo
import com.example.sequence2.R
import com.example.sequence2.ShowListActivity

class RecyclerViewAdapter(private val profilListeToDo: ProfilListeToDo, private val mContext: Context) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val parentLayout: RelativeLayout
        init {
            // Define click listener for the ViewHolder's View.
            textView = view.findViewById(R.id.list_item1)
            parentLayout = view.findViewById(R.id.parent_layout)
        }

        fun bind(s: String) {
            textView.text = s

        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.layout_listitems, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        when (holder) {
            is ViewHolder -> {
                holder.parentLayout.setOnClickListener {
                    val toShowListAct: Intent
                    toShowListAct = Intent(mContext, ShowListActivity::class.java)
                    toShowListAct.putExtra("pseudo", profilListeToDo.login)
                    toShowListAct.putExtra("position", position)
                    mContext.startActivity(toShowListAct)
                }
                // Get element from the dataset at this position, replace the contents with that element
                holder.bind(profilListeToDo.mesListeToDo[position].titreListToDo)
            }

        }
    }

    override fun getItemCount(): Int {
        return profilListeToDo.mesListeToDo.size
    }
}