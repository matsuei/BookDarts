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
import java.util.*

class NewBookActivity : AppCompatActivity() {
    private lateinit var inputTitleView: EditText
    private lateinit var inputAuthorView: EditText
    private lateinit var inputImageURLView: EditText

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_book)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        inputTitleView = findViewById(R.id.input_title)
        inputAuthorView = findViewById(R.id.input_author)
        inputImageURLView = findViewById(R.id.input_image_url)

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            when {
                TextUtils.isEmpty(inputTitleView.text) -> {
                    Toast.makeText(applicationContext, "タイトルの入力がありません", Toast.LENGTH_LONG).show()
                }
                TextUtils.isEmpty(inputAuthorView.text) -> {
                    Toast.makeText(applicationContext, "著者の入力がありません", Toast.LENGTH_LONG).show()
                }
                else -> {
                    val title = inputTitleView.text.toString()
                    val author = inputAuthorView.text.toString()
                    val imageURL = inputImageURLView.text.toString()
                    replyIntent.putExtra(EXTRA_TITLE, title)
                    replyIntent.putExtra(EXTRA_AUTHOR, author)
                    replyIntent.putExtra(EXTRA_IMAGE_URL, imageURL)
                    setResult(Activity.RESULT_OK, replyIntent)
                    finish()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home->{
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_TITLE = "com.example.android.booklistsql.TITLE"
        const val EXTRA_AUTHOR = "com.example.android.booklistsql.AUTHOR"
        const val EXTRA_IMAGE_URL = "com.example.android.booklistsql.IMAGE_URL"
    }
}