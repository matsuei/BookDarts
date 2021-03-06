package com.book.darts

import android.app.ActionBar
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog

class BookDartsListActivity : AppCompatActivity() {
    private lateinit var viewModel: DartsListViewModel
    private val newBookActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = DartsListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter.setOnItemClickListener(object : DartsListAdapter.OnItemClickListener {
            override fun onClick(view: View, dart: Dart) {
                val titleBuilder = StringBuilder()
                titleBuilder.append("ページ:").append(dart.page.toString()).append("   列:").append(dart.row.toString())
                val memo: String = if (dart.memo.isEmpty()) "メモなし" else dart.memo

                AlertDialog.Builder(this@BookDartsListActivity)
                    .setTitle(titleBuilder.toString())
                    .setMessage(memo)
                    .setPositiveButton("閉じる",
                        DialogInterface.OnClickListener { dialog, id ->
                            dialog.dismiss()
                        })
                    .show()
            }
        })

        val id: Int = intent.extras?.getInt(EXTRA_BOOK_ID, 0) ?: 0
        viewModel = ViewModelProvider(this, DartsListViewModelFactory(this.application, id)).get(DartsListViewModel::class.java)
        viewModel.dartsList.observe(this, { darts ->
            darts?.let { adapter.setDarts(it) }
        })

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@BookDartsListActivity, NewDartActivity::class.java)
            startActivityForResult(intent, newBookActivityRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newBookActivityRequestCode && resultCode == Activity.RESULT_OK) {
            val page = data?.getStringExtra(NewDartActivity.EXTRA_PAGE)?.toInt() ?: 0
            val row = data?.getStringExtra(NewDartActivity.EXTRA_ROW)?.toInt() ?: 0
            val memo = data?.getStringExtra(NewDartActivity.EXTRA_MEMO) ?: ""
            val dart = Dart(0, viewModel.bookId, page, row, memo)
            viewModel.insert(dart)
        } else {
            Toast.makeText(
                    applicationContext,
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home->{
                finish()
            }
            R.id.delete->{
                viewModel.deleteBook()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    companion object {
        const val EXTRA_BOOK_ID = "com.example.android.booklistsql.BOOK_ID"
    }
}