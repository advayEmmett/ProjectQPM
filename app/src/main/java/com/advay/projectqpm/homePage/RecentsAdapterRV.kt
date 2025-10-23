package com.advay.projectqpm.homePage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.advay.projectqpm.R

class RecentsAdapterRV(private val items: List<RecentFile>) :
    RecyclerView.Adapter<RecentsAdapterRV.RecentsViewHolder>() {
    class RecentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.fileName_RecentsRV)
        val date: TextView = itemView.findViewById(R.id.fileDate_RecentsRV)
        val time: TextView = itemView.findViewById(R.id.fileTime_RecentsRV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recents_homefr, parent, false)
        return RecentsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecentsViewHolder, position: Int) {
        val file = items[position]
        holder.name.text = file.name
        holder.date.text = file.date
        holder.time.text = file.time
    }

    override fun getItemCount() = items.size
}