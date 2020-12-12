package com.book.darts

import androidx.room.*

@Entity(tableName = "dart_table",
        foreignKeys = [ForeignKey(
                entity = Book::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("book_id"),
                onDelete = ForeignKey.CASCADE)])

data class Dart (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "book_id") val bookID: Int,
    @ColumnInfo(name = "page") val page: Int,
    @ColumnInfo(name = "row") val row: Int,
    @ColumnInfo(name = "memo") val memo: String,
)

class BookWithDarts {
    @Embedded
    lateinit var book: Book
    @Relation(parentColumn = "id", entityColumn = "book_id")
    lateinit var darts: List<Dart>
}