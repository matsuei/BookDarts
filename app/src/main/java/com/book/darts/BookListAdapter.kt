package com.book.darts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load

class BookListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<BookListAdapter.WordViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var books = emptyList<Book>() // Cached copy of words
    lateinit var listener: OnItemClickListener

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleItemView: TextView = itemView.findViewById(R.id.mainTextView)
        val authorItemView: TextView = itemView.findViewById(R.id.subTextView)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = books[position]
        val titleBuilder = StringBuilder()
        titleBuilder.append(current.title.toString())
        holder.titleItemView.text = titleBuilder.toString()
        val authorBuilder = StringBuilder()
        authorBuilder.append(current.author.toString())
        holder.authorItemView.text = authorBuilder.toString()
        holder.itemView.setOnClickListener {
            listener.onClick(it, current)
        }
        val url = books[position].imageURLRawValue
        holder.imageView.load(url) {
            crossfade(true)
        }
    }

    internal fun setWords(words: List<Book>) {
        this.books = words
        notifyDataSetChanged()
    }

    override fun getItemCount() = books.size

    interface OnItemClickListener {
        fun onClick(view: View, book: Book)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}