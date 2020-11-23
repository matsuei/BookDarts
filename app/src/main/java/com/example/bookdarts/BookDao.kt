package com.example.bookdarts

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BookDao {

    @Query("SELECT * from book_table ORDER BY created_at ASC")
    fun getBooksByCreatedAt(): LiveData<List<Book>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Book)

    @Query("DELETE FROM book_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM dart_table WHERE book_id = :id")
    fun getDartsList(id: Int): LiveData<List<Dart>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(dart: Dart)

    @Query("DELETE FROM dart_table")
    suspend fun deleteAllDarts()
}