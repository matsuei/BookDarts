package com.example.bookdarts

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookdarts.BookDartsListActivity.Companion.EXTRA_BOOK_ID
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: BookViewModel
    private val newBookActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = BookListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter.setOnItemClickListener(object : BookListAdapter.OnItemClickListener {
            override fun onClick(view: View, book: Book) {
                val intent = Intent(this@MainActivity, BookDartsListActivity::class.java)
                intent.putExtra(EXTRA_BOOK_ID, book.id)
                startActivity(intent)
            }
        })

        viewModel = ViewModelProvider(this).get(BookViewModel::class.java)
        viewModel.allBooks.observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let { adapter.setWords(it) }
        })

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewBookActivity::class.java)
            startActivityForResult(intent, newBookActivityRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newBookActivityRequestCode && resultCode == Activity.RESULT_OK) {
            val title = data?.getStringExtra(NewBookActivity.EXTRA_TITLE) ?: "title"
            val author = data?.getStringExtra(NewBookActivity.EXTRA_AUTHOR) ?: "author"
            val imageURL = data?.getStringExtra(NewBookActivity.EXTRA_IMAGE_URL) ?: ""
            val book = Book(0, title, author, Date().time.toLong(), imageURL)
            viewModel.insert(book)
            Date().time.toLong()
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG).show()
        }
    }
}