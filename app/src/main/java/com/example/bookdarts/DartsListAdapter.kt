package com.example.bookdarts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DartsListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<DartsListAdapter.WordViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var darts = emptyList<Dart>()
    lateinit var listener: OnItemClickListener

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pageItemView: TextView = itemView.findViewById(R.id.mainTextView)
        val rowItemView: TextView = itemView.findViewById(R.id.subTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = darts[position]
        holder.pageItemView.text = current.page.toString()
        holder.rowItemView.text = current.row.toString()
        holder.itemView.setOnClickListener {
            listener.onClick(it, current)
        }
    }

    internal fun setDarts(darts: List<Dart>) {
        this.darts = darts
        notifyDataSetChanged()
    }

    override fun getItemCount() = darts.size

    interface OnItemClickListener {
        fun onClick(view: View, dart: Dart)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}