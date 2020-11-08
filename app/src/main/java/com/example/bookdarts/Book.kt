package com.example.bookdarts

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_table")

class Book(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "created_at") val createdAt: Long,
    @ColumnInfo(name = "image_url_raw_value") val imageURLRawValue: String,
)