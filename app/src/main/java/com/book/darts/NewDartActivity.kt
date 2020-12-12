package com.book.darts

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class NewDartActivity : AppCompatActivity() {
    private lateinit var inputPageView: EditText
    private lateinit var inputRowView: EditText
    private lateinit var inputMemoLView: EditText

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_dart)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        inputPageView = findViewById(R.id.input_page)
        inputRowView = findViewById(R.id.input_row)
        inputMemoLView = findViewById(R.id.input_memo)

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            when {
                TextUtils.isEmpty(inputPageView.text) -> {
                    Toast.makeText(applicationContext, "ページ数の入力がありません", Toast.LENGTH_LONG).show()
                }
                TextUtils.isEmpty(inputRowView.text) -> {
                    Toast.makeText(applicationContext, "列数の入力がありません", Toast.LENGTH_LONG).show()
                }
                else -> {
                    val page = inputPageView.text.toString()
                    val row = inputRowView.text.toString()
                    val memo = inputMemoLView.text.toString()
                    replyIntent.putExtra(EXTRA_PAGE, page)
                    replyIntent.putExtra(EXTRA_ROW, row)
                    replyIntent.putExtra(EXTRA_MEMO, memo)
                    setResult(Activity.RESULT_OK, replyIntent)
                    finish()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_PAGE = "com.example.android.booklistsql.PAGE"
        const val EXTRA_ROW = "com.example.android.booklistsql.ROW"
        const val EXTRA_MEMO = "com.example.android.booklistsql.MEMO"
    }
}