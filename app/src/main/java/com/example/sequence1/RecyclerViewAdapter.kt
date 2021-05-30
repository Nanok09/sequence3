package com.example.sequence1


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(private val dataSet: MutableList<String>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val parentLayout: RelativeLayout
        init {
            // Define click listener for the ViewHolder's View.
            textView = view.findViewById(R.id.list_item1)
            parentLayout = view.findViewById(R.id.parent_layout)
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
        // Get element from the dataset at this position, replace the contents with that element
        holder.textView.text = dataSet[position]

        //onclicklistener...
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}